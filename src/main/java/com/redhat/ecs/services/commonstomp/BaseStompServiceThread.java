package com.redhat.ecs.services.commonstomp;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.redhat.ecs.commonthread.PoolWorker;
import com.redhat.ecs.commonutils.NotificationUtilities;
import com.redhat.ecs.servicepojo.BaseServiceThread;
import com.redhat.ecs.servicepojo.ServiceStarter;

import net.ser1.stomp.Client;
import net.ser1.stomp.Listener;

/**
 * This is a base class designed to be extended by a service that listens to a
 * STOMP message queue.
 */
abstract public class BaseStompServiceThread extends BaseServiceThread implements Listener
{
	/** Wait 5 seconds from a failed connection before reconnecting */
	private static final int RETRY = 5000;
	/** Attempt to reconnect every 30 seconds */
	private static final int RECONNECT = 30000;
	/** true if this service should continue to loop and receive messages */
	private final AtomicBoolean running = new AtomicBoolean(true);
	/** true if this service has shut down cleanly */
	private final AtomicBoolean shutdown = new AtomicBoolean(false);
	/** A reference to the object that was used to parse the System Properties */
	protected final ServiceStarter serviceStarter;
	/** The current STOMP client */
	protected Client client;
	/** The time remaining before a reconnection occurs */
	private int timeToWait = 0;
	/** The time from the last time in the message loop */
	private long lastTime;
	/**
	 * Used to indicate the last state of the STOMP client. Messages will only
	 * be posted to the screen if the client was connected and now can not
	 * connect, or if we previously could not connect but are now connected.
	 */
	private Boolean wasConnected = null;
	/** Use to indicate that the STOMP client is subscribed to a message queue */
	private boolean subscribed = false;

	public boolean isRunning()
	{
		return running.get();
	}

	public BaseStompServiceThread(final ServiceStarter serviceStarter)
	{
		this.serviceStarter = serviceStarter;
	}

	/**
	 * The run method provides an infinite loop that will connect, unsubscribe
	 * and disconnect from the STOMP server at regular intervals. This allows
	 * the service to reconnect to a restarted STOMP server automatically
	 * (because STOMP 1.0 doesn't have the concept of a heart beat), and also
	 * stops the WorkQueue from becoming too full (because we only resubscribe
	 * once the work queue has been cleared).
	 * 
	 * Manually ACKing messages would add more reliability, but there is a
	 * HornetQ bug that currently prevents this from being done - see
	 * https://issues.jboss.org/browse/HORNETQ-727
	 */
	public void run()
	{
		lastTime = System.currentTimeMillis();

		/* enter the service loop */
		while (running.get())
		{
			final long thisTime = System.currentTimeMillis();
			final long dt = thisTime - lastTime;
			lastTime = thisTime;

			timeToWait -= dt;

			if (timeToWait <= 0)
			{
				/*
				 * Automatically unsubscribe after a certain period of time.
				 * This will allows the service to recover from a STOMP server
				 * reboot
				 */
				if (subscribed)
				{
					if (this.serviceStarter.getMessageServerQueue() != null)
						client.unsubscribe(this.serviceStarter.getMessageServerQueue());
					subscribed = false;
				}
				/*
				 * Attempt to connect once the worker threads have finished
				 * their jobs. This gives the service a chance to clear the back
				 * log before accepting any new jobs.
				 */
				else if (StompWorkQueue.getInstance().getRunningThreadsCount() == 0)
				{
					disconnect();
					connect();
				}
			}

			/* don't chew up the CPU */
			try
			{
				Thread.sleep(100);
			}
			catch (final InterruptedException ex)
			{
				ex.printStackTrace();
				this.running.set(false);
			}
		}

		/* Stop receiving messages */
		if (subscribed)
		{
			if (this.serviceStarter.getMessageServerQueue() != null)
				client.unsubscribe(this.serviceStarter.getMessageServerQueue());
			subscribed = false;
		}

		/*
		 * Notify the queued threads that a shutdown has been requested. These
		 * threads should check to see if a shutdown has been requested before
		 * they start any processing.
		 */
		final LinkedList<BaseStompRunnable> queuedThreads = StompWorkQueue.getInstance().getQueueCopy();
		for (final BaseStompRunnable queuedThread : queuedThreads)
			queuedThread.setShutdownRequested(true);

		/*
		 * Notify the running threads that a shutdown has been requested. These
		 * threads should periodically check to see if a shutdown has been
		 * requested during any long running operations, and exit gracefully.
		 */
		final List<PoolWorker<BaseStompRunnable>> runningThreads = StompWorkQueue.getInstance().getRunningThreads();
		for (final PoolWorker<BaseStompRunnable> runningThread : runningThreads)
			runningThread.getRunnable().setShutdownRequested(true);

		/* wait until last worker threads clean up */
		while (StompWorkQueue.getInstance().getRunningThreadsCount() != 0)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (final InterruptedException ex)
			{
				System.out.println("Service Thread Interrupted");
			}
		}

