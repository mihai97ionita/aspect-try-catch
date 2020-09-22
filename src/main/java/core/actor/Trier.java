package core.actor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

class Trier {

    final private Method method;
    final private Object[] arguments;

    public Trier(final ProceedingJoinPoint proceedingJoinPoint) {
        this.method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        this.arguments = proceedingJoinPoint.getArgs();
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
