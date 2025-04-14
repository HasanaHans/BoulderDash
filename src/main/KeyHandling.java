package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandling implements KeyListener {
    GamePanel gp;

    public boolean upwards, downwards, leftwards, rightwards, enterPressed;
    //Debug
    boolean showDebugText = false;
    public KeyHandling (GamePanel gp){
        this.gp = gp;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //TITLE STATE
        if (gp.gameState == gp.titleState){
            if (code == KeyEvent.VK_W){
                gp.ui.commandNUm --;
                if (gp.ui.commandNUm < 0 ){
                    gp.ui.commandNUm = 2;
                }

            }
            if (code == KeyEvent.VK_S){
                gp.ui.commandNUm ++;
                if (gp.ui.commandNUm > 2 ){
                    gp.ui.commandNUm = 0;
                }

            }
            if (code == KeyEvent.VK_ENTER){
                if (gp.ui.commandNUm == 0 ){
                    gp.gameState = gp.playState;
                }
                if (gp.ui.commandNUm == 1){
                    //
                }
                if (gp.ui.commandNUm == 2){
                    System.exit(0);
                }
            }



        }


        // PLAY STATE
        else if (code == KeyEvent.VK_W){upwards = true;}
        if (code == KeyEvent.VK_A){rightwards = true;}
        if (code == KeyEvent.VK_S){downwards = true;}
        if (code == KeyEvent.VK_D){leftwards = true;}
        if(code == KeyEvent.VK_ENTER) {enterPressed = true;}
        if (code == KeyEvent.VK_P){
            if (gp.gameState == gp.playState){
                gp.gameState = gp.pauseState;
            } else if (gp.gameState == gp.pauseState) {
                gp.gameState = gp.playState;
            }
        }


        //Debug
        if (code == KeyEvent.VK_T){
            if (showDebugText == false){
                showDebugText = true;
        } else if (showDebugText) {
                showDebugText = false;
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
        if(code == KeyEvent.VK_ENTER) {enterPressed = false;
        }

    }

}
