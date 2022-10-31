package Main;

import GameObject.*;
import GameObject.MovingObjects.*;
import GameObject.NonMovingObjects.*;
import Graphics.Sprite;
import Sounds.Audio;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bomberman extends Application {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    public static final int numberOfLevels = 5;
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<GameObject> movingObjects = new ArrayList<>();
    public static List<GameObject> stillObjects = new ArrayList<>();
    public static Player player;
    public static int map[][];
    public static int items[][];
    public static Scanner scanner;
    private boolean leftP = false;
    private boolean rightP = false;
    private boolean upP = false;
    private boolean downP = false;
    public static int currentLevel = 1;
    public static int status = 0;
    public static int loseTimer;
    public static int animate = 0;

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH * Sprite.SCALED_SIZE, HEIGHT * Sprite.SCALED_SIZE);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W) {
                    upP = true;
                    player.setVelY(-Player.player_speed);
                }
                if (event.getCode() == KeyCode.A) {
                    leftP = true;
                    player.setVelX(-Player.player_speed);
                }
                if (event.getCode() == KeyCode.S) {
                    downP = true;
                    player.setVelY(Player.player_speed);
                }
                if (event.getCode() == KeyCode.D) {
                    rightP = true;
                    player.setVelX(Player.player_speed);
                }
                if (event.getCode() == KeyCode.SPACE) {
                    if (Bomb.placeBomb()) {
                        Audio.playEffect(Audio.bomb_fuse);
                    }
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W) {
                    upP = false;
                    if (downP) {
                        player.setVelY(Player.player_speed);
                    }
                    else player.setVelY(0);
                }
                if (event.getCode() == KeyCode.A) {
                    leftP = false;
                    if (rightP) {
                        player.setVelX(Player.player_speed);
                    }
                    else player.setVelX(0);
                }
                if (event.getCode() == KeyCode.S) {
                    downP = false;
                    if (upP) {
                        player.setVelY(-Player.player_speed);
                    }
                    else player.setVelY(0);
                }
                if (event.getCode() == KeyCode.D) {
                    rightP = false;
                    if (leftP) {
                        player.setVelX(-Player.player_speed);
                    }
                    else player.setVelX(0);
                }
            }
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                animate++;
                update();
                render();
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();

        loadLevel();
    }

    public void loadLevel() {
        /*
            map:
                0: wall
                1: brick
                2: grass
            buffs:
                0: portal
                1: increase bomb radius
                2: increase number of bombs
                3: increase player speed
                4: immortality
                5: teleport
         */
        status = 0;
        Audio.playMusic(Audio.bgm);
        movingObjects.clear();
        stillObjects.clear();
        Bomb.bombs.clear();
        Bomb.numberOfBombs = 1;
        Bomb.radius = 1;
        map = new int[HEIGHT][WIDTH];
        items = new int[HEIGHT][WIDTH];
        Player.player_speed = 3;
        try{
            File file = new File("src//main//resources//levels//Level" + currentLevel + ".txt");
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < HEIGHT; i++) {
            String str = scanner.nextLine();
            for (int j = 0; j < WIDTH; j++) {
                GameObject object;
                if (str.charAt(j) == '#') {
                    //is a wall
                    map[i][j] = 0;
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    items[i][j] = -1;
                } else if (str.charAt(j) == '.') {
                    map[i][j] = 2;
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    items[i][j] = -1;
                } else if (str.charAt(j) >= '0' && str.charAt(j) <= '9'){
                    map[i][j] = 2;
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                    if (str.charAt(j) == '1') {
                        movingObjects.add(new Enemy1(j, i, Sprite.balloon_dead.getFxImage()));
                    } else if (str.charAt(j) == '2') {
                        movingObjects.add(new Enemy2(j, i, Sprite.oneal_dead.getFxImage()));
                    } else if (str.charAt(j) == '3') {
                        movingObjects.add(new Enemy3(j, i, Sprite.doll_dead.getFxImage()));
                    } else if (str.charAt(j) == '4') {
                        movingObjects.add(new Enemy4(j, i, Sprite.minvo_dead.getFxImage()));
                    }  else if (str.charAt(j) == '5') {
                        movingObjects.add(new Enemy5(j, i, Sprite.kondoria_dead.getFxImage()));
                    }
                } else {
                    map[i][j] = 1;
                    object = new BreakableWall(j, i, Sprite.brick.getFxImage());
                    items[i][j] = -1;
                    if (str.charAt(j) == 'p') {
                        items[i][j] = 0;
                    } else if (str.charAt(j) == 'r') {
                        items[i][j] = 1;
                    } else if (str.charAt(j) == 'n') {
                        items[i][j] = 2;
                    } else if (str.charAt(j) == 's') {
                        items[i][j] = 3;
                    } else if (str.charAt(j) == 'i') {
                        items[i][j] = 4;
                    } else if (str.charAt(j) == 't') {
                        items[i][j] = 5;
                    }
                }

                stillObjects.add(object);
            }
        }
        player = new Player(1, 1, Sprite.player_down.getFxImage());
        movingObjects.add(player);
    }

    /** Update objects. */
    public void update() {
        if (status != -1) {
            movingObjects.forEach(GameObject::update);
            stillObjects.forEach(GameObject::update);
            for (Bomb bomb : Bomb.bombs) {
                bomb.update();
            }
        }

        if (status == -1) {
            loseTimer--;
            player.setImg(Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, animate, 300).getFxImage());
            if (loseTimer == 0)
                reset();
        } else if (status == 1) {
            currentLevel++;
            if (currentLevel == numberOfLevels) {
                Audio.playEffect(Audio.win);
            }
            loadLevel();
        }

        //player.auto();
    }

    /** Render objects. */
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        movingObjects.forEach(g -> g.render(gc));
        Bomb.bombs.forEach(g -> g.render(gc));
    }

    public void reset() {
        currentLevel = 1;
        loadLevel();
    }


}
