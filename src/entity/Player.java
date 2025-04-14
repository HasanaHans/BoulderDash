package entity;

import main.GamePanel;
import main.KeyHandling;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

 //   GamePanel gp;
    KeyHandling keyH;

    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    int standCounter = 0;
    boolean moving = false;
    int pixelCounter = 0;



    public Player(GamePanel gp, KeyHandling keyH) {
        super(gp);
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
        attackArea.width = 36;
        attackArea.height= 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();

    }
    public void setDefaultValues() {
        worldX = gp.tileSize*3;
        worldY = gp.tileSize*2;
        speed = 4;
        direction = "down";
    }
    public void getPlayerImage(){

        up1 = setup("boy_up_1");
        up2 = setup("boy_up_2");
        down1 = setup("boy_down_1");
        down2 = setup("boy_down_2");
        right1 = setup("boy_right_1");
        right2 = setup("boy_right_2");
        left1 = setup("boy_left_1");
        left2 = setup("boy_left_2");

    }
    public void getPlayerAttackImage(){
        attackUp1 = setupScaled("/player/boy_attack_up_1",gp.tileSize,gp.tileSize);
        attackUp2 = setupScaled("/player/boy_attack_up_2",gp.tileSize,gp.tileSize);
        attackDown1 = setupScaled("/player/boy_attack_down_1",gp.tileSize,gp.tileSize);
        attackDown2 = setupScaled("/player/boy_attack_down_2",gp.tileSize,gp.tileSize);
        attackLeft1 = setupScaled("/player/boy_attack_left_1",gp.tileSize,gp.tileSize);
        attackLeft2 = setupScaled("/player/boy_attack_left_2",gp.tileSize,gp.tileSize);
        attackRight1 = setupScaled("/player/boy_attack_right_1",gp.tileSize,gp.tileSize);
        attackRight2 = setupScaled("/player/boy_attack_right_2",gp.tileSize,gp.tileSize);

    }
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;

    }


    public void update() {

        if (moving == false) {
            if (attacking == true){
                attacking();



            }
            else if (keyH.enterPressed) {
                attacking = true;
                keyH.enterPressed = false; // Only trigger once
            }



            else if (keyH.upwards == true || keyH.downwards == true ||
                    keyH.leftwards == true || keyH.rightwards == true || keyH.enterPressed ) {
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

                gp.eHandler.checkEvent();



                //CHECK INTERACTIVE COLLISION
                int iTileIndex = gp.cChecker.checkEntity(this,gp.iTile);
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

            if (collisionOn == false && keyH.enterPressed == false) {
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
            gp.keyH.enterPressed = false;
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



    public void attacking(){
        spriteCounter++;
        if(standCounter <=5){
            spriteNum = 1;
        }
        if(spriteCounter > 5 && spriteCounter <25 ){
            spriteNum =2;
            int currentWorldX = worldX;
            int currentWorldY= worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;


            switch (direction){
                case "up": worldY -= attackArea.height; break;
                case "down": worldY += attackArea.height; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int iTileIndex = gp.cChecker.checkEntity(this, gp.iTile);
            damageInteractiveTile(iTileIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;


        }
        if (spriteCounter > 25 ){
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }
    }

    private void damageInteractiveTile(int i) {
        if ( i != 999 && gp.iTile[i].destructible == true){
            gp.iTile[i] = null;
        }
    }


    public void pickUpObject(int i){
        if (i != 999){
            String objectName = gp.obj[i].name;
            switch (objectName){
                case "Key":
                    gp.playSE(0);
                    hasKey ++;
                    gp.obj[i] = null;
                    System.out.println("key: "  + hasKey);
                break;
            }
        }

    }

    public void attack(int i){
        if(i != 999){
            if (gp.keyH.enterPressed == true){
                attacking = true;
            }
        }
    }

    public void draw(Graphics2D g2){
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up":
                if (attacking == false){
                    if (spriteNum == 1) { image = up1;}
                    if (spriteNum == 2) { image = up2;}
                }
                if (attacking == true){
                    tempScreenY = screenY + gp.tileSize;
                    if (spriteNum == 1) { image = attackUp1;}
                    if (spriteNum == 2) { image = attackUp2;}
                }
                break;

            case "down":
                if (attacking == false){
                    if (spriteNum == 1) { image = down1;}
                    if (spriteNum == 2) { image = down2;}
                }
                if (attacking == true){
                    if (spriteNum == 1) { image = attackDown1;}
                    if (spriteNum == 2) { image = attackDown2;}
                }

                break;


            case "left":
                if (attacking == false){
                    if (spriteNum == 1) { image = left1;}
                    if (spriteNum == 2) { image = left2;}
                }
                if (attacking == true){
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNum == 1) { image = attackLeft1;}
                    if (spriteNum == 2) { image = attackLeft2;}
                }

                break;

            case "right":
                if (attacking == false){
                    if (spriteNum == 1) { image = right1;}
                    if (spriteNum == 2) { image = right2;}
                }
                if (attacking == true){
                    if (spriteNum == 1) { image = attackRight1;}
                    if (spriteNum == 2) { image = attackRight2;}
                }

                break;
        }
        g2.drawImage(image, tempScreenX , tempScreenY,  null);
       // g2.setColor(Color.RED);
      //  g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);


    }

    public int getPixelCounter() {
        return pixelCounter;
    }
}
