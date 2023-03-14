package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.*;

import java.util.List;

public interface PurchaseService {
    Purchase buy(int reservedSeats, Long sessionId, Long userId, int transactionNumber) throws InstanceNotFoundException,  AlreadyStartedSessionException, FullSessionException;
    void ticketDelivery(Long userId, Long purchaseId, int transactionNumber) throws InstanceNotFoundException,
            AlreadyUsedException, AlreadyStartedSessionException, InvalidCardException;
    List<Purchase> history(Long Id, int page , int size);
}
