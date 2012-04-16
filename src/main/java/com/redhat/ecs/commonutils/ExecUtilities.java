package com.redhat.ecs.commonutils;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExecUtilities
{
	/**
	 * Run a Process, and read the various streams so there is not a buffer
	 * overrun.
	 * 
	 * @param p The Process to be executed
	 * @param output The Stream to receive the Process' output stream
	 * @return true if the Process returned 0, false otherwise
	 */
	public static boolean runCommand(final Process p, final OutputStream output)
	{
		return runCommand(p, output, output, new ArrayList<String>());
	}

	/**
	 * Run a Process, and read the various streams so there is not a buffer
	 * overrun.
	 * 
	 * @param p The Process to be executed
	 * @param output The Stream to receive the Process' output stream
	 * @param doNotPrintStrings A collection of strings that should not be
	 *            dumped to std.out
	 * @return true if the Process returned 0, false otherwise
	 */
	public static boolean runCommand(final Process p, final OutputStream output, final List<String> doNotPrintStrings)
	{
		return runCommand(p, output, output, doNotPrintStrings);
	}

	/**
	 * Run a Process, and read the various streams so there is not a buffer
	 * overrun.
	 * 
	 * @param p The Process to be executed
	 * @param output The Stream to receive the Process' output stream
	 * @param error The Stream to receive the Process' error stream
	 * @param doNotPrintStrings A collection of strings that should not be
	 *            dumped to std.out
	 * @return true if the Process returned 0, false otherwise
	 */
	public static boolean runCommand(final Process p, final OutputStream output, final OutputStream error, final List<String> doNotPrintStrings)
	{
		final StreamRedirector errorStream = new StreamRedirector(p.getErrorStream(), error, doNotPrintStrings);
		final StreamRedirector outputStream = new StreamRedirector(p.getInputStream(), output, doNotPrintStrings);

		errorStream.start();
		outputStream.start();

		try
		{
			final boolean retValue = p.waitFor() == 0;

			/*
			 * give the threads time to collect the final output from the
			 * Process
			 */
			errorStream.join();
			outputStream.join();

			return retValue;
		}
		catch (final InterruptedException ex)
		{
			ExceptionUtilities.handleException(ex);
			return false;
		}
	}

	/**
	 * @return the current environment variables as an array in the format
	 *         "VARIABLE=value"
	 */
	public static String[] getEnvironmentVars()
	{
		final Map<String, String> env = System.getenv();
		final String[] envArray = new String[env.size()];

		int i = 0;

		for (final String key : env.keySet())
		{
			envArray[i] = key + "=" + env.get(key);
			++i;
		}

		return envArray;
	}
}
