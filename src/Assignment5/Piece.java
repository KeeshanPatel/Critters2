package Assignment5;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Ellipse;

import static Assignment5.Main.TILE_SIZE;

public class Piece extends StackPane
{

    private CritterShape shape;


    public Piece(CritterShape shape, int x, int y)
    {
        this.shape = shape;

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        Shape s = null;

        if(shape == CritterShape.SQUARE)
            s = new Rectangle(TILE_SIZE * 0.3125, TILE_SIZE * 0.3125);
        else if(shape == CritterShape.DIAMOND)
            s = new Rectangle(TILE_SIZE * 0.3125, TILE_SIZE * 0.6125);
        else if(shape == CritterShape.CIRCLE)
            s = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.3125);
        else if(shape == CritterShape.TRIANGLE)
            s = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.6125);

        s.setStroke(Color.BLACK);
        s.setStrokeWidth(TILE_SIZE * 0.03);

        s.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        s.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().add(s);
    }
}