package hu.pte.schafferg.cellarManager.util;

public class ObjectMisMatchException extends RuntimeException {

	public ObjectMisMatchException() {
		super();
	}

	public ObjectMisMatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ObjectMisMatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public ObjectMisMatchException(String message) {
		super(message);
	}

	public ObjectMisMatchException(Throwable cause) {
		super(cause);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7707316558813478594L;

}
