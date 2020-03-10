package com.zferreiro.dummyreceiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

@Service
public class CustomErrorHandler implements ErrorHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void handleError(Throwable throwable) {
        logger.warn("Using Custom Error Handler!");
        logger.warn("Error Message {} !", throwable.getMessage());
    }
}
