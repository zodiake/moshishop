package exceptions;

/**
 * Date: Dec 25, 2009
 *
 * @author Kaan Yamanyar
 */
public class RecursiveCategoryException extends RuntimeException{
    public RecursiveCategoryException() {
    }

    public RecursiveCategoryException(String s) {
        super(s);
    }

    public RecursiveCategoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public RecursiveCategoryException(Throwable throwable) {
        super(throwable);
    }
}
