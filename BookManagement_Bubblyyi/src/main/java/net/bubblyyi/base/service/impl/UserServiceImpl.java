package net.bubblyyi.base.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import net.bubblyyi.base.common.utils.MD5Utils;
import net.bubblyyi.base.dao.IUserDao;
import net.bubblyyi.base.dao.support.IBaseDao;
import net.bubblyyi.base.entity.Role;
import net.bubblyyi.base.entity.User;
import net.bubblyyi.base.service.IRoleService;
import net.bubblyyi.base.service.IUserService;
import net.bubblyyi.base.service.support.impl.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 用户服务实现类
 * @author BubblyYi
 * @since 2017年6月29日
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, Integer> implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleService roleService;
	
	@Override
	public IBaseDao<User, Integer> getBaseDao() {
		return this.userDao;
	}

	@Override
	public User findByUserName(String username) {
		return userDao.findByUserName(username);
	}
	
	@Override
	public User findByUserCode(String usercode) {
		return userDao.findByUserCode(usercode);
	}

	@Override
	public void saveOrUpdate(User user) {
		if(user.getId() != null){
			User dbUser = find(user.getId());
			dbUser.setUserName(user.getUserName());
			dbUser.setPassword(MD5Utils.md5(user.getPassword()));
			dbUser.setTelephone(user.getTelephone());
			dbUser.setLocked(user.getLocked());
			dbUser.setUpdateTime(new Date());
			System.out.println("userinfo:"+user.toString());
			update(dbUser);
		}else{
			
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			user.setDeleteStatus(0);
			user.setPassword(MD5Utils.md5(user.getPassword()));
			//设定默认分权为学生权限
			Role role;
			Set<Role> roles = new HashSet<Role>();
			role = roleService.find(2);
			roles.add(role);
			user.setRoles(roles);
			System.out.println("userinfo:"+user.toString());
			save(user);
		}
	}
	
	

	@Override
	public void delete(Integer id) {
		User user = find(id);
		Assert.state(!"admin".equals(user.getUserName()),"超级管理员用户不能删除");
		super.delete(id);
	}

    //授权管理
	@Override
	public void grant(Integer id, String[] roleIds) {
		User user = find(id);
		Assert.notNull(user, "用户不存在");
		Assert.state(!"admin".equals(user.getUserName()),"超级管理员用户不能修改管理角色");
		Role role;
		Set<Role> roles = new HashSet<Role>();
		if(roleIds != null){
			for (int i = 0; i < roleIds.length; i++) {
				Integer rid = Integer.parseInt(roleIds[i]);
				role = roleService.find(rid);
				roles.add(role);
			}
		}
		user.setRoles(roles);
		update(user);
	}

	@Override
	public User find(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
