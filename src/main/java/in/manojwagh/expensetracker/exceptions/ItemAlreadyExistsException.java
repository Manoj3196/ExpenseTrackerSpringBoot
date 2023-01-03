package in.manojwagh.expensetracker.exceptions;

public class ItemAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemAlreadyExistsException(String msg) {
		super(msg);
	}

}
