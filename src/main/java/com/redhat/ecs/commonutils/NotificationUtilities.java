package com.redhat.ecs.commonutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.redhat.ecs.constants.CommonConstants;

public class NotificationUtilities
{
	public static void dumpMessageToStdOut(final String message)
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);					
		System.out.println("[" + formatter.format(new Date()) + "] " + message);
	}
}
