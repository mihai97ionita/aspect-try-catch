package core.actor;

import core.function.AlsoMethodFinder;
import org.aspectj.lang.ProceedingJoinPoint;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class Alsoer {

    final private Trier trier;
    final private Object methodInvoker;
    final private Optional<Method> method;

    public Alsoer(final ProceedingJoinPoint proceedingJoinPoint) {
        this.trier = new Trier(proceedingJoinPoint);
        this.methodInvoker = proceedingJoinPoint.getTarget();
        this.method = AlsoMethodFinder.findMethod(methodInvoker.getClass().getMethods(), trier.getMethod());
    }

    public void run() throws InvocationTargetException, IllegalAccessException {
        if(method.isPresent())
            method.get().invoke(methodInvoker, trier.getArguments());
    }
}

