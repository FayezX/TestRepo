package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.gcit.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO {
	
	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void addBorrower(Borrower borrower) throws SQLException{
		save("insert into tbl_borrower(name,address,phone) values (?,?,?)", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public Integer addBorrowerWithID(Borrower borrower) throws SQLException{
		return saveWithID("insert into tbl_borrower(name,address,phone) values (?,?,?)", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone()});
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException{
		save("update tbl_borrower set name =?,address=?,phone=? where cardNo = ?", new Object[] {borrower.getName(),borrower.getAddress(),borrower.getPhone(), borrower.getCardNo()});
	}
	
	public void updateCopies(String bookId, String branchId, String move,String numberOfCopies) throws SQLException{
		if(move.equalsIgnoreCase("1")){
			upCopy("update tbl_book_copies set noOfCopies=? where bookId = ? and branchId = ? ",Integer.parseInt(numberOfCopies)+1,bookId,branchId);
		}else{
			upCopy("update tbl_book_copies set noOfCopies=? where bookId = ? and branchId = ? ",Integer.parseInt(numberOfCopies)-1,bookId,branchId);
		}
		
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException{
		save("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getCardNo()});
	}
	
	public Integer checkBorId(String borrowerId) throws SQLException{
		return checkBorrId("select * from tbl_borrower where cardNo=?",borrowerId);
	}

	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowers() throws SQLException{
		return (List<Borrower>) read("select * from tbl_borrower", null);
	}
	
	public LinkedHashMap<Integer,String> readLoans() throws SQLException{
		LinkedHashMap<Integer,String> a = readAllLoans("select * from tbl_book_loans");	
		for(Integer b : a.keySet()){
			System.out.println(a.get(b));
		}	
		return a;
	}
	
	@SuppressWarnings("unchecked")
	public List<Borrower> readAllBorrowersByName(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Borrower>) read("select * from tbl_borrower where name like ?", new Object[]{searchString});
	}
	
	@SuppressWarnings("unchecked")
	public Borrower readBorrowersByPK(Integer BorrowerId) throws SQLException{
		List<Borrower> Borrowers = (List<Borrower>) read("select * from tbl_borrower where branchid = ?", new Object[]{BorrowerId});
		if(Borrowers!=null){
			return Borrowers.get(0);
		}
		return null;
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> Borrowers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		while(rs.next()){
			Borrower a = new Borrower();
			a.setCardNo(rs.getInt("cardNo"));
			a.setName(rs.getString("name"));
			a.setAddress(rs.getString("address"));
			a.setPhone(rs.getString("phone"));
			Borrowers.add(a);
		}
		return Borrowers;
	}
	
	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException {
		return extractData(rs);
	}	
}
