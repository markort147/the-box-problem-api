package xyz.veganslab.marcoromano.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * Global exception handler for the application.
 * <p>
 * This controller advice handles specific exceptions and returns appropriate HTTP responses.
 */
@ControllerAdvice
@Log4j2
public class ExceptionHandlingControllerAdvice {

    /**
     * Handles MethodArgumentNotValidException by returning a 400 Bad Request response.
     * Those exceptions are returned by simple json validation made by javax annotations in RequestDTO and ItemDTO classes.
     *
     * @param methodArgumentNotValidException The exception that was thrown.
     * @return A response entity containing the exception message and a BAD_REQUEST status.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<String> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        log.error("MethodArgumentNotValidException caught: ", methodArgumentNotValidException);
        return ResponseEntity
                .badRequest()
                .body("Argument " + methodArgumentNotValidException.getFieldError().getField() + " not valid: " + methodArgumentNotValidException.getFieldError().getDefaultMessage());
    }

    /**
     * Handles general RuntimeExceptions by returning a 500 Internal Server Error response.
     *
     * @param runtimeException The runtime exception that was thrown.
     * @return A response entity containing the exception message and an INTERNAL_SERVER_ERROR status.
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<String> handleRuntimeExceptionException(RuntimeException runtimeException) {
        log.error("RuntimeException caught: ", runtimeException);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("error_details", runtimeException.getMessage())
                .body(runtimeException.getMessage());
    }
}
