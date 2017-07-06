package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.LibraryBranch;

public class LibrarianService {
	ConnectionUtil cUtil = new ConnectionUtil();

	public List<LibraryBranch> getAllBranch(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			if(searchString != null){
				return ldao.readAllLibraryBranchsByName(pageNo, searchString);
			}else{
				return ldao.readAllBranchPagination(pageNo);
			}
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

	public void saveBranchName(LibraryBranch lib) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			if (lib.getBranchId() != null) {
				ldao.updateLibraryBranch(lib);
			} else {
				ldao.addLibraryBranch(lib);
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

	public String saveBranchNameByPk(Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			LibraryBranch a = ldao.readBranchByPK(branchId);
			return a.getBranchName();
			//conn.commit();
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
	/*
	public List<Integer> getBooksIdForBranch(Integer branchId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			bdao.readBooksByPK(bookId) ;
			//conn.commit();
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
	 */	
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

	public void alterCopy(String name, Integer bookId, Integer branchId) throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			ldao.changeCopy(name,bookId,branchId);
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
