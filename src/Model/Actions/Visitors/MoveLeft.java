package Model.Actions.Visitors;

import Model.BoardModel;
import Model.Tiles.CreationSquare;
import Model.Tiles.EmptySpace;
import Model.Tiles.OutOfBounds;
import Model.Tiles.Reactables.Face;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;
import Model.Tiles.Tile;

/**
 * This class provides the full implementation of the move left action
 */
public class MoveLeft extends MoveActionVisitor {

    public MoveLeft(Piece p){
        super(p);
    }

    @Override
    public void execute(BoardModel board) {
        this.board=board;
        board.setEmpty(pieceToPlace.getPosition().x, pieceToPlace.getPosition().y);
        Tile shift = board.getLeftOf(startingPiece);
        shift.accept(this);
        startingPiece.setMoved(true);
    }


    @Override
    public void visitPiece(Piece piece) {
        if(pieceToPlace!=startingPiece)piecesPushed.add(pieceToPlace);
        board.setPiece(pieceToPlace, piece.getPosition().x, piece.getPosition().y);
        pieceToPlace=piece;
        Tile shift = board.getLeftOf(pieceToPlace);
        shift.accept(this);
    }

    @Override
    public void visitCreation(CreationSquare cs) {
        if(pieceToPlace!=startingPiece) piecesPushed.add(pieceToPlace);
        if(cs.isOccupied()){
            Piece temp = cs.getPiece();
            cs.setPiece(pieceToPlace);
            pieceToPlace=temp;
            Tile shift = board.getLeftOf(pieceToPlace);
            shift.accept(this);
        } else {
            cs.setPiece(pieceToPlace);
        }
    }

    @Override
    public void undo() {
        if(piecesPushed.isEmpty()){ //just move the one piece
            if(startingPiece.getStatus().equals(Reactable.Status.CEMETERY)){
                startingPiece.toLife();
                board.setPiece(startingPiece, startingPiece.getPosition().x, startingPiece.getPosition().y);
            } else {
                board.apply(new MoveRight(startingPiece));
            }
        } else if(startingPiece.getPosition().x-piecesPushed.size()<0) { //move all pushed pieces and bring other piece back to life
            if(piecesPushed.size()==1){
                board.apply(new MoveRight(startingPiece));
            } else {
                board.apply(new MoveRight(piecesPushed.get(piecesPushed.size() - 2)));
            }
            piecesPushed.get(piecesPushed.size()-1).toLife();
            board.setPiece(piecesPushed.get(piecesPushed.size()-1), 0, startingPiece.getPosition().y);
        } else { //move all pushed pieces back
            board.apply(new MoveRight(piecesPushed.get(piecesPushed.size()-1)));
        }
        startingPiece.setMoved(false);
    }
}
