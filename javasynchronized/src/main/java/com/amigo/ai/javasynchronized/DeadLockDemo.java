package com.amigo.ai.javasynchronized;

/**
 * Created by wf on 18-3-20.
 */

public class DeadLockDemo {
    private static String resource_a = "A";
    private static String resource_b = "B";

    public static void main(String[] args){
        deadLock();
    }

    private static void deadLock() {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_a) {
                    System.out.println("AAAA get resource a");
                    try {
                        Thread.sleep(3000);
                        synchronized (resource_b) {
                            System.out.println("AAA get resource b");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_b) {
                    System.out.println("BBB  get resource b");
                    synchronized (resource_a) {
                        System.out.println("BBBB  get resource a");
                    }
                }
            }
        });

        threadA.start();;
        threadB.start();
    }


}
