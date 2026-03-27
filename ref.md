# 🧵 JVM & Kotlin Concurrency Guide

<p align="center">
  <b>High-performance concurrency for Java & Kotlin</b><br>
  Atomics · Concurrent Collections · Queues · Coroutines
</p>

---

## ⚡ Overview

This guide covers the **core building blocks of concurrency on the JVM**:

- Lock-free programming with **Atomics**
- High-throughput **Concurrent Collections**
- Producer–Consumer pipelines with **Blocking Queues**
- Structured concurrency using **Kotlin Coroutines**

---

# 1️⃣ Atomic Primitives (Lock-Free)

> 🚀 Best for: **Counters, flags, and simple shared state**

- No locks
- Uses **CAS (Compare-And-Swap)**
- Extremely fast under low/moderate contention

### 🔹 Key Types

| Type | Use Case |
|------|--------|
| `AtomicInteger` | Counters |
| `AtomicBoolean` | Flags |
| `AtomicReference<T>` | Immutable object updates |
| `LongAdder` | High-contention counters |

---

### 💻 Example

```kotlin
import java.util.concurrent.atomic.AtomicInteger

class AnalyticsTracker {
    private val requestCount = AtomicInteger(0)

    fun registerRequest() {
        val current = requestCount.incrementAndGet()
        println("Request #$current processed.")
    }
}
```

---

# 2️⃣ Concurrent Collections

> 🚀 Best for: **Shared data structures across threads**

### ❌ Problem

HashMap + multiple threads → ConcurrentModificationException

---

### ✅ Solutions

#### 🗺 Maps

- `ConcurrentHashMap` → High-performance, scalable
- `ConcurrentSkipListMap` → Sorted + thread-safe

#### 📋 Lists

- `CopyOnWriteArrayList`
  - ✅ Read-heavy workloads
  - ❌ Expensive writes

---

### 💻 Example

```kotlin
import java.util.concurrent.ConcurrentHashMap

val userCache = ConcurrentHashMap<Int, String>()

fun getOrFetchUser(id: Int): String {
    return userCache.computeIfAbsent(id) {
        "User-$id"
    }
}
```

---

# 3️⃣ Blocking Queues (Producer–Consumer)

> 🚀 Best for: **Pipelines, job processing, backpressure**

---

### 🔹 Types

| Queue | Behavior |
|------|--------|
| `LinkedBlockingQueue` | General purpose |
| `ArrayBlockingQueue` | Fixed size |
| `PriorityBlockingQueue` | Sorted |
| `SynchronousQueue` | Direct handoff |

---

### 💻 Example

```kotlin
import java.util.concurrent.LinkedBlockingQueue

val workQueue = LinkedBlockingQueue<Runnable>(10)

fun submitTask(task: Runnable) {
    workQueue.put(task)
}

fun runWorker() {
    while (true) {
        val task = workQueue.take()
        task.run()
    }
}
```

---

# 4️⃣ Kotlin Coroutine Synchronization

> ⚠️ DO NOT use `synchronized` with coroutines

---

## 🔒 Mutex (Non-blocking lock)

```kotlin
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val mutex = Mutex()
var balance = 0

suspend fun deposit(amount: Int) {
    mutex.withLock {
        balance += amount
    }
}
```

---

## 🚦 Semaphore (Concurrency limiter)

```kotlin
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

val requestLimit = Semaphore(3)

suspend fun downloadFile() {
    requestLimit.withPermit {
        println("Downloading...")
    }
}
```

---

# 🧠 Cheat Sheet

| Problem | Tool |
|--------|------|
| Counter | `AtomicLong` |
| Heavy contention | `LongAdder` |
| Thread-safe map | `ConcurrentHashMap` |
| Read-heavy list | `CopyOnWriteArrayList` |
| Task queue | `LinkedBlockingQueue` |
| Coroutine locking | `Mutex` |

---

# ⚖️ When to Use What

Single variable → Atomic  
Shared structure → Concurrent Collection  
Task pipeline → Queue  
Coroutines → Mutex / Semaphore  

---

# 🚀 Pro Tips

- Prefer **immutability + AtomicReference**
- Use **LongAdder** for metrics
- Avoid CopyOnWrite in write-heavy paths
- Use queues to decouple systems
- Never block threads inside coroutines

---

# 🧩 Target Audience

- Backend Engineers
- Android Developers
- JVM / Kotlin Developers
- System Design learners
