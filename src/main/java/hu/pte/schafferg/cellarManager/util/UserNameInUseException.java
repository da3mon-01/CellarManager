package hu.pte.schafferg.cellarManager.util;

/**
 * Exception indicating the Username a user trying to take is already being used.
 * @author Da3mon
 *
 */
public class UserNameInUseException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5995242107647071507L;

	public UserNameInUseException() {
		super();
	}

	public UserNameInUseException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public UserNameInUseException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserNameInUseException(String message) {
		super(message);
	}

	public UserNameInUseException(Throwable cause) {
		super(cause);
	}
	
	

}
