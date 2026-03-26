🧵 LeetCode Concurrency Mastery

"Kotlin" (https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
"Java" (https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
"JVM" (https://img.shields.io/badge/jvm-test-brightgreen.svg?style=for-the-badge&logo=openjdk)

Comprehensive solutions to all LeetCode Concurrency problems. Master multithreading in Kotlin and Java using Mutex, Semaphores, Condition Variables, and Barriers. This repository includes detailed implementations of thread-safe design patterns and strategies for deadlock prevention.

---

🚦 Basic Synchronization (Signaling & Locks)

The foundation of thread coordination: ensuring one thread starts only after another finishes.

- Easy | "1114. Print in Order" (https://leetcode.com/problems/print-in-order/)
- Medium | "1115. Print FooBar Alternately" (https://leetcode.com/problems/print-foobar-alternately/)
- Medium | "1116. Print Zero Even Odd" (https://leetcode.com/problems/print-zero-even-odd/)

---

🍱 Advanced Coordination (Barriers & State)

Managing multiple threads that must meet at a specific point or follow complex state transitions.

- Medium | "1117. Building H2O" (https://leetcode.com/problems/building-h2o/)
- Medium | "1195. Fizz Buzz Multithreaded" (https://leetcode.com/problems/fizz-buzz-multithreaded/)
- Medium | "1226. The Dining Philosophers" (https://leetcode.com/problems/the-dining-philosophers/)

---

🛠️ Thread-Safe System Design (Premium & Complex)

Implementing real-world concurrent data structures and high-performance crawlers.

- Medium | "1188. Design Bounded Blocking Queue" (https://leetcode.com/problems/design-bounded-blocking-queue/)
- Medium | "1242. Web Crawler Multithreaded" (https://leetcode.com/problems/web-crawler-multithreaded/)
- Medium | "1279. Traffic Light Junction" (https://leetcode.com/problems/traffic-light-junction/)

---

🧪 Quick Reference: Synchronization Primitives

This guide clarifies which synchronization tool is most effective for specific concurrency constraints within the JVM ecosystem.

Primitive| Use Case| Idiomatic Kotlin / JVM| LeetCode Strategy
"CountDownLatch"| One-way execution gates| "CountDownLatch(n)"| Force Thread B to wait for Thread A
"Semaphore"| Permit-based throttling| "Semaphore(available)"| Orchestrate turn-taking (Ping-Pong)
"ReentrantLock"| Fine-grained Mutex| "lock.withLock { ... }"| Protect shared counters/collections
"Condition"| Advanced signaling| "condition.await()"| Wait for a specific logical state
"CyclicBarrier"| Multi-thread rendezvous| "CyclicBarrier(parties)"| Wait for a full set of threads to arrive
"Volatile"| Memory visibility| "@Volatile var flag"| Ensure threads see the latest value

---

💡 Idiomatic Kotlin: The "withLock" Pattern

To ensure thread safety and avoid manual resource management (which can cause deadlocks if a thread crashes), use Kotlin's extension functions:

private val lock = ReentrantLock()

fun performThreadSafeAction() {
    // Automatically calls lock() and guarantees unlock() in a finally block
    lock.withLock {
        // Critical section
        println("Thread ${Thread.currentThread().id} is accessing shared state")
    }
}

---

🧠 Key Concurrency Patterns

This repository focuses on high-level synchronization logic, including:

- Producer-Consumer
  Managing shared buffers with thread-safe queues.

- Signaling
  Using "CountDownLatch" and "Semaphore" for execution ordering.

- Deadlock Prevention
  Implementing resource hierarchy and lock-ordering (avoiding circular wait).

- Memory Visibility
  Ensuring cross-thread communication via "volatile" and atomic variables.

---

🤝 Contributing

Contributions make the open-source community an amazing place to learn and create.

1. Fork the project
2. Create your feature branch
   git checkout -b feature/AmazingSolution
3. Commit your changes
   git commit -m "Add solution for Problem X"
4. Push to the branch
   git push origin feature/AmazingSolution
5. Open a Pull Request

---

📜 Philosophy

«“Knowledge and software should be free, transparent, and accessible.”»

---
