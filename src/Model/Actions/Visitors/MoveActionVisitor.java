package Model.Actions.Visitors;

import Model.Actions.Action;
import Model.BoardModel;
import Model.Tiles.EmptySpace;
import Model.Tiles.OutOfBounds;
import Model.Tiles.Reactables.Face;
import Model.Tiles.Reactables.Piece;
import java.util.ArrayList;
import java.util.List;

/**
 * This abstract MOVE class provides general implementations of some of the methods specified by Action and Visitor interfaces.
 */
public abstract class MoveActionVisitor implements Action, Visitor{
    protected Piece pieceToPlace;
    protected Piece startingPiece;
    protected List<Piece> piecesPushed;
    protected BoardModel board;

    /**
     * The constructor takes the piece to be moved and the player involved.
     * @param p
     */
    public MoveActionVisitor(Piece p){
        pieceToPlace=p;
        startingPiece=p;
        piecesPushed=new ArrayList<>();
    }

    @Override
    public void visitFace(Face face) {
        if(pieceToPlace!=startingPiece) piecesPushed.add(pieceToPlace);
        pieceToPlace.kill();
    }

    @Override
    public void visitOOB(OutOfBounds oob) {
        if(pieceToPlace!=startingPiece) piecesPushed.add(pieceToPlace);
        pieceToPlace.kill();
    }

    @Override
    public void visitEmpty(EmptySpace empty) {
        if(pieceToPlace!=startingPiece) piecesPushed.add(pieceToPlace);
        board.setPiece(pieceToPlace, empty.getPosition().x, empty.getPosition().y);
    }

    @Override
    public Piece getPiece(){
        return startingPiece;
    }
}
