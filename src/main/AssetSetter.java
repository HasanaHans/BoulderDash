package main;

import object.OBJ_Key;
import tille_interactive.IT_DryTree;
import tille_interactive.InteractiveTile;


public class AssetSetter {
    GamePanel gp;
    public AssetSetter(GamePanel gp){
        this.gp= gp;
    }
public void setObject(){
        gp.obj[0] = new OBJ_Key(gp);
        gp.obj[0].worldX = gp.tileSize*6;
        gp.obj[0].worldY = gp.tileSize*2;
}

public void setInteractiveTile(){
        int i = 0;
        gp.iTile[i] = new IT_DryTree(gp, 7, 2 );i++;
        gp.iTile[i] = new IT_DryTree(gp, 7, 3 );i++;
        gp.iTile[i] = new IT_DryTree(gp, 3, 3);i++;
        gp.iTile[i] = new IT_DryTree(gp, 4, 3 );i++;

}
}
