package org.pplm.plusy.dao;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class PlusyDao {
	
	@Value("${plusy.store.path}")
	private File storePath;
	
	public void savePlusy(String name) {
		
	}
	
}
