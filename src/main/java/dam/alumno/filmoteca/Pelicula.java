package dam.alumno.filmoteca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pelicula {
    private final StringProperty id = new SimpleStringProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty director = new SimpleStringProperty();
    private final DoubleProperty rating = new SimpleDoubleProperty();
    private final StringProperty poster = new SimpleStringProperty();
    //private final StringProperty genre = new SimpleStringProperty();


    public Pelicula() {

    }

    public Pelicula(String id, String title, int year, String description, String director, String genre, double rating, String poster) {
        this.id.set(id);
        this.title.set(title);
        this.year.set(year);
        this.description.set(description);
        this.director.set(director);
        /*
        this.genre.set(genre);
         */
        this.rating.set(rating);
        this.poster.set(poster);
    }

    public String getId() {return id.get();}
    public String getTitle() {return title.get();}
    public int getYear() {return year.get();}
    public String getDirector() {return director.get();}
    /*
    public String getGenre() {return genre.get();}
     */
    public String getDescription() {return description.get();}
    public double getRating() {return rating.get();}
    public String getPoster() {return poster.get();}


    public void setId(String id) {this.id.set(id);}
    public void setTitle(String title) {this.title.set(title);}
    public void setYear(int year) {this.year.set(year);}
    public void setDescription(String description) {this.description.set(description);}
    public void setDirector(String director) {this.director.set(director);}
    /*
    public void setGenre(String genre) {this.genre.set(genre);}
    */
    public void setRating(float rating) {this.rating.set(rating);}
    public void setPoster(String poster) {this.poster.set(poster);}
    public StringProperty idProperty() {return id; }
    public StringProperty titleProperty() {return title;}
    public IntegerProperty yearProperty() {return year;}
    public StringProperty descriptionProperty() {return description;}
    public StringProperty directorProperty() {return director;}
    /*
    public StringProperty genreProperty() {return genre;}
    */
    public DoubleProperty ratingProperty() {return rating;}
    public StringProperty posterProperty() {return poster;}


    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", title=" + title +
                ", year=" + year +
                ", description=" + description +
                ", director=" + director +
                //", genre=" + genre +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
