package core.function;

import core.annotation.Also;
import core.exception.MultipleAlsoException;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

final public class AlsoMethodFinder {

    public static Optional<Method> findMethod(Method[] invokerMethods, Method trierMethod){
        List<Method> alsoList = getAlsoMethodList(invokerMethods, trierMethod);
        assertExceptions(alsoList);
        if ( alsoList.size() > 0 )
            return Optional.of(alsoList.get(0));
        else
            return Optional.empty();
    }

    private static List<Method> getAlsoMethodList(Method[] methods, Method trierMethod) {
        return Arrays.stream(methods)
                .filter( method -> MethodUtils.isAnnotatedWith(method, Also.class) )
                .filter( method -> MethodUtils.hasReturnTypeOf(method, void.class) )
                .filter( method -> MethodUtils.haveSameParameterTypesUntil(method, trierMethod, trierMethod.getParameterCount()) )
                .collect( Collectors.toList() );
    }

    private static void assertExceptions(List<Method> alsoList) {
        if ( alsoList.size() > 1 )
            throw new MultipleAlsoException("Multiple @" + Also.class.getSimpleName() + " are not allowed.");
    }
}
