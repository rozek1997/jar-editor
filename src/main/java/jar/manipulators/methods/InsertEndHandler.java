package jar.manipulators.methods;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.List;

public class InsertEndHandler extends ButtonEventHandler{



    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        ButtonEventHandler buttonEventHandler = new InsertEndHandler("Insert code at the end of method", treeView, textArea, listView);
        return buttonEventHandler;
    }

    public InsertEndHandler(String name, TreeView<String> treeView, TextArea textArea, ListView<String> listView) {
        super(name, treeView, textArea, listView);
    }


    /**
     * Insert code at the end of selected method
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
                    CtMethod methodToOverride = ctClass.getDeclaredMethod(name);
                    if(!textArea.getText().isEmpty()) {
                        methodToOverride.insertAfter(textArea.getText());
                    }
                }
                else
                    Controller.showInfo("Choose method from list");
            }
        }

    }


    /**
     *
     * @return list of methods in class object
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {
        return ListMethods.fillList(resolvePath());
    }
}
