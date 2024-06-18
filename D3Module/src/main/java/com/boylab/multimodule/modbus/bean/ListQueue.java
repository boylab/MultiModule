package com.boylab.multimodule.modbus.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义循环队列，take不会从列表中删除，take是循环获取
 * 集合为空会阻塞
 * @param <E>
 */
public class ListQueue<E> {

    final ReentrantLock lock = new ReentrantLock();
    /** Condition for waiting takes */
    private final Condition notEmpty = lock.newCondition();

    private int position = 0;
    private List<E> mQueues = new ArrayList<E>();
    /**
     * 添加模块指令生产器
     * @param e
     * @return
     */
    public boolean offer(E e) {
        return offerLast(e);
    }

    public boolean offerLast(E e) {
        if (e == null) throw new NullPointerException();
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            boolean b = mQueues.add(e);
            if (b){
                notEmpty.signal();
            }
            //pos移到新添加的位置，方便第一时间刷新
            position = mQueues.size() - 1;

            return b;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 按队列取出模块指令生产器
     * @return
     */
    public E take() throws InterruptedException {
        return takeFirst();
    }

    private E takeFirst() throws InterruptedException{
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            E x;
            while ( (x = unlinkFirst()) == null)
                notEmpty.await();

            //pos自增加1，如果越界则变成0
            if (++ position >= mQueues.size()){
                position = 0;
            }

            return x;
        } finally {
            lock.unlock();
        }
    }

    private E unlinkFirst() {
        if (mQueues.isEmpty()){
            return null;
        }
        E x = mQueues.get(position);
        return x;
    }


    /**
     * 从队列移出模块指令生产器
     * @param e
     */
    public void remove(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            int indexOf = mQueues.indexOf(e);
            mQueues.remove(e);
            if (position > indexOf){
                position --;
            }
            if (position >= mQueues.size()){
                position = 0;
            }
        } finally {
            lock.unlock();
        }
    }

}
