package com.redhat.ecs.commonutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/*
 * A set of utilities to read from the local application resources.
 */
public class ResourceUtilities {
	
	/*
	 * Reads a resource file and converts it into a String
	 * 
	 * @param location The location of the resource file.
	 * @param fileName The name of the file.
	 * @return The string that represents the contents of the file or null if an error occurred.
	 */
	public static String resourceFileToString(String location, String fileName) {
		if (location == null || fileName == null) return null;
		InputStream in = ResourceUtilities.class.getResourceAsStream(location + fileName);
		if (in == null) return null;
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line;
		String output = "";
		try {
			while ((line = br.readLine()) != null) {
				output += line + "\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	/*
	 * Reads a resource file and converts it into a XML based Document object.
	 * 
	 * @param location The location of the resource file.
	 * @param fileName The name of the file.
	 * @return The Document that represents the contents of the file or null if an error occurred.
	 */
	public static Document resourceFileToXMLDocument(String location, String fileName) {
		if (location == null || fileName == null) return null;
		InputStream in = ResourceUtilities.class.getResourceAsStream(location + fileName);
		if (in == null) return null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(in);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}
}
