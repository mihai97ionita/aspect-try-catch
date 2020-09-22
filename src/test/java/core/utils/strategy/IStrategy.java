package core.utils.strategy;

import core.utils.data.CatherReturnData;

public interface IStrategy {
    CatherReturnData invoke() throws Throwable;
}
