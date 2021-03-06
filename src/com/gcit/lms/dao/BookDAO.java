package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class BookDAO extends BaseDAO{

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void addBook(Book book) throws SQLException{
		save("insert into tbl_book(title) values (?)", new Object[] {book.getTitle()});
	}

	public Integer addBookWithID(Book book,String[] ids,String[] genreId) throws SQLException{

		Integer newBookId = saveWithID("insert into tbl_book(title) values (?)", new Object[] {book.getTitle()});
		System.out.println(newBookId);

		for(int i = 0; i < ids.length; i++){
			insertForBook("insert into tbl_book_authors(bookId,authorId) values(?,?)",newBookId,ids[i]);
		}
		for(int i = 0; i < genreId.length;i++){
			insertForBook("insert into tbl_book_genres(bookId,genre_id) values(?,?)",newBookId,genreId[i]);
		}

		return 0; 
	}

	public void updateBook(Book book) throws SQLException{
		save("update tbl_book set title=? where bookId=?", new Object[] {book.getTitle(), book.getBookId()});
	}

	public void deleteBook(Book book) throws SQLException{
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	public Integer getBookCount() throws SQLException{
		return getCount("select count(*) as Count from tbl_book", null);
	}

	@SuppressWarnings("unchecked")
	public List<Book> readAllBooks() throws SQLException{
		return (List<Book>) read("select * from tbl_book", null);
	}

	@SuppressWarnings("unchecked")
	public List<Book> readAllBooksByTitle(String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		return (List<Book>) read("select * from tbl_book where title like ?", new Object[]{searchString});
	}

	public List<Book> readAllBooksByName(Integer pageNo, String searchString) throws SQLException{
		searchString = "%"+searchString+"%";
		setPageNo(pageNo);
		return (List<Book>) read("select * from tbl_book where title like ?", new Object[]{searchString});
	}

	@SuppressWarnings("unchecked")
	public List<Book> readAllBooksPagination(Integer pageNo) throws SQLException{
		setPageNo(pageNo);
		return (List<Book>) read("select * from tbl_book", null);
	}

	@SuppressWarnings("unchecked")
	public Book readBooksByPK(Integer bookId) throws SQLException{
		List<Book> books = (List<Book>) read("select * from tbl_book where bookId = ?", new Object[]{bookId});
		if(books!=null){
			return books.get(0);
		}
		return null;
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenreDAO gdao = new GenreDAO(conn); 
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setAuthors((List<Author>) adao.readFirstLevel("select * from tbl_author where authorId IN (Select authorId from tbl_book_authors where bookId = ?)", new Object[]{b.getBookId()}));
			b.setGenres((List<Genre>) gdao.readFirstLevel("select * from tbl_genre where genre_id IN (Select genre_id from tbl_book_genres where bookId = ?)", new Object[]{b.getBookId()}));
			//do the same for Genres and Publishers
			books.add(b);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			books.add(b);
		}
		return books;
	}

}
