package org.pplm.plusy.bean;

import org.apache.commons.codec.digest.DigestUtils;
import org.pplm.plusy.bean.scrapyd.ItemBean;

public class DataBean extends ItemBean {
	
	private String rowId;
	private int isRead;
	
	
	public DataBean() {
		super();
	}

	public DataBean(ItemBean itemBean) {
		super();
		this.href = itemBean.getHref();
		this.text = itemBean.getText();
		this.timestamp = itemBean.getTimestamp();
		this.genRowId();
	}
	
	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	
	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	private void genRowId() {
		this.rowId = this.timestamp.replaceAll("-",  "") + DigestUtils.sha1Hex(this.href);
	}
	
}
