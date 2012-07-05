package com.redhat.ecs.commonutils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtilities
{
	public static int rtrimCount(final String input)
	{
		if (input == null)
			return 0;
		if (input.isEmpty())
			return 0;
		int i = input.length() - 1;
		while (i >= 0 && input.charAt(i) == ' ') 
			--i;
		return  input.length() - i;
	}
	
	public static int ltrimCount(final String input)
	{
		if (input == null)
			return 0;
		if (input.isEmpty())
			return 0;
		int i = 0;
		while (i < input.length() && input.charAt(i) == ' ') 
			++i;
		return i;			
	}
	
	public static String rtrim(final String input)
	{
		if (input == null)
			return null;
		if (input.isEmpty())
			return input;
		int i = input.length() - 1;
		while (i >= 0 && input.charAt(i) == ' ') 
			--i;
		return input.substring(0, i+1);			
	}
	
	public static String ltrim(final String input)
	{
		if (input == null)
			return null;
		if (input.isEmpty())
			return input;
		int i = 0;
		while (i < input.length() && input.charAt(i) == ' ') 
			++i;
		return input.substring(i, input.length());			
	}
	
	public static String cleanTextForCSV(final String input)
	{
		if (input == null)
			return "";

		//return input.replaceAll(",", "").replace("\r\n", " ").replaceAll("\n", " ").replaceAll("", "");
		return "\"" + input.replaceAll("\"", "\"\"") + "\"";
	}
	
	public static String uncapatiliseFirstCharatcer(final String input)
	{
		if (input == null)
			return null;
		
		if (input.isEmpty())
			return "";
		
		final String firstChar = input.substring(0, 1).toLowerCase();
		final String remaining = input.length() > 1 ? input.substring(1) : "";
		
		return firstChar + remaining;
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
			.replaceAll("�", "&apos;")					// left single quote
			.replaceAll("�", "&apos;")					// right single quote
			.replaceAll("�", "&quot;")					// right double quote
			.replaceAll("�", "&quot;")					// left double quote
			.replaceAll("�", "-");						// a long dash
	}
	
	/**
	 * Prepares a string to be inserted into xml by escaping any reserved XML symbols.
	 * 
	 * The current symbols are: < >
	 * 
	 * @param input The original string
	 * @return A string with the reserved xml characters escaped.
	 */
	public static String escapeForXML(final String input)
	{
		return input.replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
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
		final StringBuilder retValue = new StringBuilder();
		for (final String line : lines)
		{
			if (retValue.length() != 0)
				retValue.append(seperator);
			retValue.append(line);
		}

		return retValue.toString();
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
	
	/**
	 * Gets the first index of a character ignoring characters that have been escaped
	 * 
	 * @param input The string to be searched
	 * @param delim The character to be found
	 * @return The index of the found character or -1 if the character wasn't found
	 */
	public static int indexOf(final String input, final char delim)
	{
		return indexOf(input, delim, 0);
	}
	
	/**
	 * Gets the first index of a character after fromIndex. Ignoring characters that have been escaped
	 * 
	 * @param input The string to be searched
	 * @param delim The character to be found
	 * @param fromIndex Start searching from this index
	 * @return The index of the found character or -1 if the character wasn't found
	 */
	public static int indexOf(final String input, final char delim, final int fromIndex)
	{
		if (input == null) return -1;
		int index = input.indexOf(delim, fromIndex);
		if (index != 0)
		{
			while (index != -1 && index != (input.length() - 1))
			{
				if (input.charAt(index - 1) != '\\') break;
				index = input.indexOf(delim, index + 1);
			}	
		}
		return index;
	}
	
	/**
	 * Gets the last index of a character ignoring characters that have been escaped
	 * 
	 * @param input The string to be searched
	 * @param delim The character to be found
	 * @return The index of the found character or -1 if the character wasn't found
	 */
	public static int lastIndexOf(final String input, final char delim)
	{
		return input == null ? -1 : lastIndexOf(input, delim, input.length());
	}
	
	/**
	 * Gets the last index of a character starting at fromIndex. Ignoring characters that have been escaped
	 * 
	 * @param input The string to be searched
	 * @param delim The character to be found
	 * @param fromIndex Start searching from this index
	 * @return The index of the found character or -1 if the character wasn't found
	 */
	public static int lastIndexOf(final String input, final char delim, final int fromIndex)
	{
		if (input == null) return -1;
		int index = input.lastIndexOf(delim, fromIndex);
		while (index != -1 && index != 0)
		{
			if (input.charAt(index-1) != '\\') break;
			index = input.lastIndexOf(delim, index-1);
		}
		return index;
	}
	
	/**
	 * Similar to the normal String split function. However this function ignores escaped characters (i.e. \[ ).
	 * 
	 * @param input The string to be split
	 * @param split The char to be used to split the input string
	 * @return An array of split strings
	 */
	public static String[] split(final String input, final char split) {
		int index = indexOf(input, split);
		int prevIndex = 0;
		final ArrayList<String> output = new ArrayList<String>();
		if (index == -1)
		{
			output.add(input);
			return output.toArray(new String[1]);
		}
		while (index != -1)
		{
			output.add(input.substring(prevIndex, index));
			prevIndex = index + 1;
			index = indexOf(input, split, index+1);
		}
		output.add(input.substring(prevIndex, input.length()));
		return output.toArray(new String[output.size()]);
	}
	
	/**
	 * Similar to the normal String split function. However this function ignores escaped characters (i.e. \[ ).
	 * 
	 * @param input The string to be split
	 * @param split The char to be used to split the input string
	 * @param limit The maximum number of times to split the string
	 * @return An array of split strings
	 */
	public static String[] split(final String input, final char split, final int limit)
	{
		int index = indexOf(input, split);
		int prevIndex = 0, count = 1;
		final ArrayList<String> output = new ArrayList<String>();
		if (index == -1)
		{
			output.add(input);
			return output.toArray(new String[1]);
		}
		while (index != -1 && count != limit)
		{
			output.add(input.substring(prevIndex, index));
			prevIndex = index + 1;
			index = indexOf(input, split, index+1);
			count++;
		}
		output.add(input.substring(prevIndex, input.length()));
		return output.toArray(new String[output.size()]);
	}
	
	/**
	 * Checks to see if a string entered is alpha numeric
	 * 
	 * @param input The string to be tested
	 * @return True if the string is alpha numeric otherwise false
	 */
	public static boolean isAlphanumeric(final String input)
	{
		for (int i = 0; i < input.length(); i++)
		{
			if (!Character.isLetterOrDigit(input.charAt(i))) return false;
		}
		return true;
	}
	
	/**
	 * Replaces the escaped chars with their normal counterpart. Only replaces ('[', ']', '(', ')', ';', ',', '+', '-' and '=')
	 * 
	 * @param input The string to have all its escaped characters replaced.
	 * @return The input string with the escaped characters replaced back to normal.
	 */
	public static String replaceEscapeChars(final String input) {
		return input.replaceAll("\\\\\\[", "[")
				.replaceAll("\\\\\\]", "]")
				.replaceAll("\\\\\\(", "(")
				.replaceAll("\\\\\\)", ")")
				.replaceAll("\\\\:", ":")
				.replaceAll("\\\\,", ",")
				.replaceAll("\\\\=", "=")
				.replaceAll("\\\\\\+", "+")
				.replaceAll("\\\\-", "-");
	}
	
	/**
	 * Checks a string to see if it has the UTF8 replacement character
	 * 
	 * @param input The string to be checked
	 * @return True of the replacement character is found otherwise false
	 */
	public static boolean hasInvalidUTF8Character(final String input)
	{
		for (char c: input.toCharArray())
		{
			if (c == 0xFFFD) return true;
		}
		return false;
	}
	
	/**
	 * Converts a string so that it can be used in a regular expression.
	 * 
	 * @param input The string to be converted.
	 * @return An escaped string that can be used in a regular expression.
	 */
	public static String convertToRegexString(final String input)
	{
		return input.replaceAll("\\\\", "\\\\")
				.replaceAll("\\*", "\\*")
				.replaceAll("\\+", "\\+")
				.replaceAll("\\]", "\\]")
				.replaceAll("\\[", "\\[")
				.replaceAll("\\(", "\\(")
				.replaceAll("\\)", "\\)")
				.replaceAll("\\?", "\\?")
				.replaceAll("\\$", "\\$")
				.replaceAll("\\|", "\\|")
				.replaceAll("\\^", "\\^")
				.replaceAll("\\.", "\\.");
	}
}
