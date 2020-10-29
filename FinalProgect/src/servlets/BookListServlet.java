package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BookRepositoryImpl;
import model.Book;
import service.BookService;
import service.BookServiceImpl;

@WebServlet("/ListOfBooks")
public class BookListServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	BookService bookService = null;

	@Override
	public void init() throws ServletException {
		bookService = new BookServiceImpl(new BookRepositoryImpl());
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<h3>Ниже список имеющихся книг!</h3>\n");
		List<Book> all = null;
		try {
			all = bookService.findAll();
			System.out.println(all);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		all.forEach(it -> {
			out.write("<h5>ID: " + it.getId() + "</h5>");
			out.write("<h5> Book name: " + it.getName() + "</h5>");
			out.write("<h5> Author: " + it.getA().getName() + "</h5>");
			out.write("<h5> Genre: " + it.getG().getName() + "</h5><br>");
		});
		out.print("<a href='http://localhost:8080/FinalProgect/BooksList.html'>Внести изменения в библиотеку</a><br>");
		out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a><br>");

		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}
}
