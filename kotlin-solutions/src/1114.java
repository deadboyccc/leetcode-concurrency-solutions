import java.util.concurrent.CountDownLatch;

class Foo {

    private CountDownLatch latch2;
    private CountDownLatch latch3;

    public Foo() {
        latch2 = new CountDownLatch(1);
        latch3 = new CountDownLatch(1);

    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        latch2.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        latch2.await();
        printSecond.run();
        latch3.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        latch3.await();
        printThird.run();
    }
}