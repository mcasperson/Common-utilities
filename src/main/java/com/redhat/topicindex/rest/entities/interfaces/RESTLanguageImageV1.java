package com.redhat.topicindex.rest.entities.interfaces;

public class RESTLanguageImageV1 extends RESTBaseEntityV1<RESTLanguageImageV1>
{
	public static final String IMAGEDATA_NAME = "imageData";
	public static final String IMAGEDATABASE64_NAME = "imageDataBase64";
	public static final String THUMBNAIL_NAME = "thumbnail";
	public static final String LOCALE_NAME = "locale";
	public static final String IMAGE_NAME = "image";
	public static final String FILENAME_NAME = "filename";
	
	private Integer id;
	private RESTImageV1 image;
	private byte[] imageData;
	private byte[] thumbnail;
	private byte[] imageDataBase64;
	private String locale;
	private String filename;

	@Override
	public RESTLanguageImageV1 clone(boolean deepCopy)
	{
		final RESTLanguageImageV1 retValue = new RESTLanguageImageV1();

		this.cloneInto(retValue, deepCopy);

		retValue.image = this.image;
		retValue.filename = this.filename;

		if (deepCopy)
		{
			/* use arraycopy as a GWT compatible alternative to clone() */

			retValue.imageData = new byte[this.imageData.length];
			System.arraycopy(this.imageData, 0, retValue.imageData, 0, this.imageData.length);

			retValue.thumbnail = new byte[this.thumbnail.length];
			System.arraycopy(this.thumbnail, 0, retValue.thumbnail, 0, this.thumbnail.length);

			retValue.imageDataBase64 = new byte[this.imageDataBase64.length];
			System.arraycopy(this.imageDataBase64, 0, retValue.imageDataBase64, 0, this.imageDataBase64.length);
		}
		else
		{
			retValue.imageData = this.imageData;
			retValue.thumbnail = this.thumbnail;
			retValue.imageDataBase64 = this.imageDataBase64;
		}

		return retValue;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public RESTImageV1 getImage()
	{
		return image;
	}

	public void setImage(RESTImageV1 image)
	{
		this.image = image;
	}

	public byte[] getImageData()
	{
		return imageData;
	}

	public void setImageData(final byte[] imageData)
	{
		this.imageData = imageData;
	}
	
	public void explicitSetImageData(final byte[] imageData)
	{
		this.imageData = imageData;
		this.setParamaterToConfigured(IMAGEDATA_NAME);
	}

	public byte[] getThumbnail()
	{
		return thumbnail;
	}

	public void setThumbnail(final byte[] thumbnail)
	{
		this.thumbnail = thumbnail;
	}

	public byte[] getImageDataBase64()
	{
		return imageDataBase64;
	}

	public void setImageDataBase64(final byte[] imageDataBase64)
	{
		this.imageDataBase64 = imageDataBase64;
	}

	public String getLocale()
	{
		return locale;
	}

	public void setLocale(final String locale)
	{
		this.locale = locale;
	}
	
	public void explicitSetLocale(final String locale)
	{
		this.locale = locale;
		this.setParamaterToConfigured(LOCALE_NAME);
	}
	
	public String getFilename()
	{
		return filename;
	}

	public void setFilename(final String filename)
	{
		this.filename = filename;
	}
	
	public void explicitSetFilename(final String filename)
	{
		this.filename = filename;
		this.setParamaterToConfigured(FILENAME_NAME);
	}
}
