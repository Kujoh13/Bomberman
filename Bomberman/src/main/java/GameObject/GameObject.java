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
        this.x = x * Sprite.SCALED_SIZE;
        this.y = y * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public boolean collision(GameObject go) {
        if (go.getX() >= x + Sprite.SCALED_SIZE || x >= go.getX() + Sprite.SCALED_SIZE
            || go.getY() >= y + Sprite.SCALED_SIZE || y >= go.getY() + Sprite.SCALED_SIZE) {
            return false;
        }
        return true;
    }

    public boolean collision(int ox, int oy) {
        if (ox >= x + Sprite.SCALED_SIZE || x >= ox + Sprite.SCALED_SIZE
                || oy >= y + Sprite.SCALED_SIZE || y >= oy + Sprite.SCALED_SIZE) {
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
