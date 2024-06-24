package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class PickException extends CustomException{

    public PickException(ErrorType errorType) {
        super(errorType);
    }

}
