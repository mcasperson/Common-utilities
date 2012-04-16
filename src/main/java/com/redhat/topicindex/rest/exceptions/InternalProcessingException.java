package com.redhat.topicindex.rest.exceptions;

public class InternalProcessingException extends Exception
{
	private static final long serialVersionUID = 1609424541151735872L;
	
	public InternalProcessingException(final String message)
	{
		super(message);
	}
}
