package Model.Tiles.Reactables;

import Model.Actions.Visitors.Visitor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is a representation of the reactable face tile of a Swords and Shields game.
 */
public class Face implements Reactable {

    private Point position;
    private Status status;
    private String color;
    private Image image;

    /**
     * The constructor takes a face character and a point as parameters.
     * The former is used to construct the character representation, and the latter used to determine it's position on the board.
     * @param color
     * @param pos
     */
    public Face(String color, Point pos){
        position=pos;
        status = Status.ON_BOARD;
        this.color = color;
        try {
            image = ImageIO.read(new File("Images/" + color+"/"+color+"Face.png"));
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
        visitor.visitFace(this);
    }

    @Override
    public ArrayList<Reactable> accept(ArrayList<Reactable> reactableList) {
        reactableList.add(this);
        return reactableList;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void kill() { status=Status.CEMETERY; }

    @Override
    public void toLife() { status=Status.ON_BOARD; }

    @Override
    public Symbol getLeftSymbol() {
        return Symbol.NOTHING;
    }

    @Override
    public Symbol getRightSymbol() {
        return Symbol.NOTHING;
    }

    @Override
    public Symbol getTopSymbol() {
        return Symbol.NOTHING;
    }

    @Override
    public Symbol getBottomSymbol() { return Symbol.NOTHING; }
}
