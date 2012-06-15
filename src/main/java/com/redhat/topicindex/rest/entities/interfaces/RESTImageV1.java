package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class RESTImageV1 extends RESTBaseEntityV1<RESTImageV1>
{
	public static final String DESCRIPTION_NAME = "description";
	public static final String LANGUAGEIMAGES_NAME = "languageimages";

	private String description;
	private BaseRestCollectionV1<RESTLanguageImageV1> languageImages_OTM = null;
	
	@Override
	public RESTImageV1 clone(boolean deepCopy)
	{
		final RESTImageV1 retValue = new RESTImageV1();
		
		this.cloneInto(retValue, deepCopy);
				
		retValue.description = this.description;
		
		if (deepCopy)
		{
			retValue.languageImages_OTM = this.languageImages_OTM == null ? null : this.languageImages_OTM.clone(deepCopy);
		}
		else
		{
			retValue.languageImages_OTM = this.languageImages_OTM;
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

	public BaseRestCollectionV1<RESTLanguageImageV1> getLanguageImages_OTM()
	{
		return languageImages_OTM;
	}

	public void setLanguageImages_OTM(BaseRestCollectionV1<RESTLanguageImageV1> languageImages_OTM)
	{
		this.languageImages_OTM = languageImages_OTM;
	}
	
	public void explicitSetLanguageImages_OTM(BaseRestCollectionV1<RESTLanguageImageV1> languageImages_OTM)
	{
		this.languageImages_OTM = languageImages_OTM;
		this.setParamaterToConfigured(LANGUAGEIMAGES_NAME);
	}
}
