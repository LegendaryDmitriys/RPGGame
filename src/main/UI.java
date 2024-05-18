package main;

import entity.Entity;
import object.OBJ_Heart;


import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    public String  message = "";
    int messageCounter = 0;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;


    public UI(GamePanel gp) {
        this.gp = gp;

        arial_40 = new Font("Arial", Font.PLAIN,40);

        // Создание объектов худа
        Entity heart = new OBJ_Heart(gp);
        heart_full  = heart.image;
        heart_half  = heart.image2;
        heart_blank = heart.image3;
    }

    public void showMessage(String text){
            message = text;
            messageOn = true;
    }

    public void draw(Graphics2D g2){

            this.g2 = g2;

            g2.setFont(arial_40);
            g2.setColor(Color.white);

            // Состояние меню
            if (gp.gameState == gp.titleState){
                drawTitleScreen();
            }


            // Состояние игры
            if (gp.gameState == gp.playState) {
                drawPlayerLife();

            }
            // Состояние паузы
            if (gp.gameState == gp.pauseState) {
                drawPlayerLife();
                drawPauseScreen();
            }
            // Состояние диалога
            if (gp.gameState == gp.dialogueState) {
                drawPlayerLife();
                drawDialogueScreen();
            }

    }
    public void  drawPlayerLife(){



        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        while (i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x,y, null);
            i++;
            x += gp.tileSize;
        }


        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while (i < gp.player.life){
            g2.drawImage(heart_half, x, y,null);
            i++;
            if (i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 76F));
            String text = "Realm of Legends";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 2;

            GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, 0, gp.screenHeight, Color.DARK_GRAY);
            g2.setPaint(gradient);
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            x = gp.screenWidth / 2 - (gp.tileSize * 2) / 2;
            y += gp.tileSize * 2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));

            text = "Новая игра";
            x = getXforCenteredText(text);
            y += gp.tileSize * 4;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Загрузить игру";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Выход";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        } else if (titleScreenState == 1) {
            // Настройки шрифта и текста для выбора класса
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42f));

            String text = "Выбор класса";
            int x = getXforCenteredText(text);
            int y = gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Лучник";
            x = getXforCenteredText(text);
            y += gp.tileSize * 3;
            g2.drawString(text, x, y);
            if (commandNum == 0) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Воин";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Танк";
            x = getXforCenteredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2) {
                g2.drawString(">", x - gp.tileSize, y);
            }

            text = "Назад";
            x = getXforCenteredText(text);
            y += gp.tileSize * 2;
            g2.drawString(text, x, y);
            if (commandNum == 3) {
                g2.drawString(">", x - gp.tileSize, y);
            }
        }
    }

    public void drawPauseScreen(){

        String text = "Пауза";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen(){

        // Окно
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);


        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,22F));
        x += gp.tileSize;
        y += gp.tileSize;

        for(String line : currentDialogue.split("\n")){
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawSubWindow(int x, int y, int width, int height){


        Color c = new Color(0,0,0);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);


    }

    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x =  gp.screenWidth/2 - length/2;
        return x;
    }



}
