package net.bubblyyi.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import net.bubblyyi.base.entity.support.BaseEntity;

/**
 * 借书表
 * @author BubblyYi
 * @since 2017年6月29日
 */
@IdClass(BorrowBook.class)
@Embeddable
@Entity
@Table(name="tb_borrow_books")
public class BorrowBook extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 用户id
	 */
	@Id
	@Column(name = "user_id", nullable = false)
	private int userId;
	
	/**
	 * 图书id
	 */
	@Id
	@Column(name = "book_id", nullable = false)
	private String bookId;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "BorrowBook [userId=" + userId + ", bookId=" + bookId + ", updateTime=" + updateTime + "]";
	}

	
	
	
	

}
