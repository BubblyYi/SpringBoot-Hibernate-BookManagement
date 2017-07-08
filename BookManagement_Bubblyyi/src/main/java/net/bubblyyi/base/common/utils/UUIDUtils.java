package net.bubblyyi.base.common.utils;

import java.util.UUID;

/**
 * 生成唯一id工具
 * @author BubblyYi
 * @since 2017年6月29日
 */
public class UUIDUtils {
	
	public static String makeID()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
