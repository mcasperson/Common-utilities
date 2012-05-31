package com.redhat.contentspec.utils.logging;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/*
 * This class keep track of the message log
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class LogMessage {

	private String message;
	private String originalMessage;
	private int debugLevel = 1;
	public static enum Type {ERROR, DEBUG, WARN, INFO};
	private Type type = null;
	private long timeStamp;
	
	/*
	 * This constructor sets the log message in the correct format along with the verbose debug level and the message type
	 * 
	 * @param msg the error message
	 * @param type the message type
	 * @param debugLevel verbose deubg level
	 */
	public LogMessage(String msg, Type type, int debugLevel) {
		this.debugLevel = debugLevel;
		timeStamp = System.nanoTime();
		this.type = type;
		this.originalMessage = msg;
		message = String.format("%-7s%s", getTypeString(type) + ":", msg);
	}
	
	/*
	 * This constructor sets the message to the correct format along with its type.
	 */
	public LogMessage(String msg, Type type) {
		timeStamp = System.nanoTime();
		this.type = type;
		this.originalMessage = msg;
		message = String.format("%-7s%s", getTypeString(type) + ":", msg);
	}
	
	public LogMessage(String msg) {
		timeStamp = System.nanoTime();
		this.originalMessage = msg;
		message = msg;
	}
	
	public LogMessage() {
		timeStamp = System.nanoTime();
	}
	
	/*
	 * This function takes in the message type and returns it as a string.
	 * 
	 * @param type This is the error type.
	 */
	private String getTypeString(Type type) {
		if (type == null) return null;
		switch (type) {
		case ERROR:
			return "ERROR";
		case INFO:
			return "INFO";
		case WARN:
			return "WARN";
		case DEBUG:
			return "DEBUG";
		}
		return null;
	}
	
	/*
	 * This function takes in the message type and sets the appropriate Type.
	 * 
	 * @param type This is the error type as a string.
	 */
	private void setTypeString(String type) {
		if (type == null) {
			type = null;
		} else if (type.equalsIgnoreCase("WARN")) {
			this.type = Type.WARN;	
		} else if (type.equalsIgnoreCase("ERROR")) {
			this.type = Type.ERROR;	
		} else if (type.equalsIgnoreCase("DEBUG")) {
			this.type = Type.DEBUG;	
		} else if (type.equalsIgnoreCase("INFO")) {
			this.type = Type.INFO;	
		}
	}
	
	@XmlElement
	public String getOriginalMessage() {
		return originalMessage;
	}
	
	public void setOriginalMessage(String message) {
		this.originalMessage = message;
	}
	
	@XmlElement
	public long getTimestamp() {
		return timeStamp;
	}
	
	public void setTimestamp(long timestamp) {
		this.timeStamp = timestamp;
	}
	
	@XmlElement(name = "type")
	@JsonProperty(value = "type")
	public String getTypeAsString() {
		return getTypeString(type);
	}
	
	@XmlTransient
	@JsonIgnore
	public Type getType() {
		return type;
	}
	
	public void setTypeAsString(String type) {
		setTypeString(type);
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@XmlElement
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return message;
	}
	
	@XmlTransient
	@JsonIgnore
	public int getDebugLevel() {
		return debugLevel;
	}
}
