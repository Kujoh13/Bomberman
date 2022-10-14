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
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class Bomberman extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    private GraphicsContext gc;
    private Canvas canvas;
    private List<GameObject> movingObjects = new ArrayList<>();
    private List<GameObject> stillObjects = new ArrayList<>();
    private Player player = new Player();

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
                render();
                update();
            }
        };
        timer.start();
        stage.setScene(scene);
        stage.show();
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
