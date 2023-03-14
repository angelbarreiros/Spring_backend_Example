package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieDao;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.exceptions.AlreadyStartedSessionException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.TimeExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional(readOnly=true)
public class BillboardServiceImpl implements BillboardService {
    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private MovieDao movieDao;


    @Override
    public Session findSessionById(Long id) throws InstanceNotFoundException, AlreadyStartedSessionException {
        Optional<Session> session = sessionDao.findById(id);

        if (session.isEmpty()) {
            throw new InstanceNotFoundException("project.entities.session",id);
        }
        if (LocalDateTime.of(session.get().getDate(),session.get().getTime()).isBefore(LocalDateTime.now())){
            throw new AlreadyStartedSessionException(session.get().getMovie().getTitle(),session.get().getTime());
        }
        return session.get();
    }

    @Override
    public List<BillboardRow> completeBillboard(LocalDate date) throws TimeExceededException {
        if (date.isBefore(LocalDateTime.now().toLocalDate())){
            throw new TimeExceededException();
        }
        if (date.isAfter(LocalDate.now().plusDays(Session.MAX_DAYS))){
            throw new TimeExceededException();
        }
        Long id=0L;
        int iter=-1;
        List<BillboardRow> billboardRows= new ArrayList<>();
        List<Session> sessions= sessionDao.findAllByDateOrderByMovieIdDescMovieTitleDescTimeAsc(date);
        for (Session item : sessions){
            if(item.getTime().isBefore(LocalTime.now())){
                continue;
            }
            if (Objects.equals(item.getMovie().getId(), id)){
                billboardRows.get(iter).getSessions().add(item);

            }
            else{
                id= item.getMovie().getId();
                iter++;
                BillboardRow billboardRow= new BillboardRow(item.getMovie(),new ArrayList<>());
                billboardRow.getSessions().add(item);
                billboardRows.add(billboardRow);
            }
        }
        return billboardRows;

    }


    @Override
    public Movie getMovie(Long id) throws InstanceNotFoundException {
        Optional<Movie> movieOptional= movieDao.findById(id);
        if (movieOptional.isEmpty()){
            throw new InstanceNotFoundException("project.entities.movie",id);
        }
        return movieOptional.get();
    }




}
