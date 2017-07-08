package net.bubblyyi.base.dao;

import net.bubblyyi.base.dao.support.IBaseDao;
import net.bubblyyi.base.entity.User;

import org.springframework.stereotype.Repository;

@Repository
public interface IUserDao extends IBaseDao<User, Integer> {

	User findByUserName(String username);
	
	User findByUserCode(String usercode);

}
