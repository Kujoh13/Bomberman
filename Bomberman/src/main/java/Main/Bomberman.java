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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bomberman extends Application {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    private GraphicsContext gc;
    private Canvas canvas;
    public static List<GameObject> movingObjects = new ArrayList<>();
    public static List<GameObject> stillObjects = new ArrayList<>();
    public static Player player;
    public static int map[][];
    public static int items[][];
    public static Scanner scanner;
    public static int currentLevel = 1;

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
                    player.moveUp();
                }
                if (event.getCode() == KeyCode.A) {
                    player.moveLeft();
                }
                if (event.getCode() == KeyCode.S) {
                    player.moveDown();
                }
                if (event.getCode() == KeyCode.D) {
                    player.moveRight();
                }
                if (event.getCode() == KeyCode.SPACE) {
                    Bomb.placeBomb();
                    Audio.playEffect(Audio.bomb_fuse);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.W) {
                   // player.moveUp();
                }
                if (event.getCode() == KeyCode.A) {
                   // player.moveLeft();
                }
                if (event.getCode() == KeyCode.S) {
                   // player.moveDown();
                }
                if (event.getCode() == KeyCode.D) {
                  //  player.moveRight();
                }
            }
        });
        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();

        Audio.playMusic(Audio.bgm);
        createMap();
        player = new Player(1, 1, Sprite.player_down.getFxImage());
        movingObjects.add(player);
    }

    public void createMap() {
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
        map = new int[HEIGHT][WIDTH];
        items = new int[HEIGHT][WIDTH];
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
                    }
                }

                stillObjects.add(object);
            }
        }
    }

    /** Gameplay, character movement and enemies behaviour. */
    public void update() {
        movingObjects.forEach(GameObject::update);
        stillObjects.forEach(GameObject::update);

        //Bomb explosion handling
        for (Bomb bomb: Bomb.bombs) {
            bomb.update();
        }

        //Player collision

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        movingObjects.forEach(g -> g.render(gc));
        Bomb.bombs.forEach(g -> g.render(gc));
    }

}
