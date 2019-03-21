package jar.manipulators.field;

import jar.utils.ClassResolver;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ListFields {



    /**
     * List all avaiable fields in class object
     * @param classpath path to class
     * @return list of fields
     * @throws NotFoundException
     */
    public static List<String> fillList(String classpath) throws NotFoundException {
        List<String> list = new LinkedList<>();
        CtClass ctClass = ClassResolver.resolveClass(classpath);
        if(ctClass!=null) {
            List<CtField> fields = Arrays.asList(ctClass.getDeclaredFields());
            for (CtField field : fields)
                list.add(field.getName());

        }
        return list;
    }
}
