package core;


import core.annotation.Also;
import core.annotation.Catch;
import core.annotation.Try;
import core.utils.BaseTest;
import core.utils.clazz.AbstractTrier;
import core.utils.data.CatherReturnData;
import core.utils.exception.ChildOfRuntime;
import core.utils.exception.CloneOfRuntime;
import core.utils.exception.NephewOfRuntime;
import core.utils.strategy.IStrategy;
import core.utils.strategy.ReturnNullStrategy;
import core.utils.strategy.ThrowStrategy;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HierarchyCatchTest extends BaseTest {

    public class HierarchyCatchTestClass extends AbstractTrier {
        public HierarchyCatchTestClass(IStrategy strategy) {
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

        @Catch
        public CatherReturnData catchRuntimeException(final RuntimeException exception) {
            return new CatherReturnData(exception, new Object() {}.getClass().getEnclosingMethod().getName() );
        }

        @Catch
        public CatherReturnData catchException(final Exception exception) {
            return new CatherReturnData(exception, new Object() {}.getClass().getEnclosingMethod().getName() );
        }

        @Catch
        public CatherReturnData catchChildOfRuntime(final ChildOfRuntime exception) {
            return new CatherReturnData(exception, new Object() {}.getClass().getEnclosingMethod().getName() );
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
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ReturnNullStrategy());
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat(catherReturnData).isNull();
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchThrowableMethodShouldCatchThrowableAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new Throwable(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(Throwable.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchThrowable");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchExceptionShouldMethodCatchExceptionAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new Exception(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(Exception.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchException");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchRuntimeExceptionMethodShouldCatchRunTimeExceptionAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new RuntimeException(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(RuntimeException.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchRuntimeException");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchChildOfRuntimeMethodShouldCatchChildOfRuntimeAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new ChildOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(ChildOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchChildOfRuntime");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchNephewOfRuntimeMethodShouldCatchNephewOfRuntimeAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new NephewOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(NephewOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchNephewOfRuntime");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

    @Test
    public void catchRuntimeMethodShouldCatchCloneOfRuntimeAndCallAlso() throws Throwable {
        HierarchyCatchTestClass instance = new HierarchyCatchTestClass(new ThrowStrategy(new CloneOfRuntime(BE_CATCH_MESSAGE)));
        CatherReturnData catherReturnData = instance.tryStrategy();
        assertThat( catherReturnData.getException().getMessage()).isEqualTo(BE_CATCH_MESSAGE);
        assertThat( catherReturnData.getExceptionType() ).isEqualTo(CloneOfRuntime.class);
        assertThat( catherReturnData.getMethodName() ).isEqualTo("catchRuntimeException");
        assertThat( instance.wasAlsoCalled() ).isTrue();
    }

}
