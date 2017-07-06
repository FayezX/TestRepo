package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.BookLoans;

public class BookLoansDAO extends BaseDAO {

	public BookLoansDAO(Connection conn) {
		super(conn);
	}

	public void addBookLoans(BookLoans bookLoan) throws SQLException{
		save("insert into tbl_book_loans(bookid,branchid,cardNo,dateOut,dueDate,datein) values (?,?,?,?,?,?)", new Object[] {bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo(),bookLoan.getCardNo(),bookLoan.getDueDate(),bookLoan.getDateIn()});
	}

	public Integer addBookLoansWithID(BookLoans bookLoan) throws SQLException{
		return saveWithID("insert into tbl_book_loans(bookid,branchid,cardNo,dateOut,dueDate,datein) values (?,?,?,?,?,?)", new Object[] {bookLoan.getBookId(),bookLoan.getBranchId(),bookLoan.getCardNo(),bookLoan.getCardNo(),bookLoan.getDueDate(),bookLoan.getDateIn()});
	}

	public void updateBookLoans(BookLoans bookLoan) throws SQLException{
		save("update tbl_book_loans set dueDate =?,dateIn=?,where bookId = ? and branchId = ? and cardNo = ?", new Object[] {bookLoan.getDateOut(),bookLoan.getDateIn(),bookLoan.getBookId(), bookLoan.getBranchId(),bookLoan.getCardNo()});
	}

	public void deleteBookLoans(BookLoans bookLoan) throws SQLException{
		save("delete from tbl_book_loans where cardNo = ?", new Object[] {bookLoan.getCardNo()});
	}


	@SuppressWarnings("unchecked")
	public List<BookLoans> readAllBookLoanss() throws SQLException{
		return (List<BookLoans>) read("select * from tbl_book_loans", null);
	}

	@SuppressWarnings("unchecked")
	public List<BookLoans> readAllBookLoanssByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<BookLoans>) read("select * from tbl_book_loans where name like ?", new Object[]{searchString});
	}

	@SuppressWarnings("unchecked")
	public BookLoans readBookLoanssByPK(Integer BookLoansId) throws SQLException{
		List<BookLoans> BookLoanss = (List<BookLoans>) read("select * from tbl_book_loans where branchid = ?", new Object[]{BookLoansId});
		if(BookLoanss!=null){
			return BookLoanss.get(0);
		}
		return null;
	}

	@Override
	public List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> BookLoanss = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			BookLoans a = new BookLoans();
			a.setCardNo(rs.getInt("cardNo"));
			a.setBranchId(rs.getInt("branchId"));
			a.setBookId(rs.getInt("bookId"));
			a.setDateOut(rs.getString("dateOut"));
			a.setDueDate(rs.getString("dueDate"));
			a.setDateIn(rs.getString("dateId"));
			BookLoanss.add(a);
		}
		return BookLoanss;
	}

	@Override
	public List<BookLoans> extractDataFirstLevel(ResultSet rs) throws SQLException {
		return extractData(rs);
	}	

}
