package exception;

public class MyServiceException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyServiceException(String message) {
		super(message);
	}
}
