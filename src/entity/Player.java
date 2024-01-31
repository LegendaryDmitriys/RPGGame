package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Enity {

    GamePanel gp;
    KeyHandler keyH;

    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        setDefaultValues();
        getPlayerImage();
    }

    public void getPlayerImage() {
        try {
            spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/Player.png"));

            int w = 64;
            int h = 64;

            up1 = spriteSheet.getSubimage(0, 512, w, h);
            up2 = spriteSheet.getSubimage(192, 512, w, h);

            down1 = spriteSheet.getSubimage(0, 640, w, h);
            down2 = spriteSheet.getSubimage(192,640, w, h);

            right1 = spriteSheet.getSubimage(0, 704, w, h);
            right2 = spriteSheet.getSubimage(192, 704, w, h);

            left1 = spriteSheet.getSubimage(0, 576, w, h);
            left2 = spriteSheet.getSubimage(192, 576, w, h);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setDefaultValues(){
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }
    public void update(){

        if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed){
                direction= "up";
                y -= speed;
            }
            else if(keyH.downPressed){
                direction= "down";
                y += speed;
            }
            else if(keyH.leftPressed){
                direction = "left";
                x -= speed;
            }
            else if (keyH.rightPressed){
                direction = "right";
                x += speed;
            }
            spriteCounter++;
            if (spriteCounter > 10){
                if (spriteNumber == 1){
                    spriteNumber = 2;
                }
                else if (spriteNumber == 2){
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch (direction){
            case "up":
                if (spriteNumber == 1){
                    image = up1;
                }
                if (spriteNumber == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNumber == 1){
                    image = down1;
                }
                if (spriteNumber == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNumber == 1){
                    image = left1;
                }
                if (spriteNumber == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1){
                    image = right1;
                }
                if (spriteNumber == 2){
                    image = right2;
                }
                break;
        }
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
