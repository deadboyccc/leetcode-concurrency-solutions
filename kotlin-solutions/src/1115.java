import java.util.concurrent.Semaphore;

/**
 * Problem: Print FooBar Alternately
 * <p>
 * Goal: Coordinate two separate threads to print "foo" and "bar" in strict sequence.
 * <p>
 * Key Points:
 * 1. Thread A calls foo() and Thread B calls bar().
 * 2. Output must be "foobarfoobar..." repeated 'n' times.
 * 3. Use Semaphores to handle cross-thread signaling and synchronization.
 * 4. Ensure no race conditions or deadlocks occur during the handoff.
 */
class FooBarJava {
    // fooPermit starts with 1 to allow foo() to execute first
    private final Semaphore fooPermit = new Semaphore(1);
    // barPermit starts with 0 to force bar() to wait for foo()
    private final Semaphore barPermit = new Semaphore(0);
    private int n;

    public FooBarJava(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // Wait until fooPermit is available (initial count is 1)
            fooPermit.acquire();

            // printFoo.run() outputs "foo". Do not change or remove this line.
            printFoo.run();

            // Release a permit to barPermit, signaling bar() to proceed
            barPermit.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {
        for (int i = 0; i < n; i++) {
            // Wait until barPermit is released by the foo() thread
            barPermit.acquire();

            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();

            // Release a permit back to fooPermit for the next iteration
            fooPermit.release();
        }
    }
}
