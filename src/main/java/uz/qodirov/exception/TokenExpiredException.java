package uz.qodirov.exception;

public class TokenExpiredException extends ApiException {
    private static final long serialVersionUID = 6151502520278153389L;

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(String msg) {
        super(msg);
    }
}
