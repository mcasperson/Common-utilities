/**
 * 
 */
package com.redhat.contentspec.exception;

/**
 * IndentationException class to be thrown when a Content Specification has incorrect indentation. 
 *
 */
public class IndentationException extends Exception {

	private static final long serialVersionUID = -4667574815926735224L;

	/**
	 * 
	 */
	public IndentationException() {
	}

	/**
	 * @param message
	 */
	public IndentationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public IndentationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public IndentationException(String message, Throwable cause) {
		super(message, cause);
	}

}
