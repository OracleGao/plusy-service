package org.pplm.plusy.dao.base;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class ListCacheDao<T> {
	
	protected List<T> cache;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	protected void initCache() {
		try {
			this.cache = objectMapper.readValue(getConfigFile(), getTypeReference());
		} catch (IOException e) {
			this.cache = Collections.emptyList();
			e.printStackTrace();
		}
	}
	
	public abstract File getConfigFile();
	
	public abstract TypeReference<List<T>> getTypeReference();
	
	public List<T> getAll() {
		return cache;
	}
	
	public void save() throws IOException {
		objectMapper.writeValue(getConfigFile(), cache);
	}

	public void put(T t) {
		int index = cache.indexOf(t);
		if (index == -1) {
			cache.add(t);
		} else {
			cache.remove(index);
			cache.add(index, t);
		}
	}
	
	public void putSave(T t) throws IOException {
		put(t);
		save();
	}
	
}
