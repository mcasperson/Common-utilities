package com.redhat.topicindex.messaging;

/**
 * This class defines the message that is sent to the Docbook Renderer Service
 * which specifies what kind of entity is being rendered, and the entity id.
 * This will be converted to JSON when sent to the messaging service.
 */
public class DocbookRendererMessage
{
	public int entityType;
	public int entityId;
	
	public DocbookRendererMessage(final int entityType, final int entityId)
	{
		this.entityType = entityType;
		this.entityId = entityId;
	}
}
