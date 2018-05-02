package Model.Tiles.Reactables;

public interface ReactionVisitorAcceptable {

    void accept(ReactionVisitor rv, Reactable reactable);

    void reverse(ReactionVisitor rv, Reactable reactable);

}
