package exceptions;

import java.io.IOException;

public class SendInfoException extends IOException {
    public SendInfoException(IOException e) {
        super(e.getMessage());
    }
}
