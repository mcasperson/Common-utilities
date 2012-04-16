package com.redhat.ecs.commonutils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to monitor the various streams managed by a Process in
 * order to prevent a buffer overrun for long running Processes.
 */
class StreamRedirector extends Thread
{
	/** A stream to poll for data */
	private InputStream is;
	/** A stream to print the data polled from is */
	private OutputStream os;
	/** A collection of strings that should not be printed */
	private List<String> doNotPrintStrings;

	/**
	 * @param is The stream to poll for data
	 */
	public StreamRedirector(final InputStream is)
	{
		this(is, null, new ArrayList<String>());
	}

	/**
	 * @param is The stream to poll for data
	 * @param redirect A stream to print the data that has been read from is
	 */
	public StreamRedirector(final InputStream is, final OutputStream redirect)
	{
		this(is, redirect, new ArrayList<String>());
	}

	/**
	 * @param is The stream to poll for data
	 * @param redirect A stream to print the data that has been read from is
	 * @param doNotPrintStrings A collection of strings that should not be printed or redirected (like passwords etc)
	 */
	public StreamRedirector(final InputStream is, final OutputStream redirect, final List<String> doNotPrintStrings)
	{
		this.is = is;
		this.os = redirect;
		this.doNotPrintStrings = doNotPrintStrings;
	}

	public void run()
	{
		try
		{
			final PrintWriter pw = os != null ? new PrintWriter(os) : null;
			final InputStreamReader isr = new InputStreamReader(is);
			final BufferedReader br = new BufferedReader(isr);

			String line = null;

			while ((line = br.readLine()) != null)
			{
				String sanatizedLine = line;
				for (String replace : doNotPrintStrings)
					sanatizedLine = sanatizedLine.replace(replace, "****");
				System.out.println(sanatizedLine);

				if (pw != null)
				{
					pw.println(line);
				}
			}

			if (pw != null)
				pw.flush();
		}

		catch (final IOException ex)
		{
			ExceptionUtilities.handleException(ex);
		}
	}
}
