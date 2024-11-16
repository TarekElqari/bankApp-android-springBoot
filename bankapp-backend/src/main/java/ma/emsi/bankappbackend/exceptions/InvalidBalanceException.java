package ma.emsi.bankappbackend.exceptions;


public class InvalidBalanceException extends RuntimeException {
    public InvalidBalanceException(String message) {
        super(message);
    }
}
