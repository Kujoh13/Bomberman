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
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class Bomberman extends Application {

    public static final int WIDTH = 25;
    public static final int HEIGHT = 15;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<GameObject> movingObjects = new ArrayList<>();
    private List<GameObject> stillObjects = new ArrayList<>();
    private Player player;
    private Bomb bomb;
    private static int[] row = {0, 1, 0, -1};
    private static int[] col = {1, 0, -1, 0};
    private int map[][];

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH * Sprite.SIZE, HEIGHT * Sprite.SIZE);
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
                update();
                render();
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();

        createMap();
        player = new Player(1, 1, Sprite.player.getFxImage());
        bomb = new Bomb(1, 1, Sprite.bomb.getFxImage());

    }

    public void createMap() {
        map = new int[HEIGHT][WIDTH];

        // Read map details from pixels from a .png file
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("\\Map\\Level1.png");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(inputStream);
        PixelReader pixelReader = image.getPixelReader();

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                //Retrieving the color of the pixel of the loaded image
                Color color = pixelReader.getColor(i, j);
                GameObject object;
                if (color.getRed() == 0 && color.getGreen() == 0 && color.getBlue() == 0) {
                    //is a wall
                    map[i][j] = 0;
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else if (color.getRed() == 0 && color.getGreen() == 255 && color.getBlue() == 0) {
                    map[i][j] = 1;
                    object = new Grass(i, j, Sprite.breakableWall.getFxImage());
                } else {
                    map[i][j] = 2;
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }

                stillObjects.add(object);
            }
        }
    }

    /** Gameplay, character movement and enemies behaviour. */
    public void update() {
        movingObjects.forEach(GameObject::update);
        if (bomb.getTimer() == 0) {
            for (int i = 0; i < 4; i++) {

            }
        }

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        movingObjects.forEach(g -> g.render(gc));

    }

}
