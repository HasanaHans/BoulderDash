package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    Font arial_40;
    BufferedImage keyImage;

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

        if (gp.gameState == gp.playState){
            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x = "  + gp.player.hasKey, 74 ,65);
        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();

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
