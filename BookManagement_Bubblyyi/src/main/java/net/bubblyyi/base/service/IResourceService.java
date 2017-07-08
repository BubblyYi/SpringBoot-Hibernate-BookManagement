package net.bubblyyi.base.service;

import java.util.List;

import net.bubblyyi.base.entity.Resource;
import net.bubblyyi.base.service.support.IBaseService;
import net.bubblyyi.base.vo.ZtreeView;

/**
 * 资源服务类
 * @author BubblyYi
 * @since 2017年6月29日
 */
public interface IResourceService extends IBaseService<Resource, Integer> {

	/**
	 * 获取角色的权限树
	 * @param roleId
	 * @return
	 */
	List<ZtreeView> tree(int roleId);

	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Resource resource);

}
