package GameObject;

import javafx.scene.image.Image;
import javafx.util.Pair;
import Main.Bomberman;
import java.util.Stack;

public class Enemy5 extends Enemy {
    public Enemy5(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        Stack<Pair<Integer, Integer> > moveList = new Stack<>();

        int[] addX = {1, -1, 0, 0};
        int[] addY = {0, 0, -1, 1};

        moveList.push(new Pair<>(x, y));

        int[][] timer = new int[26][16];
        Pair<Integer, Integer>[][] preMove = new Pair[Bomberman.WIDTH + 1][Bomberman.HEIGHT + 1];

        for (int i = 1; i <= Bomberman.WIDTH; i++)
            for (int j = 1; j <= Bomberman.HEIGHT; j++)
                timer[i][j] = Bomberman.WIDTH * Bomberman.HEIGHT;

        /** To set the current position as default. */
        timer[x][y] = 0;
        preMove[x][y] = new Pair<> (0, 0);
        /**  */

        int newX = 0, newY = 0;
        int curX = 0, curY = 0;

        while (moveList.peek().getKey() != Bomberman.player.getX()
            && moveList.peek().getValue() != Bomberman.player.getY()) {

            curX = moveList.peek().getKey();
            curY = moveList.peek().getValue();

            for (int i = 0; i < 4; i++) {
                newX = curX + addX[i];
                newY = curY + addY[i];

                if (newX <= Bomberman.WIDTH && newX >= 1
                        && newY <= Bomberman.HEIGHT && newY >= 1
                        && Bomberman.map[newX][newY] == 2) {
                    if (timer[newX][newY] > timer[curX][curY] + 1) {
                        moveList.push(new Pair<> (newX, newY));
                        timer[newX][newY] = timer[curX][curY] + 1;
                        preMove[newX][newY] = new Pair<> (curX, curY);
                    }
                }
            }
        }

        curX = x;
        curY = y;

        while (curX != 0 && curY != 0) {
            newX = curX;  newY = curY;

            curX = preMove[newX][newY].getKey();
            curY = preMove[newX][newY].getValue();
        }

        x = newX;  y = newY;
    }
}
