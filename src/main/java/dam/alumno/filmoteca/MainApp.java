package dam.alumno.filmoteca;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainApp extends Application {
    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas = datosFilmoteca.getListaPeliculas();
    private final int prefWidth = 1000;
    private final int prefHeight = 600;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), prefWidth, prefHeight);
        stage.setTitle("Bienvenido al registro de la filmoteca 3615290!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(this.getClass().getResource("/img/video.png").toString()));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void init() throws Exception{
        super.init();
        ObjectMapper filmotecaMapper = new ObjectMapper();
        try {
            File archivoFilmoteca = new File("datos/peliculas.json");
            List<Pelicula> listaFilmoteca = filmotecaMapper.readValue(archivoFilmoteca, new TypeReference<List<Pelicula>>() {});
            listaPeliculas.addAll(listaFilmoteca);
        } catch (IOException e){
            System.out.println("ERROR al cargar los datos. La aplicación no puede iniciarse");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop() {
        //System.out.println(listaPeliculas);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("datos/peliculas2.json"),listaPeliculas);
        }catch (IOException e) {
            System.out.println("ERROR no se ha podido guardar los datos de la aplicación");
            e.printStackTrace();
        }

    }
}



