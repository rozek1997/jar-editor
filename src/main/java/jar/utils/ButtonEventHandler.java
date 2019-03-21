package jar.utils;


import controllers.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javassist.CannotCompileException;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public abstract class ButtonEventHandler implements EventHandler<ActionEvent> {

    protected String name;
    protected TreeView<String> treeView;
    protected TextArea textArea;
    protected ListView<String> listView;


    public ButtonEventHandler(String name) {
        this.name = name;
    }

    public ButtonEventHandler(String name, TreeView<String> treeView) {
        this.name = name;
        this.treeView = treeView;
    }

    public ButtonEventHandler(String name, ListView<String> listView) {
        this.name = name;
        this.listView = listView;
    }

    public ButtonEventHandler(String name, TreeView<String> treeView, TextArea textArea) {
        this.name = name;
        this.treeView = treeView;
        this.textArea = textArea;
    }

    public ButtonEventHandler(String name, TreeView<String> treeView, ListView<String> listView) {
        this.name = name;
        this.treeView = treeView;
        this.listView = listView;
    }

    public ButtonEventHandler(String name, TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        this.name = name;
        this.treeView = treeView;
        this.textArea = textArea;
        this.listView = listView;
    }


    /**
     *  Invoke manipulate method
     * @param event which handle button clicked event
     */
    @Override
    public void handle(ActionEvent event) {
        try {
            manipulate();
        } catch (NotFoundException e) {
            Controller.showError(e.getMessage());
        } catch (CannotCompileException e) {
            Controller.showError(e.getReason());
        } catch (IOException e) {
            Controller.showError(e.getMessage());
        }

    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * manipulate jar package
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    public abstract void manipulate() throws NotFoundException, CannotCompileException, IOException;


    /**
     *
     * @return path for selected item in treeview
     */
    public String resolvePath() {
        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
             item != null && !item.getValue().equals("JAR"); item = item.getParent()) {
            pathBuilder.insert(0, item.getValue());
            pathBuilder.insert(0, ".");
        }
        String path = pathBuilder.toString();
        return path;
    }

    /**
     *
     * @return list of attributes to display in list view
     * @throws NotFoundException
     */

    public List<String> fillList() throws NotFoundException {

        List<String> list=new LinkedList<>();
        return list;
    }

    public void refreshListView(List<String> list){
        if(listView==null) return;
        listView.getItems().clear();
        listView.setItems(FXCollections.observableArrayList(list));
    }




}
