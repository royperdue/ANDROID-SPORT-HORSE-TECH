package com.sporthorsetech.horseshoepad.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Content
{
    public static final List<ContentItem> ITEMS = new ArrayList<ContentItem>();
    public static final Map<String, ContentItem> ITEM_MAP = new HashMap<String, ContentItem>();
    private static final int COUNT = 25;

    static
    {
        // Add some items.
        for (int i = 1; i <= COUNT; i++)
        {
            addItem(createContentItem(i));
        }
    }

    private static void addItem(ContentItem item)
    {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static ContentItem createContentItem(int position)
    {
        return new ContentItem(String.valueOf(position), "Item " + position, makeDetails(position));
    }

    private static String makeDetails(int position)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        for (int i = 0; i < position; i++)
        {
            builder.append("\nMore details information here.");
        }

        return builder.toString();
    }

    public static class ContentItem
    {
        public final String id;
        public final String content;
        public final String details;

        public ContentItem(String id, String content, String details)
        {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString()
        {
            return content;
        }
    }
}
