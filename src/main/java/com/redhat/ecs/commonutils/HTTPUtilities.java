package com.redhat.ecs.commonutils;

import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.faces.context.FacesContext;

public class HTTPUtilities
{



	/**
	 * Used to send arbitrary data to the user (i.e. to download files) with a
	 * MIME type of "application/octet-stream"
	 * 
	 * @param data The contents of the file
	 * @param filename The name of the file to send to the browser
	 */
	public static void writeOutContent(final byte[] data, final String filename)
	{
		writeOutContent(data, filename, "application/octet-stream");
	}

	/**
	 * Used to send arbitrary data to the user (i.e. to download files)
	 * 
	 * @param data The contents of the file
	 * @param filename The name of the file to send to the browser
	 * @param mime The MIME type of the file
	 */
	public static void writeOutContent(final byte[] data, final String filename, final String mime)
	{
		writeOutContent(data, filename, mime, true);
	}

	/**
	 * Used to send data through the browser. It is up to the browser to either
	 * display or download the data.
	 * 
	 * @param data The contents of the file
	 * @param mime The MIME type of the file
	 */
	public static void writeOutToBrowser(final byte[] data, final String mime)
	{
		writeOutContent(data, null, mime, false);
	}

	/**
	 * Used to send arbitrary to the user.
	 * 
	 * @param data The contents of the file to send
	 * @param filename The name of the file. This is only useful if asAttachment
	 *            is true
	 * @param mime The MIME type of the content
	 * @param asAttachment If true, the file will be downloaded. If false, it is
	 *            up to the browser to decide whether to display or download the
	 *            file
	 */
	private static void writeOutContent(final byte[] data, final String filename, final String mime, final boolean asAttachment)
	{
		try
		{
			final HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

			response.setContentType(mime);

			if (asAttachment)
				response.addHeader("Content-Disposition", "attachment;filename=" + filename);

			response.setContentLength(data.length);

			final OutputStream writer = response.getOutputStream();

			writer.write(data);

			writer.flush();
			writer.close();

			FacesContext.getCurrentInstance().responseComplete();
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}
	}

	
}
