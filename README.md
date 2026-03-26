# 🧵 LeetCode Concurrency Mastery

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JVM](https://img.shields.io/badge/JVM-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Comprehensive solutions to all LeetCode Concurrency problems. Master multithreading in Kotlin and Java using Mutex, Semaphores, Condition Variables, and Barriers. This repository includes detailed implementations of thread-safe design patterns and strategies for deadlock prevention.

---

## 🚦 Basic Synchronization — Signaling & Locks

The foundation of thread coordination: ensuring one thread starts only after another finishes.

| Difficulty | Problem |
|------------|---------|
| 🟢 Easy | [1114. Print in Order](https://leetcode.com/problems/print-in-order/) |
| 🟡 Medium | [1115. Print FooBar Alternately](https://leetcode.com/problems/print-foobar-alternately/) |
| 🟡 Medium | [1116. Print Zero Even Odd](https://leetcode.com/problems/print-zero-even-odd/) |

---

## 🍱 Advanced Coordination — Barriers & State

Managing multiple threads that must meet at a specific point or follow complex state transitions.

| Difficulty | Problem |
|------------|---------|
| 🟡 Medium | [1117. Building H2O](https://leetcode.com/problems/building-h2o/) |
| 🟡 Medium | [1195. Fizz Buzz Multithreaded](https://leetcode.com/problems/fizz-buzz-multithreaded/) |
| 🟡 Medium | [1226. The Dining Philosophers](https://leetcode.com/problems/the-dining-philosophers/) |

---

## 🛠️ Thread-Safe System Design — Premium & Complex

Implementing real-world concurrent data structures and high-performance crawlers.

| Difficulty | Problem |
|------------|---------|
| 🟡 Medium | [1188. Design Bounded Blocking Queue](https://leetcode.com/problems/design-bounded-blocking-queue/) |
| 🟡 Medium | [1242. Web Crawler Multithreaded](https://leetcode.com/problems/web-crawler-multithreaded/) |
| 🟡 Medium | [1279. Traffic Light Junction](https://leetcode.com/problems/traffic-light-junction/) |

---

## 🧪 Quick Reference — Synchronization Primitives

Which synchronization tool to reach for, and when, within the JVM ecosystem.

### `CountDownLatch`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| One-way execution gates | `CountDownLatch(n)` | Force Thread B to wait for Thread A |

```kotlin
// 1114. Print in Order
val latch2 = CountDownLatch(1)
val latch3 = CountDownLatch(1)

fun first()  { print("first");  latch2.countDown() }
fun second() { latch2.await();  print("second"); latch3.countDown() }
fun third()  { latch3.await();  print("third") }
```

---

### `Semaphore`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| Permit-based throttling | `Semaphore(available)` | Orchestrate turn-taking (Ping-Pong) |

```kotlin
// 1116. Print Zero Even Odd
val zeroSem = Semaphore(1)   // zero goes first
val evenSem = Semaphore(0)
val oddSem  = Semaphore(0)

fun zero() { zeroSem.acquire(); print(0); oddSem.release() }  // simplified
fun odd()  { oddSem.acquire();  print(i); zeroSem.release() }
fun even() { evenSem.acquire(); print(i); zeroSem.release() }
```

---

### `ReentrantLock`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| Fine-grained Mutex | `lock.withLock { ... }` | Protect shared counters/collections |

```kotlin
// 1226. The Dining Philosophers
val forkLocks = Array(5) { ReentrantLock() }

fun eat(philosopher: Int) {
    val left  = forkLocks[philosopher]
    val right = forkLocks[(philosopher + 1) % 5]
    left.withLock {
        right.withLock {
            print("Philosopher $philosopher is eating")
        }
    }
}
```

---

### `Condition`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| Advanced signaling | `condition.await()` | Wait for a specific logical state |

```kotlin
// 1188. Design Bounded Blocking Queue
val lock      = ReentrantLock()
val notFull   = lock.newCondition()
val notEmpty  = lock.newCondition()

fun enqueue(elem: Int) = lock.withLock {
    while (queue.size == capacity) notFull.await()  // block if full
    queue.add(elem)
    notEmpty.signal()                               // wake a waiting dequeue
}

fun dequeue(): Int = lock.withLock {
    while (queue.isEmpty()) notEmpty.await()        // block if empty
    val elem = queue.poll()
    notFull.signal()                                // wake a waiting enqueue
    elem
}
```

---

### `CyclicBarrier`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| Multi-thread rendezvous | `CyclicBarrier(parties)` | Wait for a full set of threads to arrive |

```kotlin
// 1117. Building H2O
val barrier = CyclicBarrier(3)  // 2 H threads + 1 O thread must arrive together

fun hydrogen() { barrier.await(); releaseHydrogen() }
fun oxygen()   { barrier.await(); releaseOxygen() }
// All three block at barrier.await() until a complete H2O set is ready
```

---

### `Volatile`
| Use Case | Idiomatic Kotlin / JVM | LeetCode Strategy |
|----------|------------------------|-------------------|
| Memory visibility | `@Volatile var flag` | Ensure threads see the latest value |

```kotlin
// 1279. Traffic Light Junction
@Volatile var currentRoad = 1  // writer thread flips this

fun carArrived(roadId: Int) {
    while (currentRoad != roadId) { /* spin */ }  // reader always sees latest value
    crossCar()
}
```

---

## 💡 Idiomatic Kotlin — The `withLock` Pattern

To ensure thread safety and avoid manual resource management (which can cause deadlocks if a thread crashes), use Kotlin's extension functions:

```kotlin
private val lock = ReentrantLock()

fun performThreadSafeAction() {
    // Automatically calls lock() and guarantees unlock() in a finally block
    lock.withLock {
        // Critical section
        println("Thread ${Thread.currentThread().id} is accessing shared state")
    }
}
```

---

## 🧠 Key Concurrency Patterns

This repository focuses on high-level synchronization logic, including:

- **Producer-Consumer** — Managing shared buffers with thread-safe queues.
- **Signaling** — Using `CountDownLatch` and `Semaphore` for execution ordering.
- **Deadlock Prevention** — Implementing resource hierarchy and lock-ordering (avoiding circular wait).
- **Memory Visibility** — Ensuring cross-thread communication via `volatile` and atomic variables.

---

## 🤝 Contributing

Contributions make the open-source community an amazing place to learn and create.

1. Fork the project
2. Create your feature branch
   ```bash
   git checkout -b feature/AmazingSolution
   ```
3. Commit your changes
   ```bash
   git commit -m "Add solution for Problem X"
   ```
4. Push to the branch
   ```bash
   git push origin feature/AmazingSolution
   ```
5. Open a Pull Request

---

## 📜 Philosophy

> *"Knowledge and software should be free, transparent, and accessible."*
