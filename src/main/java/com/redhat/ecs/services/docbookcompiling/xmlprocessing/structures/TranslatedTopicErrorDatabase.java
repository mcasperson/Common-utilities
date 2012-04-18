package com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures;

import java.util.ArrayList;
import java.util.List;

public class TranslatedTopicErrorDatabase {

	private List<String> titles = new ArrayList<String>();
	
	public void addTitle(final String relatedTitle) {
		titles.add(relatedTitle);
	}
	
	public List<String> getErrorTitles() {
		return titles;
	}
}
