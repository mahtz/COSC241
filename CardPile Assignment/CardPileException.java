/**
 * CardPileException class that extends RuntimeException. It is thrown with an appropriate error
 * message when there is an error with input.
 *
 * @author Mathew Shields
 */
public class CardPileException extends RuntimeException{

    /** Private static final long serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Calls the super class with the message as a parameter.
     */
    public CardPileException(String message){
        super(message);
    }
}
