package Model.Tiles;

import Model.Actions.Visitors.Visitor;
import Model.Tiles.Reactables.Reactable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is a representation of an OutOfBounds tile on a Swords and Shields game's board.
 */
public class OutOfBounds implements Tile {

    private Point position;
    private Image image;

    /**
     * The constructor takes the position at which the OutOfBounds tile is on the board.
     * @param pos
     */
    public OutOfBounds(Point pos){
        position=pos;
        try {
            image = ImageIO.read(new File("Images/OOB.png"));
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
    public void accept(Visitor visitor) {
        visitor.visitOOB(this);
    }

    @Override
    public ArrayList<Reactable> accept(ArrayList<Reactable> reactableList) {
        return reactableList;
    }
}
