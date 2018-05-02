package Model.Actions;

import Model.BoardModel;
import Model.Player;
import Model.Tiles.Reactables.*;

/**
 * This class provides the concrete implementation of a React action (reaction)
 */
public class ReactAction implements Action {

    private Player player;
    private Reactable r1;
    private Reactable r2;
    private BoardModel board;
    private ReactionVisitor rv;
    private Reaction reaction1, reaction2;

    /**
     * The constructor takes the two reactables to react, and the player involved in the reaction.
     * @param re1
     * @param re2
     * @param p
     */
    public ReactAction(Reactable re1, Reactable re2, Player p){
        r1=re1;
        r2=re2;
        player=p;
    }

    @Override
    public void execute(BoardModel board) {
        player.addAction(this);
        this.board=board;
        rv=new ReactionVisitor(board);
        //get symbols that will react and use them to find the reactions for each piece
        if(r1.getPosition().x<r2.getPosition().x){
            //find reaction of first reactable. Note that the direction given represents R1s position with regards to R2.
            reaction1 = r2.getLeftSymbol().causes(r1.getRightSymbol(), Direction.TO_LEFT);
            //find reaction of second piece
            reaction2 = r1.getRightSymbol().causes(r2.getLeftSymbol(), Direction.TO_RIGHT);
        } else if(r1.getPosition().x>r2.getPosition().x){
            reaction1 = r2.getRightSymbol().causes(r1.getLeftSymbol(), Direction.TO_RIGHT);
            reaction2 = r1.getLeftSymbol().causes(r2.getRightSymbol(), Direction.TO_LEFT);
        } else if(r1.getPosition().y>r2.getPosition().y){
            reaction1 = r2.getBottomSymbol().causes(r1.getTopSymbol(), Direction.BELOW);
            reaction2 = r1.getTopSymbol().causes(r2.getBottomSymbol(), Direction.ABOVE);
        } else { //if(r1.getPosition().y<r2.getPosition().y)
            reaction1 = r2.getTopSymbol().causes(r1.getBottomSymbol(), Direction.ABOVE);
            reaction2 = r1.getBottomSymbol().causes(r2.getTopSymbol(), Direction.BELOW);
        }
        reaction1.accept(rv, r1);
        reaction2.accept(rv, r2);
    }

    @Override
    public void undo() {
        reaction1.reverse(rv, r1);
        reaction2.reverse(rv, r2);
    }

    @Override
    public Piece getPiece() {
        return (Piece)r1;
    }
}
