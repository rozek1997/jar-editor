package jar.manipulators.constructors;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.*;

import java.util.List;

public class AddConstructorHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea){
        ButtonEventHandler buttonEventHandler=new AddConstructorHandler("Add constructor", treeView, textArea );
        return buttonEventHandler;
    }

    public AddConstructorHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }

    /**
     * Add new constructor to class
     * @throws NotFoundException
     * @throws CannotCompileException
     */

    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            if (!textArea.getText().isEmpty()) {
                CtConstructor ctConstructor = CtNewConstructor.make(textArea.getText(), ctClass);
                ctClass.addConstructor(ctConstructor);
            }
        }
        else
            Controller.showError("Cant add constructor here");

    }

    /**
     * List all constructors in specified class object
     * @return
     * @throws NotFoundException
     */

    @Override
    public List<String> fillList() throws NotFoundException {

       return ListConstructors.fillList(resolvePath());
    }
}
