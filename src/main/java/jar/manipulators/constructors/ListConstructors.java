package jar.manipulators.constructors;

import jar.utils.ClassResolver;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListConstructors {


    /**
     * List constructor available in class object
     * @param classpath path to class
     * @return list od constructors
     * @throws NotFoundException
     */
    public static List<String> fillList(String classpath) throws NotFoundException {
        List<String> list = new LinkedList<>();
        CtClass ctClass = ClassResolver.resolveClass(classpath);
        if(ctClass!=null) {
            List<CtConstructor> constructors = Arrays.asList(ctClass.getDeclaredConstructors());
            for (CtConstructor constructor : constructors)
                list.add(constructor.getSignature());

        }
        return list;
    }
}
