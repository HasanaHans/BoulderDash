package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage keyImage;
    public int commandNUm = 0;

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 30);
        OBJ_Key key = new OBJ_Key(gp);
        keyImage = key.image;
    }
    public void draw(Graphics2D g2){

        this.g2 = g2;
        g2.setFont(arial_40);
        g2.setColor(Color.WHITE);
        // TITLE STATE
        if (gp.gameState == gp.titleState){
            drawTitleScreen();
        }


        if (gp.gameState == gp.playState){
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x = "  + gp.player.hasKey, 74 ,65);
        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();

        }
        
    }

    private void drawTitleScreen() {
        g2.setColor(new Color(0,0,0));
        g2.fillRect(0,0,gp.screenWidth, gp.screenHeight);

        // NAME
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96f));
        String text = "Boulder Dash";
        int x = getXForCenterText(text);
        int y = gp.tileSize*3;

        // SHADOW
        g2.setColor(Color.gray);
        g2.drawString(text,x+5,y+5);

        // MAIN COLOUR
        g2.setColor(Color.WHITE);
        g2.drawString(text, x,y);
        // PLAYER IMAGE

        x = gp.screenWidth / 2 - (gp.tileSize*2) /2   ;
        y += gp.tileSize*2;
        g2.drawImage(gp.player.down1, x,y,gp.tileSize*2,gp.tileSize*2, null);

        // MENU
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "NEW GAME";
        x = getXForCenterText(text);
        y += gp.tileSize*3.5;
        g2.drawString(text,x,y);
        if (commandNUm == 0){
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "LOAD GAME";
        x = getXForCenterText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNUm == 1){
            g2.drawString(">",x-gp.tileSize,y);
        }

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48f));
        text = "QUIT";
        x = getXForCenterText(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if (commandNUm == 2){
            g2.drawString(">",x-gp.tileSize,y);
        }





    }

    private void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXForCenterText(text);

        int y = gp.screenHeight / 2;
        g2.drawString(text,x,y);

    }

    public int getXForCenterText(String text){
        int length  =  (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth / 2 - length/2;
        return x;
    }

}
