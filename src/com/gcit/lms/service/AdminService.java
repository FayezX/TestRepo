package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdminService {
	ConnectionUtil cUtil = new ConnectionUtil();

	// add or update Author
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

	// add or update Author
	public void saveBook(Book book) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBook(book);
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

	// add or update Author
	public void saveBookWithId(Book book,String[] ids,String[] genreId) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.updateBook(book);
			} else {
				bdao.addBookWithID(book,ids,genreId);
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
	
	// add or update Author
		public void saveAuthorWithId(Author author,String[] bookids) throws SQLException {
			Connection conn = null;

			conn = cUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			try {
				if (author.getAuthorId() != null) {
					adao.updateAuthor(author);
				} else {
					adao.addAuthorWithID(author, bookids);
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
		
		// add or update Author
				public void saveBranchWithId(LibraryBranch libraryBranch,String[] bookids) throws SQLException {
					Connection conn = null;

					conn = cUtil.getConnection();
					LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
					try {
						if (libraryBranch.getBranchId() != null) {
							ldao.updateLibraryBranch(libraryBranch);
						} else {
							ldao.addLibraryBranchWithID(libraryBranch,bookids);
							//adao.addAuthorWithID(libraryBranch, bookids);
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



	// add or update Author
	public void savePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			if (publisher.getPublisherId() != null) {
				pdao.updatePublisher(publisher);
			} else {
				pdao.addPublisher(publisher);
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

	// Delete Author
	public void DeleteAuthor(Author author) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if (author.getAuthorId() != null) {
				adao.deleteAuthor(author);
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	// Delete Author
	public void DeleteBook(Book book) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if (book.getBookId() != null) {
				bdao.deleteBook(book);
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	// Delete Author
	public void deleteBranch(LibraryBranch libraryBranch) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			if (libraryBranch.getBranchId() != null) {
				ldao.deleteLibraryBranch(libraryBranch);
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			conn.rollback();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
	
	//Get All authors as a list
	public List<Author> getAllAuthors(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if(searchString != null){
				return adao.readAllAuthorsByName(pageNo,searchString);
			}else{
				return adao.readAllAuthors(pageNo);

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
	
	public List<Author> getAllAuthorsNoPage(String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			if(searchString != null){
				return adao.readAllAuthorsNoPage(searchString);
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
	
	public List<Author> readAllAuthors() throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAllAuthors();

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

	public List<Genre> readAllGenre() throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		GenreDAO gdao = new GenreDAO(conn);
		try {
			return gdao.readAllGenres();

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



	public List<Book> getAllBooks(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			if(searchString != null){
				return bdao.readAllBooksByName(pageNo, searchString);
			}else{
				return bdao.readAllBooksPagination(pageNo);
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
	public List<Publisher> getAllPublishers(Integer pageNo, String searchString) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		PublisherDAO bdao = new PublisherDAO(conn);
		try {
			if(searchString != null){
				return bdao.readAllPublishersByName(pageNo, searchString);
			}else{
				return bdao.readAllPublishersPagination(pageNo);
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


	//Get All books
	public List<Book> getAllBooks() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readAllBooks();
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

	//searchbar
	public List<Author> searchAuthors(Integer pageNo) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAllAuthors(pageNo);
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

	//Get count of total authors 
	public Integer getAuthorsCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.getAuthorsCount();
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
	//Get count of total authors 
	public Integer getBooksCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.getBookCount();
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
	//Get count of branches 
	public Integer getBranchCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
			return ldao.getBranchCount();
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
	//Get count of total authors 
	public Integer getPublishersCount() throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		PublisherDAO pdao = new PublisherDAO(conn);
		try {
			return pdao.getPublisherCount();
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

	//get author by PK
	public Author getAuthorsByPk(Integer authorId) throws SQLException {
		Connection conn = null;

		conn = cUtil.getConnection();
		AuthorDAO adao = new AuthorDAO(conn);
		try {
			return adao.readAuthorsByPK(authorId);
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
	//get author by PK
	public Book getBooksByPk(Integer bookId) throws SQLException {
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);
		try {
			return bdao.readBooksByPK(bookId);
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
	
	public void overrideLoan(String cardNo, String bookId, String branchId, String days) throws SQLException, ParseException {
		Connection conn = null;

		conn = cUtil.getConnection();
		LibraryBranchDAO ldao = new LibraryBranchDAO(conn);
		try {
				ldao.alterLoans(cardNo,bookId,branchId,days);
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
	
	public List<Book> getBooksNameTitle(String searchString) throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		BookDAO bdao = new BookDAO(conn);	
		try {
			return bdao.readAllBooksByTitle(searchString);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedHashMap<Integer,String> getAllLoans() throws SQLException{
		Connection conn = null;
		conn = cUtil.getConnection();
		BorrowerDAO bdao = new BorrowerDAO(conn);	
		try {
			return bdao.readLoans();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
}
