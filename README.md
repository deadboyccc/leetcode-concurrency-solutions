# 🧵 LeetCode Concurrency Mastery

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white) 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Comprehensive solutions to all **LeetCode Concurrency** problems. Master **multithreading** in Kotlin and Java using **Mutex**, **Semaphores**, **Condition Variables**, and **Barriers**. This repository includes detailed implementations of thread-safe design patterns and strategies for **deadlock prevention**.

---

## 🚦 Basic Synchronization (Signaling & Locks)
* **Easy** | [1114. Print in Order](https://leetcode.com/problems/print-in-order/)
* **Medium** | [1115. Print FooBar Alternately](https://leetcode.com/problems/print-foobar-alternately/)
* **Medium** | [1116. Print Zero Even Odd](https://leetcode.com/problems/print-zero-even-odd/)

## 🍱 Advanced Coordination (Barriers & Semaphores)
* **Medium** | [1117. Building H2O](https://leetcode.com/problems/building-h2o/)
* **Medium** | [1195. Fizz Buzz Multithreaded](https://leetcode.com/problems/fizz-buzz-multithreaded/)
* **Medium** | [1226. The Dining Philosophers](https://leetcode.com/problems/the-dining-philosophers/)

## 🛠️ Thread-Safe System Design (Premium)
* **Medium** | [1188. Design Bounded Blocking Queue](https://leetcode.com/problems/design-bounded-blocking-queue/)
* **Medium** | [1242. Web Crawler Multithreaded](https://leetcode.com/problems/web-crawler-multithreaded/)
* **Medium** | [1279. Traffic Light Junction](https://leetcode.com/problems/traffic-light-junction/)

---

## 🧠 Key Concurrency Patterns
This repository focuses on high-level synchronization logic, including:
* **Producer-Consumer:** Managing shared buffers with thread-safe queues.
* **Signaling:** Using `CountDownLatch` and `Semaphores` for execution ordering.
* **Deadlock Prevention:** Implementing resource hierarchy and lock-ordering.
* **Monitors:** Utilizing Java/Kotlin `synchronized` blocks and `ReentrantLock`.

---

## 🧪 Quick Reference: Primitives Used

| Primitive | Best For... | Example Problem |
| :--- | :--- | :--- |
| **Semaphore** | Permit-based access / Resource pools | *Print FooBar Alternately* |
| **ReentrantLock** | Mutual exclusion with flexible scoping | *Traffic Light Junction* |
| **CountDownLatch** | One-time signaling (Start/Stop) | *Print in Order* |
| **CyclicBarrier** | Syncing a fixed number of threads | *Building H2O* |

---

## 🤝 Contributing
Feel free to contribute! Whether it's adding a more efficient solution, fixing a race condition, or providing a detailed explanation for a specific problem:

1.  **Fork** the repository.
2.  **Create** a new branch (`git checkout -b feature/solution-name`).
3.  **Commit** your changes.
4.  **Push** to the branch.
5.  **Open** a Pull Request.

I am actively implementing these in **Kotlin** and **Java**. If you'd like to provide implementations in other JVM languages or improve existing ones, your PRs are welcome!

---

## 📄 License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
