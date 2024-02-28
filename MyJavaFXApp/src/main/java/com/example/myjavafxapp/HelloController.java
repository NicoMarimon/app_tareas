/* Public class: Es accesible desde cualquier otra clase del programa, independientemente de su paquete.
* Public método o campo: Es accesible desde cualquier otra parte del código, ya sea en la misma clase, en clases del mismo paquete o en clases de paquetes diferentes
*
* Protected class: No se puede aplicar directamente. Solo puede ser aplicado a métodos o campos de una misma clase
* Protected método o campo Accesible en la misma clase, en clases del mismo pquete y también en subclases (heredados) ya sea en el mismo paquete o en otro paquete
* Private void --> solo es necesario acceder a él en la misma clase */


package com.example.myjavafxapp;

import java.io.*;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Button guardar_tarea;

    @FXML
    private Button complete_task_button;

    @FXML
    private Button delete_task;

    @FXML
    private Label ClickMeLabel;

    @FXML
    private Label explaining_label;

    @FXML
    private TextField  text_field_add_task;

    @FXML
    private VBox tareas_vbox;

    private String ruta_archivo = "C:\\Users\\nicol\\IdeaProjects\\MyJavaFXApp\\src\\main\\java\\com\\example\\myjavafxapp\\data.txt";
    private String contenido = "";
    private List<Label> label_list = new ArrayList<>();
    private List<String> label_content_list = new ArrayList<>();
    private boolean deleting_task = false;
    private boolean completing_task = false;

    private Font tamaño_f_tarea = new Font(15);

    public void initialize() {
        label_content_list = leerDeArchivo(ruta_archivo);

        actualiizar_tareas();

        text_field_add_task.setVisible(false);
        guardar_tarea.setVisible(false);
        complete_task_button.setVisible(false);
        delete_task.setVisible(false);

        if (label_content_list.size() > 0) {
            delete_task.setVisible(true);
            complete_task_button.setVisible(true);
        }

        // Agragamos un listener para ver los cambios en el texto
        text_field_add_task.textProperty().addListener((observable, oldValue, newValue) -> {
            String contenidoGuardado = "";
            // El código de aquí dentro se ejecutará cuando el texto cambie
            contenidoGuardado = newValue;
            System.out.println("Contenido guardado: "+ contenidoGuardado);
            contenido = contenidoGuardado;

            if (!deleting_task && !completing_task) {
                if (contenido.length() > 0) {
                    guardar_tarea.setVisible(true);
                }
            }
        });
    }


    @FXML
    protected void add_task_cliked() {
        ClickMeLabel.setText("Introduce una tarea");
        text_field_add_task.setVisible(true);
    }

    @FXML
    protected void save_task_click() {
        if (contenido.length() > 0) {
            ClickMeLabel.setText("Tarea añadida correctamente :)");
            text_field_add_task.setVisible(false);

            guardar_tarea.setVisible(false);

            String new_content = contenido;
            label_content_list.add(new_content);



            actualiizar_tareas();

            text_field_add_task.clear(); // Borrar el contenido del textfield

            complete_task_button.setVisible(true);
            delete_task.setVisible(true);
        }
    }


    // Limpia label_list y tareas_vbox y lo actualiza correctamente
    private void actualiizar_tareas(){
        tareas_vbox.getChildren().clear();
        label_list.clear();
        for (int i = 0; i < label_content_list.size(); i ++) {
            Label nueva_tarea = new Label((i + 1) + ". " + label_content_list.get(i));

            // Se puede personalizar el label
            nueva_tarea.setFont(tamaño_f_tarea);

            // Agregar el nuevo label a label_list y a tareas_vbox
            label_list.add(nueva_tarea);
            tareas_vbox.getChildren().add(nueva_tarea);
        }

        guardar_datos(label_content_list, ruta_archivo);
    }



    @FXML // Botón de eliminar tarea es pulsado
    protected void delete_task_click() {
        if (!(contenido.length() > 0)) {
            explaining_label.setText("Introduce el número de la tarea que deseas eliminar y vuelve a pulsar el botón");
            ClickMeLabel.setText("Introduce el número de la tarea que deseas eliminar");
            text_field_add_task.setVisible(true);
            deleting_task = true;
            complete_task_button.setVisible(false);
        } else {
            boolean wrong_input = true;
            for (int i = 0; i < label_content_list.size(); i ++){
                if (contenido.equals(String.valueOf(i + 1))) {
                    wrong_input = false;
                }
            }

            if (wrong_input) {
                text_field_add_task.clear();
                ClickMeLabel.setText("Introduce el número de la tarea correctamente");
            } else {
                label_content_list.remove(label_content_list.get(Integer.parseInt(contenido) - 1));
                actualiizar_tareas();

                deleting_task = false;
                complete_task_button.setVisible(true);
                text_field_add_task.clear();
                explaining_label.setText("");
                ClickMeLabel.setText("Tarea borrada correctamente");
                text_field_add_task.setVisible(false);

                if (label_content_list.isEmpty()) {
                    complete_task_button.setVisible(false);
                    delete_task.setVisible(false);;
                }
            }
        }
    }


    @FXML // Botón de eliminar tarea es pulsado
    protected void complete_task_click() {
        if (!(contenido.length() > 0)) {
            explaining_label.setText("Introduce el número de la tarea que deseas completar y vuelve a pulsar el botón");
            ClickMeLabel.setText("Introduce el número de la tarea que deseas completar");
            text_field_add_task.setVisible(true);
            completing_task = true;
            delete_task.setVisible(false);
        } else {
            boolean wrong_input = true;
            for (int i = 0; i < label_content_list.size(); i ++){
                if (contenido.equals(String.valueOf(i + 1))) {
                    wrong_input = false;
                }
            }

            if (wrong_input) {
                text_field_add_task.clear();
                ClickMeLabel.setText("Introduce el número de la tarea correctamente");
            } else {
                String cadena = label_content_list.get(Integer.parseInt(contenido) - 1);
                label_content_list.set(Integer.parseInt(contenido) - 1, cadena += " --> COMPLETADA");
                actualiizar_tareas();

                completing_task = false;
                delete_task.setVisible(true);
                text_field_add_task.clear();
                explaining_label.setText("");
                ClickMeLabel.setText("Tarea completada correctamente");
                text_field_add_task.setVisible(false);

            }
        }
    }

    private void print_list(List lista) {
        for (int i = 0; i < lista.size(); i++) {
            System.out.print(lista.get(i) + " ");
        }
    }

    private static void guardar_datos(List <String> datos, String rutaArchivo) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo))) {
            // Código para escribir en el archivo
            for (String dato : datos) {
                writer.println(dato);
            }
            System.out.println("Datos guardados en el archivo.");
        } catch (IOException e) {
            // Manejo de la excepción IOException
            e.printStackTrace();
        }
    }

    private static List<String> leerDeArchivo(String rutaArchivo) {
        List<String> datosLeidos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            // Código para leer del archivo
            String linea;
            while ((linea = reader.readLine()) != null) {
                datosLeidos.add(linea);
            }
            System.out.println("Datos leídos del archivo.");
        } catch (IOException e) {
            // Manejo de la excepción IOException
            e.printStackTrace();
        }
        return datosLeidos;
    }
}

