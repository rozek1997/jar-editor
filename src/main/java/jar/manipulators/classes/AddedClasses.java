package jar.manipulators.classes;

import java.util.LinkedList;
import java.util.List;

public class AddedClasses {

    private List<String> addedClasses;
    private static AddedClasses instance=new AddedClasses();

    public AddedClasses(){
        addedClasses=new LinkedList();
    }

    /**
     *
     * @return singelton of AddedClasses.class
     */
    public static AddedClasses getInstance(){
        return instance;
    }

    /**
     * Add class to list
     * @param classname
     */
    public void addClass(String classname){
        if(addedClasses.contains(classname)) return;
            addedClasses.add(classname);
    }

    /**
     *
     * @return list of added classes
     */
    public List<String> getAddedClasses() {
        return addedClasses;
    }

    /**
     *
     * Removing class from list
     * @param string class to be removed
     */
    public void remove(String string){
        if(addedClasses.contains(string))
            addedClasses.remove(string);

        return;
    }
}
