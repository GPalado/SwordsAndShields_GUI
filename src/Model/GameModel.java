package Model;

import Model.Actions.*;
import Model.Actions.Visitors.*;
import Model.Tiles.*;
import Model.Tiles.Reactables.Face;
import Model.Tiles.Reactables.Orientations.PieceOrientation;
import Model.Tiles.Reactables.Piece;
import Model.Tiles.Reactables.Reactable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is used to represent the board of a swords and shields game.
 */
public class GameModel extends Observable {
    public static final Point p1CreationCoords = new Point(7, 7);
    public static final Point p2CreationCoords = new Point(2, 2);
    public static final Point p1FaceCoords = new Point(8, 8);
    public static final Point p2FaceCoords = new Point(1, 1);
    public static final Color[] colors = new Color[]{Color.yellow, Color.green};
    private Player player1, player2;
    private Player currentPlayer;
    private BoardModel board;
    private ArrayList<Observer> observers;
    private String status="";

    public GameModel(){
        player1 = new Player(colors[0], false, new Face("Yellow", p1FaceCoords), new CreationSquare(p1CreationCoords, "Yellow"));
        player2 = new Player(colors[1], true, new Face("Green", p2FaceCoords), new CreationSquare(p2CreationCoords, "Green"));
        board = new BoardModel(player1, player2);
        currentPlayer = player1;
        observers=new ArrayList<>();
    }

    /**
     * This method executes the 'pass' action of a player
     */
    public void pass(){
        if(currentPlayer.hasCreated()||currentPlayer.hasMoved()) {
            currentPlayer.pass();
            status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has passed their turn - "+
                    (currentPlayer.color.equals(Color.yellow)?"Green ":"Yellow ") + "Player, it's your turn now!";
            swapPlayers();
        } else {
            currentPlayer.setCreated(true);
            status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has passed their creation";
        }
        notifyObservers();
        board.notifyObservers();
    }

    /**
     * This method executes the 'moveRight' action of a player on a certain piece
     * @param piece
     */
    public void moveRight(Piece piece){
        MoveActionVisitor move = new MoveRight(piece);
        if(currentPlayer.getPiecesMoved().contains(piece)){
            throw new InvalidMoveException("Cannot move a piece that has already been moved!");
        } if(currentPlayer.getPiece(piece.letter)==null){
            throw new InvalidMoveException("That piece isn't one of the current player's pieces!");
        }
        board.apply(move);
        currentPlayer.addAction(move);
        currentPlayer.pieceMoved(piece);
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has moved their piece!";
        notifyObservers();
        //todo reactions?
    }

    /**
     * This method executes the 'moveLeft' action of a player on a certain piece
     * @param piece
     */
    public void moveLeft(Piece piece){
        MoveActionVisitor move = new MoveLeft(piece);
        if(currentPlayer.getPiecesMoved().contains(piece)){
            throw new InvalidMoveException("Cannot move a piece that has already been moved!");
        } if(currentPlayer.getPiece(piece.letter)==null){
            throw new InvalidMoveException("That piece isn't one of the current player's pieces!");
        }
        board.apply(move);
        currentPlayer.addAction(move);
        currentPlayer.pieceMoved(piece);
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has moved their piece!";
        notifyObservers();
        //check for reactions
//            reactionCheck(piece);
    }

    /**
     * This method executes the 'moveUp' action of a player on a certain piece
     * @param piece
     */
    public void moveUp(Piece piece){
        MoveActionVisitor move = new MoveUp(piece);
        if(currentPlayer.getPiecesMoved().contains(piece)){
            throw new InvalidMoveException("Cannot move a piece that has already been moved!");
        } if(currentPlayer.getPiece(piece.letter)==null){
            throw new InvalidMoveException("That piece isn't one of the current player's pieces!");
        }
        board.apply(move);
        currentPlayer.addAction(move);
        currentPlayer.pieceMoved(piece);
        //check for reactions
//            reactionCheck(piece);
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has moved their piece!";
        notifyObservers();
    }

