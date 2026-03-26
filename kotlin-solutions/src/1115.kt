import java.util.concurrent.Semaphore

class FooBar(private val n: Int) {
    val fooPermit = Semaphore(1)
    val barPermit = Semaphore(0)


    @Throws(InterruptedException::class)
    fun foo(printFoo: Runnable) {
        for (i in 0 until n) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            fooPermit.acquire()
            printFoo.run()
            barPermit.release()

        }
    }

    @Throws(InterruptedException::class)
    fun bar(printBar: Runnable) {
        for (i in 0 until n) {

            // printBar.run() outputs "bar". Do not change or remove this line.
            barPermit.acquire()
            printBar.run()
            fooPermit.release()
        }
    }
}