package jar.manipulators.classes;

import controllers.Controller;
import jar.utils.ButtonEventHandler;
import jar.utils.ClassResolver;
import jar.utils.TreeBuilder;
import javafx.scene.control.TreeView;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.List;

public class DeleteClassHandler extends ButtonEventHandler {

    public static ButtonEventHandler getDefault(TreeView<String> treeView){
        ButtonEventHandler buttonEventHandler=new DeleteClassHandler("Delete class/interface", treeView);
        return buttonEventHandler;
    }

    public DeleteClassHandler(String name, TreeView<String> treeView) {
        super(name, treeView);
    }

    /**
     * Delete only class added by user
     * @throws NotFoundException
     * @throws IOException
     */
    @Override
    public void manipulate() throws NotFoundException, IOException {

        String classpath=resolvePath();
        if(classpath.contains(".class")) {
            CtClass ctClass = ClassResolver.resolveClass(classpath);
            ctClass.detach();
            classpath = classpath.substring(1, classpath.length() - 6);
            AddedClasses.getInstance().remove(classpath);
            TreeBuilder.getInstance().setTree();
        }
        else
            Controller.showError("Cant be deleted");

    }

    /**
     *
     * @return added class list
     */
    @Override
    public List<String> fillList() {
        return AddedClasses.getInstance().getAddedClasses();
    }


}
