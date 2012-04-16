package com.redhat.ecs.servicepojo;

abstract public class BaseServiceThread extends Thread
{
	public abstract void shutdown();
	public abstract boolean isShutdown();
}
