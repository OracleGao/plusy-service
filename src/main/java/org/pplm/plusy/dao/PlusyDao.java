package org.pplm.plusy.dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.PlusyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PlusyDao {
	
	@Value("${plusy.config}")
	private File configFile; 
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private List<PlusyBean> cache;
	
	@PostConstruct
	private void init() {
		try {
			cache = objectMapper.readValue(configFile, new TypeReference<List<PlusyBean>>(){});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<PlusyBean> getAll() {
		return cache;
	}
	
}
