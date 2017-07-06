package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminService;
import com.gcit.lms.service.BorrowerService;
import com.gcit.lms.service.LibrarianService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/deleteAuthor", "/editAuthor", "/addBorrower",
		"/pageAuthors", "/searchAuthors", "/searchBooks", "/pageBooks",
		"/editBooks", "/deleteBook", "/addBook", "/searchPublisher",
		"/searchBranch", "/pageBranch", "/editBranch", "/editCopy",
		"/checkBorrowerId", "/checkoutreturn", "/deleteBranch", "/addBranch",
		"/checkOverride", "/updatePages","/searchA" })
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	AdminService adminService = new AdminService();
	LibrarianService librarianService = new LibrarianService();
	BorrowerService borrowerService = new BorrowerService();

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		Author author = new Author();
		Book book = new Book();
		LibraryBranch libraryBranch = new LibraryBranch();

		switch (reqUrl) {
		case "/deleteAuthor":
			// System.out.println("Author ID to delete " +
			// request.getParameter("authorId"));
			if (request.getParameter("authorId") != null
					&& !request.getParameter("authorId").isEmpty()) {
				author.setAuthorId(Integer.parseInt(request
						.getParameter("authorId")));
				try {
					adminService.DeleteAuthor(author);
					RequestDispatcher rd = request
							.getRequestDispatcher("/viewauthor.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/deleteBook":
			// System.out.println("Author ID to delete " +
			// request.getParameter("authorId"));
			System.out.println("deletebook");
			if (request.getParameter("bookId") != null
					&& !request.getParameter("bookId").isEmpty()) {
				book.setBookId(Integer.parseInt(request.getParameter("bookId")));
				try {
					adminService.DeleteBook(book);
					RequestDispatcher rd = request
							.getRequestDispatcher("/viewbooks.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/deleteBranch":
			if (request.getParameter("branchId") != null
					&& !request.getParameter("branchId").isEmpty()) {
				System.out.println("deletebranch");
				libraryBranch.setBranchId(Integer.parseInt(request
						.getParameter("branchId")));
				try {
					adminService.deleteBranch(libraryBranch);
					RequestDispatcher rd = request
							.getRequestDispatcher("/adminViewBranch.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/pageAuthors":
			// System.out.println("Author ID to delete " +
			// request.getParameter("authorId"));
			if (request.getParameter("pageNo") != null
					&& !request.getParameter("pageNo").isEmpty()) {
				Integer pageNo = Integer.parseInt(request
						.getParameter("pageNo"));
				try {
					List<?> authors = adminService.getAllAuthors(pageNo, null);
					request.setAttribute("authors", authors);
					RequestDispatcher rd = request
							.getRequestDispatcher("/viewauthor.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/pageBooks":
			if (request.getParameter("pageNoB") != null
					&& !request.getParameter("pageNoB").isEmpty()) {
				Integer pageNo = Integer.parseInt(request
						.getParameter("pageNoB"));
				try {
					List<?> books = adminService.getAllBooks(pageNo, null);
					request.setAttribute("books", books);
					RequestDispatcher rd = request
							.getRequestDispatcher("/viewbooks.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/pagePubishers":
			if (request.getParameter("pageNoB") != null
					&& !request.getParameter("pageNoB").isEmpty()) {
				Integer pageNo = Integer.parseInt(request
						.getParameter("pageNoB"));
				try {
					List<?> publishers = adminService.getAllPublishers(pageNo,
							null);
					request.setAttribute("publishers", publishers);
					RequestDispatcher rd = request
							.getRequestDispatcher("/viewpublishers.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		case "/pageBranch":
			if (request.getParameter("pageNoB") != null
					&& !request.getParameter("pageNoB").isEmpty()) {
				Integer pageNo = Integer.parseInt(request
						.getParameter("pageNoB"));
				try {
					List<?> libraryB = librarianService.getAllBranch(pageNo,
							null);
					request.setAttribute("Branch", libraryB);
					RequestDispatcher rd = request
							.getRequestDispatcher("/librarianviewbranch.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			break;
		case "/searchAuthors":
			System.out.println("GOT OK2");
			String searchString = request.getParameter("searchString");
			System.out.println(searchString);
			try {
				List<?> authors = adminService.getAllAuthors(1, searchString);
				request.setAttribute("authors", authors);
				RequestDispatcher rd = request
						.getRequestDispatcher("/viewauthor.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchA":
			/*
			<select class="form-control" name="authorId" multiple="multiple">
			<%for(Author a : authors){%>
				<option  value="<%=a.getAuthorId()%>"><%=a.getAuthorName()%></option>
			<% 	
			}
			%>						
			</select>
			  
			 */			
			
			System.out.println("searchA");
			String searchStringg = request.getParameter("searchString");
			
			try {
				StringBuffer strB = new StringBuffer();
				List<Author> authors = adminService.getAllAuthorsNoPage(searchStringg);
				
				
				strB.append("<select class=\"form-control\" name=\"authorId\" multiple=\"multiple\">");
				for(Author a : authors){
					strB.append("<option  value=\"" + a.getAuthorId() +  "\">"  + a.getAuthorName() + "</option>");
				}
				strB.append("</select");
				response.getWriter().write(strB.toString());

			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			

			
			
			
			
			
			
			
			
			
			break;
		case "/searchBooks":
			// System.out.println("ajax");
			String searchString2 = request.getParameter("searchString");
			// System.out.println(searchString2);
			Integer count = 1;

			try {
				List<Book> books = adminService.getAllBooks(1, searchString2);
				StringBuffer strBuff = new StringBuffer();
				// System.out.println(books.size());

				strBuff.append("<tr><th></th><th>Book Title</th><th>Genre of book</th><th>Authors of book</th><th>Edit</th><th>Delete</th></tr>");
				for (Book b : books) {
					System.out.println(b.getTitle());

					strBuff.append("<td>" + count + "</td><td>" + b.getTitle()
							+ "&nbsp; &nbsp;</td><td>");
					for (Genre g : b.getGenres()) {
						strBuff.append(g.getGenreName()
								+ "&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;");
					}
					strBuff.append("</td><td>");
					for (Author a : b.getAuthors()) {
						strBuff.append(a.getAuthorName()
								+ "<br /><br />");
					}
					count++;
					strBuff.append("</td><td><button type = 'button' class = 'btn btn-primary btn-sm' data-toggle ='modal' data-target='#editBookModal' href='editbook.jsp?bookId="
							+ b.getBookId() + "'>Edit Book</button></td>");
					strBuff.append("<td><button type = 'button' class = 'btn btn-warning btn-sm' onclick ='javascript:location.href='deleteBook?bookId="
							+ b.getBookId()
							+ "''>Delete Book</button></td></tr>");
				}
				response.getWriter().write(strBuff.toString());

				/*
				 * List<Book> books = adminService.getAllBooks(1,
				 * searchString2); request.setAttribute("books", books);
				 * RequestDispatcher rd =
				 * request.getRequestDispatcher("/viewbooks.jsp");
				 * rd.forward(request, response);
				 */
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		case "/updatePages":
			try {

				List<Book> books = adminService.getBooksNameTitle(request
						.getParameter("searchString"));
				Integer numberBooks = books.size();
				Double pages = 0.0;
				
				if(request.getParameter("searchString").isEmpty()){
					pages = Math.ceil(numberBooks / 10)+1;
				}else{
				 pages = Math.ceil(numberBooks / 10);
				}
				
				StringBuffer strBuff = new StringBuffer();

				strBuff.append("<nav aria-label='Page navigation'><ul class='pagination'><li><a href='#' aria-label=\"Previous\"><span aria-hidden=\"true\">&laquo;</span></a></li>");
				for (int i = 0; i <= pages; i++) {
					strBuff.append("<li><a href='pageBooks?pageNoB=" + (i+1) + "'>"
							+ (i+1) + "</a></li>");
				}
				strBuff.append("<li><a href=\"#\" aria-label=\"Next\"> <span aria-hidden=\"true\">&raquo;</span></a></li></ul></nav>");
				response.getWriter().write(strBuff.toString());
				/*
				 * <div id='paging'> <nav aria-label="Page navigation"> <ul
				 * class="pagination"> <li><a href="#" aria-label="Previous">
				 * <span aria-hidden="true">&laquo;</span> </a></li> <% for (int
				 * i = 1; i <= pages; i++) { %>
				 * 
				 * <li><a href="pageBooks?pageNoB=<%=i%>"><%=i%></a></li> <% }
				 * %> <li><a href="#" aria-label="Next"> <span
				 * aria-hidden="true">&raquo;</span> </a></li> </ul> </nav>
				 * </div>
				 */
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchPublisher":
			System.out.println("GOT OK2");
			String searchString3 = request.getParameter("searchString");
			System.out.println(searchString3);
			try {
				List<Publisher> publishers = adminService.getAllPublishers(1,
						searchString3);
				request.setAttribute("publishers", publishers);
				RequestDispatcher rd = request
						.getRequestDispatcher("/viewpublishers.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchBranch":
			System.out.println("GOT branch");
			String searchString4 = request.getParameter("searchString");
			System.out.println(searchString4);
			try {
				List<LibraryBranch> libraryB = librarianService.getAllBranch(1,
						searchString4);
				request.setAttribute("branch", libraryB);
				RequestDispatcher rd = request
						.getRequestDispatcher("/librarianviewbranch.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/checkoutreturn":
			//System.out.println("GOT checkoutreturn");
			//System.out.println(request.getParameter("bookId"));
			//System.out.println(request.getParameter("branchId"));
			//System.out.println(request.getParameter("move"));

			String bookId = request.getParameter("bookId");
			String branchId = request.getParameter("branchId");
			String move = request.getParameter("move");
			String numberOfCopies = request.getParameter("numcopies");
			//String cardNo = request.getParameter("cardNo");
			//System.out.println(cardNo);
			
			try {
				borrowerService.updatecopy(bookId, branchId, move,
						numberOfCopies);
				
				
				RequestDispatcher rd = request
						.getRequestDispatcher("/borrowermenu.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
		// RequestDispatcher rd =
		// request.getRequestDispatcher("/viewauthor.jsp");
		// rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("Printing Author Name: " +
		// request.getParameter("authorName"));
		//
		String reqUrl = request.getRequestURI().substring(
				request.getContextPath().length(),
				request.getRequestURI().length());
		Author author = new Author();
		Borrower borrower = new Borrower();
		Book book = new Book();
		Publisher publisher = new Publisher();
		LibraryBranch lib = new LibraryBranch();

		switch (reqUrl) {
		case "/addAuthor":
			String[] bookids = request.getParameterValues("bookId");

			if (request.getParameter("authorName") != null) {
				try {
					System.out.println("ADDing AUthor");
					author.setAuthorName(request.getParameter("authorName"));
					adminService.saveAuthorWithId(author, bookids);

					// author.setAuthorName(request.getParameter("authorName"));
					// adminService.saveAuthor(author);

					RequestDispatcher rd = request
							.getRequestDispatcher("/viewauthor.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/addBranch":
			String[] aids = request.getParameterValues("bookId");

			if (request.getParameter("branchName") != null) {
				try {
					System.out.println("ADDing Branch");
					lib.setBranchName(request.getParameter("branchName"));
					lib.setBranchAddress(request.getParameter("branchAddress"));
					adminService.saveBranchWithId(lib, aids);

					// author.setAuthorName(request.getParameter("authorName"));
					// adminService.saveAuthor(author);

					RequestDispatcher rd = request
							.getRequestDispatcher("/adminViewBranch.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/addBook":
			String[] ids = request.getParameterValues("authorId");
			String[] genreId = request.getParameterValues("genreId");

			if (request.getParameter("bookName") != null) {
				try {
					System.out.println("ADDing book");
					book.setTitle(request.getParameter("bookName"));
					adminService.saveBookWithId(book, ids, genreId);

					/*
					 * book.setTitle(request.getParameter("bookName"));
					 * adminService.saveBook(book);
					 */

					RequestDispatcher rd = request
							.getRequestDispatcher("/viewbooks.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/addPublisher":
			if (request.getParameter("publisherName") != null) {
				try {
					System.out.println("ADDing publisher");

					// if(request.getParameter("authorID") != null){
					// author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
					// }
					publisher.setPublisherName("bookName");
					adminService.savePublisher(publisher);
					RequestDispatcher rd = request
							.getRequestDispatcher("/template.htm.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "/editAuthor":
			System.out.println("Edit Author");
			author.setAuthorName(request.getParameter("authorName"));
			author.setAuthorId(Integer.parseInt(request
					.getParameter("authorId")));
			try {
				// if(request.getParameter("authorID") != null){
				// author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
				// }
				adminService.saveAuthor(author);
				RequestDispatcher rd = request
						.getRequestDispatcher("/viewauthor.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/editBooks":
			// System.out.println("Edit Author");
			book.setTitle(request.getParameter("title"));
			book.setBookId(Integer.parseInt(request.getParameter("bookId")));
			try {
				// if(request.getParameter("authorID") != null){
				// author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
				// }
				adminService.saveBook(book);
				RequestDispatcher rd = request
						.getRequestDispatcher("/viewbooks.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/editCopy":
			System.out.println("COPY post");
			System.out.println(request.getParameter("newCopy"));
			System.out.println(request.getParameter("bookId"));
			System.out.println(request.getParameter("branchId"));

			String newCop = request.getParameter("newCopy");
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			Integer branchId = Integer.parseInt(request
					.getParameter("branchId"));

			if (!newCop.isEmpty()) {
				try {
					librarianService.alterCopy(newCop, bookId, branchId);
					RequestDispatcher o = request
							.getRequestDispatcher("/librarianviewbranch.jsp");
					o.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			break;
		case "/editBranch":
			// System.out.println("Edit Author");
			if (!request.getParameter("name").isEmpty()) {
				lib.setBranchName(request.getParameter("name"));
			} else {
				try {
					lib.setBranchName(librarianService
							.saveBranchNameByPk(Integer.parseInt(request
									.getParameter("branchId"))));
				} catch (NumberFormatException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (!request.getParameter("address").isEmpty()) {
				lib.setBranchAddress(request.getParameter("address"));
			}
			lib.setBranchId(Integer.parseInt(request.getParameter("branchId")));

			try {
				// if(request.getParameter("authorID") != null){
				// author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
				// }
				librarianService.saveBranchName(lib);
				RequestDispatcher rd = request
						.getRequestDispatcher("/librarianviewbranch.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/searchAuthors":
			System.out.println("GOT OK");
			String searchString = request.getParameter("searchString");
			try {
				// if(request.getParameter("authorID") != null){
				// author.setAuthorId(Integer.parseInt(request.getParameter("authorId")));
				// }
				List<?> authors = adminService.getAllAuthors(1, searchString);
				request.setAttribute("authors", authors);
				RequestDispatcher rd = request
						.getRequestDispatcher("/viewauthor.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/checkBorrowerId":
			//System.out.println(request.getRequestURL());
			//System.out.println("GOT OK");
			String borrowerId = request.getParameter("borrowerId");
			//System.out.println(borrowerId);
			//Borrower b = new Borrower();
			//b.setCardNo(Integer.parseInt(borrowerId));
			try {
				int check = borrowerService.checkBorrowerId(borrowerId);
				if (check == 1) {
					request.setAttribute("cardNo", borrowerId);
					
					RequestDispatcher rd = request
							.getRequestDispatcher("/borrowermenu.jsp");
					rd.forward(request, response);
				} else {
					RequestDispatcher rd = request
							.getRequestDispatcher("/template.htm");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "/checkOverride":
			System.out.println("Overriding");
			String cardNo = request.getParameter("cardNumber");
			String bo = request.getParameter("bookId");
			String br = request.getParameter("branchId");
			String days = request.getParameter("days");

			if (cardNo.isEmpty() || bo.isEmpty() || br.isEmpty()
					|| days.isEmpty()) {
				RequestDispatcher rd = request
						.getRequestDispatcher("/admin.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("all in");
				try {
					adminService.overrideLoan(cardNo, bo, br, days);
					RequestDispatcher rd = request
							.getRequestDispatcher("/admin.jsp");
					rd.forward(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;
		}
	}
}
