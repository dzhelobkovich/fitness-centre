package app.servlets.fitness.controllers.subscription;

import app.servlets.fitness.entities.Subscription;
import app.servlets.fitness.mappers.SubscriptionMapper;
import app.servlets.fitness.services.SubscriptionService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static app.servlets.fitness.util.Constants.*;

@WebServlet(urlPatterns = "/subscription/edit", loadOnStartup = 1)
public class EditSubscriptionController extends HttpServlet {
    private SubscriptionService subscriptionService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String subscriptionId = req.getParameter(ID);
        Subscription subscription = subscriptionService.getById(Long.parseLong(subscriptionId));
        if (subscription != null) {
            req.setAttribute(SUBSCRIPTION, subscription);
            req.getRequestDispatcher(EDIT_SUBSCRIPTION_PAGE).forward(req, resp);
        } else {
            resp.sendRedirect(SUBSCRIPTIONS_URL);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SubscriptionMapper subscriptionMapper = new SubscriptionMapper();
        String id = req.getParameter(ID);
        String subscriptionName = req.getParameter(SUBSCRIPTION_NAME);
        int subscriptionPrice = Integer.parseInt(req.getParameter(SUBSCRIPTION_PRICE));
        int subscriptionPeriod = Integer.parseInt(req.getParameter(SUBSCRIPTION_PERIOD));
        int numberOfGuestVisits = Integer.parseInt(req.getParameter(NUMBER_OF_GUEST_VISITS));
        int maxSubscriptionStop = Integer.parseInt(req.getParameter(MAX_SUBSCRIPTION_STOP));
        String description = req.getParameter(DESCRIPTION);

        Subscription subscription = subscriptionMapper.buildSubscription(Long.parseLong(id), subscriptionName, subscriptionPrice, subscriptionPeriod,
                numberOfGuestVisits, maxSubscriptionStop, description);
        subscriptionService.updateSubscription(subscription);

        resp.sendRedirect(SUBSCRIPTIONS_URL);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        subscriptionService = (SubscriptionService) config.getServletContext().getAttribute(SUBSCRIPTION_SERVICE);
    }
}