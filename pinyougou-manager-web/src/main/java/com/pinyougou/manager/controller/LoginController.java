package com.pinyougou.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/name")
	public Map name() {

		// 获取当前登录用户
		String name = SecurityContextHolder.getContext().getAuthentication().getName();

		// 创建map集合
		Map map = new HashMap<>();

		// 添加元素到集合
		map.put("loginName", name);

		return map;
	}
}
