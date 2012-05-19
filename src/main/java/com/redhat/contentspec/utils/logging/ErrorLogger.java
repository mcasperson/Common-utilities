package com.redhat.contentspec.utils.logging;
import java.util.Vector;

import org.apache.log4j.*;

/**
 * This is the error logger class, this is managed by the ErrorLoggerManager, its in charge of logging different types of messages.
 */
public class ErrorLogger {
	private Logger log = null;
	private String name = null;
	private int debugLevel = 0;
	private Vector<LogMessage> messages = new Vector<LogMessage>();

	/**
	 * ErrorLogger constructor
	 * 
	 * @param name The name of the error logger
	 */
	public ErrorLogger(String name) {
		log = Logger.getLogger(name);
		this.setName(name);
	}
	
	/**
	 * Turn debugging on for this logger.
	 * 
	 * @param state The state the debug should be in. True = On, False = Off
	 */
	public void setVerboseDebug(int level) {
		debugLevel = level;
	}
	
	/**
	 * Get the current debugger level.
	 * 
	 * @return The state of the debugger.
	 */
	public int getDebugLevel() {
		return debugLevel;
	}
	
	/**
	 * Write an error message to the logs
	 * 
	 * @param message The error message 
	 */
	public void error(String message) {
		messages.add(new LogMessage(message, LogMessage.Type.ERROR));
		log.error(message);
	}

	/**
	 * Write a debug message to the logs
	 * 
	 * @param message The debug message 
	 */
	public void debug(String message, int level) {
		messages.add(new LogMessage(message, LogMessage.Type.DEBUG, level));
		log.debug(message);
	}
	
	/**
	 * Write a debug message to the logs
	 * 
	 * @param message The debug message 
	 */
	public void debug(String message) {
		messages.add(new LogMessage(message, LogMessage.Type.DEBUG));
		log.debug(message);
	}
	
	/**
	 * Write an info message to the logs
	 * 
	 * @param message The information message 
	 */
	public void info(String message) {
		messages.add(new LogMessage(message, LogMessage.Type.INFO));
		log.info(message);
	}
	
	/**
	 * Write a warn message to the logs
	 * 
	 * @param message The warning message 
	 */
	public void warn(String message) {
		messages.add(new LogMessage(message, LogMessage.Type.WARN));
		log.warn(message);
	}

	/**
	 * Gets the name of the logger.
	 * 
	 * @return The name of the logger
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the logger
	 * 
	 * @param name The new name for the log
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Clears the custom log messages.
	 */
	public void clearLogs() {
		messages.clear();
	}
	
	/**
	 * Gets the log messages and ignores message that are higher then the debug level
	 * 
	 * @return A vector based array containing the LogMessage's
	 */
	public Vector<LogMessage> getLogMessages() {
		Vector<LogMessage> output = new Vector<LogMessage>();
		for (LogMessage msg: messages) {
			if (msg.getType() == LogMessage.Type.DEBUG) {
				if (msg.getDebugLevel() <= debugLevel) {
					output.add(msg);
				}
			} else {
				output.add(msg);
			}
		}
		return output;
	}
	
	
}
