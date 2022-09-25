import javafx.application.Application;
import javafx.stage;

public class Bomberman extends Application {

    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(Bomberman.class);
    }

    @Override
    public void start(Stage stage) {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
    }


}
