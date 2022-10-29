package GameObject.MovingObjects;

import GameObject.GameObject;
import GameObject.NonMovingObjects.BreakableWall;
import GameObject.NonMovingObjects.Explosion;
import GameObject.NonMovingObjects.Wall;
import Graphics.Sprite;
import Main.Bomberman;
import Sounds.Audio;
import javafx.scene.image.Image;

public class Player extends GameObject {
    public static int player_speed = 3;
    private int velX = 0;
    private int velY = 0;
    private boolean immortal = false;
    public Player() {

    }
    public Player(int x, int y, Image img) {
        super(x, y, img);
    }
    @Override
    public void update() {
        int collisionTimes = checkCollision(velX, velY);
        if (collisionTimes == 0) {
            x = x + velX;
            y = y + velY;
        } else if (collisionTimes == 1) {
            for (GameObject o: Bomberman.stillObjects) {
                if (o instanceof Wall && o.collision(x + velX, y + velY)) {
                    int x1 = Math.max(x + velX, o.getX());
                    int x2 = Math.min(x + velX, o.getX()) + Sprite.SCALED_SIZE - 1;
                    int y1 = Math.max(y + velY, o.getY());
                    int y2 = Math.min(y + velY, o.getY()) + Sprite.SCALED_SIZE - 1;
                    if ((x2 - x1) * (y2 - y1) <= 2 * Player.player_speed * Player.player_speed) {
                        if (o.getX() < x && o.getY() < y) {
                            if (velY < 0) {
                                x = o.getX() + Sprite.SCALED_SIZE;
                                y = y - Player.player_speed;
                            } else {
                                x = x - Player.player_speed;
                                y = o.getY() + Sprite.SCALED_SIZE;
                            }
                        } else if (o.getX() < x && o.getY() > y) {
                            if (velY > 0) {
                                x = o.getX() + Sprite.SCALED_SIZE;
                                y = y + Player.player_speed;
                            } else {
                                x = x - Player.player_speed;
                                y = o.getY() - Sprite.SCALED_SIZE;
                            }
                        } else if (o.getX() > x && o.getY() < y) {
                            if (velX > 0) {
                                x = x + Player.player_speed;
                                y = o.getY() + Sprite.SCALED_SIZE;
                            } else {
                                x = o.getX() - Sprite.SCALED_SIZE;
                                y = y - Player.player_speed;
                            }
                        } else {
                            if (velY > 0) {
                                x = o.getX() - Sprite.SCALED_SIZE;
                                y = y + Player.player_speed;
                            } else {
                                x = x + Player.player_speed;
                                y = o.getY() - Sprite.SCALED_SIZE;
                            }
                        }
                    }
                }
            }
        }
        if (immortal) {
            return;
        }
        for (GameObject o: Bomberman.movingObjects) {
            if (o instanceof Enemy) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }

        for (GameObject o: Bomberman.stillObjects) {
            if (o instanceof Explosion) {
                if (o.collision(this) && Bomberman.status != -1) {
                    Audio.stopMusic();
                    Bomberman.status = -1;
                    Audio.playEffect(Audio.lose);
                }
            }
        }
    }

    private int checkCollision(int velX, int velY) {
        int xTemp = x + velX;
        int yTemp = y + velY;
        int res = 0;
        for(GameObject o: Bomberman.stillObjects) {
            if ((o instanceof Wall || o instanceof BreakableWall)
            && o.collision(xTemp, yTemp)) {
                res++;
            }
        }
        return res;
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }
}
