package core;

import core.annotation.Also;
import core.annotation.Catch;
import core.annotation.Try;
import core.exception.CatcherNotFoundException;
import core.exception.DuplicatedCatcherException;
import core.exception.MultipleAlsoException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FrameworkExceptionTest {

    public class DuplicatedCatcherTestClass {
        @Try
        public void method() { }

        @Catch
        public void catchThrowable(final Throwable exception) { }

        @Catch
        public void catchThrowable2(final Throwable exception) { }
    }

    @Test
    public void DuplicatedCatcherExceptionShouldBeThrownAtTrytime(){
        DuplicatedCatcherTestClass instance = new DuplicatedCatcherTestClass();
        assertThatThrownBy(instance::method).isInstanceOf(DuplicatedCatcherException.class);
    }

    public class CatchNotFoundExceptionTestClass {
        @Try
        public void method() { }
    }

    @Test
    public void CatchNotFoundExceptionShouldBeThrownAtTrytime(){
        CatchNotFoundExceptionTestClass instance = new CatchNotFoundExceptionTestClass();
        assertThatThrownBy(instance::method).isInstanceOf(CatcherNotFoundException.class);
    }

    public class MultipleAlsoExceptionTestClass {

        @Try
        public void method() { }

        @Catch
        public void catchThrowable(final Throwable exception) { }

        @Also
        public void also1() { }

        @Also
        public void also2() { }
    }

    @Test
    public void MultipleAlsoExceptionShouldBeThrownAtTrytime(){
        MultipleAlsoExceptionTestClass instance = new MultipleAlsoExceptionTestClass();
        assertThatThrownBy(instance::method).isInstanceOf(MultipleAlsoException.class);
    }
}
