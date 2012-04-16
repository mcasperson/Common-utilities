package com.redhat.ecs.servicepojo;

import com.redhat.ecs.commonutils.NotificationUtilities;
import com.redhat.ecs.constants.CommonConstants;

public class ServiceStarter
{
	private static final int DEFAULT_PORT = 61613;
	private static final long SHUTDOWN_TIME = 60000;

	private final String messageServer;
	private final String messageServerPort;
	private final String messageServerUser;
	private final String messageServerPass;
	private final String messageServerQueue;
	private final String skynetServer;
	private final int port;

	public String getMessageServer()
	{
		return messageServer;
	}

	public String getMessageServerPort()
	{
		return messageServerPort;
	}

	public String getMessageServerUser()
	{
		return messageServerUser;
	}

	public String getMessageServerPass()
	{
		return messageServerPass;
	}

	public String getMessageServerQueue()
	{
		return messageServerQueue;
	}

	public String getSkynetServer()
	{
		return skynetServer;
	}

	public int getPort()
	{
		return port;
	}
	
	public boolean isValid()
	{
		return getMessageServer() != null && getMessageServerUser() != null && getMessageServerPass() != null && getMessageServerQueue() != null && getSkynetServer() != null;
	}

	public ServiceStarter()
	{
		/* get the system properties */
		messageServer = System.getProperty(CommonConstants.STOMP_MESSAGE_SERVER_SYSTEM_PROPERTY);
		messageServerPort = System.getProperty(CommonConstants.STOMP_MESSAGE_SERVER_PORT_SYSTEM_PROPERTY);
		messageServerUser = System.getProperty(CommonConstants.STOMP_MESSAGE_SERVER_USER_SYSTEM_PROPERTY);
		messageServerPass = System.getProperty(CommonConstants.STOMP_MESSAGE_SERVER_PASS_SYSTEM_PROPERTY);
		messageServerQueue = System.getProperty(CommonConstants.STOMP_MESSAGE_SERVER_QUEUE_SYSTEM_PROPERTY);
		skynetServer = System.getProperty(CommonConstants.SKYNET_SERVER_SYSTEM_PROPERTY);

		int convertedPort = DEFAULT_PORT;
		try
		{
			convertedPort = Integer.parseInt(messageServerPort);
		}
		catch (final Exception ex)
		{
			System.out.println(ex.toString());
		}

		port = convertedPort;
		
		NotificationUtilities.dumpMessageToStdOut("Message Server (" + CommonConstants.STOMP_MESSAGE_SERVER_SYSTEM_PROPERTY + "): " + messageServer);
		NotificationUtilities.dumpMessageToStdOut("Message Server Port (" + CommonConstants.STOMP_MESSAGE_SERVER_PORT_SYSTEM_PROPERTY + "): " + port);
		NotificationUtilities.dumpMessageToStdOut("Message Server User (" + CommonConstants.STOMP_MESSAGE_SERVER_USER_SYSTEM_PROPERTY + "): " + messageServerUser);
		NotificationUtilities.dumpMessageToStdOut("Message Server Queue (" + CommonConstants.STOMP_MESSAGE_SERVER_QUEUE_SYSTEM_PROPERTY + "): " + messageServerQueue);
		NotificationUtilities.dumpMessageToStdOut("REST Server (" + CommonConstants.SKYNET_SERVER_SYSTEM_PROPERTY + "): " + skynetServer);
	}

	public void start(final BaseServiceThread serviceThread)
	{
		/* add the shutdown hook */
		try
		{
			Runtime.getRuntime().addShutdownHook(new ShutdownThread(serviceThread, SHUTDOWN_TIME));
		}
		catch (final Throwable t)
		{
			// we get here when the program is run with java
			// version 1.2.2 or older
			NotificationUtilities.dumpMessageToStdOut("Could not add Shutdown hook");
		}

		/* run the service thread */
		serviceThread.start();

		/* wait for the service thread to complete */
		try
		{
			serviceThread.join();
		}
		catch (final Exception ex)
		{
			System.out.println(ex.toString());
			System.out.println("Thread interrupted");
		}
	}
}
