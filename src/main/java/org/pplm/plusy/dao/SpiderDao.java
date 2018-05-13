package org.pplm.plusy.dao;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.SpiderBean;
import org.pplm.plusy.dao.base.ListCacheDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

@Repository
public class SpiderDao extends ListCacheDao<SpiderBean> {
	
	@Value("${plusy.config.spider}")
	private File configFile; 


	@PostConstruct
	private void init() {
		super.initCache();
	}

	@Override
	public File getConfigFile() {
		return configFile;
	}
	
	@Override
	public TypeReference<List<SpiderBean>> getTypeReference() {
		return new TypeReference<List<SpiderBean>>(){};
	}
	
}
