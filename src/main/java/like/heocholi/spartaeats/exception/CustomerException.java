package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class CustomerException extends CustomException{
    public CustomerException(ErrorType errorType) {
        super(errorType);
    }
}
