package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class UserException extends CustomException{
    public UserException(ErrorType errorType) {
        super(errorType);
    }
}
