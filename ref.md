# 🧵 JVM & Kotlin Concurrency — Advanced Reference Guide

<p align="center">
  <b>Deep dive into high-performance concurrency</b><br>
  Atomics · Concurrent Collections · Queues · Locks · Coroutines · Patterns
</p>

---

# ⚡ Overview

This guide goes beyond basics and focuses on **real-world engineering trade-offs**:

- Lock-free vs blocking synchronization
- Throughput vs latency under contention
- JVM threads vs Kotlin coroutines
- Choosing the *right* primitive for the job

---

# 1️⃣ Atomic Primitives (Lock-Free)

> 🚀 Best for: **Small shared state, counters, flags**

### 🧠 Core Idea

Atomics use **CAS (Compare-And-Swap)**:

1. Read current value
2. Try to update if unchanged
3. Retry if failed

No locks → no thread blocking → high performance

---

### 🔹 Key Types

| Type | Use Case |
|------|--------|
| `AtomicInteger` | Counters |
| `AtomicLong` | High-range counters |
| `AtomicBoolean` | Flags |
| `AtomicReference<T>` | Immutable updates |
| `LongAdder` | High contention counters |

---

### ⚖️ AtomicInteger vs LongAdder

| Feature | AtomicInteger | LongAdder |
|--------|-------------|----------|
| Contention | Poor | Excellent |
| Memory | Low | Higher |
| Accuracy | Exact | Eventually consistent |

👉 Use `LongAdder` for metrics systems.

---

### 💻 Example

```kotlin
import java.util.concurrent.atomic.AtomicReference

data class User(val id: Int)

val ref = AtomicReference(User(1))

fun updateUser() {
    ref.updateAndGet { it.copy(id = it.id + 1) }
}
```

---

# 2️⃣ Locks & Synchronization (JVM)

> 🚀 Best for: **Complex shared state**

---

### 🔒 synchronized

```kotlin
class Counter {
    private var count = 0

    @Synchronized
    fun increment() {
        count++
    }
}
```

✔ Simple  
❌ Blocks threads  
❌ No timeout / fairness  

---

### 🔒 ReentrantLock

```kotlin
import java.util.concurrent.locks.ReentrantLock

val lock = ReentrantLock()

fun criticalSection() {
    lock.lock()
    try {
        // critical work
    } finally {
        lock.unlock()
    }
}
```

✔ More control (tryLock, fairness)  
✔ Better for complex flows  

---

# 3️⃣ Concurrent Collections

> 🚀 Best for: **Shared data across threads**

---

### 🗺 ConcurrentHashMap

- Segment-based / striped locking
- Non-blocking reads
- High scalability

```kotlin
val map = java.util.concurrent.ConcurrentHashMap<Int, String>()

fun getOrPut(id: Int): String {
    return map.computeIfAbsent(id) { "User-$id" }
}
```

---

### 📋 CopyOnWriteArrayList

✔ Safe iteration  
✔ No locks for reads  
❌ Expensive writes (full copy)

---

### 🌲 ConcurrentSkipListMap

✔ Sorted  
✔ Thread-safe  
❌ Slower than HashMap  

---

# 4️⃣ Blocking Queues (Backpressure)

> 🚀 Best for: **Pipelines & task processing**

---

### 🔹 Types

| Queue | Use Case |
|------|--------|
| `LinkedBlockingQueue` | General |
| `ArrayBlockingQueue` | Fixed memory |
| `PriorityBlockingQueue` | Scheduling |
| `SynchronousQueue` | Thread handoff |

---

### 💻 Worker Pool Example

```kotlin
val queue = java.util.concurrent.LinkedBlockingQueue<Runnable>()

fun worker() {
    while (true) {
        queue.take().run()
    }
}
```

---

# 5️⃣ Kotlin Coroutines (Modern Concurrency)

> 🚀 Best for: **Scalable async systems**

---

## 🔥 Key Difference

| Threads | Coroutines |
|--------|----------|
| Expensive | Lightweight |
| Blocking | Non-blocking |
| OS managed | Kotlin managed |

---

## 🔒 Mutex (Coroutine Lock)

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

✔ Suspends instead of blocking  
✔ Safe for structured concurrency  

---

## 🚦 Semaphore

```kotlin
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit

val semaphore = Semaphore(3)

suspend fun task() {
    semaphore.withPermit {
        println("Running")
    }
}
```

✔ Limits concurrency  

---

## 🔄 Channels (Producer–Consumer)

```kotlin
import kotlinx.coroutines.channels.Channel

val channel = Channel<Int>()

suspend fun producer() {
    repeat(5) { channel.send(it) }
}

suspend fun consumer() {
    for (x in channel) println(x)
}
```

✔ Non-blocking queue  
✔ Backpressure built-in  

---

## 🌊 Flow (Reactive Streams)

```kotlin
import kotlinx.coroutines.flow.*

fun stream() = flow {
    emit(1)
    emit(2)
}
```

✔ Async data streams  
✔ Cold & lazy  

---

# 6️⃣ Patterns (Real Engineering)

---

### 🧩 Worker Pool

- Queue + threads
- Scales processing

---

### 🧩 Rate Limiter

- Semaphore-based

---

### 🧩 Cache

- ConcurrentHashMap + computeIfAbsent

---

### 🧩 Actor Model (Coroutines)

- Channel per actor
- No shared state

---

# 🧠 Cheat Sheet

| Problem | Solution |
|--------|--------|
| Counter | Atomic / LongAdder |
| Complex state | Lock |
| Shared map | ConcurrentHashMap |
| Pipeline | BlockingQueue |
| Async system | Coroutines |
| Limit concurrency | Semaphore |

---

# ⚖️ Decision Guide

Single variable → Atomic  
High contention → LongAdder  
Shared structure → Concurrent Collection  
Blocking workflow → Queue  
Async scalable → Coroutines  

---

# 🚀 Pro Tips

- Prefer **immutability**
- Avoid shared state when possible
- Use **coroutines over threads**
- Measure performance — don’t guess
- Keep concurrency **simple**

---

# 🧩 Target Audience

- Backend Engineers
- Android Developers
- JVM Engineers
- System Design learners
