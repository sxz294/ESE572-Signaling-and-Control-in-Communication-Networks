package hash_IPLookup;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MultiValueMap<K, V> {
		 /**
	     * add Key-Value�
	     *
	     * @param key   key.
	     * @param value value.
	     */
	    void add(K key, V value);

	    /**
	     * add Key-List<Value>�
	     *
	     * @param key    key.
	     * @param values values.
	     */
	    void add(K key, List<V> values);

	    /**
	     * set a Key-Value if this Key exists, it will be replaced,else add this new key
	     *
	     * @param key   key.
	     * @param value values.
	     */
	    void set(K key, V value);

	    /**
	     * set Key-List<Value>,exists, it will be replaced,else add this new key
	     * @param key    key.
	     * @param values values.
	     * @see #set(Object, Object)
	     */
	    void set(K key, List<V> values);

	    /**
	     * replace all Key-List<Value>�
	     *
	     * @param values values.
	     */
	    void set(Map<K, List<V>> values);

	    /**
	     * remove a Key as well as its corresponding values
	     *
	     * @param key key.
	     * @return value.
	     */
	    List<V> remove(K key);

	    /**
	     * 
	     * Remove all key-value.
	     */
	    void clear();

	    /**
	     * get Key set�
	     * @return Set.
	     */
	    Set<K> keySet();

	    /**
	     * get all value list�
	     *
	     * @return List.
	     */
	    List<V> values();

	    /**
	     * get some value under some key�
	     *
	     * @param key   key.
	     * @param index index value.
	     * @return The value.
	     */
	    V getValue(K key, int index);

	    /**
	     * get all the values of one key�
	     *
	     * @param key key.
	     * @return values.
	     */
	    List<V> getValues(K key);

	    /**
	     *  get MultiValueMap's size.
	     *
	     * @return size.
	     */
	    int size();

	    /**
	     * judge if MultiValueMap is null.
	     *
	     * @return True: empty, false: not empty.
	     */
	    boolean isEmpty();

	    /**
	     * judge if MultiValueMap contain some Key.
	     *
	     * @param key key.
	     * @return True: contain, false: none.
	     */
	    boolean containsKey(K key);

	}

	

