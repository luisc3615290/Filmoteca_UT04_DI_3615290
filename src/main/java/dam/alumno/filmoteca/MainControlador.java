package dam.alumno.filmoteca;
//
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MainControlador {
    @FXML
    public Label welcomeText;
    public HBox hBoxFilmoteca;
    @FXML
    public Text txtFilmotecaTitulo;
    @FXML
    public Text txtNombrePelicula;
    @FXML
    public Text txtId;
    @FXML
    public Text txtYear;
    @FXML
    public Text txtGenre;
    @FXML
    public Text txtDirector;
    @FXML
    public ImageView imgPoster;
    @FXML
    public Label lblRating;
    @FXML
    public TextArea txtAreaDescription;
    @FXML
    public TableView<Pelicula> tablaPeliculas;
    @FXML
    public TableColumn<Pelicula,Integer> colId;
    @FXML
    public TableColumn<Pelicula,String> colName;
    @FXML
    public TableColumn<Pelicula,Integer> colYear;
    @FXML
    public TableColumn<Pelicula,String> colDirector;
    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas;
    private int indexSeleccionado;

    public void initialize(){
        hBoxFilmoteca.getStyleClass().add("hboxFilmoteca");
        txtFilmotecaTitulo.getStyleClass().add("txtFilmotecaTitulo");
        listaPeliculas = datosFilmoteca.getListaPeliculas();
        colId.setCellValueFactory(new PropertyValueFactory<Pelicula,Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<Pelicula,Integer>("year"));
        colDirector.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("director"));
        System.out.println(listaPeliculas);
        tablaPeliculas.setItems(listaPeliculas);

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bienvenido al registro de la filmoteca 3615290");
    }
}