package hash_IPLookup;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class LinkedMultiValueMap<K, V> implements MultiValueMap<K, V> {

	    protected Map<K, List<V>> mSource = new LinkedHashMap<K, List<V>>();

	    public LinkedMultiValueMap() {
	    }

	    @Override
	    public void add(K key, V value) {
	        if (key != null) {
	        	//if key exists, append value; else create a new list to add value
	            if (!mSource.containsKey(key))
	                mSource.put(key, new ArrayList<V>(2));
	            mSource.get(key).add(value);
	        }
	    }

	    @Override
	    public void add(K key, List<V> values) {
	        // traverse the value in the list
	        for (V value : values) {
	            add(key, value);
	        }
	    }

	    @Override
	    public void set(K key, V value) {
	        // remove the key and add new key-value
	        mSource.remove(key);
	        add(key, value);
	    }

	    @Override
	    public void set(K key, List<V> values) {
	        //remove key, add List<V>
	        mSource.remove(key);
	        add(key, values);
	    }

	    @Override
	    public void set(Map<K, List<V>> map) {
	        // remove all the value, append all the value in the map
	        mSource.clear();
	        mSource.putAll(map);
	    }

	    @Override
	    public List<V> remove(K key) {
	        return mSource.remove(key);
	    }

	    @Override
	    public void clear() {
	        mSource.clear();
	    }

	    @Override
	    public Set<K> keySet() {
	        return mSource.keySet();
	    }

	    @Override
	    public List<V> values() {
	        // create a temp list to save key's value
	        List<V> allValues = new ArrayList<V>();

	        // append all the value of the key to the temp list
	        Set<K> keySet = mSource.keySet();
	        for (K key : keySet) {
	            allValues.addAll(mSource.get(key));
	        }
	        return allValues;
	    }

	    @Override
	    public List<V> getValues(K key) {
	        return mSource.get(key);
	    }

	    @Override
	    public V getValue(K key, int index) {
	        List<V> values = mSource.get(key);
	        if (values != null && index < values.size())
	            return values.get(index);
	        return null;
	    }

	    @Override
	    public int size() {
	        return mSource.size();
	    }

	    @Override
	    public boolean isEmpty() {
	        return mSource.isEmpty();
	    }

	    @Override
	    public boolean containsKey(K key) {
	        return mSource.containsKey(key);
	    }

	}

