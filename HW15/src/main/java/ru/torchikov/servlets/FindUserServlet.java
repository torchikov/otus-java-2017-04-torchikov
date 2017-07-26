package ru.torchikov.servlets;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.torchikov.base.FrontendService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

/**
 * Created by Torchikov Sergei on 26.07.2017.
 *
 */
@WebServlet(name = "findUserServlet", urlPatterns = "/load")
public class FindUserServlet extends WebSocketServlet {
	private final static int LOGOUT_TIME = 10 * 60 * 1000;
	private FrontendService frontendService;

	@Override
	public void configure(WebSocketServletFactory webSocketServletFactory) {
		webSocketServletFactory.getPolicy().setIdleTimeout(LOGOUT_TIME);
		webSocketServletFactory.setCreator((request, response) -> new FindUserWebSocket(frontendService));
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
		frontendService = context.getBean(FrontendService.class);
	}
}
