package co.com.pragma.model.shared.exception;

import co.com.pragma.model.shared.ErrorCode;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

/**
 * Factory for creating {@link DomainException} instances.
 */
@UtilityClass
public class DomainExceptionFactory {

    /**
     * Creates a {@link DomainException} by formatting the error message
     * with the provided arguments.
     *
     * @param code error code
     * @param args message format arguments
     * @return a new {@link DomainException}
     */
    public static DomainException exceptionOf(ErrorCode code, Object... args ) {
        String message = MessageFormat.format( code.getMessage(), args );
        return new DomainException(code, message);
    }

}
