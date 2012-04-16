package com.redhat.topicindex.rest.entities;

/**
 * A REST representation of the ImageFile database entity
 */
public class ImageV1 extends BaseRESTEntityV1<ImageV1>
{
	public static final String FILENAME_NAME = "filename";
	public static final String IMAGEDATA_NAME = "imageData";
	public static final String DESCRIPTION_NAME = "description";
	
	private String filename;
	private byte[] imageData;
	private byte[] thumbnail;
	private byte[] imageDataBase64;
	private String description;

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(final String filename)
	{
		this.filename = filename;
	}
	
	public void setFilenameExplicit(final String filename)
	{
		this.filename = filename;
		this.setParamaterToConfigured(FILENAME_NAME);
	}

	public byte[] getImageData()
	{
		return imageData;
	}

	public void setImageData(final byte[] imageData)
	{
		this.imageData = imageData;
	}
	
	public void setImageDataExplicit(final byte[] imageData)
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void setDescriptionExplicit(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}
}
