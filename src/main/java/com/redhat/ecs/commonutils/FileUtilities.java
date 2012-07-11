package com.redhat.ecs.commonutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class FileUtilities
{
	/**
	 * @param file The file to be read
	 * @return The contents of the file as a String
	 */
	public static String readFileContents(final File file)
	{
		String output = "";
		Scanner scanner = null;
		try
		{
			if (file.exists())
			{
				scanner = new Scanner(new FileReader(file));
				while (scanner.hasNextLine())
					output += scanner.nextLine() + "\n";
			}

		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}
		finally
		{
			if (scanner != null)
				scanner.close();
		}
		return output;
	}

	/**
	 * @param file The file to be read
	 * @return The contents of the file as a byte array
	 */
	public static byte[] readFileContentsAsByteArray(final File file)
	{
		if (!file.exists())
			return null;
		
		InputStream is = null;
		
		try
		{
			is = new FileInputStream(file);

			// Get the size of the file
			final long length = file.length();

			// You cannot create an array using a long type.
			// It needs to be an int type.
			// Before converting to an int type, check
			// to ensure that file is not larger than Integer.MAX_VALUE.
			if (length > Integer.MAX_VALUE)
			{
				throw new IOException("File is too large: " + file.getName());
			}

			// Create the byte array to hold the data
			final byte[] bytes = new byte[(int) length];

			// Read in the bytes
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0)
			{
				offset += numRead;
			}

			// Ensure all the bytes have been read in
			if (offset < bytes.length)
			{
				throw new IOException("Could not completely read file " + file.getName());
			}

			return bytes;
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}
		finally
		{
			try
			{
				if (is != null)
					is.close();
			}
			catch (final Exception ex)
			{
				ExceptionUtilities.handleException(ex);
			}
		}

		return null;
	}
	
	public static String getFileExtension(final String fileName)
	{
		if (fileName == null)
			return null;
		
		final int lastPeriodIndex = fileName.lastIndexOf(".");
		/* make sure there is an extension, and that the filename doesn't end with a period */
		if (lastPeriodIndex != -1 && lastPeriodIndex < fileName.length() - 1)
		{
			final String extension = fileName.substring(lastPeriodIndex + 1);
			return extension;
		}

		return null;
	}
	
}
