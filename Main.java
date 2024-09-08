public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
                counter.synchronizedIncrement();
            }
        };

        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println("Final count (Counter): " + counter.getCount());

        DeadlockExample deadlockExample = new DeadlockExample();
        Thread t1 = new Thread(deadlockExample::method1);
        Thread t2 = new Thread(deadlockExample::method2);

        t1.start();
        t2.start();

        BoundedBuffer buffer = new BoundedBuffer();
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    buffer.put("Item " + i);
                    System.out.println("Produced: Item " + i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 10; i++) {
                    Object item = buffer.take();
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        // Test AtomicCounter
        AtomicCounter atomicCounter = new AtomicCounter();
        Runnable atomicTask = () -> {
            for (int i = 0; i < 1000; i++) {
                atomicCounter.increment();
            }
        };

        Thread atomicThread1 = new Thread(atomicTask);
        Thread atomicThread2 = new Thread(atomicTask);

        atomicThread1.start();
        atomicThread2.start();

        atomicThread1.join();
        atomicThread2.join();

        System.out.println("Final count (AtomicCounter): " + atomicCounter.getCount());
    }
}
