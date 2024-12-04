package congestion.calculator.exception;

import java.io.Serial;

public class TaxException extends Exception{

	@Serial
	private static final long serialVersionUID = 1L;

	public TaxException(String message) {
		super(message);
	}

}
