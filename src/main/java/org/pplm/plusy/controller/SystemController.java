package org.pplm.plusy.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.pplm.plusy.bean.UserBean;
import org.pplm.plusy.dao.UserDao;
import org.pplm.plusy.service.TokenService;
import org.pplm.plusy.utils.Constant;
import org.pplm.plusy.utils.ResHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class SystemController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping
	public Map<String, Object> getVersion() {
		return ResHelper.success(Constant.SYSTEM_INFO);
	}
	
	@PostMapping(path = "/system/login")
	public Map<String, Object> loginOnPost(@RequestBody UserBean loginInfo) {
		UserBean userBean = userDao.getUser(loginInfo.getUsername());
		if (userBean == null) {
			return ResHelper.error("invalid username");
		}
		if (!userBean.getPassword().equals(loginInfo.getPassword())) {
			return ResHelper.error("password error");
		}
		UserBean result = new UserBean();
		result.setUsername(userBean.getUsername());
		result.setToken(tokenService.genToken(userBean.getUsername()));
		return ResHelper.success(result);
	}
	
	@PostMapping(path = "/system/logout")
	public Map<String, Object> logoutOnPost(HttpServletRequest request) {
		Object obj = request.getAttribute("username");
		if (obj != null) {
			tokenService.removeUsername(obj.toString());
		}
		return ResHelper.success();
	}
	
}
