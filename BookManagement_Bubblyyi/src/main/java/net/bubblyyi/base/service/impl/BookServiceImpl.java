package net.bubblyyi.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.bubblyyi.base.common.utils.UUIDUtils;
import net.bubblyyi.base.dao.IBookDao;
import net.bubblyyi.base.dao.support.IBaseDao;
import net.bubblyyi.base.entity.Book;
import net.bubblyyi.base.service.IBookService;
import net.bubblyyi.base.service.support.impl.BaseServiceImpl;

/**
 * 图书管理服务实现类
 * @author BubblyYi
 * @since 2017年6月29日
 */
@Service
public class BookServiceImpl extends BaseServiceImpl<Book, String> implements IBookService{

	@Autowired
	private IBookDao bookDao;
	
	@Override
	public IBaseDao<Book, String> getBaseDao() {
		// TODO Auto-generated method stub
		return this.bookDao;
	}
	
	@Override
	public Book findByBookId(String id) {
		// TODO Auto-generated method stub
		return bookDao.findByBookId(id);
	}

	@Override
	public Book findByBookName(String bookName) {
		// TODO Auto-generated method stub
		return bookDao.findByBookName(bookName);
	}

	@Override
	public Book findByBookPress(String bookPress) {
		// TODO Auto-generated method stub
		return bookDao.findByBookPress(bookPress);
	}

	@Override
	public void saveOrUpdate(Book book) {
		
		if(book.getBookId()==null||book.getBookId().length()==0)
		{
			book.setBookId(UUIDUtils.makeID());
			book.setCurrentInventory(book.getBookInventory());
			save(book);
		}else
		{
			Book mBook = findByBookId(book.getBookId());
			mBook.setBookName(book.getBookName());
			mBook.setBookAuthor(book.getBookAuthor());
			mBook.setBookPress(book.getBookPress());
			mBook.setBookInventory(book.getBookInventory());
			mBook.setCurrentInventory(book.getCurrentInventory());
			update(mBook);
		}
		
	}
	
}
