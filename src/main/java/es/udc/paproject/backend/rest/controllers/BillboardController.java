package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.exceptions.AlreadyStartedSessionException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.TimeExceededException;
import es.udc.paproject.backend.model.services.BillboardService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/billboard")

public class BillboardController {
    private final static String TIME_EXCEEDED_EXCEPTION_CODE = "project.exceptions.TimeExceededException";
    private final static String ALREADY_STARTED_SESSION_EXCEPTION = "project.exceptions.AlreadyStartedSessionException";
    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler(TimeExceededException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorsDto handleMaxQuantityExceededException(TimeExceededException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(TIME_EXCEEDED_EXCEPTION_CODE,
                new Object[] {exception.getAllowedDays()}, TIME_EXCEEDED_EXCEPTION_CODE, locale);

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
    @Autowired
    private BillboardService billboardService;
    @GetMapping(path = "/{day}")
    public List<BillboardRowDto> getBillboard(@Validated @PathVariable("day") Long day) throws TimeExceededException {
        Instant instant = Instant.ofEpochMilli(day);
        LocalDateTime fechaLocalDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        LocalDate fechaLocalDate = fechaLocalDateTime.toLocalDate();

        return BillboardRowConversor.toBillboardDtos(billboardService.completeBillboard(fechaLocalDate));
    }
    @GetMapping(path = "/session/{id}")
    public SessionDto getSession(@Validated @PathVariable("id") Long id) throws InstanceNotFoundException, AlreadyStartedSessionException {
        return SessionConversor.toSessionDto(billboardService.findSessionById(id));
    }

    @GetMapping(path = "/movie/{id}")
    public MovieDto getMovie(@Validated @PathVariable("id") Long id) throws InstanceNotFoundException, AlreadyStartedSessionException {
        return MovieConversor.toMovieDto(billboardService.getMovie(id));
    }
}
