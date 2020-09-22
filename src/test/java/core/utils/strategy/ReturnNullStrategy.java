package core.utils.strategy;

import core.utils.data.CatherReturnData;

public class ReturnNullStrategy implements IStrategy  {

    @Override
    public CatherReturnData invoke() {
        return null;
    }
}
