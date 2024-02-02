package object;

import javax.imageio.ImageIO;
import java.io.IOException;

public class OBJ_key extends SuperObject {

    public OBJ_key(){

        name = "Coin";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/objects/coins.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
    //solidArea.x =15

}
