package Model.Tiles;

import Model.Actions.Visitors.Visitor;
import Model.InvalidMoveException;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is a representation of a creation square on a Swords and Shields game's board.
 */
public class CreationSquare implements Tile {
    private Point position;
    private Piece piece;
    private String color;
    private Image image;

    /**
     * The constructor takes a point parameter which is the position at which the Creation square will be on the board
     * @param pos
     */
    public CreationSquare(Point pos, String color){
        this.color=color;
        position=pos;
        try {
            image = ImageIO.read(new File("Images/" + color+"/"+color+"Creation.png"));
        } catch (IOException e) {
            throw new NullPointerException("Image not found");
        }
    }

    /**
     * This method returns a boolean representing whether there is a piece on the creation square or not.
     */
    public boolean isOccupied(){
        return piece!=null;
    }

    /**
     * This method returns the piece on the creation square, if there is any, and throws and exception otherwise.
     * @return
     */
    public Piece getPiece(){
        return piece;
    }

    /**
     * This method takes a piece as a parameter, to be set as the creation square's piece.
     * I.e. the player created a piece.
     * @param piece
     */
    public void setPiece(Piece piece){
        this.piece=piece;
        if(piece!=null) {
            this.piece.setPosition(position.x, position.y);
        }
    }

    @Override
    public Image getImage() {
        if(piece!=null) return piece.getImage();
        else return image;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitCreation(this);
    }

    @Override
    public ArrayList<Reactable> accept(ArrayList<Reactable> reactableList) {
        return reactableList;
    }
}
