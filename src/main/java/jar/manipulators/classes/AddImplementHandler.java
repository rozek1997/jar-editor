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

public class AddImplementHandler extends ButtonEventHandler{

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea) {
        ButtonEventHandler buttonEventHandler = new AddImplementHandler("Add interface to class", treeView, textArea);
        return buttonEventHandler;
    }


    public AddImplementHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }

    /**
     * Sets implemented interfaces
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            if (!textArea.getText().isEmpty()) {
                CtClass[] ctClassList = {TreeBuilder.getClassPool().get(textArea.getText())};
                ctClass.setInterfaces(ctClassList);
            }
        }
        else
            Controller.showError("Cant add interface here");

    }


}
