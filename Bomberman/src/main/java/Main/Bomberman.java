package Main;

import GameObject.*;
import GameObject.Player;
import Graphics.Sprite;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Bomberman extends Application {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<GameObject> movingObjects = new ArrayList<>();
    private List<GameObject> stillObjects = new ArrayList<>();
    public static Player player;
    private Bomb bomb;
    private static int[] row = {0, 1, 0, -1};
    private static int[] col = {1, 0, -1, 0};
    public static int map[][];

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
                switch (event.getCode()) {
                    case W:
                        player.moveUp();
                        break;
                    case A:
                        player.moveLeft();
                        break;
                    case S:
                        player.moveDown();
                        break;
                    case D:
                        player.moveRight();
                        break;
                    default:
                        break;
                }
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

        createMap();
        player = new Player(1, 1, Sprite.player_down.getFxImage());
        bomb = new Bomb(1, 1, Sprite.bomb.getFxImage());
        movingObjects.add(player);
    }

    public void createMap() {
        map = new int[HEIGHT][WIDTH];

        // Read map details from pixels from a .png file
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("E:\\Uni\\HK1 - 2nd year\\INT2204 22 - Lap trinh huong doi tuong\\Bomberman\\Bomberman\\src\\main\\java\\Map\\Level1.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(inputStream);
        PixelReader pixelReader = image.getPixelReader();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReader.getColor(j, i);
                GameObject object;
                if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
                    //is a wall
                    map[i][j] = 0;
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (color.getRed() == 0 && color.getGreen() == 255 && color.getBlue() == 0) {
                    map[i][j] = 1;
                    object = new BreakableWall(j, i, Sprite.brick.getFxImage());
                } else {
                    map[i][j] = 2;
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                }

                stillObjects.add(object);
            }
        }
    }

    /** Gameplay, character movement and enemies behaviour. */
    public void update() {
        movingObjects.forEach(GameObject::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        movingObjects.forEach(g -> g.render(gc));

    }

}
