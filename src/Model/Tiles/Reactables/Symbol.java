package Model.Tiles.Reactables;


import Model.InvalidMoveException;

/**
 * These enums represent the symbols that can make up a piece in the game.
 */
public enum Symbol implements SymbolReactable {
    SWORD_VERTICAL {
        @Override
        public Reaction reactWithSword(Direction placement) { return Reaction.KILLED; }

        @Override
        public Reaction reactWithShield(Direction placement) {
            switch(placement) {
                case ABOVE: return Reaction.PUSHED_UP;
                case BELOW: return Reaction.PUSHED_DOWN;
                default: throw new InvalidMoveException("Direction invalid");
            }
        }

        @Override
        public Reaction reactWithNothing(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction causes(Symbol symbol, Direction placement) { return symbol.reactWithSword(placement); }
    },

    SWORD_HORIZONTAL {
        @Override
        public Reaction reactWithSword(Direction placement) { return Reaction.KILLED; }

        @Override
        public Reaction reactWithShield(Direction placement) {
            switch(placement) {
                case TO_RIGHT: return Reaction.PUSHED_RIGHT;
                case TO_LEFT: return Reaction.PUSHED_LEFT;
                default: throw new InvalidMoveException("Direction invalid");
            }
        }

        @Override
        public Reaction reactWithNothing(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction causes(Symbol symbol, Direction placement) { return symbol.reactWithSword(placement); }
    },

    SHIELD {
        @Override
        public Reaction reactWithSword(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction reactWithShield(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction reactWithNothing(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction causes(Symbol symbol, Direction placement) { return symbol.reactWithShield(placement); }
    },

    NOTHING {
        @Override
        public Reaction reactWithSword(Direction placement) { return Reaction.KILLED; }

        @Override
        public Reaction reactWithShield(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction reactWithNothing(Direction placement) { return Reaction.NONE; }

        @Override
        public Reaction causes(Symbol symbol, Direction placement) { return symbol.reactWithNothing(placement); }
    }

}
