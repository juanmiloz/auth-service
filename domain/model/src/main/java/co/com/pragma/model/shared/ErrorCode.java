package co.com.pragma.model.shared;

/**
 * Represents an application error code.
 */
public interface ErrorCode {
    String getAppCode();
    int getHttpCode();
    String getMessage();
}
