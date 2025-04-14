package object;

import entity.Entity;
import main.GamePanel;



public class OBJ_Key extends Entity {
    GamePanel gp;



    public OBJ_Key(GamePanel gp){
        super(gp);
        this.gp =gp;
        name = "Key";
        down1 = setupScaled("/objects/key", gp.tileSize,gp.tileSize);


        collisionOn = true;

    }


}

