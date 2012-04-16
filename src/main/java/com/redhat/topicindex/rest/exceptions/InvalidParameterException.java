package com.redhat.topicindex.rest.exceptions;

public class InvalidParameterException extends Exception
{
	private static final long serialVersionUID = 7976417994826671712L;

	public InvalidParameterException(final String message)
	{
		super(message);
	}
}
