package app.servlets.fitness.controllers.subscription;

import app.servlets.fitness.dto.SubscriptionDto;
import app.servlets.fitness.mappers.SubscriptionDtoMapper;
import app.servlets.fitness.services.SubscriptionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.servlets.fitness.util.Constants.*;

@WebServlet(urlPatterns = "/subscription/create", loadOnStartup = 1)
public class CreateSubscriptionController extends HttpServlet {
    private SubscriptionService subscriptionService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubscriptionDtoMapper subscriptionDtoMapper = new SubscriptionDtoMapper();
        SubscriptionDto subscriptionDto = subscriptionDtoMapper.buildSubscriptionDto(
                subscriptionService.determineSubscriptionCategory(req.getParameter(SUBSCRIPTION_CATEGORY)),
                req.getParameter(SUBSCRIPTION_NAME),
                Integer.parseInt(req.getParameter(SUBSCRIPTION_PRICE)),
                Integer.parseInt(req.getParameter(SUBSCRIPTION_PERIOD)),
                Integer.parseInt(req.getParameter(NUMBER_OF_GUEST_VISITS)),
                Integer.parseInt(req.getParameter(MAX_SUBSCRIPTION_STOP)),
                req.getParameter(DESCRIPTION)
        );
        subscriptionService.createSubscription(subscriptionDtoMapper.toEntity(subscriptionDto));
        req.getRequestDispatcher(INDEX_PAGE).forward(req, resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        subscriptionService = (SubscriptionService) config.getServletContext().getAttribute(SUBSCRIPTION_SERVICE);
    }
}