package app.servlets.fitness.controllers.user;

import app.servlets.fitness.services.UserService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


import static app.servlets.fitness.util.Constants.*;

@WebServlet(urlPatterns = "/deleteUser", loadOnStartup = 1)
public class DeleteUserController extends HttpServlet {
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
        try {
            String userId = req.getParameter(ID);
            userService.deleteById(Long.parseLong(userId));
            resp.sendRedirect(ADMIN_READ_USERS_URL);
        } catch (IOException e) {
            req.setAttribute(ERROR_MESSAGE, ERROR_USER_DELETE);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        userService = (UserService) config.getServletContext().getAttribute(USER_SERVICE);
    }
}