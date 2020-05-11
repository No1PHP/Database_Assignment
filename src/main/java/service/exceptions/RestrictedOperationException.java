package service.exceptions;

public class RestrictedOperationException extends RuntimeException {
    public RestrictedOperationException(String info) {
        super(info);
    }
}
