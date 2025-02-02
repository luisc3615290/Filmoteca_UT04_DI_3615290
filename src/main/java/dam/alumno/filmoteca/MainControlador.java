package dam.alumno.filmoteca;
//
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.binding.Bindings;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

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
        //Styles
        hBoxFilmoteca.getStyleClass().add("hboxFilmoteca");
        txtFilmotecaTitulo.getStyleClass().add("txtFilmotecaTitulo");
        txtNombrePelicula.getStyleClass().add("txtNombrePelicula");
        imgPoster.getStyleClass().add("imgPoster");
        txtId.getStyleClass().add("txt");
        txtYear.getStyleClass().add("txt");
        txtDirector.getStyleClass().add("txt");
        txtRating.getStyleClass().add("txt");
        //Empty contents
        txtNombrePelicula.setText("");
        txtId.setText("");
        txtYear.setText("");
        txtGenre.setText("");
        txtDirector.setText("");
        txtAreaDescription.setText("");
        txtRating.setText("");
        //hyperlinkDescargar.setText("");

        //Llenar la tabla izquierad
        listaPeliculas = datosFilmoteca.getListaPeliculas();
        colId.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<Pelicula,Integer>("year"));
        colDirector.setCellValueFactory(new PropertyValueFactory<Pelicula,String>("director"));
        tablaPeliculas.setItems(listaPeliculas);


        tablaPeliculas.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) ->{
            if (newValue != null){
                panelVisible();
                //listaPeliculas.remove(indexSeleccionado);
                txtId.setText(newValue.getId());
                txtYear.setText(String.valueOf(newValue.getYear()));
                txtNombrePelicula.textProperty().bind(newValue.titleProperty());
                txtDirector.textProperty().bind(newValue.directorProperty());
                txtAreaDescription.textProperty().bind(newValue.descriptionProperty());
                txtRating.textProperty().bind(Bindings.createStringBinding(
                        () -> String.format("%.1f", newValue.ratingProperty().get()), // Convierte Float a String con 1 decimal
                        newValue.ratingProperty()
                ));
                imgPoster.setImage(new Image(newValue.getPoster()));
            }else {
                txtId.textProperty().unbind();
                txtYear.textProperty().unbind();
                txtNombrePelicula.textProperty().unbind();
                txtDirector.textProperty().unbind();
                txtAreaDescription.textProperty().unbind();
                txtRating.textProperty().unbind();
                imgPoster.accessibleTextProperty().unbind();
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
            indexSeleccionado = tablaPeliculas.getSelectionModel().getSelectedIndex();
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


    public void handleDownload(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos", "*.jpg", "*.png", "*.svg"));
        Stage stage = (Stage) hyperlinkDescargar.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            try {
                URL url = new URL(hyperlinkDescargar.getText());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                // Leer el archivo desde la URL
                try (InputStream inputStream = connection.getInputStream();
                     FileOutputStream outputStream = new FileOutputStream(file)) {

                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "¡Descarga completa!", javafx.scene.control.ButtonType.OK);
                    alert.setTitle("Descarga Completada");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Hubo un error al intentar descargar el archivo.", javafx.scene.control.ButtonType.OK);
                    alert.setTitle("Error de descarga");
                    alert.setHeaderText(null);
                    alert.showAndWait();
                }
            } catch (ProtocolException e) {
                throw new RuntimeException(e);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }


    private void confirmacionCierreApp(){
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Cerrar App");
        confirmacion.setHeaderText("Se procederá a cerrar App");
        confirmacion.setContentText("Estás seguro?");
        confirmacion.showAndWait();
        if(confirmacion.getResult()==ButtonType.OK){
            //Se deja status = 0 porque es flujo de salida esperado y la aplicación funciona como se espera
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                objectMapper.writeValue(new File("datos/peliculas2.json"),listaPeliculas);
                System.exit(0);
            }catch (IOException e) {
                System.out.println("ERROR no se ha podido guardar los datos de la aplicación");
                e.printStackTrace();
            }
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