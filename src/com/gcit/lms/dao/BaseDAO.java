package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class BaseDAO {

	public static Connection conn = null;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	private Integer pageNo = 1;
	private Integer pageSize = 10;

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo
	 *            the pageNo to set
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize
	 *            the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void save(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		pstmt.executeUpdate();
	}

	public Integer getCopy(String query, Integer bookId, Integer branchId)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, bookId);
		pstmt.setInt(2, branchId);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getInt("noOfCopies");
		}
		return 0;
	}
	
	public Date overLoan(String query, String bookId, String branchId,String cardNo)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, bookId);
		pstmt.setString(2, branchId);
		pstmt.setString(3, cardNo);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return rs.getDate("dueDate");
		}else{
			System.out.println("no due date");
		}
		return null;
	}
	
	public void insertLoan(String query,String newdate, String bookId, String branchId,String cardNo)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, newdate);
		pstmt.setString(2, bookId);
		pstmt.setString(3, branchId);
		pstmt.setString(4, cardNo);
		pstmt.executeUpdate();
	}
	

	public void reCopy(String query, String name, Integer bookId,
			Integer branchId) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, name);
		pstmt.setInt(2, bookId);
		pstmt.setInt(3, branchId);
		pstmt.executeUpdate();
	}

	public void upCopy(String query, Integer copies, String bookId,
			String branchId) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, copies);
		pstmt.setString(2, bookId);
		pstmt.setString(3, branchId);
		pstmt.executeUpdate();
	}
	
	@SuppressWarnings("null")
	public LinkedHashMap<Integer,String> readAllLoans(String query) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		LinkedHashMap<Integer,String> list = new LinkedHashMap<Integer,String>();
		ResultSet rs = pstmt.executeQuery();
		Integer count = 1;
		while(rs.next()){
			list.put(count, rs.getString("bookId")+","+rs.getString("branchId")+","+rs.getString("cardNo")+","+rs.getString("dateOut")+","+rs.getString("dueDate")
					+","+rs.getString("dateIn"));
			count++;
		}
		return list;
	}
	

	public void insertForBook(String query, Integer bookId, String branchId)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, bookId);
		pstmt.setString(2, branchId);
		pstmt.executeUpdate();
	}

	public Integer checkBorrId(String query, String cardNo) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, cardNo);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			return 1;
		} else {
			return 0;
		}
	}

	public Integer saveWithID(String query, Object[] vals) throws SQLException {
		// Connection conn = null;
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		setPstmtObjects(vals, pstmt);
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return null;
	}

	public List<?> read(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		if (getPageNo() > 0) {
			int count = 0;
			int index = (getPageNo() - 1) * 10;
			query += " Limit " + index + " , " + getPageSize();
		}
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}

	public Integer getCount(String query, Object[] vals) throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		ResultSet rs = pstmt.executeQuery();
		if (rs.next())
			return rs.getInt("Count");
		return null;
	}

	public abstract List<?> extractData(ResultSet rs) throws SQLException;

	public List<?> readFirstLevel(String query, Object[] vals)
			throws SQLException {
		PreparedStatement pstmt = null;
		pstmt = conn.prepareStatement(query);
		setPstmtObjects(vals, pstmt);
		ResultSet rs = pstmt.executeQuery();
		return extractDataFirstLevel(rs);
	}

	public abstract List<?> extractDataFirstLevel(ResultSet rs)
			throws SQLException;

	private void setPstmtObjects(Object[] vals, PreparedStatement pstmt)
			throws SQLException {
		if (vals != null) {
			int count = 1;
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
	}
}
