package GameObject.NonMovingObjects;

import GameObject.GameObject;
import GameObject.MovingObjects.Enemy;
import GameObject.MovingObjects.Enemy2;
import GameObject.MovingObjects.Enemy3;
import Graphics.Sprite;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

public class Explosion extends GameObject {
    private int timer = 15;

    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        for(GameObject o: Bomberman.movingObjects) {
            if (o instanceof Enemy && o.collision(this)) {
                if (!(o instanceof Enemy2) || (o instanceof Enemy2 && ((Enemy2) o).timer <= 0)) {
                    Bomberman.movingObjects.remove(o);
                }
                if (o instanceof Enemy2)
                    System.out.println("timer" + ((Enemy2) o).timer);
                Audio.playEffect(Audio.enemy_die);
                if (o instanceof Enemy3) {
                    Enemy2 temp1 = new Enemy2(1, 1, Sprite.oneal_dead.getFxImage());
                    temp1.setX(o.getX());
                    temp1.setY(o.getY());
                    Enemy2 temp2 = new Enemy2(1, 1, Sprite.oneal_dead.getFxImage());
                    temp2.setX(o.getX());
                    temp2.setY(o.getY());
                    Bomberman.movingObjects.add(temp1);
                    Bomberman.movingObjects.add(temp2);
                }
            }
        }
        timer--;
        if (timer == 0) {
            Bomberman.stillObjects.remove(this);
        } else {

        }
    }
}
