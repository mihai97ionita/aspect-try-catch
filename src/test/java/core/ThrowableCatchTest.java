package core;


import core.annotation.Catch;
import core.annotation.Try;
import core.utils.BaseTest;
import core.utils.clazz.AbstractTrier;
import core.utils.data.CatherReturnData;
import core.utils.exception.ChildOfRuntime;
import core.utils.exception.NephewOfRuntime;
import core.utils.strategy.IStrategy;
import core.utils.strategy.ReturnNullStrategy;
import core.utils.strategy.ThrowStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ThrowableCatchTest extends BaseTest {

    public class ThrowableCatchTestClass extends AbstractTrier {
        public ThrowableCatchTestClass(IStrategy strategy) {
            super(strategy);
        }

        @Try
        public CatherReturnData tryStrategy() throws Throwable {
            return strategy.invoke();
        }

        @Catch
        public CatherReturnData catchThrowable(final Throwable exception) {
            return new CatherReturnData(exception, new Object() {}.getClass().getEnclosingMethod().getName() );
        }

    }

    @Test
    public void shouldBehaveNormallyAndNotCallAlso() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ReturnNullStrategy());
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat(catherReturnData).isNull();
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

    @Test
    public void shouldCatchThrowable() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ThrowStrategy(new Throwable(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(Throwable.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

    @Test
    public void shouldCatchException() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ThrowStrategy(new Exception(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(Exception.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

    @Test
    public void shouldCatchRunTimeException() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ThrowStrategy(new RuntimeException(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(RuntimeException.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

    @Test
    public void shouldCatchChildOfRuntime() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ThrowStrategy(new ChildOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(ChildOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

    @Test
    public void shouldCatchNephewOfRuntime() throws Throwable {
        ThrowableCatchTestClass instance = new ThrowableCatchTestClass(new ThrowStrategy(new NephewOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(NephewOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isFalse();
    }

}
