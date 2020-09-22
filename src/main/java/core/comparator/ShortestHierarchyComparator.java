package core.comparator;

import core.function.ClassUtils;
import core.function.MethodUtils;
import java.lang.reflect.Method;
import java.util.Comparator;

public class ShortestHierarchyComparator implements Comparator<Method> {

    private final Class<?> assertedClass;

    public ShortestHierarchyComparator(Class<?> assertedClass) {
        this.assertedClass = assertedClass;
    }

    @Override
    public int compare(Method o1, Method o2) {
        //if o1 is closer to throwable that o2, then we get negative => lower
        //we get from least generic type to lowest position
        return ClassUtils.getHierarchyLength(assertedClass, MethodUtils.getLastParameterType(o1))
                - ClassUtils.getHierarchyLength(assertedClass, MethodUtils.getLastParameterType(o2));
    }
}
