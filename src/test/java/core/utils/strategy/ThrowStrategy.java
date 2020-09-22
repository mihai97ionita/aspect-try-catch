package core.utils.strategy;

import core.utils.data.CatherReturnData;

public class ThrowStrategy implements IStrategy {
    final private Throwable runtimeException;

    public ThrowStrategy(Throwable runtimeException) {
        if(runtimeException == null)
            throw new RuntimeException("parameter must be NotNull");
        this.runtimeException = runtimeException;
    }

    @Override
    public CatherReturnData invoke() throws Throwable {
        throw runtimeException;
    }
}
