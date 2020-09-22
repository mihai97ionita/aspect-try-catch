package core.utils.clazz;


import core.utils.strategy.IStrategy;

public abstract class AbstractTrier {
    protected final IStrategy strategy;
    public AbstractTrier(IStrategy strategy) {
        this.strategy = strategy;
    }

    protected boolean wasAlsoCalled = false;
    public boolean wasAlsoCalled() {
        return wasAlsoCalled;
    }
}
