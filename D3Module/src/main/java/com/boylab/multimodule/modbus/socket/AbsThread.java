package com.boylab.multimodule.modbus.socket;

/**
 * 功能与 AbsLoopThread 类似，
 */
public abstract class AbsThread implements Runnable {
    public volatile Thread thread = null;
    protected volatile String threadName = "";
    private volatile boolean isStop = false;
    private volatile boolean isShutdown = true;
    private volatile Exception ioException = null;
    private volatile long loopTimes = 0L;

    public AbsThread() {
        this.isStop = true;
        this.threadName = this.getClass().getSimpleName();
    }

    public AbsThread(String name) {
        this.isStop = true;
        this.threadName = name;
    }

    public synchronized void start() {
        if (this.isStop) {
            this.thread = new Thread(this, this.threadName);
            this.isStop = false;
            this.loopTimes = 0L;
            this.thread.start();
        }

    }

    public final void run() {
        try {
            this.isShutdown = false;
            this.beforeLoop();

            while(!this.isStop) {
                this.runInLoopThread();
                ++this.loopTimes;
            }
        } catch (Exception var5) {
            if (this.ioException == null) {
                this.ioException = var5;
            }
        } finally {
            this.isShutdown = true;
            this.loopFinish(this.ioException);
            this.ioException = null;
        }

    }

    public long getLoopTimes() {
        return this.loopTimes;
    }

    public String getThreadName() {
        return this.threadName;
    }

    protected void beforeLoop() throws Exception {
    }

    protected abstract void runInLoopThread() throws Exception;

    protected abstract void loopFinish(Exception var1);

    public synchronized void shutdown() {
        if (this.thread != null && !this.isStop) {
            this.isStop = true;
            this.thread.interrupt();
            this.thread = null;
        }
    }

    public synchronized void shutdown(Exception e) {
        this.ioException = e;
        this.shutdown();
    }

    public boolean isShutdown() {
        return this.isShutdown || this.isStop;
    }
}

