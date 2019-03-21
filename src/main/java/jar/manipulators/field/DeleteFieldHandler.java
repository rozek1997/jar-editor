package jar.manipulators.field;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeView;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import java.util.List;

public class DeleteFieldHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, ListView<String> listView){
        ButtonEventHandler buttonEventHandler=new DeleteFieldHandler("Delete field", treeView, listView);
        return buttonEventHandler;
    }

    public DeleteFieldHandler(String name, TreeView<String> treeView, ListView<String> listView) {
        super(name, treeView, listView);
    }

    /**
     * Delete selected field form class object
     * @throws NotFoundException
     */
    @Override
    public void manipulate() throws NotFoundException {

        String classpath = resolvePath();
        if (!classpath.isEmpty()) {
            if (classpath.contains(".class")) {
                if (!listView.getSelectionModel().isEmpty()) {
                    String name = listView.getSelectionModel().getSelectedItem();
                    CtClass ctClass = ClassResolver.resolveClass(classpath);
                    CtField fieldToDelete = ctClass.getField(name);
                    ctClass.removeField(fieldToDelete);
                }
                else
                    Controller.showInfo("Choose field from list");
            }
        }
    }


    /**
     *
     * @return list of fields in class
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {

        return ListFields.fillList(resolvePath());
    }
}
