package GameObject;

import Main.Bomberman;

import javafx.scene.image.Image;

import java.util.Random;

public class Enemy1 extends Enemy {

    Random random = new Random();
    /** To check if the enemy goes horizontally or vertically. */
    private boolean horizontal = random.nextBoolean();
    /** To find out the direction the enemy will start with. */
    private boolean add = random.nextBoolean();

    public Enemy1(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        int dif = (add) ? 1 : -1;

        if (horizontal) {
            if ((x + dif <= 0 && x + dif > Bomberman.WIDTH) || (map[x + dif][y] != 2)) {
                dif = -dif;
            }

            if (map[x + dif][y] == 2) {
                x += dif;
            }
        } else {
            if ((y + dif <= 0 && y + dif > Bomberman.HEIGHT) || (map[x][y + dif] != 2)) {
                dif = -dif;
            }

            if (map[x][y + dif] == 2) {
                y += dif;
            }
        }
    }
}
