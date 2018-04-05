package Assignment5;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;

public class Main extends Application
{
    public static final int TILE_SIZE = 50;
    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    public static final int MENU_WIDTH = 2;

    public void start(Stage primaryStage) throws Exception
    {
        Scene scene = new Scene(createContent());

        primaryStage.setTitle("source.CrittersLab");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private Group createTileGroup()
    {
        Group tileGroup = new Group();

        for(int x = 0; x < WIDTH; x++)
        {
            for(int y = 0; y < HEIGHT; y++)
            {
                Rectangle r = new Rectangle();

                r.setWidth(TILE_SIZE);
                r.setHeight(TILE_SIZE);
                r.relocate(x * TILE_SIZE, y * TILE_SIZE);
                r.setFill((x+y) % 2 == 0 ? Color.valueOf("#feb") : Color.valueOf("#582"));

                tileGroup.getChildren().add(r);
            }
        }

        return tileGroup;
    }

    private Piece makePiece(CritterShape shape, int x, int y)
    {
        Piece piece = new Piece(shape, x, y);
        return piece;
    }

    private Parent createContent()
    {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE + MENU_WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        try {
            Critter.makeCritter("Critter1");
        }
        catch(InvalidCritterException ex)
        {
            System.out.println(ex.toString());
        }

        root.getChildren().addAll(createTileGroup(), Critter.displayWorld());

        return root;
    }
}
