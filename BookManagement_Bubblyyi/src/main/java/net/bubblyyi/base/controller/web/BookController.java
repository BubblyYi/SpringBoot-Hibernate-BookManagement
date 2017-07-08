package net.bubblyyi.base.controller.web;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.google.gson.Gson;

import net.bubblyyi.base.common.JsonResult;
import net.bubblyyi.base.controller.BaseController;
import net.bubblyyi.base.entity.Book;
import net.bubblyyi.base.entity.BorrowBook;
import net.bubblyyi.base.entity.BorrowList;
import net.bubblyyi.base.service.IBookService;
import net.bubblyyi.base.service.IBorrowBookService;
import net.bubblyyi.base.service.specification.SimpleSpecificationBuilder;
import net.bubblyyi.base.service.specification.SpecificationOperator.Operator;

/**
 * 图书管理类
 * @author BubblyYi
 * @since 2017年6月29日
 */
@Controller
@RequestMapping("/web/books")
public class BookController extends BaseController{
	
	
	@Autowired
	private IBookService bookService;
	@Autowired
	private IBorrowBookService borrowBookService;

	/**
	 * 默认索引页
	 * @parameter 参数及其意义
	 * @return index页面
	 */
	@RequestMapping("/index")
	public String index() {
		return "/admin/books/index";
	}
	/**
	 * 添加图书
	 * @parameter 
	 * @return addform页面
	 */
	@RequestMapping(value = {"/addBook"}, method = RequestMethod.GET)
	public String addBook() {
		return "admin/books/addform";
	}
	
	/**
	 * 删除图书
	 * @parameter 图书id，数据集
	 * @return JsonResult 是否成功
	 */
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult delete(@PathVariable String id,ModelMap map) {
		try {
			bookService.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}
	
	/**
	 * 借书
	 * @parameter Json类型的借书表，数据集
	 * @return JsonResult
	 */
	@RequestMapping(value = { "/borrowlist/{borrowlist}" },method = RequestMethod.POST)
	@ResponseBody
	public JsonResult borrowList(@PathVariable String borrowlist,ModelMap map) {
		
		if(!borrowlist.equals("undefine"))
		{
			Gson gson=new Gson();
			BorrowList mBorrowList=gson.fromJson(borrowlist,BorrowList.class);
			BorrowBook[] borrowBook=new BorrowBook[mBorrowList.getBooklist().length];
			Book[] book=new Book[mBorrowList.getBooklist().length];
			int i=0;
			while(i<mBorrowList.getBooklist().length)
			{
				borrowBook[i]=new BorrowBook();
				book[i]=new Book();
				borrowBook[i].setUserId(mBorrowList.getId());
				borrowBook[i].setBookId(mBorrowList.getBooklist()[i]);
				book[i]=bookService.findByBookId(mBorrowList.getBooklist()[i]);
				//一本书只能借一次，因此需要判断一下该用户是否已经借过该书
				BorrowBook isBorrowBook=borrowBookService.findByUserIdAndBookId(mBorrowList.getId(), mBorrowList.getBooklist()[i]);
				if(book[i].getCurrentInventory()>0)
				{
					if(isBorrowBook==null)
					{
						book[i].setCurrentInventory(book[i].getCurrentInventory()-1);
						bookService.saveOrUpdate(book[i]);
						borrowBookService.save(borrowBook[i]);
					}else
					{
						return JsonResult.failure("您已经借阅该书！");
					}
					
				}else
				{
					return JsonResult.failure("库存不足请重新选择图书！");
				}
				
				i++;
			}
			i=0;
			
			return JsonResult.success();
		}else
		{
			return JsonResult.failure("未选择要借阅的书籍！");
		}
		
		
	}
	
	/**
	 * 还书表
	 * @parameter 借书用户id
	 * @return String 借书表信息和书籍信息
	 */
	@RequestMapping(value = { "/returnBookList/{id}" },method = RequestMethod.POST)
	@ResponseBody
	public String ReturnBookList(@PathVariable String id,ModelMap map) {
		
		BorrowBook[] borrowBooks=borrowBookService.findByUserId(Integer.parseInt(id));
		Book[] books=new Book[borrowBooks.length];
		Date date=null;
		for(int i=0;i<books.length;i++)
		{
			books[i]=bookService.findByBookId(borrowBooks[i].getBookId());			
		}
		Map<String,Object> resultMap=new HashMap();
		resultMap.put("borrowBooks", borrowBooks);
		resultMap.put("books", books);
		Gson gson=new Gson();
		String jsonStr = gson.toJson(resultMap);	
		
		return jsonStr;
	}
	
	/**
	 * 归还图书
	 * @parameter Json类型的借书表
	 * @return JsonResult
	 */
	@RequestMapping(value = {"/returnBook/{borrowlist}"}, method = RequestMethod.POST)
	@ResponseBody
	public JsonResult returnBook(@PathVariable String borrowlist) {

		Gson gson=new Gson();
		BorrowList mBorrowList=gson.fromJson(borrowlist,BorrowList.class);
		BorrowBook[] borrowBook=new BorrowBook[mBorrowList.getBooklist().length];
		Book[] book=new Book[mBorrowList.getBooklist().length];
		int i=0;
		while(i<mBorrowList.getBooklist().length)
		{
			borrowBook[i]=new BorrowBook();
			book[i]=new Book();
			borrowBook[i].setUserId(mBorrowList.getId());
			borrowBook[i].setBookId(mBorrowList.getBooklist()[i]);
			book[i]=bookService.findByBookId(mBorrowList.getBooklist()[i]);
			book[i].setCurrentInventory(book[i].getCurrentInventory()+1);
			bookService.saveOrUpdate(book[i]);
			borrowBookService.deletByUserIdAndBookId(borrowBook[i].getUserId(), borrowBook[i].getBookId());;
			i++;
		}
		i=0;
		return JsonResult.success();
	}
	
	/**
	 * 修改图书响应请求
	 * @parameter 修改的图书id，数据集
	 * @return String addform页面
	 */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable String id,ModelMap map) {
		
		Book book = bookService.findByBookId(id);
		map.put("book", book);
		return "admin/books/addform";
	}
	
