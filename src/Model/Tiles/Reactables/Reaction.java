package Model.Tiles.Reactables;


import Model.Actions.Visitors.MoveActionVisitor;

public enum Reaction implements ReactionVisitorAcceptable {
    PUSHED_RIGHT {
        private MoveActionVisitor move;

        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) { move = rv.visitPushedRight(reactable); }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {
            rv.reversePush(move);
        }
    },

    PUSHED_LEFT {
        private MoveActionVisitor move;

        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) { move = rv.visitPushedLeft(reactable); }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {
            rv.reversePush(move);
        }
    },

    PUSHED_UP {
        private MoveActionVisitor move;

        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) { move = rv.visitPushedUp(reactable); }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {
            rv.reversePush(move);
        }
    },

    PUSHED_DOWN {
        private MoveActionVisitor move;

        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) { move = rv.visitPushedDown(reactable); }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {
            rv.reversePush(move);
        }
    },

    KILLED {
        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) {
            rv.visitKilled(reactable);
        }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {
            rv.reverseKilled(reactable);
        }
    },

    NONE {
        @Override
        public void accept(ReactionVisitor rv, Reactable reactable) {
            rv.visitNone(reactable);
        }

        @Override
        public void reverse(ReactionVisitor rv, Reactable reactable) {}
    }
}
