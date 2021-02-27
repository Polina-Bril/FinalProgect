package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookRepositoryImpl;
import model.Book;
import service.BookService;
import service.BookServiceImpl;

@WebServlet("/MyServlet")
public class MyServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Map<String, List<String>> users = new ConcurrentHashMap<>();
	BookService bookService = null;

	@Override
	public void init() throws ServletException {
		super.init();
		users.put("Vanya", List.of("ijfwhe", "Иван"));
		users.put("Katya", List.of("uhryop3n", "Екатерина"));
		users.put("Poli", List.of("laeuhgdfuyqg", "Полина"));
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
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String login = request.getParameter("login");
		String passwSecured = null;
		try {
			passwSecured = users.get(login).get(0);
		} catch (NullPointerException e) {
			request.getRequestDispatcher("RegistrationNewUser.html").forward(request, response);
			return;
		}
		if (passwSecured != null) {
			String password = request.getParameter("password");
			if (passwSecured.equals(password)) {
				String name = users.get(login).get(1);
				session.setAttribute("name", name);
				session.setAttribute("password", password);
				session.setAttribute("login", login);
				out.println("<h3>Привет, " + session.getAttribute("name") + "!</h3>\n\n");
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
				out.print(
						"<a href='http://localhost:8080/FinalProgect/BooksList.html'>Внести изменения в библиотеку</a><br>");
				out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a><br>");
			} else {
				out.print("<h3>Пароль введен не верно! Попробуйте еще раз!</h3>");
				out.print("<a href='http://localhost:8080/FinalProgect/StartPage.html'>На главную</a>");
			}
		}
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		if (!users.containsKey(login)) {
			users.put(login, List.of(password, name));
			out.println("<h3>Привет, " + users.get(login).get(1) + "!</h3>\n");
			out.println("<h3>Ниже список имеющихся книг!</h3>\n");
			List<Book> all = null;
			try {
				all = bookService.findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			all.forEach(it -> {
				out.write("<h5>ID: " + it.getId() + "</h5>");
				out.write("<h5> Book name: " + it.getName() + "</h5>");
				out.write("<h5> Author: " + it.getA().getName() + "</h5>");
				out.write("<h5> Genre: " + it.getG().getName() + "</h5><br>");
			});
			out.print(
					"<a href='http://localhost:8080/FinalProgect/BooksList.html'>Внести изменения в библиотеку</a><br>");
			out.print("<a href='http://localhost:8080/FinalProgect/LogOut'>Выйти</a><br>");

		} else {
			out.print("<h3>Пользователь с login " + login + " уже существует. Выберите другой login</h3>");
			out.print("<a href='http://localhost:8080/FinalProgect/RegistrationNewUser.html'>Назад</a>");
		}
		out.close();
	}
}
