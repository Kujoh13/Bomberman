import GameObject.GameObject;
import Graphics.Sprite;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Bomberman extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<GameObject> entities = new ArrayList<>();
    private List<GameObject> stillObjects = new ArrayList<>();

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH * Sprite.SIZE, HEIGHT * Sprite.SIZE);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();
    }


}
