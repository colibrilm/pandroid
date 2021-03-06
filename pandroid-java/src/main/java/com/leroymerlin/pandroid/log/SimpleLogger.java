package com.leroymerlin.pandroid.log;

public abstract class SimpleLogger implements LogWrapper {

    protected boolean debuggable = false;
    protected int logLevel = VERBOSE;

    public boolean shouldLog(int i) {
        return debuggable && i >= logLevel;
    }

    public void setLogLevel(int level) {
        logLevel = level;
    }

    @Override
    public void addLogger(LogWrapper logWrapper) {
        throw new IllegalStateException("SimpleLogger can't wrap an other logger");
    }

    @Override
    public void removeLogger(LogWrapper logWrapper) {
        throw new IllegalStateException("SimpleLogger can't wrap an other logger");
    }

    @Override
    public void setDebug(boolean isDebug) {
        debuggable = isDebug;
    }

    @Override
    public void v(String tag, String msg) {
        if (shouldLog(VERBOSE))
            verbose(tag, msg, null);
    }

    @Override
    public void v(String tag, String msg, Throwable tr) {
        if (shouldLog(VERBOSE))
            verbose(tag, msg, tr);
    }

    @Override
    public void d(String tag, String msg) {
        if (shouldLog(DEBUG))
            debug(tag, msg, null);
    }

    @Override
    public void d(String tag, String msg, Throwable tr) {
        if (shouldLog(DEBUG))
            debug(tag, msg, tr);
    }

    @Override
    public void i(String tag, String msg) {
        if (shouldLog(INFO))
            info(tag, msg, null);
    }

    @Override
    public void i(String tag, String msg, Throwable tr) {
        if (shouldLog(INFO))
            info(tag, msg, tr);
    }


    @Override
    public void w(String tag, String msg) {
        if (shouldLog(WARN))
            warn(tag, msg, null);
    }

    @Override
    public void w(String tag, String msg, Throwable tr) {
        if (shouldLog(WARN))
            warn(tag, msg, tr);
    }


    @Override
    public void w(String tag, Throwable tr) {
        if (shouldLog(WARN))
            warn(tag, getMessage(tr), tr);
    }

    @Override
    public void e(String tag, String msg) {
        if (shouldLog(ERROR))
            error(tag, msg, null);
    }


    @Override
    public void e(String tag, Throwable tr) {
        if (shouldLog(ERROR))
            error(tag, getMessage(tr), tr);
    }

    @Override
    public void e(String tag, String msg, Throwable tr) {
        if (shouldLog(ERROR))
            error(tag, msg, tr);


    }

    @Override
    public void wtf(String tag, String msg) {
        if (shouldLog(ASSERT))
            logAssert(tag, msg, null);
    }


    @Override
    public void wtf(String tag, Throwable tr) {
        if (shouldLog(ASSERT))
            logAssert(tag, getMessage(tr), tr);
    }

    @Override
    public void wtf(String tag, String msg, Throwable tr) {
        if (shouldLog(ASSERT))
            logAssert(tag, msg, tr);
    }

    private String getMessage(Throwable tr) {
        if (tr == null || tr.getMessage() == null)
            return "";
        return tr.getMessage();
    }

    @Override
    public void addKey(String key, String value) {

    }

    public abstract void debug(String tag, String msg, Throwable tr);

    public abstract void verbose(String tag, String msg, Throwable tr);

    public abstract void info(String tag, String msg, Throwable tr);

    public abstract void warn(String tag, String msg, Throwable tr);

    public abstract void error(String tag, String msg, Throwable tr);

    public abstract void logAssert(String tag, String msg, Throwable tr);

}
