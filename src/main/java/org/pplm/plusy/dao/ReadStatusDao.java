package org.pplm.plusy.dao;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class ReadStatusDao {
	public static final String READ_STATUS_FILE_POSTFIX = ".jrs";
	
	private Map<String, Map<String, List<String>>> cache;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Value("${plusy.store.readstatus}")
	private File storePath;
	
	@PostConstruct
	public void init () {
		cache = new HashMap<>();
		if (storePath.exists()) {
			File[] files = storePath.listFiles((dir, name)-> name.endsWith(READ_STATUS_FILE_POSTFIX));
			if (files != null && files.length > 0) {
				Stream.of(files).filter(file->file.isFile()).forEach(file->{
					try {
						Map<String, List<String>> readStatus = objectMapper.readValue(file, new TypeReference<Map<String, List<String>>>(){});
						if (readStatus != null && readStatus.size() > 0) {
							String filename = file.getName();
							String username = filename.substring(0, filename.length() - 3);
							cache.put(username, readStatus);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
			}
		} else {
			storePath.mkdirs();
		}
	}
	
	public Map<String, List<String>> getReadStatus(String username) {
		if (cache.containsKey(username)) {
			return cache.get(username);
		}
		return Collections.emptyMap();
	}
	
}
