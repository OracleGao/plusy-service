package org.pplm.plusy.dao;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.DataBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PlusyDao {
	
	public static final String DATA_FILE_POSTFIX = ".jd";
	
	@Value("${plusy.store.path}")
	private File storePath;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Map<String, List<DataBean>> cache;
	
	@PostConstruct
	private void init() {
		cache = new HashMap<>();
		if (storePath.exists()) {
			File[] files = storePath.listFiles((dir, name)-> name.endsWith(DATA_FILE_POSTFIX));
			if (files != null && files.length > 0) {
				Stream.of(files).filter(file->file.isFile()).forEach(file->{
					try {
						List<DataBean> datas = objectMapper.readValue(file, new TypeReference<List<DataBean>>(){});
						if (datas != null && datas.size() > 0) {
							String filename = file.getName();
							String spider = filename.substring(0, filename.length() - 3);
							cache.put(spider, datas);
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
	
	public void putSave(String spider, List<DataBean> datas) throws JsonGenerationException, JsonMappingException, IOException {
		cache.put(spider, datas);
		save(spider, datas);
	}
	
	public List<DataBean> get(String spider) {
		if (cache.containsKey(spider)) {
			return cache.get(spider);
		}
		return Collections.emptyList();
	}
	
	public void save(String spider) throws JsonGenerationException, JsonMappingException, IOException {
		List<DataBean> datas = get(spider);
		save(spider, datas);
	}
	
	private void save(String spider, List<DataBean> datas) throws JsonGenerationException, JsonMappingException, IOException {
		objectMapper.writeValue(new File(storePath, spider + DATA_FILE_POSTFIX), datas); 
	}
	
	public void saveAll() {
		cache.entrySet().forEach(entry -> {
			try {
				save(entry.getKey(), entry.getValue());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
}
