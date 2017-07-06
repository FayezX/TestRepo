package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibraryBranchDAO extends BaseDAO {

	public LibraryBranchDAO(Connection conn) {
		super(conn);
	}

	public void addLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		save("insert into tbl_library_branch(branchName) values (?)", new Object[] {libraryBranch.getBranchName()});
	}

	public void addLibraryBranchWithID(LibraryBranch libraryBranch,String[] bookids) throws SQLException{
		Integer newBranchId = saveWithID("insert into tbl_library_branch(branchName,branchAddress) values (?,?)", new Object[] {libraryBranch.getBranchName(),libraryBranch.getBranchAddress()});
		
		for(int i = 0; i < bookids.length; i++){
			insertForBook("insert into tbl_book_copies(branchId,bookId) values(?,?)",newBranchId,bookids[i]);
		}	 
	}

	public void updateLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		save("update tbl_library_branch set branchName =?, branchAddress=?  where branchId = ?", new Object[] {libraryBranch.getBranchName(),libraryBranch.getBranchAddress(), libraryBranch.getBranchId()});
	}
	public Integer getCopiesforBook(Integer bookId,Integer branchId ) throws SQLException{
		return getCopy("select noOfCopies from tbl_book_copies where bookId=? and branchId=?",bookId,branchId);
	}

	public void changeCopy(String name, Integer bookId,Integer branchId ) throws SQLException{
		reCopy("Update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?",name,bookId,branchId);
	}

	public void deleteLibraryBranch(LibraryBranch libraryBranch) throws SQLException{
		save("delete from tbl_library_branch where branchId = ?", new Object[] {libraryBranch.getBranchId()});
	}
	
	public void alterLoans(String cardNo, String bookId, String branchId, String days) throws SQLException, ParseException{
		Date a = overLoan("select * from tbl_book_loans where bookId=? and branchId=? and cardNo=?",bookId,branchId,cardNo);
		System.out.println(a);

		Calendar cal = Calendar.getInstance(); 
		cal.setTime(a); 
		cal.add(Calendar.DATE, Integer.parseInt(days)+1);
		a = cal.getTime();
		String newstring = new SimpleDateFormat("yyyy-MM-dd").format(a);
		System.out.println(newstring);
		
		insertLoan("update tbl_book_loans set dueDate=? where bookId=? and branchId=? and cardNo=?",newstring,bookId,branchId,cardNo);
		
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllLibraryBranchs() throws SQLException{
		return (List<LibraryBranch>) read("select * from tbl_library_branch", null);
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllLibraryBranchsByName(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<LibraryBranch>) read("select * from tbl_library_branch where branchName like ?", new Object[]{searchString});
	}

	@SuppressWarnings("unchecked")
	public LibraryBranch readLibraryBranchsByPK(Integer LibraryBranchId) throws SQLException{
		List<LibraryBranch> LibraryBranchs = (List<LibraryBranch>) read("select * from tbl_library_branch where branchid = ?", new Object[]{LibraryBranchId});
		if(LibraryBranchs!=null){
			return LibraryBranchs.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<LibraryBranch> readAllBranchPagination(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		return (List<LibraryBranch>) read("select * from tbl_library_branch", null);
	}

	@SuppressWarnings("unchecked")
	public LibraryBranch readBranchByPK(Integer branchId) throws SQLException{
		List<LibraryBranch> libraryBranch = (List<LibraryBranch>) read("select * from tbl_library_branch where branchId = ?", new Object[]{branchId});
		if(libraryBranch!=null){
			return libraryBranch.get(0);
		}
		return null;
	}
	public Integer getBranchCount() throws SQLException{
		return getCount("select count(*) as Count from tbl_library_branch", null);
	}

	@Override
	public List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> LibraryBranchs = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("branchName"));
			a.setBranchAddress(rs.getString("branchAddress"));
			a.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId IN (Select bookId from tbl_book_copies where branchId = ?)", new Object[]{a.getBranchId()}));
			LibraryBranchs.add(a);
		}
		return LibraryBranchs;
	}

	@Override
	public List<LibraryBranch> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<LibraryBranch> LibraryBranchs = new ArrayList<>();
		while(rs.next()){
			LibraryBranch a = new LibraryBranch();
			a.setBranchId(rs.getInt("branchId"));
			a.setBranchName(rs.getString("LibraryBranchName"));
			LibraryBranchs.add(a);
		}
		return LibraryBranchs;
	}



}
