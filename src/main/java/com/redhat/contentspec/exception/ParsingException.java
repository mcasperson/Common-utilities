/**
 * 
 */
package com.redhat.contentspec.exception;

/**
 * IndentationException class to be thrown when a Content Specification has incorrect indentation. 
 *
 */
public class ParsingException extends Exception {

	private static final long serialVersionUID = 6946236720289332910L;

	/**
	 * 
	 */
	public ParsingException() {
	}

	/**
	 * @param message
	 */
	public ParsingException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ParsingException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ParsingException(String message, Throwable cause) {
		super(message, cause);
	}

}
