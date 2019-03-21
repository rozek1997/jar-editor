package jar.manipulators.classes;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import jar.utils.TreeBuilder;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

public class AddSuperClassHandler extends ButtonEventHandler {


    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea) {
        ButtonEventHandler buttonEventHandler = new AddSuperClassHandler("Add superclass", treeView, textArea);
        return buttonEventHandler;
    }


    public AddSuperClassHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }

    /**
     * Add super class to class object
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            if (!textArea.getText().isEmpty()) {
                ctClass.setSuperclass(TreeBuilder.getClassPool().get(textArea.getText()));
                refreshListView(fillList());
            }
        }
        else
            Controller.showError("Cant add super class here");

    }




}

