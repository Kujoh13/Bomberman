package GameObject.NonMovingObjects;

import GameObject.GameObject;
import Graphics.Sprite;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class Bomb extends GameObject {
    public static int radius = 1;
    private int timer = 300;
    public static int numberOfBombs = 1;
    private static final int[] row = {0, 1, 0, -1};
    private static final int[] col = {1, 0, -1, 0};
    public boolean collidePlayer = true;
    public static List<Bomb> bombs = new ArrayList<>();

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        setImg(Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, Bomberman.animate, 100).getFxImage());
        timer--;
        if (timer == 0) {
            Audio.playEffect(Audio.explosion);
            Explosion ex = new Explosion(this.x / Sprite.SCALED_SIZE, this.y / Sprite.SCALED_SIZE, Sprite.bomb_exploded.getFxImage());
            ex.setAnimation(Sprite.bomb_exploded2, Sprite.bomb_exploded1, Sprite.bomb_exploded);
            Bomberman.stillObjects.add(ex);
            for (int j = 0; j < col.length; j++) {
                int x = this.x;
                int y = this.y;
                int curRadius = 1;
                while (curRadius <= radius) {
                    x += col[j] * Sprite.SCALED_SIZE;
                    y += row[j] * Sprite.SCALED_SIZE;
                    boolean metWall = false;
                    for(GameObject o: Bomberman.stillObjects) {
                        if ((o instanceof Wall || o instanceof BreakableWall)
                                && o.getY() == y && o.getX() == x){
                            metWall = true;
                            if (o instanceof BreakableWall) {
                                int curX = x / Sprite.SCALED_SIZE;
                                int curY = y / Sprite.SCALED_SIZE;
                                Bomberman.stillObjects.add(new Grass(curX, curY, Sprite.grass.getFxImage()));
                                Bomberman.map[curY][curX] = 2;
                                Bomberman.stillObjects.remove(o);
                                if (Bomberman.items[curY][curX] != -1) {
                                    GameObject object;
                                    if (Bomberman.items[curY][curX] == 0) {
                                        object = new Portal(curX, curY, Sprite.portal.getFxImage());
                                    } else if (Bomberman.items[curY][curX] == 1) {
                                        object = new Flame(curX, curY, Sprite.powerup_flames.getFxImage());
                                    } else if (Bomberman.items[curY][curX] == 2) {
                                        object = new Bombs(curX, curY, Sprite.powerup_bombs.getFxImage());
                                    } else {
                                        object = new SpeedUp(curX, curY, Sprite.powerup_speed.getFxImage());
                                    }
                                    Bomberman.stillObjects.add(object);
                                }
                            }
                            break;
                        }
                    }

                    if(metWall) {
                        break;
                    } else {
                        Explosion explosion;
                        if (curRadius < radius) {
                            if (j % 2 == 0) {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal.getFxImage());
                                explosion.setAnimation(Sprite.explosion_horizontal2, Sprite.explosion_horizontal1, Sprite.explosion_horizontal);
                            } else {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_vertical.getFxImage());
                                explosion.setAnimation(Sprite.explosion_vertical2, Sprite.explosion_vertical1, Sprite.explosion_vertical);
                            }
                        } else {
                            if (j == 0) {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal_right_last.getFxImage());
                                explosion.setAnimation(Sprite.explosion_horizontal_right_last2, Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last);
                            } else if (j == 1) {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_vertical_down_last.getFxImage());
                                explosion.setAnimation(Sprite.explosion_vertical_down_last2, Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last);
                            } else if (j == 2) {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_horizontal_left_last.getFxImage());
                                explosion.setAnimation(Sprite.explosion_horizontal_left_last2, Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last);
                            } else {
                                explosion = new Explosion(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, Sprite.explosion_vertical_top_last.getFxImage());
                                explosion.setAnimation(Sprite.explosion_vertical_top_last2, Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last);
                            }
                        }
                        Bomberman.stillObjects.add(explosion);
                    }
                    curRadius++;
                }
            }
            bombs.remove(this);
        } else if (timer == 70) {
            Audio.playEffect(Audio.bomb_countdown);
        }
    }

    public static boolean placeBomb() {
        if (bombs.size() == numberOfBombs) {
            return false;
        }
        int x = Bomberman.player.getX();
        int y = Bomberman.player.getY();
        int finalX = 1, finalY = 1;
        int collisionArea = 0;
        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Grass && o.collision(Bomberman.player)) {
                int x1 = Math.max(Bomberman.player.getX(), o.getX());
                int x2 = Math.min(Bomberman.player.getX(), o.getX()) + Sprite.SCALED_SIZE - 1;
                int y1 = Math.max(Bomberman.player.getY(), o.getY());
                int y2 = Math.min(Bomberman.player.getY(), o.getY()) + Sprite.SCALED_SIZE - 1;
                if ((x2 - x1) * (y2 - y1) > collisionArea) {
                    finalX = o.getX() / Sprite.SCALED_SIZE;
                    finalY = o.getY() / Sprite.SCALED_SIZE;
                    collisionArea = (x2 - x1) * (y2 - y1);
                }
            }
        }
        for (Bomb bomb: bombs) {
            if (bomb.getX() == finalX * Sprite.SCALED_SIZE && bomb.getY() == finalY * Sprite.SCALED_SIZE)
                return false;
        }
        bombs.add(new Bomb(finalX, finalY, Sprite.bomb.getFxImage()));
        return true;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setRadius(int radius) {
        Bomb.radius = radius;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public static int getNumberOfBombs() {
        return numberOfBombs;
    }

    public static void setNumberOfBombs(int numberOfBombs) {
        Bomb.numberOfBombs = numberOfBombs;
    }
}
