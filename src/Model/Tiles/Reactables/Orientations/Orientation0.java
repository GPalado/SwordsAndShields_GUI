package Model.Tiles.Reactables.Orientations;

import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Symbol;
import static Model.Tiles.Reactables.Piece.orientations;

/**
 * This class provides a representation of a '0' degree orientation for a piece.
 */
public class Orientation0 implements PieceOrientation {

    public Orientation0(){}

    @Override
    public void rotate(Piece piece, int amount) {
        piece.setOrientation(orientations.get(amount));
    }

    @Override
    public Symbol getLeftSymbol(Piece piece) {
        return piece.symbols[3];
    }

    @Override
    public Symbol getRightSymbol(Piece piece) {
        return piece.symbols[1];
    }

    @Override
    public Symbol getTopSymbol(Piece piece) {
        return piece.symbols[0];
    }

    @Override
    public Symbol getBottomSymbol(Piece piece) {
        return piece.symbols[2];
    }

    @Override
    public int getInt() {
        return 0;
    }
}
