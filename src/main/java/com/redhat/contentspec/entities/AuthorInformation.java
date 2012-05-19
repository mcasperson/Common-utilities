package com.redhat.contentspec.entities;

public class AuthorInformation {

	private Integer authorTagId = null;
	private String firstName = null;
	private String lastName = null;
	private String email = null;
	private String organization = null;
	private String orgDivision = null;

	public AuthorInformation() {
	}

	public AuthorInformation(Integer authorId, String firstName, String lastName,
			String email) {
		this.authorTagId = authorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public int getAuthorId() {
		return this.authorTagId;
	}

	public void setAuthorId(int authorId) {
		this.authorTagId = authorId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDivision() {
		return orgDivision;
	}

	public void setOrgDivision(String orgDivision) {
		this.orgDivision = orgDivision;
	}

}
