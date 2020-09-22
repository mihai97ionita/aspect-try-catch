package core.aspect;

import core.actor.Alsoer;
import core.actor.Catcher;
import core.exception.internal.InternalNoMatchedCatherFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.lang.reflect.InvocationTargetException;

@Aspect
public class TryCatchAlsoAspect {

    @Around("@annotation(core.annotation.Try) && execution(* *(..))")
    public Object aroundTry(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Catcher catcher = new Catcher(proceedingJoinPoint);
        try{
            return proceedingJoinPoint.proceed();
        }catch (Throwable throwable){
            return tryToCatchIt(catcher, throwable);
        }finally{
            also(proceedingJoinPoint);
        }
    }

    private Object tryToCatchIt(Catcher catcher, Throwable throwable) throws Throwable{
        try {
            return catcher.catchIt(throwable);
        }catch (InternalNoMatchedCatherFoundException internalNoMatchedCatherFoundException){
            throw throwable;
        }
    }

//    private Object tryToCatchIt(ProceedingJoinPoint proceedingJoinPoint, Throwable throwable) throws Throwable{
//        try {
//            return new Catcher(proceedingJoinPoint).catchIt(throwable);
//        }catch (InternalNoMatchedCatherFoundException internalNoMatchedCatherFoundException){
//            throw throwable;
//        }
//    }

    private void also(ProceedingJoinPoint proceedingJoinPoint) throws InvocationTargetException, IllegalAccessException {
        new Alsoer(proceedingJoinPoint).run();
    }
}