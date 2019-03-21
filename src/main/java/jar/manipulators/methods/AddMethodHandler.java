package jar.manipulators.methods;

import controllers.Controller;
import jar.utils.ClassResolver;
import jar.utils.ButtonEventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.*;

import java.io.IOException;
import java.util.List;

public class AddMethodHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea) {
        ButtonEventHandler buttonEventHandler = new AddMethodHandler("Add method", treeView, textArea);
        return buttonEventHandler;
    }


    public AddMethodHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }

    /**
     * Add method to class object
     * @throws NotFoundException
     * @throws CannotCompileException
     * @throws IOException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException, IOException {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            if (!textArea.getText().isEmpty()) {
                String code = textArea.getText();
                CtMethod ctMethod = CtMethod.make(code, ctClass);
                ctClass.addMethod(ctMethod);
            }
        }
        else
            Controller.showError("Cant add method here");

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
