package main;

import object.OBJ_key;


public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new OBJ_key();
        gp.obj[0].worldX = 45 * gp.tileSize;
        gp.obj[0].worldY = 7 * gp.tileSize;
    }
}
