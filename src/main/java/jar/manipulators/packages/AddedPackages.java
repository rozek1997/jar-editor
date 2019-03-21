package jar.manipulators.packages;

import java.util.LinkedList;
import java.util.List;

public class AddedPackages {

    private List<String> addedPackages;
    private static AddedPackages instance = new AddedPackages();

    /**
     * Create list of added packages
     */
    public AddedPackages() {
        addedPackages = new LinkedList();
    }

    /**
     * Get instance of singleton AddedPackages object
     *
     * @return AddedPackages object
     */
    public static AddedPackages getInstance() {
        return instance;
    }

    /**
     * Adding package to list of packages
     *
     * @param classname path to package
     */
    public void addPackage(String classname) {
        if (addedPackages.contains(classname))
            return;
        addedPackages.add(classname);
    }

    /**
     * List all added packages
     *
     * @return list of packages
     */
    public List<String> getAddedPackagess() {
        return addedPackages;
    }

    /**
     * Remove package from lsit
     *
     * @param string package to be removed
     */
    public void remove(String string) {
        if (addedPackages.contains(string))
            addedPackages.remove(string);
    }
}
