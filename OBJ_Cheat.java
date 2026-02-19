import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Cheat extends SuperObject {
    public OBJ_Cheat() {
        name = "Cheat";
        collision = true;

        try {
            image = ImageIO.read(
                getClass().getResourceAsStream("/Object/chest.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
