package org.pplm.plusy.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.pplm.plusy.bean.DataBean;
import org.pplm.plusy.bean.scrapyd.ItemBean;
import org.pplm.plusy.dao.DataDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

	@Autowired
	private DataDao dataDao;

	public List<DataBean> getDatas(String spider) {
		return dataDao.get(spider);
	}

	public void putItems(final String spider, List<ItemBean> items) throws IOException {
		if (items.isEmpty()) {
			return;
		}
		final List<DataBean> datas = dataDao.get(spider);
		if (datas.isEmpty()) {
			List<DataBean> newDatas = items.stream().map(item -> new DataBean(item)).distinct()
					.sorted((d1, d2) -> d2.getRowId().compareTo(d1.getRowId())).collect(Collectors.toList());
			dataDao.putSave(spider, newDatas);
		} else {
			List<DataBean> newDatas = items.stream().map(item -> new DataBean(item)).distinct()
					.filter(data -> datas.indexOf(data) == -1)
					.sorted((d1, d2) -> d2.getRowId().compareTo(d1.getRowId())).collect(Collectors.toList());
			if (!newDatas.isEmpty()) {
				newDatas.addAll(datas);
				dataDao.putSave(spider, newDatas);
			}
		}
	}

	public void setRead(String spider, String rowId) throws IOException {
		List<DataBean> datas = dataDao.get(spider);
		if (!datas.isEmpty()) {
			for (DataBean data : datas) {
				if (rowId.equals(data.getRowId())) {
					data.setIsRead(1);
					dataDao.putSave(spider, datas);
					break;
				}
			}
		}
	}
	
}
