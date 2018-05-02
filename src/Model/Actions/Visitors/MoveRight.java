package Model.Actions.Visitors;


import Model.BoardModel;
import Model.Tiles.CreationSquare;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;
import Model.Tiles.Tile;

import static View.GraphicalInterface.BOARD_SIZE;

/**
 * This class provides the full implementation of the move right action
 */
public class MoveRight extends MoveActionVisitor {

    public MoveRight(Piece p){
        super(p);
    }

    @Override
    public void execute(BoardModel board) {
        this.board=board;
        board.setEmpty(pieceToPlace.getPosition().x, pieceToPlace.getPosition().y);
        Tile shift = board.getRightOf(startingPiece);
        shift.accept(this);
        startingPiece.setMoved(true);
    }

    @Override
    public void visitPiece(Piece piece) {
        if(pieceToPlace!=startingPiece)piecesPushed.add(pieceToPlace);
        board.setPiece(pieceToPlace, piece.getPosition().x, piece.getPosition().y);
        pieceToPlace=piece;
        Tile shift = board.getRightOf(pieceToPlace);
        shift.accept(this);
    }

    @Override
    public void visitCreation(CreationSquare cs) {
        if(pieceToPlace!=startingPiece) piecesPushed.add(pieceToPlace);
        if(cs.isOccupied()){
            Piece temp = cs.getPiece();
            cs.setPiece(pieceToPlace);
            pieceToPlace=temp;
            Tile shift = board.getRightOf(pieceToPlace);
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
                board.apply(new MoveLeft(startingPiece));
            }
        } else if(startingPiece.getPosition().x+piecesPushed.size()>BOARD_SIZE-1) { //move all pushed pieces and bring other piece back to life
            if(piecesPushed.size()==1){
                board.apply(new MoveLeft(startingPiece));
            } else {
                board.apply(new MoveLeft(piecesPushed.get(piecesPushed.size() - 2)));
            }
            piecesPushed.get(piecesPushed.size()-1).toLife();
            board.setPiece(piecesPushed.get(piecesPushed.size()-1), BOARD_SIZE-1, startingPiece.getPosition().y);
        } else { //move all pushed pieces back
            board.apply(new MoveLeft(piecesPushed.get(piecesPushed.size()-1)));
        }
        startingPiece.setMoved(false);
    }
}
