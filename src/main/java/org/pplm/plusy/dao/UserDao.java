package org.pplm.plusy.dao;

import java.io.File;
import java.util.List;

import javax.annotation.PostConstruct;

import org.pplm.plusy.bean.UserBean;
import org.pplm.plusy.dao.base.ListCacheDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.type.TypeReference;

@Repository
public class UserDao extends ListCacheDao<UserBean> {
	
	@Value("${plusy.config.user}")
	private File configFile; 
	
	@PostConstruct
	private void init() {
		super.initCache();
	}
	
	public UserBean getUser(String username) {
		for (UserBean userBean : super.cache) {
			if (username.equals(userBean.getUsername())) {
				return userBean;
			}
		}
		return null;
	}

	@Override
	public File getConfigFile() {
		return configFile;
	}

	@Override
	public TypeReference<List<UserBean>> getTypeReference() {
		return new TypeReference<List<UserBean>>(){};
	}
	
}
