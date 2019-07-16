package com.pinyougou.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;

/**
 * 认证类: (在读取数据时不是直接从数据库中读取,而是从自己定义的服务方法来从数据库中提取的)
 * 
 * @author 张巍
 *
 */

public class UserDetailsServiceImpl implements UserDetailsService {

	private SellerService sellerService;

	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	/**
	 * username:用户在界面登录的id
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 测试输出
		System.out.println("经过了UserDetailsServiceImpl");

		// 构建角色列表

		List<GrantedAuthority> grantAuths = new ArrayList();

		grantAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));

		// 得到商家对象
		TbSeller seller = sellerService.findOne(username);

		// 判断是否存在
		if (seller != null) {
			// 假如商家审核通过
			if (seller.getStatus().equals("1")) {

				return new User(username, seller.getPassword(), grantAuths);
			} else {
				// 审核不通过
				return null;
			}
		} else {
			return null;
		}

	}

}
