package main;

import entity.Entity;
import entity.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public  final int screenWidth = tileSize * maxScreenCol; //Ширина
    public final int screenHeight = tileSize * maxScreenRow; //Высота

    // FPS
    final int FPS = 60;


    // Настройки карты
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;


    // Система
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Thread gameThread;

    // Сущности и объекты
    public Player player = new Player(this,keyH);
    public Entity obj[] = new Entity[10];
    public Entity npc[] = new Entity[10];
    public Entity  monster[] = new Entity[20];

    ArrayList<Entity> entityList = new ArrayList<>();

    // Игровое состояние
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;





    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame(){

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){
            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if (remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){

        if (gameState == playState){
            player.update();

            for (int i = 0; i < npc.length; i++){
                if(npc[i] !=null){
                    npc[i].update();
                }
            }
            for (int i = 0; i < monster.length; i++){
                if(monster[i] !=null){
                    monster[i].update();
                }
            }
        }
        if (gameState == pauseState){
            // ничего
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Отладка
        long drawStart = 0;
        if (keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }


        // Меню окно
        if (gameState == titleState){
            ui.draw(g2);
        }

        else {
            // Отрисовка тайлов
            tileM.draw(g2);

            // Добавление сущностей
            entityList.add(player);
            for (int i = 0 ; i  < npc.length; i++ ){
                if (npc[i] != null){
                    entityList.add(npc[i]);
                }
            }

            for (int i = 0 ; i  < obj.length; i++ ){
                if (obj[i] != null){
                    entityList.add(obj[i]);
                }
            }

            for (int i = 0 ; i  < monster.length; i++ ){
                if (monster[i] != null){
                    entityList.add(monster[i]);
                }
            }


            // Сортировка списка

            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldX, e2.worldY);
                    return result;
                }
            });

            // Отрисовка сущностей
            for (int i = 0; i < entityList.size(); i++){
                entityList.get(i).draw(g2);
            }

            // Очистка списка сущностей
            entityList.clear();



            // Интерфейс
            ui.draw(g2);
        }


        if (keyH.checkDrawTime == true){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Время отрисовки " + passed,10, 400);
            System.out.println("Время отрисовки " + passed);
        }


        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
}
