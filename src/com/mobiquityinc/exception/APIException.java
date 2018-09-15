package com.mobiquityinc.exception;

/**
 * This class is custom type of an exception to throw a business exception with message and details of an exception
 *
 * @author ShahKA
 * @since 1/22/2018
 */
public class APIException extends Exception {

    static final long serialVersionUID = -3387516993124229948L;

    public APIException(final String message) {
        super(message);
    }

    public APIException(final Throwable cause) {
        super(cause);
    }

    public APIException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
