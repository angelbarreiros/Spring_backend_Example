package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.services.PurchaseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PurchaseServiceTest {
    private final static Long NON_EXISTENT_ID = (long) -1;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private UserDao userDao;

    @Autowired
    private MovieTheaterDao movieTheaterDao;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private SessionDao sessionDao;

    private User createUser(String userName) {
        return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com");
    }

    private Session createSessionToday(Movie movie, MovieTheater movieTheater) {
        return new Session(movie, movieTheater, LocalDate.now(), LocalTime.now(), new BigDecimal(3), 60);
    }

    private Session createSessionTomorrow(Movie movie, MovieTheater movieTheater) {
        return new Session(movie, movieTheater, LocalDate.now().plusDays(1), LocalTime.now(), new BigDecimal(3), 60);
    }
    private Session createFullSessionTomorrow(Movie movie, MovieTheater movieTheater){
        return new Session(movie,movieTheater, LocalDate.now().plusDays(1), LocalTime.now(), new BigDecimal(3),0);
    }

    private Movie createMovie(String Title) {
        return new Movie(Title, "SumaryTest", Integer.toUnsignedLong(120));
    }

    private MovieTheater createMovieTheater(String name) {
        return new MovieTheater(name, 180);
    }
    /*[FUNC-5]*/

    @Test
    public void testHistory() {

        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertEquals(purchaseService.history(user.getId(), 0, 10).size(), 0);

    }

    @Test
    public void getHistory() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");

        Session session = createSessionToday(movie, movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        Purchase purchase = new Purchase(5, session, user, 192190, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertEquals(purchaseService.history(user.getId(), 0, 10).size(), 1);
    }

    @Test
    public void paginationTest() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");

        Session session = createSessionToday(movie, movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        for (int i = 0; i < 15; i++) {
            purchaseDao.save(new Purchase(5, session, user, 192190, LocalDateTime.now(), false));
        }
        assertEquals(purchaseService.history(user.getId(), 0, 10).size(), 10);
        assertEquals(purchaseService.history(user.getId(), 1, 10).size(), 5);

    }

    @Test
    public void userNotFound() {
        assertEquals(purchaseService.history(NON_EXISTENT_ID, 0, 10).size(), 0);

    }

    /*[FUNC-4]*/
    @Test
    public void buyTest() throws InstanceNotFoundException, AlreadyStartedSessionException, FullSessionException {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");

        Session session = createSessionTomorrow(movie, movieTheater);


        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        Purchase bought = purchaseService.buy(6, session.getId(), user.getId(), 87675554);
        assertTrue(purchaseDao.findById(bought.getId()).isPresent());
        assertEquals(bought, purchaseDao.findById(bought.getId()).get());

    }

    @Test
    public void buyNonExistUserTest() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");

        Session session = createSessionToday(movie, movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);

        assertThrows(InstanceNotFoundException.class, () -> purchaseService.buy(6, session.getId(), NON_EXISTENT_ID, 87675554));
    }

    @Test
    public void buyNonExistSessionTest() {
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);
        assertThrows(InstanceNotFoundException.class, () -> purchaseService.buy(6, NON_EXISTENT_ID, user.getId(), 87675554));
    }


    @Test
    public void buyWhenFullSession()  {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");

        Session session = createFullSessionTomorrow(movie,movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user= createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        assertThrows(FullSessionException.class, ()->purchaseService.buy(6,session.getId(), user.getId(),87675554));
    }

    @Test
    public void buyWhenStartedSession() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater= createMovieTheater("sala1");

        Session session = createSessionToday(movie,movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user= createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        assertThrows(AlreadyStartedSessionException.class, ()->purchaseService.buy(6,session.getId(), user.getId(),87675554));
    }

    /*[FUNC-6]*/
    @Test
    public void ticketDeliveryCorrectlyTest() throws InstanceNotFoundException, AlreadyStartedSessionException, AlreadyUsedException, InvalidCardException {
        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);

        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);

        Session session = createSessionTomorrow(movie, movieTheater);
        sessionDao.save(session);

        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        Purchase purchase = new Purchase(11, session, user, 213456217, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        purchaseService.ticketDelivery(user.getId(), purchase.getId(), 213456217);
        assertTrue(purchase.isUsed());
    }

    @Test
    public void ticketDeliveryFailDateTest(){
        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);

        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);

        Session session = createSessionToday(movie, movieTheater);
        sessionDao.save(session);

        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        Purchase purchase = new Purchase(10, session, user, 213456217, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        assertThrows(AlreadyStartedSessionException.class, () -> purchaseService.ticketDelivery(user.getId(), purchase.getId(), 87675554));
    }

    @Test
    public void ticketDeliveryFailTransactionNumberTest()  {
        Movie movie = createMovie("Muscle Man");
        movieDao.save(movie);

        MovieTheater movieTheater = createMovieTheater("sala1");
        movieTheaterDao.save(movieTheater);

        Session session = createSessionTomorrow(movie, movieTheater);
        sessionDao.save(session);

        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        Purchase purchase = new Purchase(10, session, user, 213456217, LocalDateTime.now(), false);
        purchaseDao.save(purchase);
        assertTrue(purchaseDao.findById(purchase.getId()).isPresent());
        assertThrows(InvalidCardException.class, () -> purchaseService.ticketDelivery(user.getId(), purchase.getId(), 87675554 + 1));
    }

    @Test
    public void alreadyUsedTickets() {
        Movie movie = createMovie("Muscle Man");
        MovieTheater movieTheater = createMovieTheater("sala1");

        Session session = createSessionToday(movie, movieTheater);

        movieDao.save(movie);
        movieTheaterDao.save(movieTheater);
        sessionDao.save(session);
        User user = createUser("alguien");
        user.setRole(User.RoleType.USER);
        userDao.save(user);

        Purchase purchase = new Purchase(10, session, user, 213456217, LocalDateTime.now(), true);
        purchaseDao.save(purchase);
        assertThrows(AlreadyUsedException.class, () -> purchaseService.ticketDelivery(user.getId(), purchase.getId(), 87675554));
    }
}
