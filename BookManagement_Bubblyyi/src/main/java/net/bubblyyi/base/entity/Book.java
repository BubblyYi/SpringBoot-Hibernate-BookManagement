package net.bubblyyi.base.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.bubblyyi.base.entity.support.BaseEntity;


/**
 * 图书表
 * @author BubblyYi
 * @since 2017年6月29日
 */
@Entity
@Table(name="tb_books")
public class Book extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 图书id
	 */
	@Id
	@Column(name = "book_id", nullable = false)
	private String bookId;
	
	/**
	 * 图书名
	 */
	private String bookName;
	
	/**
	 * 作者
	 */
	private String bookAuthor;
	
	/**
	 * 出版社
	 */
	private String bookPress;
	
	/**
	 * 图书总库存
	 */
	private Integer bookInventory;
	
	/**
	 * 图书现有库存
	 */
	private Integer currentInventory;



	public Integer getCurrentInventory() {
		return currentInventory;
	}

	public void setCurrentInventory(Integer currentInventory) {
		this.currentInventory = currentInventory;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setBookInventory(Integer bookInventory) {
		this.bookInventory = bookInventory;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookPress() {
		return bookPress;
	}

	public void setBookPress(String bookPress) {
		this.bookPress = bookPress;
	}

	public Integer getBookInventory() {
		return bookInventory;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", bookPress="
				+ bookPress + ", bookInventory=" + bookInventory + ", currentInventory=" + currentInventory + "]";
	}

	

	

	
	
	
	

}
