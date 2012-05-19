package com.redhat.contentspec.utils.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.contentspec.sort.LogMessageComparator;
/**
 * An Error logger manager that manages error logs. 
 */
public class ErrorLoggerManager {
	
	private Map<String, ErrorLogger> logs = Collections.synchronizedMap(new HashMap<String, ErrorLogger>());
	private int debugLevel = 0;
	
	/**
	 * Gets a logger for a specified name. If the logger doesn't exist then it creates one.
	 * 
	 * @return An error logger object for the specified name
	 */
	public ErrorLogger getLogger(String name) {
		if (name == null || name.equals("")) return null;
		if (!hasLog(name)) {
			addLog(name);
		}
		return getLog(name);
	}
	
	/**
	 * Gets a logger for a specified class. If the logger doesn't exist then it creates one.
	 * 
	 * @return An error logger object for the specified class
	 */
	@SuppressWarnings("rawtypes")
	public ErrorLogger getLogger(Class clazz) {
		return getLogger(clazz.getCanonicalName());
	}
	
	/**
	 * Prints all the logger messages to the server console.
	 */
	public void printAll() {
		System.out.print(generateLogs());
	}
	
	/**
	 *  ErrorLoggerManager constructor
	 */
	public ErrorLoggerManager() {
	}
	
	/**
	 * Checks if the logs contain a specific log by name
	 * 
	 * @param name The logger name
	 * @return boolean True if the error log is found
	 */
	public boolean hasLog(String name) {
		return logs.containsKey(name);
	}
	
	/**
	 * Gets the error log
	 * 
	 * @param name The logger name
	 * 
	 * @return ErrorLogger
	 */
	public ErrorLogger getLog(String name) {
		return hasLog(name) ? logs.get(name) : null;
	}
	
	/**
	 * Adds the error log
	 * 
	 * @param name The logger name
	 */
	public void addLog(String name) {
		ErrorLogger log = new ErrorLogger(name);
		logs.put(name, log);
		log.setVerboseDebug(debugLevel);
	}
	
	/**
	 * Sets verbose debug level (0, 1 or 2)
	 * 
	 * @param level verbose debug level
	 */
	public void setVerboseDebug(int level) {
		debugLevel = level;
		for (String logName: logs.keySet()) {
			logs.get(logName).setVerboseDebug(level);
		}
	}
	
	/**
	 * Generates a custom log for all of the error/info/warn/debug messages sent.
	 * 
	 * @return A string containing the contents of the logs
	 */
	@SuppressWarnings("unchecked")
	public String generateLogs() {
		String output = "";
		ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
		for (String logName: logs.keySet()) {
			messages.addAll(logs.get(logName).getLogMessages());
		}
		Collections.sort(messages, new LogMessageComparator());
		for (LogMessage msg: messages) {
			output += msg.getMessage() + "\n";
		}
		return output;
	}
	
	/**
	 * Gets a list of all of the logs that are managed.
	 * 
	 * @return A List of all the log messages ordered by their timestamp.
	 */
	@SuppressWarnings("unchecked")
	public List<LogMessage> getLogs() {
		ArrayList<LogMessage> messages = new ArrayList<LogMessage>();
		for (String logName: logs.keySet()) {
			messages.addAll(logs.get(logName).getLogMessages());
		}
		Collections.sort(messages, new LogMessageComparator());
		return messages;
	}
	
	/**
	 * Clears the error logs
	 */
	public void clearLogs() {
		for (String logName: logs.keySet()) {
			logs.get(logName).clearLogs();
		}
	}
}
