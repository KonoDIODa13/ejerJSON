package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Pelicula;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utils.AlertUtils;


import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public static ObjectMapper JSON_MAPPER;

    Pelicula peliculaSeleccionada;

    @FXML
    private ListView<Pelicula> LVPeliculas;

    @FXML
    private TextField TFTitulo, TFFecha, TFDirector, TFGenero;

    @FXML
    public void importar(ActionEvent event) {
        try {
            //convierte un fichero JSON a un arrayList    Deserializacion
            List<Pelicula> peliculas =
                    JSON_MAPPER.readValue(new File("src/main/resources/data/peliculas.json"),
                            JSON_MAPPER.getTypeFactory().constructCollectionType
                                    (List.class, Pelicula.class));
            LVPeliculas.setItems(FXCollections.observableList(peliculas));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JSON_MAPPER = new ObjectMapper();
        LVPeliculas.getItems().clear();

    }

    public void seleccionarPelicula(MouseEvent mouseEvent) {
        try {
            peliculaSeleccionada = LVPeliculas.getSelectionModel().getSelectedItem();
            cargarPelicula(peliculaSeleccionada);
        } catch (NullPointerException e) {
            AlertUtils.mostrarError("No has seleccionado ningun dato.\nPrimero has de pulsar el boton de importar y luego seleccionar una de las pelis");
        }
    }

    public void cargarPelicula(Pelicula peliculaSeleccionada) {
        TFTitulo.setText(peliculaSeleccionada.getTitulo());
        TFFecha.setText(peliculaSeleccionada.getFecha());
        TFDirector.setText(peliculaSeleccionada.getDirector());
        TFGenero.setText(peliculaSeleccionada.getGenero());
    }
}
