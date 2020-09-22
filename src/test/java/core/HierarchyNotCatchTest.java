package core;

import core.annotation.Also;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HierarchyNotCatchTest extends BaseTest {

    public class HierarchyNotCatchTestClass extends AbstractTrier {
        public HierarchyNotCatchTestClass(IStrategy strategy) {
            super(strategy);
        }

        @Try
        public CatherReturnData tryStrategy() throws Throwable {
            return strategy.invoke();
        }

        @Catch
        public CatherReturnData catchNephewOfRuntime(final NephewOfRuntime exception) {
            return new CatherReturnData(exception, new Object() {}.getClass().getEnclosingMethod().getName() );
        }

        @Also
        public void alsoCall(){
            System.out.println("Also called");
            wasAlsoCalled = true;
        }

    }

    @Test
    public void shouldBehaveNormallyAndCallAlso() throws Throwable {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ReturnNullStrategy());
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat(catherReturnData).isNull();
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void shouldNotCatchThrowable() {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ThrowStrategy(new Throwable(NOT_BE_CATCH_MESSAGE)));
        assertThatThrownBy(instance::tryStrategy).isInstanceOf(Throwable.class).hasMessage(NOT_BE_CATCH_MESSAGE);
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void shouldNotCatchException() {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ThrowStrategy(new Exception(NOT_BE_CATCH_MESSAGE)));
        assertThatThrownBy(instance::tryStrategy).isInstanceOf(Exception.class).hasMessage(NOT_BE_CATCH_MESSAGE);
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void shouldNotCatchRunTimeException() {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ThrowStrategy(new RuntimeException(NOT_BE_CATCH_MESSAGE)));
        assertThatThrownBy(instance::tryStrategy).isInstanceOf(RuntimeException.class).hasMessage(NOT_BE_CATCH_MESSAGE);
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void shouldNotCatchChildOfRuntime() {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ThrowStrategy(new ChildOfRuntime(NOT_BE_CATCH_MESSAGE)));
        assertThatThrownBy(instance::tryStrategy).isInstanceOf(ChildOfRuntime.class).hasMessage(NOT_BE_CATCH_MESSAGE);
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void shouldCatchNephewOfRuntime() throws Throwable {
        HierarchyNotCatchTestClass instance = new HierarchyNotCatchTestClass(new ThrowStrategy(new NephewOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(NephewOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchNephewOfRuntime");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }
}
