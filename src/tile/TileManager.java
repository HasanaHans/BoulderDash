package tile;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;
    public TileManager (GamePanel gp){
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int [gp.maxWorldCol][gp.maxWorldRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage( ){


        setup(1, "earth", false);
        setup(0, "boulder", true);
        setup(2, "wall", true);
        setup(3, "key", false);
        setup(4, "door_iron", false);
        setup(5, "sand", false);



    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();
        try {
            tile[index] = new Tile();

            // Check if the resource exists
            InputStream is = getClass().getClassLoader().getResourceAsStream("tiles/" + imageName + ".png");
            if (is == null) {
                System.out.println("Image not found: " + imageName + ".png");
            } else {
                tile[index].image = ImageIO.read(is);
                tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
                tile[index].collision = collision;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void loadMap() {
        try {
            // Import the txt file
            InputStream is = getClass().getResourceAsStream("/maps/world01.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;
            int col = 0;
            while (col < gp.maxWorldCol && row < gp.maxWorldRow){
                String line = br.readLine();
                while (col < gp.maxWorldCol){
                    String numbers[] = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                    col++;
                }
                if (col == gp.maxWorldCol){
                    col = 0;
                    row++;
                }
            }

           br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2){
       int worldCol = 0;
       int worldRow = 0;


       while (worldCol <gp.maxWorldCol && worldRow < gp.maxWorldRow){
           int tileNum = mapTileNum[worldCol][worldRow];
           int worldX = worldCol * gp.tileSize;
           int worldY = worldRow * gp.tileSize;
           int screenX = worldX - gp.player.worldX + gp.player.screenX;
           int screenY = worldY - gp.player.worldY + gp.player.screenY;
           if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
              worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
              worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
              worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ){
               g2.drawImage(tile[tileNum].image, screenX, screenY, null);}

           worldCol++;

           if (worldCol == gp.maxWorldCol){
               worldCol = 0;

               worldRow++;

           }
       }
    }
}
