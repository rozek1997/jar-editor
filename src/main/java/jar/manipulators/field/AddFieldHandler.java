package jar.manipulators.field;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javassist.*;

import java.util.List;

public class AddFieldHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView, TextArea textArea){
        ButtonEventHandler buttonEventHandler=new AddFieldHandler("Add field", treeView, textArea);
        return buttonEventHandler;
    }


    public AddFieldHandler(String name, TreeView<String> treeView, TextArea textArea) {
        super(name, treeView, textArea);
    }


    /**
     * Add field to class object
     * @throws NotFoundException
     * @throws CannotCompileException
     */
    @Override
    public void manipulate() throws NotFoundException, CannotCompileException {
        String classpath = resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            if (!textArea.getText().isEmpty()) {
                CtField ctField = CtField.make(textArea.getText(), ctClass);
                ctClass.addField(ctField);

            }
        }
        else
            Controller.showError("Cant add field here");


    }


    /**
     *
     * @return list of fileds in class
     * @throws NotFoundException
     */
    @Override
    public List<String> fillList() throws NotFoundException {

       return ListFields.fillList(resolvePath());
    }
}
