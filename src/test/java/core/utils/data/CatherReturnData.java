package core.utils.data;

public class CatherReturnData {

    final private Throwable exception;
    final private Class<? extends Throwable> exceptionType;
    final private String methodName;

    public CatherReturnData(Throwable catchException, String methodName) {
        this.exception = catchException;
        this.exceptionType = catchException.getClass();
        this.methodName = methodName;
    }

    public Throwable getException() {
        return exception;
    }

    public String getMethodName() {
        return methodName;
    }

    public Class<? extends Throwable> getExceptionType() {
        return exceptionType;
    }

}
