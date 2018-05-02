package Model.Tiles.Reactables;

public interface SymbolReactable {

    Reaction reactWithSword(Direction placement);

    Reaction reactWithShield(Direction placement);

    Reaction reactWithNothing(Direction placement);

    Reaction causes(Symbol symbol, Direction placement);
}
