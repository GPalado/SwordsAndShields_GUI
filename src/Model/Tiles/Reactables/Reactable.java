package Model.Tiles.Reactables;


import Model.Tiles.Tile;

/**
 * This interface determines the actions required by reactable tiles in the swords and shields game.
 */
public interface Reactable extends Tile {

    /**
     * The reactable tiles have statuses:
     * Unused, on the board, and dead (cemetery)
     */
    enum Status{
        UNUSED,
        ON_BOARD,
        CEMETERY
    }

    /**
     * This method returns the reactable tile's status
     * @return
     */
    Status getStatus();

    /**
     * This method sets the reactable tile's status to the appropriate "dead" status for that tile
     */
    void kill();

    /**
     * This method revives/reactivates a reactable tile.
     */
    void toLife();

    /**
     * This method returns the reactable's left symbol in it's current orientation
     * @return
     */
    Symbol getLeftSymbol();

    /**
     * This method returns the reactable's right symbol in it's current orientation
     * @return
     */
    Symbol getRightSymbol();

    /**
     * This method returns the reactable's top symbol in it's current orientation
     * @return
     */
    Symbol getTopSymbol();

    /**
     * This method returns the reactable's bottom symbol in it's current orientation
     * @return
     */
    Symbol getBottomSymbol();

}
