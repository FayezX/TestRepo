<%@page import="com.gcit.lms.service.AdminService"%>
<%@page import="com.gcit.lms.service.BorrowerService"%>
<%@page import="com.gcit.lms.service.LibrarianService"%>
<%@page import="com.gcit.lms.entity.Author"%>
<%@page import="com.gcit.lms.entity.LibraryBranch"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList" %>
<%@page import="com.gcit.lms.entity.Book"%>
<%@page import="com.gcit.lms.entity.Genre"%>
<%@page import="com.gcit.lms.entity.Borrower"%>
<%@page import="java.util.LinkedHashMap"%>

<%
Integer bookId = 0;
Integer branchId = 0;
Integer numberOfCopies = 0;

Object cardNo = request.getAttribute("cardNo");

LibrarianService LibrarianService = new LibrarianService();
AdminService adminService = new AdminService();
AdminService borrowerService = new AdminService();

LinkedHashMap<Integer,String> loans = adminService.getAllLoans();
List<LibraryBranch> libraryBranch = new ArrayList<>();
Integer authorCount = adminService.getBranchCount();
int pages = 0;
if(authorCount%10 > 0){
	pages = authorCount/10+1;
}else{
	pages = authorCount/10;
}
if(request.getAttribute("branch")!=null){
	libraryBranch = (List<LibraryBranch>)request.getAttribute("branch");
	
}else{
	libraryBranch = adminService.getAllBranch(1, null);
}
%>



<!DOCTYPE html>
<!-- saved from url=(0057)https://blackrockdigital.github.io/startbootstrap-agency/ -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>GCIT Library Management</title>

    <!-- Bootstrap Core CSS -->
    <link href="./template_files/bootstrap.min.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="./template_files/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="./template_files/css" rel="stylesheet" type="text/css">
    <link href="./template_files/css(1)" rel="stylesheet" type="text/css">
    <link href="./template_files/css(2)" rel="stylesheet" type="text/css">
    <link href="./template_files/css(3)" rel="stylesheet" type="text/css">

    <!-- Theme CSS -->
    <link href="./template_files/agency.min.css" rel="stylesheet">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js" integrity="sha384-0s5Pv64cNZJieYFkXYOTId2HMA2Lfb6q2nAcx2n0RTLUnCAoTTsS0nKEO27XyKcY" crossorigin="anonymous"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js" integrity="sha384-ZoaMbDF+4LeFxg6WdScQ9nnR1QC2MIRxA1O9KWEXQwns1G8UNyIEZIQidzb0T1fo" crossorigin="anonymous"></script>
    <![endif]-->

</head>

<body id="page-top" class="index">

    <!-- Navigation -->
    <nav id="mainNav" class="navbar navbar-default navbar-custom navbar-fixed-top affix">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header page-scroll">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span> Menu <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="http://localhost:8080/LMSWeb/">GCIT Library Management</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li class="hidden active">
                        <a href="http://localhost:8080/LMSWeb/"></a>
                    </li>
                    <li class="">
                        <a class="page-scroll" href="admin.jsp">Administrator</a>
                    </li>
                    <li class="">
                        <a class="page-scroll" href="librarianmenu.jsp">Librarian</a>
                    </li>
                    <li class="">
                        <a class="page-scroll"  data-toggle ="modal" data-target="#borrowerCheck"  href="borrowercheck.jsp">Borrower</a>
                    </li>
                    <li class="">
                        <a class="page-scroll" onclick="aboutUs()">About Library</a>
                    </li>
                    <li class="">
                        <a class="page-scroll" onclick="contactUs()">Help</a>
                    </li>
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
    
    <div class="modal fade bs-example-modal-lg" tabindex="-1" id=borrowerCheck role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
    
    </div>
  </div>
</div>


<script>
function aboutUs(){
	alert('The mission of the Library is to create and sustain an evolving information environment that advances learning, research, and innovation at GCIT. We are committed to excellence in services, strategies, and systems that promote discovery, preserve knowledge, and improve worldwide scholarly communication.')
}

function contactUs(){
	alert('Need technical support or help with your service? Please Call us at ###########');
}
</script>

    
    <!-- Portfolio Grid Section -->
    <section id="portfolio" class="bg-light-gray">
        <div class="container">
            <div class="row">
                <div class="col-lg-12 text-center">
                    <h2 class="section-heading">Welcome Borrower</h2>
                    <h3 class="section-subheading text-muted">Click on any book you want to checkout or return</h3><br />
                    <button type="button" class="btn btn-warning btn-sm" data-target="#loans" data-toggle="modal">View your loans</button>
               
                </div>
            </div>
             
            
            
            <div class="row">
                    
     <div class="input-group">
  		<button type="submit">Search</button>
 		<input type="text" name="searchString" class="form-control" placeholder="Enter Name" aria-describedby="basic-addon1">
	</div>     
      
    <nav aria-label="Page navigation">
  <ul class="pagination">
    <li>
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
    <%for(int i = 1; i <= pages; i++){ %>
    
    <li><a><%=i%></a></li>
    <%} %>
    <li>
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
  </ul>
