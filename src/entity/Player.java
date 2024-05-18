package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {

    KeyHandler keyH;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;

    public BufferedImage[] up, down, left, right;

    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
    }

    public void getPlayerImage() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/Player.png"));

            int frameWidth = spriteSheet.getWidth() / 6;
            int frameHeight = spriteSheet.getHeight() / 10;

            up = new BufferedImage[3];
            down = new BufferedImage[3];
            left = new BufferedImage[3];
            right = new BufferedImage[3];


            up[0] = getSubImage(spriteSheet, 0, 5 * frameHeight, frameWidth, frameHeight);
            up[1] = getSubImage(spriteSheet, frameWidth, 5 * frameHeight, frameWidth, frameHeight);
            up[2] = getSubImage(spriteSheet, 2 * frameWidth, 5 * frameHeight, frameWidth, frameHeight);


            down[0] = getSubImage(spriteSheet, 0, 3 * frameHeight, frameWidth, frameHeight);
            down[1] = getSubImage(spriteSheet, frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            down[2] = getSubImage(spriteSheet, 2 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);

            left[0] = getSubImage(spriteSheet, 0, 2 * frameHeight, frameWidth, frameHeight);
            left[1] = getSubImage(spriteSheet, frameWidth, 2 * frameHeight, frameWidth, frameHeight);
            left[2] = getSubImage(spriteSheet, 2 * frameWidth, 2 * frameHeight, frameWidth, frameHeight);

            right[0] = getSubImage(spriteSheet, 0, 3 * frameHeight, frameWidth, frameHeight);
            right[1] = getSubImage(spriteSheet, frameWidth, 3 * frameHeight, frameWidth, frameHeight);
            right[2] = getSubImage(spriteSheet, 2 * frameWidth, 3 * frameHeight, frameWidth, frameHeight);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getPlayerAttackImage() {
        attackUp1= setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
        attackUp2= setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
        attackDown1= setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
        attackDown2= setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
        attackLeft1= setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
        attackLeft2= setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
        attackRight1= setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
        attackRight2= setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
    }

    private BufferedImage getSubImage(BufferedImage spriteSheet, int x, int y, int w, int h) {
        return spriteSheet.getSubimage(x, y, w, h);
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

        // Состояния игрока

        maxLife = 6;
        life = maxLife;
    }

    public void update() {

        if (attacking == true){
            attacking();
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed  || keyH.rightPressed) {
            if (keyH.upPressed ) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed ) {
                direction = "left";
            } else if (keyH.rightPressed ) {
                direction = "right";
            }

            // Проверка колизии тайлов
            collisionOn = false;
            gp.cCheck.checkTile(this);

            // Проверка столкнования колизий с объектом
            int objIndex = gp.cCheck.checkObject(this, true);
            pickUpObject(objIndex);

            // Проверка столкнования колизий с нпс
            int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // Проверка столкнования колизий с монстрами
            int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);


            // Проверка ивентов
            gp.eHandler.checkEvent();

            gp.keyH.enterPressed = false;


            // Если колизия == false, то игрок не может передвигаться дальше
            if (!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteNumber++;
                if (spriteNumber >= up.length) {
                    spriteNumber = 0;
                }
                spriteCounter = 0;
            }
        }

        if (invincible == true){
            invincibleCounter++;
            if (invincibleCounter > 60){
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    public void  attacking(){
        spriteCounter++;

        if (spriteCounter < -5){
            spriteCounter = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNumber = 2;

            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height;break;
                case "left": worldX -= attackArea.width;break;
                case "right": worldX += attackArea.width;break;

            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;


            int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);


            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;


        }
        if (spriteCounter > 25){
            spriteNumber = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }


    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;

            switch (objectName){
                case "Key":
                    hasKey++;
                    gp.obj[i] = null;
                    break;
                case "Door":
                    if (hasKey > 0){
                        gp.obj[i] = null;
                    }
                    break;
                case "Chest":
                    if (hasKey > 0){
                        gp.obj[i] = null;
                    }
            }
        }
    }

    public void interactNPC(int i){
        if (gp.keyH.enterPressed == true){
            if (i != 999){
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
            else {
                attacking = true;
            }
        }
    }

    public void contactMonster(int i){
        if (i != 999){
            if (invincible == false){
                life -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i){
        if (i != 999){
            if (gp.monster[i].invincible == false){
                gp.monster[i].life -= 1;
                gp.monster[i].invincible = true;

                if (gp.monster[i].life <= 0){
                    gp.monster[i] = null;
                }
            }
        }
    }


    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false){
                    image = up[spriteNumber];
                }
                if (attacking == true){
                    tempScreenY = screenY - gp.tileSize;
                    image = attackUp1;
                    image = attackUp2;
                }
                break;
            case "down":
                if (attacking == false){
                    image = down[spriteNumber];
                }
                if (attacking == true){
                    image = attackDown1;
                    image = attackDown1;
                }
                break;
            case "left":
                if (attacking == false){
                    image = left[spriteNumber];
                }
                if (attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    image = attackLeft1;
                    image = attackLeft2;
                }
                break;
            case "right":
                if (attacking == false){
                    image = right[spriteNumber];
                }
                if (attacking == true){
                    image = attackRight1;
                    image =  attackRight2;
                }
                break;
        }

        if (invincible == true){
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1F));
    }
}
