package hu.pte.schafferg.cellarManager.util;

public class TargetNotFoundInDBException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5479634377067826302L;

	public TargetNotFoundInDBException() {
		super();
	}

	public TargetNotFoundInDBException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TargetNotFoundInDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public TargetNotFoundInDBException(String message) {
		super(message);
	}

	public TargetNotFoundInDBException(Throwable cause) {
		super(cause);
	}

}
