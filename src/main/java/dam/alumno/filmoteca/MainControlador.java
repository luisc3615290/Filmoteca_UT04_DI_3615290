package dam.alumno.filmoteca;
//
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

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
    public Text txtRating;
    @FXML
    public TextArea txtAreaDescription;
    @FXML
    public Hyperlink hyperlinkDescargar;
    @FXML
    public TableView<Pelicula> tablaPeliculas;
    @FXML
    public TableColumn<Pelicula,String> colId;
    @FXML
    public TableColumn<Pelicula,String> colName;
    @FXML
    public TableColumn<Pelicula,Integer> colYear;
    @FXML
    public TableColumn<Pelicula,String> colDirector;
    public Label lblId;
    public Label lblYear;
    public Label lblGenre;
    public Label lblDirector;
    public Label lblDescription;
    public Button btnNuevo;
    public Button btnModificar;
    public Button btnBorrar;


    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas;
    private int indexSeleccionado;

    public void initialize(){
        panelInvisible();


        hBoxFilmoteca.getStyleClass().add("hboxFilmoteca");
        txtFilmotecaTitulo.getStyleClass().add("txtFilmotecaTitulo");
        txtNombrePelicula.getStyleClass().add("txtNombrePelicula");
        imgPoster.getStyleClass().add("imgPoster");
        txtId.getStyleClass().add("txt");
        txtYear.getStyleClass().add("txt");
        txtDirector.getStyleClass().add("txt");
        txtRating.getStyleClass().add("txt");
        //txtAreaDescription.getStyleClass().add("txt");

        txtNombrePelicula.setText("");
        txtId.setText("");
        txtYear.setText("");
        txtGenre.setText("");
        txtDirector.setText("");
        txtAreaDescription.setText("");
        txtRating.setText("");
        //hyperlinkDescargar.setText("");

        listaPeliculas = datosFilmoteca.getListaPeliculas();
        colId.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<Pelicula,Integer>("year"));
        colDirector.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("director"));
        tablaPeliculas.setItems(listaPeliculas);


        tablaPeliculas.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            if (newValue != null){
                txtId.setText(newValue.getId());
                txtYear.setText(String.valueOf(newValue.getYear()));
                txtNombrePelicula.setText(newValue.getTitle());
                txtDirector.setText(newValue.getDirector());
                //txtGenre.setText(newValue.getGenre());
                txtAreaDescription.setText(newValue.getDescription());
                txtRating.setText(String.valueOf(newValue.getRating()));
                imgPoster.setImage(new Image(newValue.getPoster()));
                panelVisible();
            }
        });
    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Bienvenido al registro de la filmoteca 3615290");
    }

    public void handleNuevaPelicula(ActionEvent actionEvent) {
        Scene escenaModificar = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ModificarPeliculaView.fxml"));
        try{
            escenaModificar = new Scene(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Error al cargar la ventana Crear/Modificar");
            e.printStackTrace();
        }
        Stage st = new Stage();
        st.setScene(escenaModificar);
        st.setTitle("Añadir Película");
        st.showAndWait();

    }

    public void handleModificarPelicula(ActionEvent actionEvent) {
        Scene escenaModificar = null;
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("ModificarPeliculaView.fxml"));
        try{
            escenaModificar = new Scene(fxmlLoader.load());
        }catch(IOException e){
            System.out.println("Error al cargar la ventana Crear/Modificar");
            e.printStackTrace();
        }
        Stage st = new Stage();
        st.setScene(escenaModificar);
        st.setTitle("Editar Película");
        ModificarPelicula controlador = fxmlLoader.getController();
        controlador.setPelicula(tablaPeliculas.getSelectionModel().getSelectedItem());
        st.showAndWait();
    }

    public void handleBorrarPelicula(ActionEvent actionEvent) {
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Borrar Película");
        confirmacion.setHeaderText("Se procederá a borrar este film");
        confirmacion.setContentText("Estás seguro?");
        confirmacion.showAndWait();
        if(confirmacion.getResult()==ButtonType.OK){
            //Se deja status = 0 porque es flujo de salida esperado y la aplicación funciona como se espera
            listaPeliculas.remove(indexSeleccionado);
        }
        else{
            confirmacion.close();
        }
    }

    public void handleCerrar(ActionEvent actionEvent) {
        confirmacionCierreApp();
    }


    private void confirmacionCierreApp(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar App");
        confirmacion.setHeaderText("Se procederá a cerrar App");
        confirmacion.setContentText("Estás seguro?");
        confirmacion.showAndWait();
        if(confirmacion.getResult()==ButtonType.OK){
            //Se deja status = 0 porque es flujo de salida esperado y la aplicación funciona como se espera
            System.exit(0);
        }
        else{
            confirmacion.close();
        }
    }

    private void panelInvisible(){
        lblId.setVisible(false);
        lblYear.setVisible(false);
        lblGenre.setVisible(false);
        lblDirector.setVisible(false);
        lblDescription.setVisible(false);
        //btnNuevo.setVisible(false);
        btnModificar.setVisible(false);
        btnBorrar.setVisible(false);
        txtAreaDescription.setVisible(false);
        lblRating.setVisible(false);
        txtRating.setVisible(false);
        hyperlinkDescargar.setVisible(false);
    }

    private void panelVisible(){
        lblId.setVisible(true);
        lblYear.setVisible(true);
        lblGenre.setVisible(true);
        lblDirector.setVisible(true);
        lblDescription.setVisible(true);
        //btnNuevo.setVisible(false);
        btnModificar.setVisible(true);
        btnBorrar.setVisible(true);
        txtAreaDescription.setVisible(true);
        lblRating.setVisible(true);
        txtRating.setVisible(true);
        hyperlinkDescargar.setVisible(true);
    }
}