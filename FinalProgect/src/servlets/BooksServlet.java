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

import dao.AuthorRepositoryImpl;
import dao.BookRepositoryImpl;
import dao.GenreRepositoryImpl;
import model.Author;
import model.Book;
import model.Genre;
import service.AuthorService;
import service.AuthorServiceImpl;
import service.BookService;
import service.BookServiceImpl;
import service.GenreService;
import service.GenreServiceImpl;

@WebServlet("/BooksServlet")
public class BooksServlet extends HttpServlet {
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
		Book book = new Book();
		book.setName(request.getParameter("nameCreate"));
		String genreName = request.getParameter("genreCreate");
		Genre genre = new Genre();
		genre.setName(genreName);
		List<Genre> genres;
		try {
			genres = genreService.findAll();
			for (Genre g : genres) {
				if (g.getName().equals(genreName)) {
					genre.setId(g.getId());
					break;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (genre.getId() == null) {
			try {
				genre = genreService.create(genre);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		book.setG(genre);
		String authorName = request.getParameter("authorCreate");
		Author author = new Author();
		author.setName(authorName);
		List<Author> authors;
		try {
			authors = authorService.findAll();
			for (Author a : authors) {
				if (a.getName().equals(authorName)) {
					author.setId(a.getId());
					break;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (author.getId() == null) {
			try {
				author = authorService.create(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		book.setA(author);
		List<Book> books = null;
		try {
			books = bookService.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean bookExist = false;
		for (Book b : books) {
			if (b.getName().equals(book.getName()) && b.getA().getName().equals(book.getA().getName())) {
				out.println("<h3>Такая книга уже имеется!</h3>\n");
				out.println("<h3>Вот она:</h3>\n");
				out.println("<h5>ID: " + b.getId() + "</h5>");
				out.println("<h5> Book name: " + b.getName() + "</h5>");
				out.println("<h5> Author: " + b.getA().getName() + "</h5>");
				out.println("<h5> Genre: " + b.getG().getName() + "</h5><br>");
				bookExist = true;
				break;
			}
		}
		if (!bookExist) {
			try {
				Book newBook = bookService.create(book);
				out.println("<h3>Книга добавлена в библиотеку:</h3>");
				out.println("<h5>ID: " + newBook.getId() + "</h5>");
				out.println("<h5> Book name: " + newBook.getName() + "</h5>");
				out.println("<h5> Author: " + newBook.getA().getName() + "</h5>");
				out.println("<h5> Genre: " + newBook.getG().getName() + "</h5><br>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Book book = new Book();
		book.setId(Integer.parseInt(request.getParameter("idUpdate")));
		book.setName(request.getParameter("nameUpdate"));
		String genreName = request.getParameter("genreUpdate");
		Genre genre = new Genre();
		genre.setName(genreName);
		List<Genre> genres;
		try {
			genres = genreService.findAll();
			for (Genre g : genres) {
				if (g.getName().equals(genreName)) {
					genre.setId(g.getId());
					break;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (genre.getId() == null) {
			try {
				genre = genreService.create(genre);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		book.setG(genre);
		String authorName = request.getParameter("authorUpdate");
		Author author = new Author();
		author.setName(authorName);
		List<Author> authors;
		try {
			authors = authorService.findAll();
			for (Author a : authors) {
				if (a.getName().equals(authorName)) {
					author.setId(a.getId());
					break;
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		if (author.getId() == null) {
			try {
				author = authorService.create(author);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		book.setA(author);
		List<Book> books = null;
		try {
			books = bookService.findAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean bookExist = false;
		for (Book b : books) {
			if (b.getName().equals(book.getName()) && b.getA().getName().equals(book.getA().getName())) {
				out.println("<h3>Такая книга уже имеется!</h3>\n");
				out.println("<h3>Вот она:</h3>\n");
				out.println("<h5>ID: " + b.getId() + "</h5>");
				out.println("<h5> Book name: " + b.getName() + "</h5>");
				out.println("<h5> Author: " + b.getA().getName() + "</h5>");
				out.println("<h5> Genre: " + b.getG().getName() + "</h5><br>");
				bookExist = true;
				break;
			}
		}
		if (!bookExist) {
			try {
				Book newBook = bookService.update(book);
				out.println("<h3>Книга обновлена в библиотеке:</h3>");
				out.println("<h5>ID: " + newBook.getId() + "</h5>");
				out.println("<h5> Book name: " + newBook.getName() + "</h5>");
				out.println("<h5> Author: " + newBook.getA().getName() + "</h5>");
				out.println("<h5> Genre: " + newBook.getG().getName() + "</h5><br>");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		out.print("<a href='http://localhost:8080/FinalProgect/BooksList.html'>Редактировать дальше</a><br>");
		out.print(
				"<a href='http://localhost:8080/FinalProgect/ListOfBooks'>Посмотреть список книг в библиотеке</a><br>");
		out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a>");
		out.close();
	}
}
