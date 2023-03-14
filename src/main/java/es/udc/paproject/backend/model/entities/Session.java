package es.udc.paproject.backend.model.entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Session {
    public static final int MAX_DAYS = 6;
    private Long id;

    private Movie movie;

    private MovieTheater movieTheater;

    private LocalDate date;
    private LocalTime time;

    private BigDecimal price;

    private int seatsAvailable;

    public Session(){}

    public Session(Long id, Movie movie, MovieTheater movieTheater,
                   LocalDate date, LocalTime time,
                   BigDecimal price, int seatsAvailable) {
        this.id = id;
        this.movie = movie;
        this.movieTheater = movieTheater;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }
    public Session( Movie movie, MovieTheater movieTheater,
                   LocalDate date, LocalTime time,
                   BigDecimal price, int seatsAvailable) {
        this.movie = movie;
        this.movieTheater = movieTheater;
        this.date = date;
        this.time = time;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="movieTheaterId")
    public MovieTheater getMovieTheater() {
        return movieTheater;
    }

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="movieId")
    public Movie getMovie() {
        return movie;
    }

     public void setMovie(Movie movie) {
     this.movie = movie;
    }


    public void setMovieTheater(MovieTheater movieTheater) {
        this.movieTheater = movieTheater;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
