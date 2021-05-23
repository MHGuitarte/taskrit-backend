package com.mangh.taskrit.util.pojo;

import java.util.logging.Level;

public class Logger {

    private final String className;
    private final java.util.logging.Logger logger;

    public Logger(final String className) {
        this.className = className;
        this.logger = java.util.logging.Logger.getLogger(className);
    }

    public void info(final String msg, final String... vars) {
        this.logger.log(Level.INFO, this.buildMessage(msg, vars));
    }

    public void warn(final String msg, final String... vars) {
        this.logger.log(Level.WARNING, this.buildMessage(msg, vars));
    }

    public void error(final String msg, final String... vars) {
        this.logger.log(Level.SEVERE, this.buildMessage(msg, vars));
    }

    private String buildMessage(final String msg, final String... vars) {
        String result = msg;
        if (vars.length > 0) {
            for (final String var : vars) {
                result = msg.replaceFirst("\\{\\}", var);
            }
        }
        return !result.isEmpty() ? result : "No message";
    }
}
