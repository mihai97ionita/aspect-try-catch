package core.function;

import core.annotation.Catch;
import core.exception.DuplicatedCatcherException;
import core.exception.CatcherNotFoundException;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

final public class CatherMethodFinder {

    public static List<Method> findMethods(Method[] invokerMethods, Method trierMethod){
        List<Method> catherList = getCatherMethodsList(invokerMethods, trierMethod);
        assertExceptions(catherList);
        return catherList;
    }

    private static List<Method> getCatherMethodsList(Method[] methods, Method trierMethod) {
        return Arrays.stream(methods)
                .filter (method -> MethodUtils.isAnnotatedWith(method, Catch.class) )
                .filter( method -> MethodUtils.hasReturnTypeOf(method, trierMethod.getReturnType()) )
                .filter( method -> MethodUtils.hasPlusOneParameterThan(method, trierMethod) )
                .filter( method -> MethodUtils.haveSameParameterTypesUntil(method, trierMethod, trierMethod.getParameterCount()) )
                .filter( MethodUtils::hasLastParameterTypeAsException )
                .collect( Collectors.toList() );
    }

    private static void assertExceptions(List<Method> catherList) {
        if ( catherList.size() == 0 )
            throw new CatcherNotFoundException("Could not found any @" + Catch.class.getSimpleName() + " annotated methods suitable for catching exceptions.");
        assertDuplicatedCatcher(catherList);
    }

    private static void assertDuplicatedCatcher(final List<Method> list){
        List<Class<?>> parameterCatchList =
                list
                .stream()
                .map( MethodUtils::getLastParameterType )
                .collect( Collectors.toList() );
        List<Class<?>> duplicatedParameterCatch = parameterCatchList
                .stream()
                .filter( parameterCatch -> Collections.frequency(parameterCatchList, parameterCatch) > 1 )
                .collect( Collectors.toList() );
        if ( duplicatedParameterCatch.size() > 0 )
            throw new DuplicatedCatcherException("Duplicated @"+ Catch.class.getSimpleName()+" were found for exceptions:"+ duplicatedParameterCatch);
    }
}