    /**
     * This method executes the 'moveDown' action of a player on a certain piece
     * @param piece
     */
    public void moveDown(Piece piece){
        MoveActionVisitor move = new MoveDown(piece);
        if(currentPlayer.getPiecesMoved().contains(piece)){
            throw new InvalidMoveException("Cannot move a piece that has already been moved!");
        } if(currentPlayer.getPiece(piece.letter)==null){
            throw new InvalidMoveException("That piece isn't one of the current player's pieces!");
        }
        board.apply(move);
        currentPlayer.pieceMoved(piece);
        currentPlayer.addAction(move);
        //check for reactions
//            reactionCheck(piece);
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has moved their piece!";
        notifyObservers();
    }

    /**
     * This method executes the 'create' action of a player on a certain piece
     * @param piece
     */
    public void create(Piece piece){
        if(currentPlayer.getPiece(piece.letter)==null){
            throw new InvalidMoveException("That piece is not the current player's piece!");
        }
        CreateAction create = new CreateAction(currentPlayer.getPiece(piece.letter), piece.getOrientation(), currentPlayer);
        board.apply(create);
        //check for reactions
//        reactionCheck(piece);
        currentPlayer.notifyObservers();
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has created a piece!";
        notifyObservers();
    }

    public void rotate(char letter, PieceOrientation newO, PieceOrientation oldO){
        RotateAction rotate = new RotateAction(currentPlayer.getPiece(letter), newO, oldO, currentPlayer);
        board.apply(rotate);
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has rotated their piece!";
        notifyObservers();
    }

    /**
     * This method returns a string specifying which player surrendered
     * @return
     */
    public String surrender(){
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player has surrendered! Game over";
        return status;
    }

    /**
     * This method returns the current player.
     * @return
     */
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    /**
     * This method executes the 'undo' action of a player
     */
    public void undo(){
        Action toReverse = currentPlayer.undo();
        board.reverse(toReverse);
        currentPlayer.notifyObservers();
        status = (currentPlayer.color.equals(Color.yellow)?"Yellow ":"Green ") +"Player undid their last move!";
        notifyObservers();
        //check for reactions if the piece involved is still on the board
//        if(toReverse.getPiece().getStatus().equals(Reactable.Status.ON_BOARD)) {
//            reactionCheck(toReverse.getPiece());
//        }
    }

