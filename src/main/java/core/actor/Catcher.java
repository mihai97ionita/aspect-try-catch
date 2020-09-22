package core.actor;

import core.exception.NotAllowedException;
import core.function.CatherMatcher;
import core.function.CatherMethodFinder;
import core.function.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.Method;
import java.util.*;

public class Catcher {

    final private Trier trier;
    final private Object methodInvoker;
    final private List<Method> methods;

    public Catcher(final ProceedingJoinPoint proceedingJoinPoint) {
        this.trier = new Trier(proceedingJoinPoint);
        this.methodInvoker = proceedingJoinPoint.getTarget();
        this.methods = CatherMethodFinder.findMethods(methodInvoker.getClass().getMethods(), trier.getMethod());
    }

    public Object catchIt(final Throwable throwable) throws Throwable{
        if(throwable == null)
            throw new NotAllowedException("Exception to catch can not be null");

        Object[] arguments = ObjectUtils.append(trier.getArguments(), throwable);
        return CatherMatcher.match(throwable, methods).invoke(methodInvoker, arguments);
    }
}

