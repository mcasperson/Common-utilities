package com.redhat.ecs.commonutils;

import java.security.MessageDigest;

import org.jboss.resteasy.util.Hex;

public class HashUtilities
{
	/**
	 * Generates a MD5 Hash for a specific string
	 * 
	 * @param input The string to be converted into an MD5 hash.
	 * @return The MD5 Hash string of the input string.
	 */
	public static String generateMD5(final String input) {
		try
		{
			final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			byte[] digest = messageDigest.digest(input.getBytes("UTF-8"));
			return Hex.encodeHex(digest);
		}
		catch (Exception e)
		{
			ExceptionUtilities.handleException(e);
			return null;
		}
	}
}
