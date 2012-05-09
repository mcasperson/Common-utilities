package com.redhat.ecs.commonutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class CollectionUtilities
{
	public static <T extends Comparable<? super T>> List<T> sortAndReturn(final List<T> list)
	{
		Collections.sort(list);
		return list;
	}
	
	/**
	 * @param array A collection of elements to be included in the returned ArrayList
	 * @return An ArrayList that includes all the elements passed in via the array parameter
	 */
	public static <T> ArrayList<T> toArrayList(final T... array)
	{
		final ArrayList<T> retValue = new ArrayList<T>();
		for (final T item : array)
			retValue.add(item);
		return retValue;
	}
	
	/**
	 * @param set The Set to be converted to an ArrayList
	 * @return An ArrayList containing the elements in the set
	 */
	public static <T> ArrayList<T> toArrayList(final Set<T> set)
	{
		final ArrayList<T> retValue = new ArrayList<T>();
		for (final T item : set)
			retValue.add(item);
		return retValue;
	}
	
	/**
	 * @param array The elements to be included in the returned ArrayList
	 * @return An ArrayList containing the String representation of the elements passed in via the array parameter
	 */
	public static ArrayList<String> toStringArrayList(final Object... array)
	{
		final ArrayList<String> retValue = new ArrayList<String>();
		for (final Object item : array)
			retValue.add(item.toString());
		return retValue;
	}
	
	public static List<String> replaceStrings(final List<String> input, final String originalRE, final String replacement)
	{
		final List<String> retValue = new ArrayList<String>();
		for (final String element : input)
		{
			retValue.add(element.replaceAll(originalRE, replacement));
		}
		return retValue;
	}
	
	public static <T> ArrayList<T> mergeLists(final T... array)
	{
		final ArrayList<T> retValue = new ArrayList<T>();
		Collections.addAll(retValue, array);
		return retValue;
	}
	
	public static <T> int addAll(final T[] source, final ArrayList<T> destination)
	{
		int count = 0;
		for (final T sourceItem : source)
		{
			destination.add(sourceItem);
			++count;
		}
		
		return count;
	}
	
	public static <T> int addAllThatDontExist(final List<T> source, final List<T> destination)
	{
		int count = 0;
		for (final T sourceItem : source)
		{
			if (!destination.contains(sourceItem))
			{
				destination.add(sourceItem);
				++count;
			}
		}
		
		return count;
	}

	public static <T> String toSeperatedString(final List<T> list)
	{	
		final StringBuffer stringBuffer = new StringBuffer();
		for (final T element : list)
		{
			if (stringBuffer.length() != 0)
				stringBuffer.append(",");
			stringBuffer.append(element.toString());
		}
		return stringBuffer.toString();
	}
	
	/**
	 * Merges two arrays together
	 * 
	 * @param first The first source array
	 * @param second The second source array
	 * @return An array that combines the two arrays
	 */
	public static <T> T[] concat(final T[] first, final T[] second)
	{
		/* deal with null inputs */
		if (first == null && second == null)
			return null;		
		if (first == null)
			return second;
		if (second == null)
			return first;
		
		final T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	/**
	 * Provides an easy way to compare two possibly null comparable objects
	 * 
	 * @param first The first object to compare
	 * @param second The second object to compare
	 * @return < 0 if the first object is less than the second object, 0 if they
	 *         are equal, and > 0 otherwise
	 */
	public static <T extends Comparable<? super T>> Integer getSortOrder(final T first, final T second)
	{
		if (first == null && second == null)
			return null;

		if (first == null && second != null)
			return -1;

		if (first != null && second == null)
			return 1;

		return first.compareTo(second);
	}

	/**
	 * Provides an easy way to see if two possibly null objects are equal
	 * 
	 * @param first The first object to test
	 * @param second The second to test
	 * @return true if both objects are equal, false otherwise
	 */
	public static <T> boolean isEqual(final T first, final T second)
	{
		/* test to see if they are both null, or both reference the same object */
		if (first == second)
			return true;

		if (first == null && second != null)
			return false;

		return first.equals(second);
	}
	
	public static List<Integer> toAbsIntegerList(final List<Integer> array)
	{
		final ArrayList<Integer> retValue = new ArrayList<Integer>();
		for (final Integer item : array)
			retValue.add(Math.abs(item));
		return retValue;
	}
}
