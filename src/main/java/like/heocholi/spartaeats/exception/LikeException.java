package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class LikeException extends CustomerException {
    public LikeException(ErrorType errorType) {
        super(errorType);
    }
}