package org.pplm.plusy.bean;

import org.springframework.data.annotation.Transient;

public class UserBean {
	
	private String username;
	
	private String password;
	
	@Transient
	private String token;

	public UserBean() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserBean)) {
			return false;
		}
		String username = ((UserBean)obj).username;
		if (username == null) {
			if (this.username == null) {
				return true;
			}
			return false;
		}
		return username.equals(this.username);
	}
	
}
