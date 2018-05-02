package Model;

public class InvalidMoveException extends RuntimeException {

    public InvalidMoveException(){
        super();
    }

    public InvalidMoveException(String message){
        super(message);
    }

    public InvalidMoveException(String message, Throwable cause){
        super(message, cause);
    }

    public InvalidMoveException(Throwable cause){
        super(cause);
    }
}
