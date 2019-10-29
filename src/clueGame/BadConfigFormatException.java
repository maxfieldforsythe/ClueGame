/*
 * Caroline Arndt
 * Maxfield Forsythe
 */
package clueGame;

public class BadConfigFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadConfigFormatException() {
		super("Error: Bad config format");
	}
	
	public BadConfigFormatException(String fileName) {
		super("Error: Bad config format:" + fileName);
	}

}
