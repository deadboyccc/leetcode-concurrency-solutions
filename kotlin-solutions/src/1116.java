/**
 * Problem: 1116. Print Zero Even Odd
 * * Logic:
 * Uses three Semaphores to coordinate the turn-taking between threads.
 * - zeroSem: Initialized to 1 to allow the 'zero' thread to start first.
 * - oddSem: Initialized to 0, waits for signal from 'zero' thread.
 * - evenSem: Initialized to 0, waits for signal from 'zero' thread.
 * * The 'zero' thread acts as a coordinator, signaling the 'odd' thread on
 * odd-indexed iterations (1st, 3rd...) and the 'even' thread on
 * even-indexed iterations (2nd, 4th...).
 */

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

class ZeroEvenOdd {
    private int n;

    // Semaphores to control the execution flow
    private Semaphore zeroSem = new Semaphore(1);
    private Semaphore oddSem = new Semaphore(0);
    private Semaphore evenSem = new Semaphore(0);

    public ZeroEvenOdd(int n) {
        this.n = n;
    }

    // Thread A: Always prints 0, then decides which number thread to wake up
    public void zero(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            zeroSem.acquire();
            printNumber.accept(0);

            // On iteration 1, 3, 5... release Odd thread
            // On iteration 2, 4, 6... release Even thread
            if (i % 2 == 1) {
                oddSem.release();
            } else {
                evenSem.release();
            }
        }
    }

    // Thread B: Prints even numbers and hands control back to zero thread
    public void even(IntConsumer printNumber) throws InterruptedException {
        for (int i = 2; i <= n; i += 2) {
            evenSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
    }

    // Thread C: Prints odd numbers and hands control back to zero thread
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i += 2) {
            oddSem.acquire();
            printNumber.accept(i);
            zeroSem.release();
        }
    }
}
