package dam.alumno.filmoteca;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class ModificarPelicula {
    public Text txtTituloVentana;
    public Label lblNombrePeli;
    public Label lblYearPeli;
    public Label lblDescripcionPeli;
    public Label lblRatingPeli;
    public Label lblUrlPoster;
    public Text txtNotaRating;
    public Slider sldSliderRating;
    public TextArea inputDescriptionPeli;
    public TextField inputNombre;
    public TextField inputYear;
    public ImageView imgViewPoster;
    public Label lblDirector;
    public TextField inputUrlPoster;
    public TextField inputDirector;

    private DatosFilmoteca datosFilmotecaNew = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculasNew = datosFilmotecaNew.getListaPeliculas();

    public void initialize(){
        txtTituloVentana.getStyleClass().add("txtTituloVentana");
        txtTituloVentana.setText("Añadir Film");

        sldSliderRating.setMin(0.0);
        sldSliderRating.setMax(10.0);
        sldSliderRating.setShowTickMarks(true);
        sldSliderRating.setBlockIncrement(0.5);
        sldSliderRating.setMajorTickUnit(2.0);
        sldSliderRating.setShowTickLabels(true);


    }
    public void handlerGetRating(MouseEvent mouseDragEvent) {
        String formattedValue = String.format("%.1f",sldSliderRating.getValue());
        txtNotaRating.setText(formattedValue);
    }

    public void handlerAceptar(ActionEvent actionEvent) {
        //Añadir al TableView y al resto que cuando se añade película hay que mostrarlo en pantalla
        Pelicula PeliculaNew = new Pelicula();

        float rating = (float) sldSliderRating.getValue();
        DecimalFormat ratingFloat =  new DecimalFormat("#.#");
        float formattedRating = Float.parseFloat(ratingFloat.format(rating));
        PeliculaNew.setTitle(inputNombre.getText());
        PeliculaNew.setYear(Integer.parseInt(inputYear.getText()));
        PeliculaNew.setDirector(inputDirector.getText());
        PeliculaNew.setDescription(inputDescriptionPeli.getText());
        PeliculaNew.setId(String.valueOf(DatosFilmoteca.getSize()+1));
        PeliculaNew.setPoster(inputUrlPoster.getText());
        PeliculaNew.setRating(formattedRating);



        listaPeliculasNew.add(PeliculaNew);

        Stage stageCerrar = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
        stageCerrar.close();
    }

    public void handlerCancelar(ActionEvent actionEvent) {
        Stage stageCerrar = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
        stageCerrar.close();
    }

    public void setPelicula(Pelicula pelicula){
        if (pelicula!=null) {
            txtTituloVentana.setText("Modificar Film");
            inputNombre.setText(pelicula.getTitle());
            inputYear.setText(String.valueOf(pelicula.getYear()));
            inputDirector.setText(pelicula.getDirector());
            inputUrlPoster.setText(pelicula.getPoster());
            inputDescriptionPeli.setText(pelicula.getDescription());
            //txtNotaRating.setText(String.valueOf(pelicula.getRating()));
            txtNotaRating.setVisible(true);
            sldSliderRating.setValue(pelicula.getRating());
            //imgViewPoster.setImage(new Image(pelicula.getPoster()));
            float rating = (float) sldSliderRating.getValue();
            DecimalFormat ratingFloat =  new DecimalFormat("#.#");
            float formattedRating = Float.parseFloat(ratingFloat.format(rating));
            txtNotaRating.setText(String.valueOf(formattedRating));

        }
    }


}
