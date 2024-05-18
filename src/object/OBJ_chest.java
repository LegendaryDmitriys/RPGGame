package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_chest extends Entity {

    public OBJ_chest(GamePanel gp){
        super(gp);

        name = "Chest";
        down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
        collision = true;
    }
}

