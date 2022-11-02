package GameObject.NonMovingObjects;

import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

public class Portal extends Item {
    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        if (PlayerPickUp()) {
            Audio.playEffect(Audio.portal);
            Bomberman.status = 1;
            Bomberman.stillObjects.remove(this);
        }
    }
}