		/*
		 * do any cleanup once the shutdown has completed, but before we
		 * disconnect
		 */
		postShutdownPreDisconnect();

		/* disconnect */
		disconnect();

		/*
		 * do any cleanup once the shutdown has completed, and after we have
		 * disconnected
		 */
		postShutdownPostDisconnect();

		/* notify the shutdown thread that the service thread has finished */
		shutdown.set(true);
	}

	/**
	 * Connects the STOMP client, and optionally subscribes to the message
	 * queue.
	 */
	private void connect()
	{
		try
		{
			client = new Client(serviceStarter.getMessageServer(), serviceStarter.getPort(), serviceStarter.getMessageServerUser(), serviceStarter.getMessageServerPass());

			/* Add a listener to watch for ERROR frames being returned */
			client.addErrorListener(new Listener()
			{
				public void message(@SuppressWarnings("rawtypes") Map headers, String body)
				{
					NotificationUtilities.dumpMessageToStdOut(body);
				}
			});

			/*
			 * ACKing currently doesn't work
			 */

			/*
			 * Set the ack header to client to indicate that we need to manually
			 * ack messages
			 */

			/*
			 * final Map<String, String> headers = new HashMap<String,
			 * String>(); headers.put("ack", "client");
			 * 
			 * client.subscribe(serviceStarter.getMessageServerQueue(), this,
			 * headers);
			 */

			if (serviceStarter.getMessageServerQueue() != null)
				client.subscribe(serviceStarter.getMessageServerQueue(), this);

			if (wasConnected == null || !wasConnected)
			{
				NotificationUtilities.dumpMessageToStdOut("Connected to " + serviceStarter.getMessageServer() + " with the queue " + serviceStarter.getMessageServerQueue());
			}

			wasConnected = true;
			subscribed = true;
			timeToWait = RECONNECT;
		}
		catch (final Exception ex)
		{
			if (wasConnected == null || wasConnected)
				NotificationUtilities.dumpMessageToStdOut("Failed to connect to " + serviceStarter.getMessageServer() + " with the queue " + serviceStarter.getMessageServerQueue());

			/* if we can't connect, clean up the resources */
			if (client != null)
				client.disconnect();

			client = null;
			wasConnected = false;
			subscribed = false;

			timeToWait = RETRY;
		}

	}

	/**
	 * Disconnects the STOMP client
	 */
	private void disconnect()
	{
		if (client != null)
		{
			client.disconnect();
			client = null;
			subscribed = false;
			timeToWait = 0;
		}
	}

	@Override
	public void shutdown()
	{
		running.set(false);
	}

	@Override
	public boolean isShutdown()
	{
		return shutdown.get();
	}

	/**
	 * A default implementation of the message method.
	 */
	public void message(@SuppressWarnings("rawtypes") final Map headers, final String message)
	{
	};

	/**
	 * This method is called after the running threads have finished, but before
	 * the STOMP client has been disconnected
	 */
	protected void postShutdownPreDisconnect()
	{
	};

	/**
	 * This method is called after the running threads have finished, and after
	 * the STOMP client has been disconnected
	 */
	protected void postShutdownPostDisconnect()
	{
	};
}
