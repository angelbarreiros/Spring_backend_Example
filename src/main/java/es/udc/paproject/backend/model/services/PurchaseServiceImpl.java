package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService{

    private final PermissionChecker permissionChecker;

    private final SessionDao sessionDao;

    private final PurchaseDao purchaseDao;
    public PurchaseServiceImpl(PermissionChecker permissionChecker, SessionDao sessionDao, PurchaseDao purchaseDao) {
        this.permissionChecker = permissionChecker;
        this.sessionDao = sessionDao;
        this.purchaseDao = purchaseDao;
    }


    @Override
    public Purchase buy(int reservedSeats, Long sessionId, Long userId, int transactionNumber) throws
            InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {

        Optional<Session> optionalSession = sessionDao.findById(sessionId);
        User user = permissionChecker.checkUser(userId); //función dada por el checker
        if(optionalSession.isEmpty()){
            throw new InstanceNotFoundException("project.entities.session", sessionId);
        }
        Session session = optionalSession.get();
        if(session.getDate().atTime(session.getTime()).isBefore(LocalDateTime.now())){
            throw new AlreadyStartedSessionException(session.getMovie().getTitle(),session.getTime());
        }
        if (session.getSeatsAvailable()<reservedSeats || session.getSeatsAvailable()==0){
            throw new FullSessionException(session.getSeatsAvailable());
        }

        session.setSeatsAvailable(session.getSeatsAvailable()-reservedSeats);
        Purchase purchase= new Purchase(reservedSeats,session,user,transactionNumber,LocalDateTime.now(),false);

        purchaseDao.save(purchase);
        return purchase;
    }

    @Override
    public List<Purchase> history(Long Id, int page, int size) {
        Slice<Purchase> purchaseSlice = purchaseDao.findAllByUserId_IdOrderByPurchaseDateDescIdDesc(Id, PageRequest.of(page, size));
        return purchaseSlice.getContent();
    }

    @Override
    public void ticketDelivery(Long userId, Long purchaseId, int transactionNumber)
            throws InstanceNotFoundException, AlreadyUsedException, AlreadyStartedSessionException, InvalidCardException {
        User user = permissionChecker.checkUser(userId);
        Optional<Purchase> optionalPurchase = purchaseDao.findById(purchaseId);
        if(optionalPurchase.isEmpty()){
            throw new InstanceNotFoundException("project.entities.purchase", userId);
        }
        Purchase purchase= optionalPurchase.get();
        if (purchase.isUsed()){
            throw new AlreadyUsedException("Already used");
        }
        Optional<Session> session = sessionDao.findById(purchase.getSession().getId());
        if (session.isEmpty()){
            throw new InstanceNotFoundException("project.entities.session", userId);
        }
        if(session.get().getDate().atTime(session.get().getTime()).isBefore(LocalDateTime.now())){
            throw new AlreadyStartedSessionException(session.get().getMovie().getTitle(),session.get().getTime());
        }
        if(purchase.getCardNumber() != transactionNumber){
            throw new InvalidCardException(transactionNumber);
        }
        purchase.setUsed(true);
    }

}
