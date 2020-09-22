package core.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final public class ObjectUtils {

    public static Object[] append(final Object[] arguments, Object argument){
        int newSize = arguments.length + 1;
        List<Object> newArguments = new ArrayList<>(newSize);
        newArguments.addAll(Arrays.asList(arguments));
        newArguments.add(argument);
        return newArguments.toArray(new Object[newSize]);
    }
}
