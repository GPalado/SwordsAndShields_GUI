package Model.Actions;

import Model.BoardModel;
import Model.InvalidMoveException;
import Model.Player;
import Model.Tiles.Reactables.Orientations.PieceOrientation;
import Model.Tiles.Reactables.Piece;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class provides the concrete implementation of a Rotate action
 */
public class RotateAction implements Action {
    private Player player;
    private Piece piece;
    private PieceOrientation originalOrientation;
    private PieceOrientation newOrientation;

    /**
     * The constructor takes the piece to be rotated, the new orientation, and the player involved.
     * @param p
     * @param newO
     * @param oldO
     * @param pl
     */
    public RotateAction(Piece p, PieceOrientation newO, PieceOrientation oldO, Player pl){
        if(p==null) throw new InvalidMoveException("Cannot rotate a null piece");
        if(pl.getPiecesMoved().contains(p)) throw new InvalidMoveException("Cannot move a piece that has already been moved!");
        newOrientation = newO;
        originalOrientation=oldO;
        piece=p;
        player=pl;
    }

    @Override
    public void execute(BoardModel board) {
        piece.setOrientation(newOrientation);
        piece.setMoved(true);
        player.pieceMoved(piece);
        player.addAction(this);
    }

    @Override
    public void undo() {
        piece.setOrientation(originalOrientation);
        player.pieceNotMoved(piece);
        piece.setMoved(false);
    }

    @Override
    public Piece getPiece() {
        return piece;
    }
}