	/**
	 * 修改图书
	 * @parameter 图书实体，数据集
	 * @return JsonResult 是否修改成功
	 */
	
	@RequestMapping(value= {"/edit"} ,method = RequestMethod.POST)
	@ResponseBody
	public JsonResult edit( Book book,ModelMap map){
		try {
			bookService.saveOrUpdate(book);
		} catch (Exception e) {
			return JsonResult.failure(e.getMessage());
		}
		return JsonResult.success();
	}

	
	/**
	 * index页面中BootStrapTable请求列表响应
	 * @parameter 
	 * @return Page<Book>
	 */
	@RequestMapping(value = { "/list" })
	@ResponseBody
	public Page<Book> list() {
		
		SimpleSpecificationBuilder<Book> builder = new SimpleSpecificationBuilder<Book>();
		String searchText = request.getParameter("searchText");
		if(StringUtils.isNotBlank(searchText)){
			builder.add("bookName", Operator.likeAll.name(), searchText);
		}
		Page<Book> page = bookService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	/**
	 * 查询图书
	 * @parameter 
	 * @return Page<Book>
	 */
	@RequestMapping(value = { "/findlist" })
	@ResponseBody
	public Page<Book> findList() {
		
		SimpleSpecificationBuilder<Book> builder = new SimpleSpecificationBuilder<Book>();
		String bookName = request.getParameter("inputBookName");
		String bookAuthor = request.getParameter("inputAuthor");
		String bookPress = request.getParameter("inputPublication");		
		if(StringUtils.isNotBlank(bookName)){
			builder.add("bookName", Operator.likeAll.name(), bookName);
		}
		if(StringUtils.isNotBlank(bookAuthor)){
			builder.add("bookAuthor", Operator.likeAll.name(), bookAuthor);
		
		}
		if(StringUtils.isNotBlank(bookPress)){
			builder.add("bookPress", Operator.likeAll.name(), bookPress);
		}
		Page<Book> page = bookService.findAll(builder.generateSpecification(), getPageRequest());
		return page;
	}
	
	
	
	
}
