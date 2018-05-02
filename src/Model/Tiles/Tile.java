package Model.Tiles;

import Model.Actions.Visitors.Visitor;
import Model.Tiles.Reactables.Reactable;
import java.awt.*;
import java.util.ArrayList;

/**
 * This interface specifies the methods required to be implemented by the tiles on a Swords and Shields game's board
 */
public interface Tile {

    /**
     * This method returns the tile's representation
     * @return
     */
    Image getImage();

    /**
     * This method returns the tile's position
     * @return
     */
    Point getPosition();

    /**
     * This method accepts a visitor to apply actions determined by the tile's concrete type.
     * @param visitor
     */
    void accept(Visitor visitor);

    /**
     * This method accepts a List of Reactable objects. It returns the list, adding itself to the list if applicable.
     */
    ArrayList<Reactable> accept(ArrayList<Reactable> reactableList);
}
