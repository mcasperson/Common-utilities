package com.redhat.contentspec.utils;

public class ContentSpecUtilities {

	/**
	 * Generates a random target it in the form of T<Line Number>0<Random Number><count>.
	 * I.e. The topic is on line 50 and the target to be created for is topic 4 in a process, the 
	 * output would be T500494
	 * 
	 * @param count The count of topics in the process.
	 * @return The partially random target id.
	 */
	public static String generateRandomTargetId(int line, int count) {
		return generateRandomTargetId(line) + count;
	}
	
	/**
	 * Generates a random target it in the form of T<Line Number>0<Random Number>.
	 * The random number is between 0-49.
	 * 
	 * @param line The line number the topic is on.
	 * @return The partially random target id.
	 */
	public static String generateRandomTargetId(int line) {
		int randomNum = (int) (Math.random() * 50);
		return "T" + line + "0" + randomNum;
	}
}
