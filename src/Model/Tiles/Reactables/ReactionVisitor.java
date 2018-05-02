package Model.Tiles.Reactables;

import Model.Actions.Visitors.*;
import Model.BoardModel;

public class ReactionVisitor {

    private BoardModel board;

    public ReactionVisitor(BoardModel b){
        board=b;
    }

    public void visitKilled(Reactable r){
        board.setEmpty(r.getPosition().x, r.getPosition().y);
        r.kill();
    }

    public MoveActionVisitor visitPushedRight(Reactable r){
        MoveActionVisitor move = new MoveRight((Piece)r);
        board.apply(move);
        return move;
    }

    public MoveActionVisitor visitPushedLeft(Reactable r){
        //note only pieces can be pushed
        MoveActionVisitor move = new MoveLeft((Piece)r);
        board.apply(move);
        return move;
    }

    public MoveActionVisitor visitPushedUp(Reactable r){
        MoveActionVisitor move = new MoveUp((Piece)r);
        board.apply(move);
        return move;
    }

    public MoveActionVisitor visitPushedDown(Reactable r){
        MoveActionVisitor move = new MoveDown((Piece)r);
        board.apply(move);
        return move;
    }

    public void visitNone(Reactable r){}

    public void reverseKilled(Reactable r){
        board.setPiece((Piece)r, r.getPosition().x, r.getPosition().y);
        r.toLife();
    }

    public void reversePush(MoveActionVisitor move){
        board.reverse(move);
    }

}
