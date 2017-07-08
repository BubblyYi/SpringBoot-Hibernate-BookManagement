package net.bubblyyi.base.dao;

import org.springframework.stereotype.Repository;

import net.bubblyyi.base.dao.support.IBaseDao;
import net.bubblyyi.base.entity.Book;
import net.bubblyyi.base.entity.User;

@Repository
public interface IBookDao extends IBaseDao<Book, String>{
	
	Book findByBookId(String bookId);
	
	Book findByBookName(String bookName);
	
	Book findByBookPress(String bookPress);
	
	
}
