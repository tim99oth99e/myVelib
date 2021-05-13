package src.exception;

public class CreditCardNotValidException extends Exception {
    public CreditCardNotValidException(String cardNumber) {
        super("Credit card number should only contain digits and be of length 16, but is " + cardNumber);
    }
}
