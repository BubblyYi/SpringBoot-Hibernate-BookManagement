package net.bubblyyi.base.entity;

import java.util.Arrays;

import net.bubblyyi.base.entity.support.BaseEntity;
/**
 * 借阅列表
 * @author BubblyYi
 * @since 2017年6月29日
 */
public class BorrowList extends BaseEntity{
	
	private static final long serialVersionUID = -1894163644285296223L;
	
	private int id;
	private String[] booklist;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String[] getBooklist() {
		return booklist;
	}
	public void setBooklist(String[] booklist) {
		this.booklist = booklist;
	}
	@Override
	public String toString() {
		return "BorrowList [id=" + id + ", booklist=" + Arrays.toString(booklist) + "]";
	}
	

	
	

}
