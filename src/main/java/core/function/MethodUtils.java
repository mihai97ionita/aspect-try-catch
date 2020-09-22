package core.function;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

final public class MethodUtils {

    public static boolean hasPlusOneParameterThan(Method method1, Method method2) {
        return method1.getParameterCount() == ( method2.getParameterCount() +1 );
    }

    public static boolean hasLastParameterTypeAsException(Method method){
        return Throwable.class.isAssignableFrom(getLastParameterType(method));
    }

    public static Class<?> getLastParameterType(Method method) {
        return method.getParameterTypes()[method.getParameterCount() - 1];
    }

    public static boolean haveSameParameterTypesUntil(Method method1, Method method2, int number){
        if( method1.getParameterCount() < number || method2.getParameterCount() < number )
            return false;
        Class<?>[] parameterTypes1 = method1.getParameterTypes();
        Class<?>[] parameterTypes2 = method2.getParameterTypes();
        for ( int i=0; i < number; i++ ){
            if( !parameterTypes1[i].equals(parameterTypes2[i]) )
                return false;
        }
        return true;
    }

    public static boolean hasReturnTypeOf(final Method method, final Class<?> returnType){
        return returnType.equals(method.getReturnType());
    }

    public static boolean isAnnotatedWith(final Method method, final Class<? extends Annotation> annotationClass){
        Annotation[] annotations = method.getDeclaredAnnotations();
        for ( Annotation annotation: annotations ){
            if ( annotationClass.equals(annotation.annotationType()) )
                return true;
        }
        return false;
    }
}
