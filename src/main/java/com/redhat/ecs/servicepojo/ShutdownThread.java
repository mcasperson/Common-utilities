package com.redhat.ecs.servicepojo;

import com.redhat.ecs.commonutils.NotificationUtilities;

/**
 * An instance of this thread will be executed when the Java VM is shutdown
 * (probably in response to CTRL-C). We capture this event, and use it to
 * disconnect from the STOMP server.
 */
public class ShutdownThread extends Thread
{
	private final long shutdownTime;
	private final BaseServiceThread serviceThread;

	public ShutdownThread(final BaseServiceThread serviceThread, final long shutdownTime)
	{
		this.serviceThread = serviceThread;
		this.shutdownTime = shutdownTime;
	}

	public void run()
	{
		NotificationUtilities.dumpMessageToStdOut("Shutdown Requested");
		
		/* exit the service loop */
		if (serviceThread != null)
			serviceThread.shutdown();

		/* wait for a certain amount of time for the service thread to shutdown */
		final long startTime = System.currentTimeMillis();
		long now = System.currentTimeMillis();
		while (!serviceThread.isShutdown() && now - startTime <= shutdownTime)
		{
			try
			{
				Thread.sleep(100);
			}
			catch (final InterruptedException ex)
			{
				NotificationUtilities.dumpMessageToStdOut("Shutdown Thread Interrupted");
				break;
			}
			
			now = System.currentTimeMillis();
		}
		
		if (now - startTime <= shutdownTime)
		{
			NotificationUtilities.dumpMessageToStdOut("Clean shutdown performed");
		}
		else
		{
			NotificationUtilities.dumpMessageToStdOut("Unclean shutdown performed. Consider increasing the shutdownTime.");
		}
	}
}
