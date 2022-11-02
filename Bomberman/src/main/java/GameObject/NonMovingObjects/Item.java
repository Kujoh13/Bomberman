package GameObject.NonMovingObjects;

import GameObject.GameObject;
import GameObject.MovingObjects.Player;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Item extends GameObject {
    protected int timer = 0;
    protected boolean pickedUp = false;
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    protected boolean PlayerPickUp() {
        if (Bomberman.player.collision(this)) {
            pickedUp = true;
            return true;
        }

        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(GraphicsContext gc) {
        if (!pickedUp) {
            gc.drawImage(img, x, y);
        }
    }


}
class Immortality extends Item {
    public Immortality(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (PlayerPickUp() && timer < 0) {
            Audio.playEffect(Audio.collect_item);
            timer = 600;
            Bomberman.player.setImmortal(true);
        }
        timer--;
        if (timer == 0) {
            Bomberman.stillObjects.remove(this);
            Bomberman.player.setImmortal(false);
        }
    }
}

class SpeedUp extends Item {
    public SpeedUp(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (PlayerPickUp() && timer == 0) {
            Audio.playEffect(Audio.collect_item);
            Player.player_speed = 6;
            timer = 600;
        }
        timer--;
        if (timer == 0) {
            Player.player_speed = 3;
            Bomberman.stillObjects.remove(this);
        }
    }
}

class Teleport extends Item {
    public Teleport(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {

        if (PlayerPickUp()) {
            Audio.playEffect(Audio.collect_item);
            Random rd = new Random();

            int nextX = rd.nextInt() % (Bomberman.WIDTH - 1) + 1;
            int nextY = rd.nextInt() % (Bomberman.HEIGHT - 1) + 1;

            while (Bomberman.map[nextX][nextY] != 2) {
                nextX = rd.nextInt() % (Bomberman.WIDTH - 1) + 1;
                nextY = rd.nextInt() % (Bomberman.HEIGHT - 1) + 1;
            }

            Bomberman.player.setX(nextX);
            Bomberman.player.setY(nextY);
            Bomberman.stillObjects.remove(this);
        }
    }
}

class Flame extends Item {
    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }
    public void update() {
        if (PlayerPickUp()) {
            Audio.playEffect(Audio.collect_item);
            Bomb.radius++;
            Bomberman.stillObjects.remove(this);
        }
    }
}

class Bombs extends Item {
    public Bombs(int x, int y, Image img) {
        super(x, y, img);
    }
    public void update() {
        if (PlayerPickUp()) {
            Audio.playEffect(Audio.collect_item);
            Bomb.numberOfBombs++;
            Bomberman.stillObjects.remove(this);
        }
    }
}

class Portal extends Item {
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
