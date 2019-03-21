package jar.manipulators.constructors;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.*;

import java.util.List;

public class OverrideConstructorHandler extends ButtonEventHandler {


    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        ButtonEventHandler buttonEventHandler = new OverrideConstructorHandler("Override contructor", treeView, textArea, listView);
        return buttonEventHandler;
    }


    public OverrideConstructorHandler(String name, TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        super(name, treeView, textArea, listView);
    }

    /**
     * Override body of constructor selected from list view
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {

        String classpath = resolvePath();
        if (!classpath.isEmpty()) {
            if (classpath.contains(".class")) {
                if (!listView.getSelectionModel().isEmpty()) {
                    String name = listView.getSelectionModel().getSelectedItem();
                    CtClass ctClass = ClassResolver.resolveClass(classpath);
                    CtConstructor constructorToOverride = ctClass.getConstructor(name);
                    if(!textArea.getText().isEmpty()) {
                        constructorToOverride.setBody(textArea.getText());
                    }
                }
                else
                    Controller.showInfo("Choose constructor from list");
            }
        }

    }


    /**
     * List all connstructors signature from class object
     * @return list of constructors
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {
        return ListConstructors.fillList(resolvePath());
    }
}
