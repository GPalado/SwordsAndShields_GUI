package Model.Actions;

import Model.BoardModel;
import Model.InvalidMoveException;
import Model.Player;
import Model.Tiles.Reactables.Orientations.PieceOrientation;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;

/**
 * This class provides the concrete implementation of the Create action
 */
public class CreateAction implements Action {

    private Player player;
    private Piece piece;
    private PieceOrientation orientation;

    /**
     * The constructor takes the piece to be created, the orientation of the piece, and the player involved in the creation.
     * @param p
     * @param pl
     */
    public CreateAction(Piece p, PieceOrientation o, Player pl){
        if(p==null) throw new InvalidMoveException("Cannot create null piece");
        if(pl==null) throw new InvalidMoveException("Null player");
        piece=p;
        player=pl;
        orientation=o;
    }

    @Override
    public void execute(BoardModel board) {
        if(player.creationSquare.isOccupied()){
            throw new InvalidMoveException("Your creation square is occupied");
        }
        if(player.hasCreated()){
            throw new InvalidMoveException("You cannot create twice in one move");
        }
        if(player.hasMoved()){
            throw new InvalidMoveException("You can only create at the start of your turn");
        }
        if(piece.getStatus().equals(Reactable.Status.CEMETERY)){
            throw new InvalidMoveException("Cannot create piece that is deado");
        }
        if(piece.getStatus().equals(Reactable.Status.ON_BOARD)){
            throw new InvalidMoveException("Cannot create a piece already on the board");
        }
        piece.setOrientation(orientation);
        piece.toLife();
        player.creationSquare.setPiece(piece);
        player.addAction(this);
        player.setCreated(true);
    }

    @Override
    public void undo() {
        player.creationSquare.setPiece(null);
        player.setCreated(false);
        piece.backToUnused();
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
