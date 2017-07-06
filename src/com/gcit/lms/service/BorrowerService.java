package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.LibraryBranch;

public class BorrowerService {
	ConnectionUtil cUtil = new ConnectionUtil();

	public void saveAuthor(Author author) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if (author.getAuthorId() != null) {
				adao.updateAuthor(author);
			} else {
				adao.addAuthor(author);
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	//get author by PK
	public LibraryBranch getBranchsByPk(Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			return ldao.readBranchByPK(branchId);
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}


	public Integer checkBorrowerId(String branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BorrowerDAO ldao = new BorrowerDAO(conn);
		try {
			return ldao.checkBorId(branchId);
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}

	public Integer getCopiesForBranch(Integer bookId,Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		System.out.println(bookId);
		System.out.println(branchId);

		try {
			Integer a = ldao.getCopiesforBook(bookId,branchId);
			return a;
			//conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return 0;
	}

	public void updatecopy(String bookId,String branchId,String move,String numberOfCopies) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);
		try {
			bdao.updateCopies(bookId,branchId,move,numberOfCopies);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

}
