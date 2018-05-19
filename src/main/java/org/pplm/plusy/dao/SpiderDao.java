package org.pplm.plusy.dao;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.SpiderConfigBean;
import org.pplm.plusy.dao.base.ListCacheDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

@Repository
public class SpiderDao extends ListCacheDao<SpiderConfigBean> {
	
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
	public TypeReference<List<SpiderConfigBean>> getTypeReference() {
		return new TypeReference<List<SpiderConfigBean>>(){};
	}
	
}
