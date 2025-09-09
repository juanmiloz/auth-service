package co.com.pragma.api.error;

import co.com.pragma.model.shared.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Global exception handler for REST controllers.
 * <p>
 * Catches domain-specific and other exceptions and
 * returns standardized error responses.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link DomainException} and builds a response with its details.
     *
     * @param ex the thrown domain exception
     * @return {@link ResponseEntity} with error information (code, httpCode, message)
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomain(DomainException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getHttpCode());
        return ResponseEntity.status(status).body(Map.of(
                "httpCode", ex.getHttpCode(),
                "message", ex.getMessage()
        ));
    }

    // /**
    //  * Handles unexpected errors with a generic 500 response.
    //  */
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String,Object>> handleUnexpected(Exception ex){
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
//                Map.of("code","GEN_000","httpCode",500,"message","Unexpected error")
//        );
//    }

}
