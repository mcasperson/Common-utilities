package com.redhat.ecs.services.commonstomp;

import com.redhat.ecs.commonthread.BaseWorkQueue;

public final class StompWorkQueue extends BaseWorkQueue<BaseStompRunnable>
{
	private static StompWorkQueue instance = null;
	
	synchronized public static StompWorkQueue getInstance()
	{
		if (instance == null)
		{
			instance = new StompWorkQueue();
		}
		return instance;
	}
	
	private StompWorkQueue()
	{
		super();
	}
}