    /**
     * This method translates user input into player moves and triggers the relevant actions.
     * TO BE REFACTORED
     */
//    public void playerMove(String in) throws InvalidMoveException{
//        String[] input = in.split(" ");
//        if (input.length > 3) {
//            throw new InvalidMoveException("Input too long!");
//        }
//        if (input[0].toLowerCase().equals("create")) {
////            if (input.length < 3) {
////                throw new InvalidMoveException("Input too short!");
////            }
////            char c = parseChar(input[1]);
////            int i = parseInt(input[2]);
//////            CreateAction create = new CreateAction(currentPlayer.getPiece(c), i, currentPlayer);
////            board.apply(create);
////            //check for reactions
////            reactionCheck(currentPlayer.getPiece(c));
//        } else if (input[0].toLowerCase().equals("rotate")) {
//            if (input.length < 3) {
//                System.out.println("Input too short!");
//                return;
//            }
//            try {
//                char c = parseChar(input[1]);
//                int i = parseInt(input[2]);
//                RotateAction rotate = new RotateAction(currentPlayer.getPiece(c), i, currentPlayer);
//                board.apply(rotate);
//                //check for reactions
//                reactionCheck(currentPlayer.getPiece(c));
//            } catch (InvalidMoveException e){
//                System.out.println(e.getMessage());
//                return;
//            }
//        } else if (input[0].toLowerCase().equals("move")) {
////            if (input.length < 3) {
////                System.out.println("Input too short!");
////                return;
////            }
////            try {
////                MoveActionVisitor move;
////                char c = parseChar(input[1]);
////                String direction = input[2];
////                if (direction.toLowerCase().equals("up")) {
////                    move = new MoveUp(currentPlayer.getPiece(c));
////
////                } else if (direction.toLowerCase().equals("down")) {
////                    move = new MoveDown(currentPlayer.getPiece(c));
////                } else if (direction.toLowerCase().equals("left")) {
////                    move = new MoveLeft(currentPlayer.getPiece(c));
////                } else if (direction.toLowerCase().equals("right")) {
////                    move = new MoveRight(currentPlayer.getPiece(c));
////                } else {
////                    throw new InvalidMoveException("Invalid direction");
////                }
////                if(currentPlayer.getPiecesMoved().contains(currentPlayer.getPiece(c))){
////                    throw new InvalidMoveException("Cannot move a piece that has already been moved!");
////                }
////                board.apply(move);
////                currentPlayer.addAction(move);
////                currentPlayer.pieceMoved(currentPlayer.getPiece(c));
////                //check for reactions
////                reactionCheck(currentPlayer.getPiece(c));
////            } catch (InvalidMoveException e){
////                System.out.println(e.getMessage());
////                return;
////            }
//        } else if (input[0].toLowerCase().equals("undo")) {
////            if(input.length>1){
////                System.out.println("Input too long for undo command");
////                return;
////            }
////            try {
////                Action toReverse = currentPlayer.undo();
////                board.reverse(toReverse);
////                //check for reactions if the piece involved is still on the board
////                if(toReverse.getPiece().getStatus().equals(Reactable.Status.ON_BOARD)) {
////                    reactionCheck(toReverse.getPiece());
////                }
////            } catch (InvalidMoveException e){
////                System.out.println(e.getMessage());
////                return;
////            }
//        } else if (input[0].toLowerCase().equals("pass")) {
////            if(input.length>1){
////                System.out.println("Input too long for pass command");
////                return;
////            }
////            if(currentPlayer.hasCreated()||currentPlayer.hasMoved()) {
////                currentPlayer.pass();
////                swapPlayers();
////            } else {
////                System.out.println("Create option passed.");
////                currentPlayer.setCreated(true);
////            }
//        } else {
//            System.out.println("Unrecognizable command: '"+in+"'");
//            return;
//        }
//        notifyObservers();
//    }
//
//    public void reactionCheck(Piece piece){
//        if(!board.offerReactions(piece).isEmpty()){
//            //todo how to require a reaction? NOT YET
////            System.out.println("Choose a reaction for "+piece.letter+": (enter a character)");
////            printTiles(board.offerReactions(piece));
////            Map<Character, Reactable> chars = new HashMap<>();
////            for(Reactable r : board.offerReactions(piece)){
////                chars.put(r.getLetter(), r);
////            }
////            String input = scanner.next();
////            char c = input.charAt(0);
////            scanner.nextLine();
////            while(!input.equals("undo") && !chars.keySet().contains(c)){
////                System.out.println("Sorry, that wasn't one of the options. Choose again. (Case Sensitive)");
////                printTiles(board.offerReactions(piece));
////                input = scanner.next();
////                c = input.charAt(0);
////                scanner.nextLine();
////            }
////            if(input.equals("undo")){
////                playerMove(input);
////            } else {
////                board.apply(new ReactAction(piece, chars.get(c), currentPlayer));
////            }
//        }
//    }

    /**
     * This method checks the "faces" of each player to determine if the game has been won.
     * @return boolean [true if game won, false otherwise]
     */
    private boolean isGameOver(){
        return player1.face.getStatus().equals(Reactable.Status.CEMETERY) || player2.face.getStatus().equals(Reactable.Status.CEMETERY);
        //todo notify listeners?
    }

    /**
     * This is a helper method that swaps the current player after each turn
     */
    private void swapPlayers(){
        if (currentPlayer == player2) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    public BoardModel getBoard(){
        return board;
    }

    public Player getYellowPlayer(){ return player1; }

    public Player getGreenPlayer() { return player2; }

    @Override
    public synchronized void addObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o:observers){
            o.update(this, null);
        }
    }

    /**
     * This method returns the string that is the game's current status
     * @return
     */
    public String getMessage(){
        return status;
    }
}
