package Model.Tiles.Reactables;

import Model.Actions.Visitors.Visitor;
import Model.InvalidMoveException;
import Model.Tiles.Reactables.Orientations.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * This class provides a representation of a Piece in the Swords and Shields game.
 */
public class Piece extends Observable implements Reactable {

    public static final Map<Symbol, Character> symbolCharacterMap;
    public static final Map<Integer, PieceOrientation> orientations;
    public final Character letter;
    public final Symbol[] symbols;
    private Map<PieceOrientation, Image> pieceImages;
    private Point position;
    private Status status;
    private PieceOrientation pieceOrientation;
    private ArrayList<Observer> observers;
    private boolean moved;

    static {
        symbolCharacterMap = new HashMap<>();
        symbolCharacterMap.put(Symbol.SWORD_VERTICAL, '|');
        symbolCharacterMap.put(Symbol.SWORD_HORIZONTAL, '-');
        symbolCharacterMap.put(Symbol.SHIELD, '#');
        symbolCharacterMap.put(Symbol.NOTHING, ' ');
        orientations=new HashMap<>();
        orientations.put(0, new Orientation0());
        orientations.put(90, new Orientation90());
        orientations.put(180, new Orientation180());
        orientations.put(270, new Orientation270());
    }

    /**
     * The constructor takes a character array representation of the piece.
     * It sets initial values of moved to false, and status to unused.
     * Note, the Symbol array symbols are in order of NORTH, EAST, SOUTH, WEST
     */
    public Piece(Symbol[] symbols, char letter){
        if(symbols.length!=4){
            throw new IllegalArgumentException("There should be 4 symbols in the array");
        }
        observers = new ArrayList<>();
        pieceImages = new HashMap<>();
        status=Status.UNUSED;
        this.symbols=symbols;
        this.letter=letter;
        pieceOrientation=orientations.get(0);
        initializeImages();
    }

    private void initializeImages(){
        for(int i=0; i<360; i+=90) {
            BufferedImage img;
            try {
                img = ImageIO.read(new File("Images/" + ((Character.isUpperCase(letter)) ? "Green/" : "Yellow/") + letter + i +".png"));
            } catch (IOException e) {
                throw new NullPointerException("Image not found");
            }
            pieceImages.put(orientations.get(i), img);
        }
    }

    /**
     * This method sets the 'moved' field to the given boolean
     * @param moved
     */
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    /**
     * This method returns the value of the 'moved' field
     * @return
     */
    public boolean getMoved(){
        return moved;
    }

    /**
     * This method sets the piece's position to the given coordinates.
     * @param x
     * @param y
     */
    public void setPosition(int x, int y){
        position=new Point(x, y);
    }

    /**
     * This method rotates the piece by the given amount (0/90/180/270)
     * @param amount
     */
    public void rotate(int amount){
        if(!orientations.keySet().contains(amount)){
            throw new InvalidMoveException("That is an invalid orientation");
        }
        pieceOrientation.rotate(this, amount);
    }

    /**
     * This method sets the piece's orientation to the given orientation
     * @param o
     */
    public void setOrientation(PieceOrientation o){
        if(!orientations.values().contains(o)){
            throw new InvalidMoveException("That is an invalid orientation");
        }
        pieceOrientation=o;
        notifyObservers();
    }

    /**
     * This method sets the piece's orientation based on the given integer
     * @param o
     */
    public void setOrientation(int o){
        if(!orientations.keySet().contains(o)){
            throw new InvalidMoveException("That is an invalid orientation");
        }
        pieceOrientation=orientations.get(o);
        notifyObservers();
    }

    /**
     * This method returns the piece's orientation
     * @return
     */
    public PieceOrientation getOrientation(){
        return pieceOrientation;
    }

    /**
     * This method reverts the piece back to the unused state.
     */
    public void backToUnused(){ status=Status.UNUSED; }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public void kill() {
        status=Status.CEMETERY;
    }

    @Override
    public void toLife() {
        status=Status.ON_BOARD;
    }

    @Override
    public Symbol getLeftSymbol() {
        return pieceOrientation.getLeftSymbol(this);
    }

    @Override
    public Symbol getRightSymbol() {
        return pieceOrientation.getRightSymbol(this);
    }

    @Override
    public Symbol getTopSymbol() {
        return pieceOrientation.getTopSymbol(this);
    }

    @Override
    public Symbol getBottomSymbol() {
        return pieceOrientation.getBottomSymbol(this);
    }

    @Override
    public Image getImage() {
        return pieceImages.get(pieceOrientation);
    }

    @Override
    public Point getPosition() { return position; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitPiece(this);
    }

    @Override
    public ArrayList<Reactable> accept(ArrayList<Reactable> reactableList) {
        reactableList.add(this);
        return reactableList;
    }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
         for(Observer o : observers){
             o.update(this, null);
         }
    }
}
