package es.udc.paproject.backend.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movie {

    private Long id;

    private String Title;

    private String Summary;

    private Long Duration;

    public Movie(){}

    public Movie(Long id, String Title, String Summary, Long Duration){
        this.id = id;
        this.Title = Title;
        this.Summary = Summary;
        this.Duration = Duration;
    }

    public Movie(String Title, String Summary, Long Duration){
        this.Title = Title;
        this.Summary = Summary;
        this.Duration = Duration;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTitle(){
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }


    public String getSummary(){
        return Summary;
    }

    public void setSummary(String summary){
        this.Summary = summary;
    }

    public Long getDuration() {
        return Duration;
    }

    public void setDuration(Long duration) {
        this.Duration = duration;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                '}'+"\n";
    }

}

