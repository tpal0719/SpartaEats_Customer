package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class CartException extends CustomException{
	public CartException(ErrorType errorType) {
		super(errorType);
	}
}
