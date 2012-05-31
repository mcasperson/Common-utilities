package com.redhat.topicindex.rest.specimpl;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * 
 * Copied here to provide an implementation that can be used with GWT
 */
@SuppressWarnings("serial")
public class MultivaluedMapImpl<K, V> extends HashMap<K, List<V>> implements MultivaluedMap<K, V>
{
   public void putSingle(K key, V value)
   {
      List<V> list = new ArrayList<V>();
      list.add(value);
      put(key, list);
   }

   public final void add(K key, V value)
   {
      getList(key).add(value);
   }


   public final void addMultiple(K key, Collection<V> values)
   {
      getList(key).addAll(values);
   }

   public V getFirst(K key)
   {
      List<V> list = get(key);
      return list == null ? null : list.get(0);
   }

   public final List<V> getList(K key)
   {
      List<V> list = get(key);
      if (list == null)
         put(key, list = new ArrayList<V>());
      return list;
   }

   public void addAll(MultivaluedMapImpl<K, V> other)
   {
      for (Map.Entry<K, List<V>> entry : other.entrySet())
      {
         getList(entry.getKey()).addAll(entry.getValue());
      }
   }
}