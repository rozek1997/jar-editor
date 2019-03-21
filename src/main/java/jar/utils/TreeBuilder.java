package jar.utils;

import jar.manipulators.classes.AddedClasses;
import jar.manipulators.packages.AddedPackages;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import javassist.ClassPool;
import javassist.NotFoundException;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class TreeBuilder {


    private static ClassPool classPool;
    private String path;
    private TreeView<String> treeView;
    private static TreeBuilder instance;


    /**
     *
     * @return singeton instance of TreeBuilder class
     */
    public static TreeBuilder getInstance(){
        return instance;
    }

    public static TreeBuilder initialize(String path, TreeView<String> treeView) throws NotFoundException {

         instance=new TreeBuilder(path, treeView);
         return instance;
    }

    public TreeBuilder(String path, TreeView<String> treeView) throws NotFoundException {

        classPool = ClassPool.getDefault();
        this.path = path;
        this.treeView = treeView;
        ClassPool.doPruning = false;
        classPool.insertClassPath(path);
        classPool.appendClassPath(path);
        classPool.appendSystemPath();
    }

    /**
     * List all classes in jar file
     * @return list of all classes in jar file
     * @throws NotFoundException
     * @throws IOException
     */
    public  List<String> loadClasses() throws NotFoundException, IOException {


        JarFile jarFile = new JarFile(path);
        Enumeration<JarEntry> e = jarFile.entries();


        List<String> classList = new LinkedList<>();

        while (e.hasMoreElements()) {
            JarEntry je = e.nextElement();
            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                continue;
            }

            String className = je.getName().substring(0, je.getName().length() - 6);
            className = className.replace('/', '.');
            String ctClass = classPool.get(className).getName();
            ctClass=ctClass.replace('.','/');
            ctClass+=".class";
            classList.add(ctClass);


        }

        for (String mClass: AddedClasses.getInstance().getAddedClasses()) {
            classList.add(mClass.replace('.','/') + ".class");
        }

        return classList;
    }


    /**
     * Build tree view
     * @throws IOException
     * @throws NotFoundException
     */
    public void setTree() throws IOException, NotFoundException {
        List<String> paths;
        paths = loadClasses();
        for (String mPackages: AddedPackages.getInstance().getAddedPackagess()) {
            paths.add(mPackages.replace('.','/'));
        }
        TreeItem<String> root = new TreeItem<>("JAR");
        root.setExpanded(true);
        treeView.setRoot(root);
        for (String path : paths) {
            TreeItem<String> current = root;
            for (String component : path.split("\\/")) {
                current = createTreeView(current, component);
            }
        }

    }

    /**
     *
     * Build new item in tree view
     * @param parent item in tree view
     * @param value path to new item in tree view
     * @return new tree item
     */
    private TreeItem<String> createTreeView(TreeItem<String> parent, String value) {

        for (TreeItem<String> child : parent.getChildren()) {
            if (value.equals(child.getValue())) {
                return child;
            }
        }
        TreeItem<String> newChild = new TreeItem<>(value);
        newChild.setExpanded(true);
        parent.getChildren().add(newChild);
        return newChild;
    }

    /**
     *
     * @return ClassPool object
     */
    public static ClassPool getClassPool() {
        return classPool;
    }

    /**
     *
     * @return path to opened jar file
     */
    public String getPath() {
        return path;
    }
}
