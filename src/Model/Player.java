package Model;

import Model.Actions.Action;
import Model.Tiles.CreationSquare;
import Model.Tiles.Reactables.Face;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;
import Model.Tiles.Reactables.Symbol;
import java.awt.*;
import java.util.*;

/**
 * This class is a representation of a Player in the Swords an Shields game.
 */
public class Player extends Observable {
    public final Color color;
    public final Face face;
    public final CreationSquare creationSquare;
    public final boolean isCaps;
    private Map<Character, Piece> pieces = new HashMap<>();
    private Deque<Action> actions = new ArrayDeque<>();
    private Set<Piece> piecesMoved = new HashSet<>();
    private boolean hasCreated;
    private ArrayList<Observer> observers;

    /**
     * The constructor takes the player's color, whether their piece representations will be CAPITALS or lowercase,
     * their face, and their creation square.
     * The constructor then uses this information to initialize the player's 24 pieces.
     * @param c
     * @param caps
     * @param f
     * @param cs
     */
    public Player(Color c, boolean caps, Face f, CreationSquare cs){
        observers = new ArrayList<>();
        color=c;
        isCaps=caps;
        hasCreated = false;
        face=f;
        creationSquare=cs;
        initializePieces();
    }

    /**
     * This method returns the pieces that the player has moved during their current turn.
     * @return
     */
    public Set<Piece> getPiecesMoved(){
        return piecesMoved;
    }

    /**
     * This method receives a piece that has been moved in this players current turn and adds it to the set of piecesMoved
     * @param p
     */
    public void pieceMoved(Piece p){
        piecesMoved.add(p);
//        p.setMoved(true);
//        notifyObservers();
    }

    /**
     * This method receives a piece that is to be removed from the set of piecesMoved
     * @param p
     */
    public void pieceNotMoved(Piece p){
        if(piecesMoved.contains(p)) {
            piecesMoved.remove(p);
//            p.setMoved(false);
        }
    }

    /**
     * This method adds the given action to the player's Deque of actions
     * @param action
     */
    public void addAction(Action action){
        actions.add(action);
//        notifyObservers();
    }

    /**
     * This method returns the most recent action made by the player to be undone.
     * @return
     */
    public Action undo(){
        if(actions.isEmpty()){
            throw new InvalidMoveException("Cannot undo any further");
        }
        pieceNotMoved(actions.peek().getPiece());
        return actions.pollLast();
    }

    /**
     * This method returns a boolean representing whether the player has created a piece/skipped creating a piece this turn or not.
     * @return
     */
    public boolean hasCreated(){
        return hasCreated;
    }

    /**
     * This method takes a boolean, which hasCreated is to be set to.
     * @param b
     */
    public void setCreated(boolean b){
        hasCreated=b;
    }

    /**
     * This method returns a boolean representing whether the player has moved a piece this turn or not.
     * @return
     */
    public boolean hasMoved(){ return !piecesMoved.isEmpty(); }

    /**
     * This method returns all the player's unused pieces to offer them choices for creation.
     */
    public ArrayList<Piece> getUnusedPieces(){
        ArrayList<Piece> unusedPieces = new ArrayList<>();
        for(Piece p:pieces.values()){
            if(p.getStatus().equals(Reactable.Status.UNUSED)){
                unusedPieces.add(p);
            }
        }
        return unusedPieces;
    }

    /**
     * This method returns all the player's dead pieces to draw in the cemetery.
     */
    public ArrayList<Piece> getDeadPieces(){
        ArrayList<Piece> deadPieces = new ArrayList<>();
        for(Piece p:pieces.values()){
            if(p.getStatus().equals(Reactable.Status.CEMETERY)){
                deadPieces.add(p);
            }
        }
        return deadPieces;
    }

    /**
     * This move passes a players turn.
     * This involves clearing the player's actions and pieces moved, and setting hasCreated to false.
     */
    public void pass(){
        actions=new ArrayDeque<>();
        hasCreated=false;
        for(Piece p : piecesMoved) {
            p.setMoved(false);
        }
        piecesMoved.clear();
    }

    /**
     * This method returns the player's piece which corresponds to the given character.
     * @param c
     * @return
     */
    public Piece getPiece(char c){
        return pieces.get(c);
    }

    /**
     * This method initializes the player's pieces depending on whether their representations are CAPITALIZED or lowercase
     */
    private void initializePieces() {
        if (isCaps) {
            char c = 'A';
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SWORD_VERTICAL, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
        } else {
            char c = 'a';
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL, Symbol.SWORD_VERTICAL, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SWORD_VERTICAL, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SHIELD, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.NOTHING, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.SWORD_VERTICAL, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.NOTHING, Symbol.NOTHING, Symbol.SWORD_HORIZONTAL}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.NOTHING, Symbol.NOTHING}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.SWORD_VERTICAL, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
            pieces.put(c, new Piece(new Symbol[]{Symbol.NOTHING, Symbol.SHIELD, Symbol.SHIELD, Symbol.SHIELD}, c++));
        }
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
