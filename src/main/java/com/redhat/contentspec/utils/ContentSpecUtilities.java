package com.redhat.contentspec.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.redhat.contentspec.ContentSpec;
import com.redhat.contentspec.KeyValueNode;
import com.redhat.contentspec.Level;
import com.redhat.contentspec.Node;
import com.redhat.ecs.commonstructures.StringToCSNodeCollection;
import com.redhat.ecs.commonutils.CollectionUtilities;

public class ContentSpecUtilities
{
	private static final List<String> translatableMetaData = CollectionUtilities.toArrayList(new String[]
			{ "title", "product", "subtitle", "abstract" });
	
	/**
	 * Generates a random target it in the form of T<Line Number>0<Random Number><count>.
	 * I.e. The topic is on line 50 and the target to be created for is topic 4 in a process, the 
	 * output would be T500494
	 * 
	 * @param count The count of topics in the process.
	 * @return The partially random target id.
	 */
	public static String generateRandomTargetId(final int line, final int count)
	{
		return generateRandomTargetId(line) + count;
	}
	
	/**
	 * Generates a random target it in the form of T<Line Number>0<Random Number>.
	 * The random number is between 0-49.
	 * 
	 * @param line The line number the topic is on.
	 * @return The partially random target id.
	 */
	public static String generateRandomTargetId(final int line)
	{
		int randomNum = (int) (Math.random() * 50);
		return "T" + line + "0" + randomNum;
	}
	
	public static List<StringToCSNodeCollection> getTranslatableStrings(final ContentSpec contentSpec, final boolean allowDuplicates)
	{
		if (contentSpec == null)
			return null;

		final List<StringToCSNodeCollection> retValue = new ArrayList<StringToCSNodeCollection>();

		final List<Node> contentSpecNodes = contentSpec.getNodes();
		for (final Node node : contentSpecNodes)
		{
			if (node instanceof KeyValueNode)
			{
				if (translatableMetaData.contains(((KeyValueNode<?>) node).getKey()))
				{
					
				}
			}
		}
		
		final List<Level> levels = contentSpec.getBaseLevel().getChildLevels();
		for (final Level level : levels)
		{
			getTranslatableStringsFromLevel(level, retValue, allowDuplicates);
		}

		return CollectionUtilities.toArrayList(retValue);
	}
	
	private static void getTranslatableStringsFromLevel(final Level level, final List<StringToCSNodeCollection> translationStrings, final boolean allowDuplicates)
	{
		if (level == null || translationStrings == null)
			return;

		addTranslationToNodeDetailsToCollection(level.getTitle(), level, allowDuplicates, translationStrings);
		
		final List<Level> levels = level.getChildLevels();
		for (final Level childLevel : levels)
		{
			getTranslatableStringsFromLevel(childLevel, translationStrings, allowDuplicates);
		}
	}
	
	public static void replaceTranslatedStrings(final ContentSpec contentSpec, final Map<String, String> translations)
	{
		if (contentSpec == null || translations == null || translations.size() == 0)
			return;

		/*
		 * Get the translation strings and the nodes that the string maps to. We
		 * assume that the text being provided here is an exact match for the
		 * text that was supplied to getTranslatableStrings originally, which we
		 * then assume matches the strings supplied as the keys in the
		 * translations parameter.
		 */
		final List<StringToCSNodeCollection> stringToNodeCollections = getTranslatableStrings(contentSpec, false);

		if (stringToNodeCollections == null || stringToNodeCollections.size() == 0)
			return;

		for (final StringToCSNodeCollection stringToNodeCollection : stringToNodeCollections)
		{
			final String originalString = stringToNodeCollection.getTranslationString();
			final ArrayList<ArrayList<Node>> nodeCollections = stringToNodeCollection.getNodeCollections();

			if (nodeCollections != null && nodeCollections.size() != 0)
			{
				if (translations.containsKey(originalString))
				{
					final String translation = translations.get(originalString);

					for (final ArrayList<Node> nodes : nodeCollections)
					{
						if (nodes != null && nodes.size() != 0)
						{
							for (final Node node : nodes)
							{
								if (node instanceof Level)
								{
									((Level)node).setTitle(translation);
								}
							}
						}
					}
				}
			}
		}
	}
	
	private static StringToCSNodeCollection findExistingText(final String text, final List<StringToCSNodeCollection> translationStrings)
	{
		for (final StringToCSNodeCollection stringToNodeCollection : translationStrings)
		{
			if (stringToNodeCollection.getTranslationString().equals(text))
				return stringToNodeCollection;
		}

		return null;
	}

	private static void addTranslationToNodeDetailsToCollection(final String text, final Node node, final boolean allowDuplicates, final List<StringToCSNodeCollection> translationStrings)
	{
		final ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		addTranslationToNodeDetailsToCollection(text, nodes, allowDuplicates, translationStrings);
	}

	private static void addTranslationToNodeDetailsToCollection(final String text, final ArrayList<Node> nodes, final boolean allowDuplicates, final List<StringToCSNodeCollection> translationStrings)
	{

		if (allowDuplicates)
		{
			translationStrings.add(new StringToCSNodeCollection(text).addNodeCollection(nodes));
		}
		else
		{
			final StringToCSNodeCollection stringToNodeCollection = findExistingText(text, translationStrings);

			if (stringToNodeCollection == null)
				translationStrings.add(new StringToCSNodeCollection(text).addNodeCollection(nodes));
			else
				stringToNodeCollection.addNodeCollection(nodes);
		}
	}
}
