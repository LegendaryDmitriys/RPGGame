package main;


import entity.NPC_OldMan;
import monster.MON_Goblin;
import monster.MON_GreenSlime;
import object.OBJ_chest;
import object.OBJ_door;
import object.OBJ_key;

public class AssetSetter {

    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){

        gp.obj[0] = new OBJ_chest(gp);
        gp.obj[0].worldX = gp.tileSize*15;
        gp.obj[0].worldY = gp.tileSize*22;

        gp.obj[1] = new OBJ_key(gp);
        gp.obj[1].worldX = gp.tileSize*23;
        gp.obj[1].worldY = gp.tileSize*40;

        gp.obj[2] = new OBJ_key(gp);
        gp.obj[2].worldX = gp.tileSize*23;
        gp.obj[2].worldY = gp.tileSize*40;

        gp.obj[2] = new OBJ_door(gp);
        gp.obj[2].worldX = gp.tileSize*19;
        gp.obj[2].worldY = gp.tileSize*21;


    }

    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*22;

    }

    public void setMonster(){
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize*26;
        gp.monster[0].worldY = gp.tileSize*21;


        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize*21;
        gp.monster[1].worldY = gp.tileSize*21;


        gp.monster[2] = new MON_GreenSlime(gp);
        gp.monster[2].worldX = gp.tileSize*23;
        gp.monster[2].worldY = gp.tileSize*23;

        gp.monster[3] = new MON_Goblin(gp);
        gp.monster[3].worldX = gp.tileSize*23;
        gp.monster[3].worldY = gp.tileSize*27;

    }



}
