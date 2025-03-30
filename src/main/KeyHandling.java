package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandling implements KeyListener {
    GamePanel gp;

    public boolean upwards, downwards, leftwards, rightwards;
    //Debug
    boolean checkDrawTime = false;
    public KeyHandling (GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){upwards = true;}
        if (code == KeyEvent.VK_A){rightwards = true;}
        if (code == KeyEvent.VK_S){downwards = true;}
        if (code == KeyEvent.VK_D){leftwards = true;}
        if (code == KeyEvent.VK_P){
            if (gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }


        //Debug
        if (code == KeyEvent.VK_T){
            if (checkDrawTime == false){
            checkDrawTime = true;
        } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }

        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W){upwards = false;}
        if (code == KeyEvent.VK_A){rightwards = false;}
        if (code == KeyEvent.VK_S){downwards = false;}
        if (code == KeyEvent.VK_D){leftwards = false;}

    }

}
