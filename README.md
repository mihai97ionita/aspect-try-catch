# Aspect-try-catch

It's a project that uses aspectj for creating annotaion for @Try @Catch and @Also (similar with finally)

This annotations work at an instance level.

### @Try
- used to mark a method that can throw a Throwable
- can have any signature
- when it's applied, the presence of at least one @Cath method becames mandatory 

### @Catch
- used to mark a method than can catch a specific Throwable 
- must have the same return type, method parameters as the @Try annotated method + one catch parameter that is a subclass of Throwable
- can't have multiple methods that catch the same exception type
- when mutiple methods are marked with @Catch, hierarchical priority is used ( same exception type then closest super type of exception )
- at least one method annotated when @Try is used in same class

### @Also
- used to mark a method that is called after a @Try @Catch combo is executed
- it's called even if no @Catch methods could catch any exception
- void return type is mandatory
- must have same method parameters as @Try
- it is optional


## Example:

```
@Try
public String tryMethod(String msg) {
    ...
}

@Catch
public String catchMethod1(String tryMsg, RuntimeException runtimeException) {
    ...
}

@Catch
public String catchMethod2(String message, IllegalMonitorStateException exception) {
    ...
}

@Also
public void alsoMethod(String string){
    ...
}
```