</nav>        
            
            
            
	<table class = "table" rules = "all">
		<tr>
			<th></th>
			<th>Branch Name</th>	
			<th>Branch Address</th>
			<th>Books in branch</th>
		</tr>
		<tr>
			
			<%
			int count = 1;
			for(LibraryBranch a : libraryBranch){
			%>
		<tr>
			<td><%out.println(count);%></td>
			<td><%out.println(a.getBranchName());%></td>
			<td><%out.println(a.getBranchAddress()); count++; %></td>
			<td><% for(Book b : a.getBooks()){ 
				//out.println(b.getTitle() + "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;");
				bookId = b.getBookId();
				branchId = a.getBranchId();			
				numberOfCopies = LibrarianService.getCopiesForBranch(bookId,branchId);
				/*
				if(numberOfCopies == 0){
					continue;
				}
				*/
				request.setAttribute("bookId", bookId);				
			%>
			<p class="text-muted"><%=b.getTitle()%> has <%=numberOfCopies%> Copies left</p>
			<%
			%>	
			<%
			/*
			out.println("&nbsp;" + "&nbsp;"  + "&nbsp;"  + "&nbsp;"  + "&nbsp;"  + "&nbsp;"  + "&nbsp;"  + "&nbsp;" 
					+ "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;"
					+ "&nbsp;" + "&nbsp;" + "&nbsp;" + "&nbsp;");
			*/
			
			if(numberOfCopies == 0){
				%>
				    <button type = "button" class = "btn btn-success" onclick ="javascript:location.href='checkoutreturn?bookId=<%=b.getBookId()%>&branchId=<%=a.getBranchId()%>&move=1&numcopies=<%=numberOfCopies%>'">Return</button>
					 <button type = "button"class = "btn btn-danger" onclick ="javascript:location.href='checkoutreturn?bookId=<%=b.getBookId()%>&branchId=<%=a.getBranchId()%>&move=2'" disabled>Checkout</button>	
			
			<% 
			}else{
			%>
			
		    <button type = "button" class = "btn btn-success" onclick ="javascript:location.href='checkoutreturn?bookId=<%=b.getBookId()%>&branchId=<%=a.getBranchId()%>&move=1&numcopies=<%=numberOfCopies%>'">Return</button>   
		    <button type = "button" class = "btn btn-danger" onclick ="javascript:location.href='checkoutreturn?bookId=<%=b.getBookId()%>&branchId=<%=a.getBranchId()%>&move=2&numcopies=<%=numberOfCopies%>'">Checkout</button>		
			
			
			<%	
			}
			%>
			<br />
			<br />
			
			<%	
			//out.println("&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;"+ "&nbsp;" + "&nbsp;" + "&nbsp;");
			}
			%>
			</td>			
		</tr>
			<%} %>	
	</table>
            </div>
        </div>
	</section>
	
	
	<div class="modal fade bs-example-modal-lg" tabindex="-1" id=loans role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content">
    
    <div class="modal-header">

        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" >Viewing Loans.....</h4>
     </div>

	  
      <div class="modal-body">
      <h2>loan Details</h2>
      </div>
      
      <table class="table" rules="all">
      	<tr>
			<th>Date Out</th>
			<th>Due Date</th>
			<th>Date In</th>
		</tr>
      
       <% 
       for(String a : loans.values()){
    	   String[] loanInfo = a.split(","); 
    	   if(loanInfo[5].equalsIgnoreCase("null")){
    		   loanInfo[5] = "Not In";
    	   }
    	   
       %>
       <tr>
       <td>
       	<%=loanInfo[3]%>
       </td>
       
       <td>
       	<%=loanInfo[4]%>
       </td>
       
       <td>
       	<%=loanInfo[5]%>
       </td>
       </tr>
     <% 
       }
      %>
      
      </table>
      
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
   </div>

    </div>
  </div>
</div>
	
	
	
	
	

    <!-- jQuery -->
    <script src="./template_files/jquery.min.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="./template_files/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="./template_files/jquery.easing.min.js" integrity="sha384-mE6eXfrb8jxl0rzJDBRanYqgBxtJ6Unn4/1F7q4xRRyIw7Vdg9jP4ycT7x1iVsgb" crossorigin="anonymous"></script>

    <!-- Contact Form JavaScript -->
    <script src="./template_files/jqBootstrapValidation.js"></script>
    <script src="./template_files/contact_me.js"></script>

    <!-- Theme JavaScript -->
    <script src="./template_files/agency.min.js"></script>

</body></html>
