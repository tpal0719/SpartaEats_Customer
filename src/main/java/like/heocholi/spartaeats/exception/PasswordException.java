package like.heocholi.spartaeats.exception;

import like.heocholi.spartaeats.constants.ErrorType;

public class PasswordException extends CustomerException {
	public PasswordException(ErrorType errorType) {
		super(errorType);
	}
}
