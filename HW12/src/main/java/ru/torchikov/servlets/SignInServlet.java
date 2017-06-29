package ru.torchikov.servlets;

import ru.torchikov.PageGenerator;
import ru.torchikov.jdbc.cache.CacheEngine;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sergei on 28.06.17.
 */
public class SignInServlet extends HttpServlet {
    public static final String URL = "/signin";

    private final String GET_HITS = "getHits";
    private final String GET_MISSES = "getMisses";
    private final String GET_MAX_ELEMENT_COUNT = "getMaxElementCount";
    private final String GET_LIFE_TIME_MS = "getLifeTimeMs";
    private final String GET_IDLE_TIME_MS = "getIdleTimeMs";
    private final String IS_ETERNAL = "isEternal";

    private final CacheEngine<?> cacheEngine;

    public SignInServlet(CacheEngine<?> cacheEngine) {
        this.cacheEngine = cacheEngine;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login == null || password == null) {
            System.out.println("Login or password wasn't be specified");
            response.setContentType(getContentTypeText());
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print("Login or password wasn't be specified");
        }

        if (!login.equalsIgnoreCase("Admin") || !password.equals("123456")) {
            response.setContentType(getContentTypeText());
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username", login);
        parameters.put(GET_HITS, cacheEngine.getHitCount());
        parameters.put(GET_MISSES, cacheEngine.getMissCount());
        parameters.put(GET_MAX_ELEMENT_COUNT, cacheEngine.getMaxElements());
        parameters.put(GET_LIFE_TIME_MS, cacheEngine.getLifeTimeMs());
        parameters.put(GET_IDLE_TIME_MS, cacheEngine.getIdleTimeMs());
        parameters.put(IS_ETERNAL, cacheEngine.isEtermal());
        response.setContentType(getContentTypeText());
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print(PageGenerator.getInstance().getPage("cache_manager.html", parameters));
    }

    private String getContentTypeText() {
        return "text/html;charset=utf-8";
    }
}
