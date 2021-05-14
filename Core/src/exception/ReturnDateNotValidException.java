package src.exception;

import java.time.LocalDateTime;

public class ReturnDateNotValidException extends Exception {
    public ReturnDateNotValidException(LocalDateTime returnDateTime){
        super("The return datetime must be greater or equal to the rent datetime.");
    }
}
