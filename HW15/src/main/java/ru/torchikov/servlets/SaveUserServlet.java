package ru.torchikov.servlets;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.torchikov.base.DBService;
import ru.torchikov.dataset.UserDataSet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@WebServlet(name = "saveUserServlet", urlPatterns = "/save")
public class SaveUserServlet extends HttpServlet{

	private DBService dbService;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String userName = req.getParameter("userName");
		String userAge = req.getParameter("userAge");

		UserDataSet user = new UserDataSet(userName, Integer.parseInt(userAge));
		dbService.save(user);
		resp.setContentType("text/html;charset=utf-8");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.sendRedirect("insert_user.html");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		dbService = context.getBean(DBService.class);
	}
}
