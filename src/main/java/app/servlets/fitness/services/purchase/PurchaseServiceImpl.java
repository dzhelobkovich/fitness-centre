package app.servlets.fitness.services.purchase;

import app.servlets.fitness.dto.purchase.PurchaseRequest;
import app.servlets.fitness.dto.purchase.PurchaseResponse;
import app.servlets.fitness.entities.Purchase;
import app.servlets.fitness.entities.Subscription;
import app.servlets.fitness.entities.User;
import app.servlets.fitness.mappers.PurchaseMapper;
import app.servlets.fitness.repositories.purchase.PurchaseRepository;
import app.servlets.fitness.services.subscription.SubscriptionService;
import app.servlets.fitness.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static app.servlets.fitness.util.Constants.*;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService{
    private final PurchaseRepository purchaseRepository;
    private final PurchaseMapper purchaseMapper;
    private final UserService userService;
    private final SubscriptionService subscriptionService;


    @Override
    public PurchaseResponse create(PurchaseRequest purchaseRequest) {
        Purchase purchase = purchaseMapper.purchaseRequestToPurchase(purchaseRequest);
        Optional<User> optionalUser = userService.findUserByIdForPurchase(purchaseRequest.getUserRequest().getUserId());
        Optional<Subscription> optionalSubscription = subscriptionService.findSubscriptionByIdForPurchase(1L);
        //PurchaseResponse purchaseResponse = purchaseMapper.makePurchase(purchase, optionalUser, optionalSubscription);
        purchaseRepository.save(purchase);
        return purchaseResponse;
    }

    @Override
    public List<PurchaseResponse> read() {
        List<Purchase> purchases = purchaseRepository.findAll();
        return purchaseMapper.purchasesToPurchaseResponses(purchases);
    }

    @Override
    public PurchaseResponse update(Long id, PurchaseRequest purchaseRequest) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(SUBSCRIPTION_SEARCH_EXCEPTION + id));
        purchaseMapper.updatePurchase(purchase, purchaseRequest);
        return purchaseMapper.purchaseToPurchaseResponse(purchaseRepository.save(purchase));
    }

    @Override
    public boolean delete(Long id) {
        Optional<Purchase> optionalPurchase = purchaseRepository.findById(id);
        optionalPurchase.ifPresent(purchaseRepository::delete);
        return optionalPurchase.isPresent();
    }

    @Override
    public PurchaseResponse findByID(Long id) {
        Purchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ERROR_SEARCH_PURCHASE_EXCEPTION + id));
        return purchaseMapper.purchaseToPurchaseResponse(purchase);
    }
}
