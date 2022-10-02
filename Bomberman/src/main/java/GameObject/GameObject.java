package GameObject;

import Graphics.Sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class GameObject {
    protected int x;
    protected int y;
    protected Image img;

    /** Initialize a GameObject. */
    public GameObject() {

    }
    public GameObject(int x, int y, Image img) {
        this.x = x;
        this.y = y;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public boolean collision(GameObject go) {
        if (go.getX() >= x + Sprite.SIZE || x >= go.getX() + Sprite.SIZE
            || go.getY() >= y + Sprite.SIZE || y >= go.getY() + Sprite.SIZE) {
            return false;
        }
        return true;
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
