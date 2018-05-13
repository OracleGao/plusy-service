package org.pplm.plusy.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	private Map<String, String> tokens = new HashMap<>();
	private Map<String, String> usernames = new HashMap<>();
	
	public String getUsername(String token) {
		if (tokens.containsKey(token)) {
			return tokens.get(token);
		}
		return null;
	}
	
	public String genToken(String username) {
		removeUsername(username);
		String token = DigestUtils.sha1Hex(String.valueOf(System.currentTimeMillis()).getBytes());
		tokens.put(token, username);
		usernames.put(username, token);
		return token;
	}
	
	public void removeUsername(String username) {
		if (usernames.containsKey(username)) {
			tokens.remove(usernames.remove(username));
		}
	}
	
}
