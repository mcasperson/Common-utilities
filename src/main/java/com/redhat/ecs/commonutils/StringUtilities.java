package com.redhat.ecs.commonutils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilities
{
	public static String cleanTextForCSV(final String input)
	{
		if (input == null)
			return "";

		//return input.replaceAll(",", "").replace("\r\n", " ").replaceAll("\n", " ").replaceAll("", "");
		return "\"" + input.replaceAll("\"", "\"\"") + "\"";
	}
	
	/**
	 * @param input The original string
	 * @return A string with special characters that break Publican stripped out
	 */
	public static String cleanTextForXML(final String input)
	{
		return input	
			.replaceAll("\\u00C2\\u00A0", "&nbsp;")		// non breaking space (UTF-8)
			.replaceAll("\\u00A0", "&nbsp;")			// non breaking space (ISO-8859-1 or US-ASCII)
			.replaceAll("\\u00E9", "\u00C3\u00A9")		// a lower case Latin e with acute
			.replaceAll("’", "&apos;")					// left single quote
			.replaceAll("‘", "&apos;")					// right single quote
			.replaceAll("“", "&quot;")					// right double quote
			.replaceAll("”", "&quot;")					// left double quote
			.replaceAll("–", "-");						// a long dash
	}
	
	/**
	 * A utility function to allow us to build a single string with line breaks
	 * from an array of strings. This is really just used to make defining text
	 * files in code easier to read, as opposed to having to add and maintain
	 * line breaks in the initial string definition.
	 */
	public static String buildString(final String[] lines)
	{
		return buildString(lines, "\n");
	}

	public static String buildString(final String[] lines, final String seperator)
	{
		String retValue = "";
		for (final String line : lines)
		{
			if (retValue.length() != 0)
				retValue += seperator;
			retValue += line;
		}

		return retValue;
	}
	
	public static boolean startsWithWhitespace(final String input)
	{
		if (input == null || input.isEmpty())
			return false;
		
		/* compile the regular expression */
		final Pattern injectionSequencePattern = Pattern.compile("\\s");
		
		/* find any matches */
		final Matcher injectionSequencematcher = injectionSequencePattern.matcher(input.substring(0, 1));
		
		/* loop over the regular expression matches */
		return injectionSequencematcher.find();

	}
	
	public static boolean endsWithWhitespace(final String input)
	{
		if (input == null || input.isEmpty())
			return false;
		
		/* compile the regular expression */
		final Pattern injectionSequencePattern = Pattern.compile("\\s");
		
		/* find any matches */
		final Matcher injectionSequencematcher = injectionSequencePattern.matcher(input.substring(input.length() - 1 , input.length()));
		
		/* loop over the regular expression matches */
		return injectionSequencematcher.find();
	}
	
	public static String convertToLinuxLineEndings(final String input)
	{
		if (input == null) return "";
		return input.replaceAll("\\r", "");
	}
	
	public static String convertToWindowsLineEndings(final String input)
	{
		if (input == null) return "";
		return input.replaceAll("(?<!\\r)\\n", "\\r\\n");
	}
	
	/**
	 * @param input The String to convert
	 * @return The byte array from the input String, or null if the input is null
	 */
	public static byte[] getStringBytes(final String input)
	{
	 	return input == null ? new byte[] { } : input.getBytes();
	}
}
