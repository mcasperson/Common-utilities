package com.redhat.ecs.commonutils;

public class ExceptionUtilities
{
	/**
	 * A standard function to deal with exceptions
	 * @param ex
	 */
	public static void handleException(final Exception ex)
	{
		System.out.println(ex.toString());
		ex.printStackTrace();
	}
}
