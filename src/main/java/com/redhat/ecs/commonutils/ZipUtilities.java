package com.redhat.ecs.commonutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.ArrayUtils;


public class ZipUtilities
{
	private static int BUFFER_SIZE = 2048;
	
	public static byte[] createZip(final HashMap<String, byte[]> files)
	{
		try
		{
			final ByteArrayOutputStream bos = new ByteArrayOutputStream();
			final ZipOutputStream zipfile = new ZipOutputStream(bos);
			for (final String fileName : files.keySet())
			{
				final ZipEntry zipentry = new ZipEntry(fileName);
				zipfile.putNextEntry(zipentry);
				zipfile.write(files.get(fileName));
			}
			zipfile.close();
			return bos.toByteArray();
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}

		return null;
	}

	public static void createZipMap(final File path, final String absolutePathPrefix, final HashMap<String, byte[]> fileMap)
	{
		if (path.exists())
		{
			final File[] files = path.listFiles();
			for (final File file : files)
			{
				if (file.isDirectory())
				{
					createZipMap(file, absolutePathPrefix, fileMap);
				}
				else
				{
					final String absolutePath = file.getAbsolutePath();
					final String relativePath = absolutePath.replace(absolutePathPrefix, "");

					try
					{
						fileMap.put(relativePath, FileUtilities.readFileContents(file).getBytes("UTF-8"));
					}
					catch (UnsupportedEncodingException e)
					{
						/* UTF-8 is a valid format so this should exception should never get thrown */
					}
				}
			}
		}
	}

	/**
	 * Takes a ZIP file (as a byte array), and extracts its contents into a map of filename to file contents
	 */
	public static void mapZipFile(final byte[] zipFile, final Map<String, byte[]> output, final String keyPrefix, final String keyUnPrefix)
	{
		try
		{
			final String fixedKeyPrefix = keyPrefix == null ? "" : keyPrefix;
			final String fixedKeyUnPrefix = keyUnPrefix == null ? "" : keyUnPrefix;
			final ZipInputStream zf = new ZipInputStream(new ByteArrayInputStream(zipFile));
			
			ZipEntry ze = null;
			while ((ze = zf.getNextEntry()) != null)
			{
				final String name = ze.getName();
				final long size = ze.getSize();
				
				/* see if we are working with a file or a directory */
				if (size != 0)
				{
					byte[] fileContents = new byte[0];
					final byte[] fileBuffer = new byte[BUFFER_SIZE];
					
					int bytesRead = 0;					
					while ((bytesRead = zf.read(fileBuffer, 0, BUFFER_SIZE)) != -1)
						fileContents = ArrayUtils.addAll(fileContents, bytesRead == BUFFER_SIZE ? fileBuffer : ArrayUtils.subarray(fileBuffer, 0, bytesRead));
					
					output.put(fixedKeyPrefix + name.replace(fixedKeyUnPrefix, ""), fileContents);
				}
			}

		}
		catch (final IOException ex)
		{
			ExceptionUtilities.handleException(ex);
		}
	}
	
	public static void mapZipFile(final byte[] zipFile, final Map<String, byte[]> output, final String keyPrefix)
	{
		mapZipFile(zipFile, output, keyPrefix, "");
	}
	
	public static void mapZipFile(final byte[] zipFile, final Map<String, byte[]> output)
	{
		mapZipFile(zipFile, output, "", "");
	}
	
	public static Map<String, byte[]> mapZipFile(final byte[] zipFile, final String keyPrefix, final String keyUnPrefix)
	{
		final Map<String, byte[]> retValue = new HashMap<String, byte[]>();
		mapZipFile(zipFile, retValue, keyPrefix, keyUnPrefix);
		return retValue;
	}
	
	public static Map<String, byte[]> mapZipFile(final byte[] zipFile, final String keyPrefix)
	{
		return mapZipFile(zipFile, keyPrefix, "");
	}
	
	public static Map<String, byte[]> mapZipFile(final byte[] zipFile)
	{
		return mapZipFile(zipFile, "", "");
	}
}
