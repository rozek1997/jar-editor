package controllers;

import jar.manipulators.classes.*;
import jar.utils.ButtonEventHandler;
import jar.manipulators.constructors.AddConstructorHandler;
import jar.manipulators.constructors.DeleteConstructorHandler;
import jar.manipulators.constructors.OverrideConstructorHandler;
import jar.manipulators.field.AddFieldHandler;
import jar.manipulators.field.DeleteFieldHandler;
import jar.manipulators.methods.*;
import jar.manipulators.packages.AddPackageHandler;
import jar.manipulators.packages.DeletePackageHandler;
import jar.utils.JarSaver;
import jar.utils.TreeBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javassist.NotFoundException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    @FXML
    private ComboBox<ButtonEventHandler> chooseActionComboBox;

    @FXML
    private TreeView<String> jarTreeView;

    @FXML
    private ListView<String> listView;

    @FXML
    private Button chooseJarButton;

    @FXML
    private Button okButton;

    @FXML
    private TextArea codeArea;


    /**
     * changing attributes listed in listView after tree item was clicked
     * @param event
     */

    @FXML
    public void treeViewClicked(MouseEvent event) {

        if (jarTreeView.getSelectionModel().getSelectedItem() != null && jarTreeView.getSelectionModel().getSelectedItem().getValue().contains(".class")) {
            int temp = chooseActionComboBox.getSelectionModel().getSelectedIndex();
            chooseActionComboBox.getSelectionModel().clearSelection();
            chooseActionComboBox.getSelectionModel().select(temp);

        }
    }

    /**
     * Man chooseJar button, init combo box list with specified jar action
     * @param event
     */
    @FXML
    public void chooseFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JAR files", "*.jar"));
        File jarFile = fileChooser.showOpenDialog(null);
        String path;
        path = jarFile.getAbsolutePath();
        System.out.println(path.substring(0, path.lastIndexOf(File.separator)));
        try {
            if (jarFile == null) throw new IOException();
            TreeBuilder loader = TreeBuilder.initialize(path, jarTreeView);
            loader.loadClasses();
            loader.setTree();
            chooseJarButton.setVisible(false);
            initComboBox();
            okButton.fire();
        } catch (NotFoundException | IOException e) {
            showError(e.getMessage());
        }

    }


    /**
     * handle action specified in combobox
     */
    @FXML
    public void okButtonListener() {


        okButton.setOnAction(event -> {
                    ButtonEventHandler handler = chooseActionComboBox.getValue();
                    if (handler != null) {
                        handler.handle(event);
                    }
                    int temp = chooseActionComboBox.getSelectionModel().getSelectedIndex();
                    chooseActionComboBox.getSelectionModel().clearSelection();
                    chooseActionComboBox.getSelectionModel().select(temp);
                }
        );

    }

    /**
     * Save jar to specified path. Using multithreading->saving jar is long action.
     * DO NOT add .jar extension when saving
     */

    @FXML
    public void saveJar() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            Thread thread = new Thread(new JarSaver(file.getAbsolutePath()));
            thread.start();

        }
        else
            showError("Cant save file");

    }

    /**
     * init combo box with buttoneventhandler list, adding change listener
     */

    public void initComboBox() {
        chooseActionComboBox.setItems(FXCollections.observableArrayList(initComboBoxItems()));
        chooseActionComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                    ObservableList<String> list = FXCollections.observableArrayList();
                    list.clear();
                    listView.getItems().clear();
                    if (newValue != null && jarTreeView.getSelectionModel().getSelectedItem() != null) {
                        try {
                            listView.getItems().clear();
                            list.addAll(newValue.fillList());
                            listView.setItems(list);
                        } catch (NotFoundException e) {
                            showError(e.getMessage());
                        }

                    }
                }
        );
    }


    public List<ButtonEventHandler> initComboBoxItems() {

        List<ButtonEventHandler> list = new LinkedList<>();
        list.add(AddFieldHandler.getDefault(jarTreeView, codeArea));
        list.add(AddMethodHandler.getDefault(jarTreeView, codeArea));
        list.add(AddConstructorHandler.getDefault(jarTreeView, codeArea));
        list.add(DeleteFieldHandler.getDefault(jarTreeView, listView));
        list.add(DeleteMethodHandler.getDefault(jarTreeView, listView));
        list.add(DeleteConstructorHandler.getDefault(jarTreeView, listView));
        list.add(OverrideConstructorHandler.getDefault(jarTreeView, codeArea, listView));
        list.add(OverrideMethodHandler.getDefault(jarTreeView, codeArea, listView));
        list.add(InsertBeginningHandler.getDefault(jarTreeView, codeArea, listView));
        list.add(InsertEndHandler.getDefault(jarTreeView, codeArea, listView));
        list.add(AddClassHandler.getDefault(jarTreeView, codeArea));
        list.add(DeleteClassHandler.getDefault(jarTreeView));
        list.add(AddPackageHandler.getDefault(jarTreeView, codeArea));
        list.add(DeletePackageHandler.getDefault(jarTreeView));
        list.add(AddInterfaceHandler.getDefault(jarTreeView, codeArea));
        list.add(AddSuperClassHandler.getDefault(jarTreeView, codeArea));
        list.add(AddImplementHandler.getDefault(jarTreeView, codeArea));

        return list;
    }

    /**
     * show error dialog
     * @param reason text for display in error dialog
     */

    public static void showError(String reason) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText(reason);
        alert.showAndWait();
    }

    /**
     * show info dialog
     * @param info text for display in info dialog
     */
    public static void showInfo(String info) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText(info);

        alert.showAndWait();

    }


}
