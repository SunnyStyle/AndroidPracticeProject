package com.amigo.ai.rxjavatest.model.exception;

/**
 * Created by wf on 18-4-2.
 */

public interface ErrorData {
    Exception getException();
    String getErrorMessage();
}
