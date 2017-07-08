package net.bubblyyi.base.service;

import net.bubblyyi.base.entity.Book;
import net.bubblyyi.base.service.support.IBaseService;

/**
 * 图书服务类
 * @author BubblyYi
 * @since 2017年6月29日
 */
public interface IBookService extends IBaseService<Book, String>{
	
	/**
	 * 根据图书id查找图书
	 * @parameter id
	 * @return Book
	 */
	Book findByBookId(String id);
	
	/**
	 * 根据书名查图书
	 * @parameter bookName
	 * @return Book
	 */
	Book findByBookName(String bookName);
	
	/**
	 * 根据出版社查图书
	 * @parameter bookPress
	 * @return Book
	 */
	Book findByBookPress(String bookPress);
	
	/**
	 * 保存或更新图书信息
	 * @parameter book
	 * @return void
	 */
	void saveOrUpdate(Book book);

}
