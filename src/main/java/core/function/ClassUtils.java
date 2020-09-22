package core.function;

import core.exception.internal.InternalNotRelatedException;

final public class ClassUtils {

    public static int getHierarchyLength(Class<?> assertedClass, Class<?> superClass){
        if(!isSameOrSubClass(assertedClass, superClass))
            throw new InternalNotRelatedException(assertedClass.getSimpleName() + " is not a subclass of" + superClass.getSimpleName());
        int hierarchyLength = 0;
        for (Class<?> clazz = assertedClass; isSameOrSubClass(clazz, superClass); clazz = clazz.getSuperclass()) {
            hierarchyLength += 1;
        }
        return hierarchyLength;
    }

    public static boolean isSameOrSubClass(Class<?> assertClass, Class<?> superClass) {
        //is superClass a super class of assertClass
        return superClass.isAssignableFrom(assertClass);
    }
}
