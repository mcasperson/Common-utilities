package com.redhat.contentspec.entities;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class BugzillaOptions {

	private String product = null;
	private String component = null;
	private String version = null;
	private String urlComponent = null;
	private boolean injectLinks = true;

	/**
	 * @return the product
	 */
	public String getProduct() {
		return product;
	}

	/**
	 * @param product the product to set
	 */
	public void setProduct(String product) {
		this.product = product;
	}

	/**
	 * @return the component
	 */
	public String getComponent() {
		return component;
	}

	/**
	 * @param component the component to set
	 */
	public void setComponent(String component) {
		this.component = component;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	public boolean isBugzillaLinksEnabled() {
		return injectLinks;
	}
	
	/**
	 * @param enabled Whether bug links should be injected
	 */
	public void setBugzillaLinksEnabled(boolean enabled) {
		this.injectLinks = enabled;
	}
	
	public String createBugzillUrl(String bugzillaUrl) {
		try {
			return bugzillaUrl + (bugzillaUrl.endsWith("/") ? "" : "/") + "enter_bug.cgi?product=" + URLEncoder.encode(product, "UTF-8") + "&amp;component=" + URLEncoder.encode(component, "UTF-8") + (version == null ? "" : ("&amp;version=" + URLEncoder.encode(version, "UTF-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Get the URL component that is used in the .ent file when
	 * building the Docbook files.
	 * 
	 * @return The BZURL component for the content specification.
	 */
	public String getUrlComponent()
	{
		return urlComponent;
	}

	/**
	 * Set the URL component that is used in the .ent file when
	 * building the Docbook files.
	 * 
	 * @param urlComponent The BZURL component to be used when building.
	 */
	public void setUrlComponent(final String urlComponent)
	{
		this.urlComponent = urlComponent;
	}
}
