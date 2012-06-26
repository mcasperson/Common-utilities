package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTImageCollectionV1;
import com.redhat.topicindex.rest.collections.RESTLanguageImageCollectionV1;

public class RESTImageV1 extends RESTBaseEntityV1<RESTImageV1, RESTImageCollectionV1>
{
	public static final String DESCRIPTION_NAME = "description";
	public static final String LANGUAGEIMAGES_NAME = "languageimages";

	private String description = null;
	private RESTLanguageImageCollectionV1 languageImages_OTM = null;
	/** A list of the Envers revision numbers */
	private RESTImageCollectionV1 revisions = null;
	
	@Override
	public RESTImageCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTImageCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	
	@Override
	public RESTImageV1 clone(boolean deepCopy)
	{
		final RESTImageV1 retValue = new RESTImageV1();
		
		this.cloneInto(retValue, deepCopy);
				
		retValue.description = this.description;
		
		if (deepCopy)
		{
			if (this.languageImages_OTM != null)
			{
				retValue.languageImages_OTM = new RESTLanguageImageCollectionV1();
				this.languageImages_OTM.cloneInto(retValue.languageImages_OTM, deepCopy);
			}
			
			if (this.getRevisions() != null)
			{
				retValue.revisions = new RESTImageCollectionV1();
				this.revisions.cloneInto(retValue.revisions, deepCopy);
			}			
		}
		else
		{
			retValue.languageImages_OTM = this.languageImages_OTM;
			retValue.revisions = this.revisions;
		}
				
		return retValue;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void explicitSetDescription(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}

	public RESTLanguageImageCollectionV1 getLanguageImages_OTM()
	{
		return languageImages_OTM;
	}

	public void setLanguageImages_OTM(RESTLanguageImageCollectionV1 languageImages_OTM)
	{
		this.languageImages_OTM = languageImages_OTM;
	}
	
	public void explicitSetLanguageImages_OTM(RESTLanguageImageCollectionV1 languageImages_OTM)
	{
		this.languageImages_OTM = languageImages_OTM;
		this.setParamaterToConfigured(LANGUAGEIMAGES_NAME);
	}
}
