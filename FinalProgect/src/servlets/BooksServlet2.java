package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AuthorRepositoryImpl;
import dao.BookRepositoryImpl;
import dao.GenreRepositoryImpl;
import model.Book;
import service.AuthorService;
import service.AuthorServiceImpl;
import service.BookService;
import service.BookServiceImpl;
import service.GenreService;
import service.GenreServiceImpl;

@WebServlet("/BooksServlet2")
public class BooksServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	BookService bookService = null;
	GenreService genreService = null;
	AuthorService authorService = null;

	@Override
	public void init() throws ServletException {
		super.init();
		bookService = new BookServiceImpl(new BookRepositoryImpl());
		genreService = new GenreServiceImpl(new GenreRepositoryImpl());
		authorService = new AuthorServiceImpl(new AuthorRepositoryImpl());
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
		Integer id = Integer.parseInt(request.getParameter("idDelete"));
		try {
			bookService.delete(id);
			out.println("<h3>Книга удалена из библиотеки!</h3>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print("<a href='http://localhost:8080/FinalProgect/BooksList.html'>Редактировать дальше</a><br>");
		out.print(
				"<a href='http://localhost:8080/FinalProgect/ListOfBooks'>Посмотреть список книг в библиотеке</a><br>");
		out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a>");
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		Integer id = Integer.parseInt(request.getParameter("idGet"));
		try {
			Book book = bookService.findById(id);
			out.println("<h3>Книга найдена в библиотеке!</h3>");
			out.println("<h3>Вот она: </h3>\n");
			out.println("<h5>ID: " + book.getId() + "</h5>");
			out.println("<h5> Book name: " + book.getName() + "</h5>");
			out.println("<h5> Author: " + book.getA().getName() + "</h5>");
			out.println("<h5> Genre: " + book.getG().getName() + "</h5><br>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.print("<a href='http://localhost:8080/FinalProgect/BooksList.html'>Редактировать дальше</a><br>");
		out.print(
				"<a href='http://localhost:8080/FinalProgect/ListOfBooks'>Посмотреть список книг в библиотеке</a><br>");
		out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a>");
		out.close();
	}
}
