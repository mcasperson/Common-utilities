package com.redhat.ecs.services.commonstomp;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import com.redhat.ecs.commonutils.NotificationUtilities;
import com.redhat.ecs.servicepojo.ServiceStarter;

import net.ser1.stomp.Client;

public abstract class BaseStompRunnable implements Runnable
{
	/** The message that is going to be processed */
	private final String message;
	/** The headers sent with the STOMP message */
	private final Map<String, String> headers;
	/**
	 * true if a shutdown has been initialised. This variable should be checked
	 * at regular intervals in long running processes.
	 * 
	 * TODO: Should use Thread.interrupted() here instead.
	 */
	private AtomicBoolean shutdownRequested = new AtomicBoolean(false);
	/** a reference to the STOMP client */
	private final Client client;
	/** A reference to the object that holds the details of the connections */
	private final ServiceStarter serviceStarter;

	public String getMessage()
	{
		return message;
	}

	public BaseStompRunnable(final Client client, final ServiceStarter serviceStarter, final String message, final Map<String, String> headers, final boolean shutdownRequested)
	{
		this.message = message;
		this.client = client;
		this.serviceStarter = serviceStarter;
		this.headers = headers;
		this.shutdownRequested.set(shutdownRequested);
	}

	public boolean isShutdownRequested()
	{
		return shutdownRequested.get();
	}

	public void setShutdownRequested(final boolean shutdownRequested)
	{
		this.shutdownRequested.set(shutdownRequested);
	}

	public Client getClient()
	{
		return client;
	}

	/**
	 * This method should be called when a thread is shutdown early and did not
	 * get a chance to fully process its message. This method should NOT be
	 * called if message ACking is being used to remove messages only when they
	 * have been fully processed.
	 */
	protected void resendMessage()
	{
		NotificationUtilities.dumpMessageToStdOut("Adding message back to queue in response to a shutdown event");
		this.client.send(this.getServiceStarter().getMessageServerQueue(), message);
	}

	public Map<String, String> getHeaders()
	{
		return headers;
	}

	public ServiceStarter getServiceStarter()
	{
		return serviceStarter;
	}
}
