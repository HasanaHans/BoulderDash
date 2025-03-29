package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    // settings of the screen
    final int originalTileSize = 16; //16x16
    final int scale = 3;

    public final int tileSize = originalTileSize*scale; //48*48
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; // 768 Pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 Pixels

    // World map Parameters
    public final int maxWorldCol = 40;
    public final int maxWorldRow = 22;

    // FPS
    int FPS = 60;

    // System
    TileManager tileM = new TileManager(this);
    KeyHandling keyH = new KeyHandling();
    Sound sound = new Sound();


    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    Thread gameThread;
    public UI ui = new UI(this);

    // Entity AND OBJECT
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[15];


    public GamePanel () {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();

    }
    public void setupGame(){
        aSetter.setObject();
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();

    }
    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread !=null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1){
                update();
                repaint();
                delta--;

            }

        }

    }
    public void update(){
        player.update();


    }
    public void paintComponent (Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Debug
        long drawStart = 0;
        if (keyH.checkDrawTime == true){
            drawStart = System.nanoTime();
        }

        // Tile
        tileM.draw(g2);
        // Object
        for (int i = 0; i < obj.length; i++)
            if (obj[i] != null){
                obj[i].draw(g2,this);
            }
        // Player
        player.draw(g2);

        // UI
        ui.draw(g2);
        // Debug
        if (keyH.checkDrawTime){
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.WHITE);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: " + passed);
        }


        g2.dispose();
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){
        sound.stop();
    }

    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
