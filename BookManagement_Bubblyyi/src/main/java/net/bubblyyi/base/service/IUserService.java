package net.bubblyyi.base.service;

import net.bubblyyi.base.entity.User;
import net.bubblyyi.base.service.support.IBaseService;

/**
 * 用户服务类
 * @author BubblyYi
 * @since 2017年6月29日
 */
public interface IUserService extends IBaseService<User, Integer> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUserName(String username);
	
	User findByUserCode(String userCode);

	/**
	 * 增加或者修改用户
	 * @param user
	 */
	void saveOrUpdate(User user);

	/**
	 * 给用户分配角色
	 * @param id 用户ID
	 * @param roleIds 角色Ids
	 */
	void grant(Integer id, String[] roleIds);

}
