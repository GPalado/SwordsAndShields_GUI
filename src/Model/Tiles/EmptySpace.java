package Model.Tiles;

import Model.Actions.Visitors.Visitor;
import Model.Tiles.Reactables.Reactable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is a representation of an empty tile on the Swords and Shields board.
 */
public class EmptySpace implements Tile {
    private Point position;
    private Image image;

    /**
     * The constructor takes a point parameter which is the position at which the EmptySpace tile will be on the board
     * @param pos
     */
    public EmptySpace(Point pos){
        position=pos;
        try {
            image = ImageIO.read(new File("Images/Empty.png"));
        } catch (IOException e) {
            throw new NullPointerException("Image not found");
        }
    }


    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public ArrayList<Reactable> accept(ArrayList<Reactable> reactableList) {
        return reactableList;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitEmpty(this);
    }

}
