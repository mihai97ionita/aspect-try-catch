package core.function;

import core.comparator.ShortestHierarchyComparator;
import core.exception.internal.InternalNoMatchedCatherFoundException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

final public class CatherMatcher {

    public static Method match(final Throwable throwable, final List<Method> methods) throws InternalNoMatchedCatherFoundException {
        List<Method> desiredCatchers = methods
                .stream()
                .filter(method -> ClassUtils.isSameOrSubClass(throwable.getClass(), MethodUtils.getLastParameterType(method)))
                .collect(Collectors.toList());

        if(desiredCatchers.size() == 0){
            throw new InternalNoMatchedCatherFoundException("No match was found for the desired exception");
        }

        if(desiredCatchers.size() > 1){
            //this indicates multiple interfaces matched
            //as we know that the throwable is subclass or same to multiple matches
            //we will determine the shortest hierarchy
            desiredCatchers.sort(new ShortestHierarchyComparator(throwable.getClass()));
        }
        return desiredCatchers.get(0);
    }
}
