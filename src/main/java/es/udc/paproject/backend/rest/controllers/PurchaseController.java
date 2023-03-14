package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.PurchaseService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.PurchaseDto;
import es.udc.paproject.backend.rest.dtos.PurchaseHistoryConversor;
import es.udc.paproject.backend.rest.dtos.PurchaseHistoryDto;
import es.udc.paproject.backend.rest.dtos.TicketDeliveryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {

    @Autowired
    private MessageSource messageSource;

    private final static String ALREADY_STARTED_SESSION_EXCEPTION = "project.exceptions.AlreadyStartedSessionException";
    private final static String INVALID_CART_EXCEPTION = "project.exceptions.InvalidCardException";
    private final static String ALREADY_USED_EXCEPTION = "project.exceptions.AlreadyUsedException";
    private final static String FULL_SESSION_EXCEPTION = "project.exceptions.FullSessionException";

    @ExceptionHandler(FullSessionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyUsedException(FullSessionException exception, Locale locale){
        String  errorMessage = messageSource.getMessage(FULL_SESSION_EXCEPTION, new Object[]{exception.getFreeSeats()}, FULL_SESSION_EXCEPTION,
                locale);
        return new ErrorsDto(errorMessage);
    }

    @ExceptionHandler(AlreadyUsedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyUsedException(AlreadyUsedException exception, Locale locale){
        String  errorMessage = messageSource.getMessage(ALREADY_USED_EXCEPTION, null, ALREADY_USED_EXCEPTION,
                locale);
        return new ErrorsDto(errorMessage);
    }



    @ExceptionHandler(AlreadyStartedSessionException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleAlreadyStartedSessionException(AlreadyStartedSessionException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(ALREADY_STARTED_SESSION_EXCEPTION,
                new Object[] {exception.getTitle(),exception.getTime()}, ALREADY_STARTED_SESSION_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);

    }
    @ExceptionHandler(InvalidCardException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleInvalidCardException(InvalidCardException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(INVALID_CART_EXCEPTION, new Object[]{exception.getCardNumber()}, INVALID_CART_EXCEPTION, locale);

        return new ErrorsDto(errorMessage);

    }
    @Autowired
    private PurchaseService purchaseService;
    @GetMapping(path = "/history")
    public List<PurchaseHistoryDto> getHistory(@RequestAttribute Long userId,
                                               @RequestParam(defaultValue="0") int page){
        return PurchaseHistoryConversor.toPurchaseDtos(purchaseService.history(userId,page, Purchase.PAGE_SIZE));
    }

    @PostMapping(path = "/{sessionId}/buy")
    public Long buy(@RequestAttribute Long userId, @PathVariable("sessionId") Long sessionId, @Validated @RequestBody PurchaseDto params)
            throws InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {
        return purchaseService.buy(params.getNumberOfTickets(),
                sessionId,
                userId,
                params.getTransactionNumber()).getId();
    }
    @PostMapping(path = "/delivery")
    public void ticketDelivery(@RequestAttribute Long userId, @Validated @RequestBody TicketDeliveryDto params)
        throws InstanceNotFoundException, AlreadyUsedException, AlreadyStartedSessionException,
            InvalidCardException{
        purchaseService.ticketDelivery(userId,
                params.getPurchaseId(),
                params.getTransactionNumber());
    }
}
