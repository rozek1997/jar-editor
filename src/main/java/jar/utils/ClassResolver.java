package jar.utils;

import javassist.CtClass;
import javassist.NotFoundException;

public class ClassResolver {

    /**
     * Get CtClass object from classpool
     * @param classpath
     * @return CtClass object
     * @throws NotFoundException
     */
    public static CtClass resolveClass(String classpath) throws NotFoundException {

        if (classpath.contains(".class")) {
            classpath = classpath.substring(1, classpath.length() - 6);
            CtClass ctClass = TreeBuilder.getClassPool().getCtClass(classpath);
            return ctClass;
        }

        return null;
    }
}
