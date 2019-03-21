package jar.manipulators.methods;

import jar.utils.ClassResolver;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListMethods {


    /**
     * List all avaiable methods in class object
     * @param classpath path to class object
     * @return list of methods in class object
     * @throws NotFoundException
     */
    public static List<String> fillList(String classpath) throws NotFoundException {
        List<String> list=new LinkedList<String>();
        CtClass ctClass = ClassResolver.resolveClass(classpath);
        if(ctClass!=null) {
            List<CtMethod> methods = Arrays.asList(ctClass.getDeclaredMethods());
            for (CtMethod met : methods) {
                list.add(met.getName());
            }
        }

        return list;
    }


}
