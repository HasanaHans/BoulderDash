package entity;

import main.GamePanel;
import main.KeyHandling;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{
    GamePanel gp;
    KeyHandling keyH;

    public final int screenX;
    public final int screenY;
    int hasKey = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;



    public Player(GamePanel gp, KeyHandling keyH) {
        this.gp = gp;
        this.keyH = keyH;
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        solidArea = new Rectangle();
        solidArea.x = 1;
        solidArea.y = 1;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 46;
        solidArea.height = 46;
        setDefaultValues();
        getPlayerImage();

    }
    public void setDefaultValues() {
        worldX = gp.tileSize*3;
        worldY = gp.tileSize*2;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){
        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2= ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));

        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void update() {

        if (moving == false) {
            if (keyH.upwards == true || keyH.downwards == true ||
                    keyH.leftwards == true || keyH.rightwards == true) {
                if (keyH.upwards == true) {
                    direction = "up";

                } else if (keyH.downwards) {
                    direction = "down";

                } else if (keyH.leftwards) {
                    direction = "right";

                } else if (keyH.rightwards) {
                    direction = "left";
                }

                moving = true;


                // Check Tile Collision
                collisionOn = false;
                gp.cChecker.checkTile(this);
                // Check Obj Collision
                int objIndex = gp.cChecker.checkObject(this, true);
                pickUpObject(objIndex);
            } else {
                standCounter++;
                if (standCounter == 20) {
                    spriteNum = 1;
                    standCounter = 0;

                }
            }

        }
        if (moving == true) {

            // If collision == false, player can move.

            if (collisionOn == false) {
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
            if (spriteCounter > 15) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
            pixelCounter += speed;
            if (pixelCounter == 48){
                moving = false;
                pixelCounter = 0;
            }
        }
    }










    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.obj[i].name;
            switch (objectName){
                case "Key":
                    hasKey ++;
                    gp.obj[i] = null;
                    System.out.println("key: "  + hasKey);
                break;
            }
        }

    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) { image = up1;}
                if (spriteNum == 2) { image = up2;}
                break;
            case "down":
                if (spriteNum == 1) { image = down1;}
                if (spriteNum == 2) { image = down2;}
                break;


            case "left":
                if (spriteNum == 1) { image = left1;}
                if (spriteNum == 2) { image = left2;}
                break;

            case "right":
                if (spriteNum == 1) { image = right1;}
                if (spriteNum == 2) { image = right2;}
                break;
        }
        g2.drawImage(image, screenX , screenY, gp.tileSize, gp.tileSize, null);
        g2.setColor(Color.RED);
        g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


    }
}
