package Model.Actions.Visitors;

import Model.Tiles.CreationSquare;
import Model.Tiles.EmptySpace;
import Model.Tiles.OutOfBounds;
import Model.Tiles.Reactables.Face;
import Model.Tiles.Reactables.Piece;

/**
 * This interface specifies the actions to be implemented by concrete visitors
 */
public interface Visitor {

    void visitPiece(Piece piece);

    void visitFace(Face face);

    void visitOOB(OutOfBounds oob);

    void visitEmpty(EmptySpace empty);

    void visitCreation(CreationSquare cs);

}
