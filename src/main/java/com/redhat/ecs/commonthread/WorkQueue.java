package com.redhat.ecs.commonthread;

public final class WorkQueue extends BaseWorkQueue<Runnable>
{
	private static WorkQueue instance = null;
	
	synchronized public static WorkQueue getInstance()
	{
		if (instance == null)
		{
			instance = new WorkQueue();
		}
		return instance;
	}
	
	private WorkQueue()
	{
		super();
	}
}