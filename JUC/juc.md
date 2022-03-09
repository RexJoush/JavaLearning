# JUC

## 1.JUC 概述

#### 什么是 JUC

* 在 java 中，线程部分是一个重点，本章所说的 JUC 也是关于线程的。
* JUC 指的是 java.util.concurrent 工具包的简称。这是一个处理线程的工具包
* 从 jdk1.5 开始出现

#### 线程和进程基本的概念

* 进程的线程

  * 进程：指的是系统中正在运行的一个应用程序，程序一旦运行就是进程；进程是资源分配的最小单位
  * 线程：系统分配处理器时间的基本单元，或者说进程之内独立执行的一个单元执行流，线程是程序执行的最小单位

* 线程的状态

  * NEW，新建
  * RUNNABLE，运行
  * BLOCKED，阻塞
  * WAITING，等待，一直等
  * TIMED_WAITING，超时等待，过时就不等了
  * TERMINATED，结束

* wait 和 sleep

  * sleep 是 Thread 类的静态方法；wait 是 Object 的方法，任何对象都能调用
  * sleep 不会释放锁，也不需要占用锁，wait 会释放锁，但调用它的前提是当前线程占有锁，即代码要在 synchronized 中
  * 它们都可以被 interrupted 方法中断

* 并发和并行

  * 多个线程同时干一件事叫并发，也是时间段
  * 多个线程同时干不同的事叫并行，指时刻

* 管程

  * Monitor，监视器，也就是常说的锁
  * jvm 同步进入（monitorenter）退出（monitorexit）是使用管程对象实现的

* 用户线程和守护线程

  * 用户线程，自定义线程基本都是用户线程

  * 守护线程，运行在后台，比如垃圾回收线程等

  * 主线程结束了，用户线程还在运行，jvm 存活；没有用户线程了，只有守护线程，jvm 结束

    ```java
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            // 打印当前线程是否是守护线程
            System.out.println(Thread.currentThread().getName() + " :" + Thread.currentThread().isDaemon());
            while (true) {
    
            }
        }, "aa");
        /*
        	如果不设置守护线程，则默认为 false，即用户线程，那么主线程结束后，jvm 也不会停止
        	如果设置守护线程为 true，则主线程结束后，jvm 
        */
        // 设置当前线程为守护线程，应在 start 方法之前设置
        aa.setDaemon(true); 
        aa.start();
    
        System.out.println(Thread.currentThread().getName() + " over");
    }
    ```

## 2.Lock 接口

#### 复习 synchronized

* synchronized 作用范围

  * synchronized 是 java 中的关键字，是一种同步锁，修饰的对象有以下几种
  * 修饰一个代码块，被修饰的代码块称为同步语句块，作用的范围是大括号括起来的代码，作用的对象是调用这个代码块的对象
  * 修饰一个方法，被修饰的方法称为同步方法，其作用的范围是整个方法，作用的对象是调用这个方法的对象
    * 虽然可以使用 synchronized 来定义方法，但 synchronized 并不属于方法定义的一部分，因此 synchronized 关键字不能被继承。如果父类中的某个方法使用了 synchronized  关键字，而子类中覆盖了这个方法，在子类中的这个方法默认情况下是非同步的，必须显式的在方法上加上 synchronized  关键字才可以。
  * 修饰静态方法，其作用范围是整个静态方法，作用的是这个类的所有对象
  * 修饰一个类，其作用范围是 synchronized 后面括号括起来的部分，作用对象是这个类的所有对象

* synchronized 实现卖票例子

  ```java
  class Ticket {
      // 票数
      private int number = 30;
  
      // 操作方法
      public synchronized void sale() {
          // 查看是否还有票
          if (number > 0) {
              System.out.println(Thread.currentThread().getName() + ": sale " + number--);
              System.out.println("还剩下：" + number);
          }
      }
  }
  public class SaleTicket {
  
      // 2.创建多个线程，调用资源类的方法
      public static void main(String[] args) {
          // 创建 ticket 对象
          Ticket ticket = new Ticket();
  
          // 创建三个线程
          new Thread(() -> {
              // 调用卖票的方法
              for (int i = 0; i < 40; i++) {
                  ticket.sale();
              }
          }, "AA").start();
  
          new Thread(() -> {
              // 调用卖票的方法
              for (int i = 0; i < 40; i++) {
                  ticket.sale();
              }
          }, "BB").start();
  
          new Thread(() -> {
              // 调用卖票的方法
              for (int i = 0; i < 40; i++) {
                  ticket.sale();
              }
          }, "CC").start();
      }
  }
  ```

* 多线程编程步骤（上），比较固定，高内聚，低耦合

  * 第一步，创建资源类，在资源类中创建属性和方法
  * 第二步，创建多个线程，调用资源类的操作方法

#### 什么是 Lock 接口

* Lock 接口实现了比使用 synchronized 方法和语句可获得的更广泛的锁定操作

* 实现类，ReentrantLock，ReentrantReadWriteLock.ReadLock，ReentrantReadWriteLock.WriteLock

* Lock 与 synchronized 区别

  * Lock 不是 Java 语言内置的，synchronized 时 Java 语言的关键字，因此时内置特性，Lock 是一个类，通过这个类可以实现同步访问
  * synchronized 与 Lock 有一个最大的不同，synchronized 不需要用户手动释放锁，当 synchronized 方法或者 synchronized 代码块执行完后，系统会自动让线程释放对锁的占用；而 Lock 则必须用户去手动释放锁，如果没有主动释放锁，则会出现死锁。
  * synchronized 在发生异常时会自动释放锁，因此不会导致死锁；而 Lock 在发生异常时，如果没有主动释放锁，则会发生死锁，因此使用 Lock 时需要在 finally 块中
  * Lock 可以让在等待锁的线程响应中断，而 synchronized 不行，使用 synchronized 时，等待的线程会一直等待，不能响应中断。
  * Lock 可以提高多个线程进行读操作的效率。

* 使用 Lock 实现卖票例子

  ```java
  class LTicket {
      // 票数
      private int number = 30;
  
      // 创建可重入锁
      private final ReentrantLock lock = new ReentrantLock();
  
      public void sale() {
          // 上锁
          lock.lock();
  
          try {
              if (number > 0) {
                  System.out.println(Thread.currentThread().getName() + ": sale " + number-- + "剩余: " + number);
              }
          } finally {
              // 释放锁
              lock.unlock();
          }
      }
  }
  ```

#### 创建线程的多种方式

* 继承 Thread 类
* 实现 Runnable 接口
* 使用 Callable 接口
* 使用线程池

#### 使用 Lock 实现卖票例子

## 3.线程间通信

#### 多线程编程步骤（中）

* 第一步，创建资源类，在资源类中创建属性和方法
* 第二步，在资源类中的操作方法
  * 判断
  * 执行
  * 通知
* 第三步，创建多个线程，调用资源类的操作方法
* 第四步，防止虚假唤醒，使用 while 判断

#### 例子

* 两个线程交替对某个值实现 + 1，- 1 操作

```java
class Share {
    // 初始值
    private int number = 0;

    // +1 方法
    public synchronized void increment() throws InterruptedException {
        // 判断
        if (number != 0) { // 如果当前值不为 0.则等待，否则 +1
            this.wait();
        }
        // 操作
        number++;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }
    // -1 方法
    public synchronized void decrement() throws InterruptedException {
        // 判断
        if (number != 1) { // 如果当前值不为 1.则等待，否则 -1
            this.wait();
        }
        // 操作
        number--;
        System.out.println(Thread.currentThread().getName() + ": " + number);
        // 通知
        this.notifyAll();
    }
}

public class ThreadDome1 {

    public static void main(String[] args) {

        Share share = new Share();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.increment(); // +1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Increment").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    share.decrement(); // -1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Decrement").start();
    }
}
```

* 虚假唤醒，在上面的例子中，if 会出现虚假唤醒的问题，当使用 while 时，可以避免此问题。wait() 方法的问题
* wait 方法的特点，在哪里睡就在哪里醒，因此，if 条件只会判断第一次，之后判断就失效了

#### 使用 Lock 方法实现上面的 +1 - 1 例子

```java
class Share {
    private int number = 0;

    // 创建 Lock 对象
    private Lock lock = new ReentrantLock();

    // 用 lock 创建 condition 对象
    private Condition condition = lock.newCondition();

    public void increment() throws InterruptedException {
        // 上锁
        lock.lock();

        try {
            // 判断，使用 while 防止虚假唤醒
            while (number != 0) {
                condition.await();
            }
            // 操作
            number++;
            System.out.println(Thread.currentThread().getName() + ": " + number);

            // 通知
            condition.signalAll();
        } finally {
            // 解锁
            lock.unlock();
        }
    }

    public void decrement() throws InterruptedException {
        // 上锁
        lock.lock();

        try {
            // 判断，使用 while 防止虚假唤醒
            while (number != 1) {
                condition.await();
            }
            // 操作
            number--;
            System.out.println(Thread.currentThread().getName() + ": " + number);

            // 通知
            condition.signalAll();
        } finally {
            // 解锁
            lock.unlock();
        }
    }
}
```

## 4.线程间定制化通信

#### 要求

```text
启动三个线程，要求如下
AA 打印 5 次，BB 打印 10 次，CC 打印 15 次
AA 打印 5 次，BB 打印 10 次，CC 打印 15 次
······
进行 10 轮
```

#### synchronized 方法实现

```java
class Print {
    // 用于三个线程之间的通信
    private int flag = 1;

    /**
     * 打印方法
     * @param times 打印的次数
     * @param index 当前的轮次
     * @param flag 当前线程要使用的标志位
     * @param next 唤醒的下一个线程的标志位
     */
    public synchronized void print(int times, int index, int flag, int next) throws InterruptedException {
        while (this.flag != flag) {
            this.wait();
        }
        for (int i = 1; i <= times; i++) {
            System.out.println(Thread.currentThread().getName() + " times " + i + " 轮次: " + index);
        }
        System.out.println();
        // 将下一个线程唤醒
        this.flag = next;
        this.notifyAll();
    }

}

// 使用 synchronized 关键字
public class CrdCommunication {

    public static void main(String[] args) {
        Print print = new Print();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(5, i, 1, 2);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "AA").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(10, i, 2, 3);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "BB").start();

        new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    print.print(15, i, 3, 1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "CC").start();
    }
}
```

#### Lock 方法实现

```java
class PrintLock {

    private int flag = 1;

    private final Lock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    /**
     * 打印方法
     * @param times 打印的次数
     * @param index 当前的轮次
     * @param flag 当前线程要使用的标志位
     * @param next 唤醒的下一个线程的标志位
     */
    public void print(int times, int index, int flag, int next) throws InterruptedException {
        // 上锁
        lock.lock();
        try {
            while (this.flag != flag) {
                condition.await();
            }

            for (int i = 1; i <= times; i++) {
                System.out.println(Thread.currentThread().getName() + " times " + i + " 轮次: " + index);
            }
            System.out.println();
            // 修改标志位
            this.flag = next;
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
```

## 5.集合的线程安全

#### 集合线程不安全演示 — ArrayList

* `java.util.ConcurrentModificationException`

* 下面的代码中，添加时会出现上面的异常

  ```java
  public class Demo01List {
  
      public static void main(String[] args) {
          List<String> list = new ArrayList<>();
  
          for (int i = 0; i < 30; i++) {
              new Thread(() -> {
                  // 向集合加内容
                  list.add(UUID.randomUUID().toString().substring(0, 8));
                  // 向集合取内容
                  System.out.println(list);
              }, String.valueOf(i)).start();
          }
      }
  }
  ```

#### 解决方案 Vector

* 新建 Vector 即可

  ```java
  // 因为 Vector 中添加了 synchronized 关键字，但较为古老，一般不常用
  List<String> list = new Vector<>();
  ```

#### 解决方案 Collections

* 通过 Collections 获取线程安全的集合

  ```java
  // 本方法也比较古老，因此使用的也不多
  List<String> list2 = Collections.synchronizedList(new ArrayList<>());
  ```

#### 解决方案 CopyOnWriteArrayList

* juc 提供的类 CopyOnWriteArrayList

  ```java
  // 本方法用的最多，叫做写时复制技术
  List<String> list3 = new CopyOnWriteArrayList<>();
  ```

* 读时可以并发读，写是独立写
* 写，通俗的理解是当我们往一个容器添加元素的时候，不直接往当前容器添加，而是先将当前容器进行Copy，复制出一个新的容器，然后新的容器里添加元素，添加完元素之后，再将原容器的引用指向新的容器。
* 这样做的好处是我们可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素。所以CopyOnWrite容器是一种读写分离的思想，读和写不同的容器、最终一致性 以及使用另外开辟空间的思路，来解决并发冲突的思想。
* CopyOnWriteArrayList 的特点
  * CopyOnWriteArrayList适用于读多写少的场景
  * 在并发操作容器对象时不会抛出ConcurrentModificationException，并且返回的元素与迭代器创建时的元素是一致的
  * 容器对象的复制需要一定的开销，如果对象占用内存过大，可能造成频繁的 YoungGC 和 Full GC
  * CopyOnWriteArrayList 不能保证数据实时一致性，只能保证最终一致性
  * 在需要并发操作List对象的时候优先使用 CopyOnWriteArrayList
  * 随着CopyOnWriteArrayList中元素的增加，CopyOnWriteArrayList 的修改代价将越来越昂贵，因此，CopyOnWriteArrayList 适用于读操作远多于修改操作的并发场景中。

#### 集合线程不安全演示 — HashSet，HashMap

* HashSet 和 List 一样，会出现 `java.util.ConcurrentModificationException` 异常

* 解决方案和 List 一样

  ```java
  Set<String> set = new CopyOnWriteArraySet<>();
  ```

* HashMap 是 HashSet 的底层结构，因此 HashMap 会出现和 HashSet 一样的异常

* 解决方案是使用 `ConcurrentHashMap`

  ```java
  Map<String, String> map = new ConcurrentHashMap<>();
  ```

* ConcurrentHashMap 线程安全的原理
  * JDK1.7 中，ConcurrentHashMap 使用的锁分段技术，将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。
  * 上面所说的一段一段就是指 Segment，它继承了 ReentrantLock，具备锁和释放锁的功能。ConcurrentHashMap只有16个Segment，并且不会扩容，最多可以支持16个线程并发写。
  * JDK1.8 放弃了锁分段的做法，采用 CAS 和 synchronized 方式处理并发。以 put 操作为例，CAS 方式确定 key 的数组下标，synchronized 保证链表节点的同步效果。
* JDK1.8 的做法有什么好处呢
  * 减少内存开销，假设使用可重入锁，那么每个节点都需要继承 AQS，但并不是每个节点都需要同步支持，只有链表的头节点（红黑树的根节点）需要同步，这无疑消耗巨大内存。
  * 获得 JVM 的支持，可重入锁毕竟是 API 级别的，后续的性能优化空间很小。synchronized 则是 JVM 直接支持的，JVM 能够在运行时作出相应的优化措施：锁粗化、锁消除、锁自旋等等。使得 synchronized 能够随着 JDK 版本的升级而不改动代码的前提下获得性能上的提升。

* 为什么不使用 HashTable
  * HashTable 容器使用 synchronized 来保证线程安全，但在线程竞争激烈的情况下 HashTable 的效率非常低下。因为多个线程访问HashTable的同步方法时，可能会进入阻塞或轮询状态。
  * 线程1使用 put 进行添加元素，线程2不但不能使用 put 方法添加元素，并且也不能使用 get 方法来获取元素，所以竞争越激烈效率越低。

#### AQS

* AQS全名：AbstractQueuedSynchronizer，是并发容器 J.U.C（java.util.concurrent）下 locks 包内的一个类。它实现了一个**FIFO** (FirstIn、FisrtOut先进先出)的队列。底层实现的数据结构是一个**双向链表**。
* Sync queue：同步队列，是一个双向链表。包括 head 节点和 tail 节点。head 节点主要用作后续的调度。 
* Condition queue：非必须，单向链表。当程序中存在 cindition 的时候才会存在此列表。
* AQS 核心思想是，如果被请求的共享资源空闲，则将当前请求资源的线程设置为有效的工作线程，并且将共享资源设置为锁定状态。如果被请求的共享资源被占用，那么就需要一套线程阻塞等待以及被唤醒时锁分配的机制，这个机制AQS是用 CLH(Craig, Landin, and Hagersten (CLH) locks) 队列锁实现的，即将暂时获取不到锁的线程加入到队列中。
* AQS使用一个int成员变量来表示同步状态，通过内置的 FIFO 队列来完成获取资源线程的排队工作。AQS 使用 CAS 对该同步状态进行原子操作实现对其值的修改。
* 状态信息通过protected类型的getState，setState，compareAndSetState进行操作

#### AQS的大致实现思路

* AQS内部维护了一个CLH队列来管理锁。线程会首先尝试获取锁，如果失败就将当前线程及等待状态等信息包装成一个node节点加入到同步队列sync queue里。 接着会不断的循环尝试获取锁，条件是当前节点为head的直接后继才会尝试。如果失败就会阻塞自己直到自己被唤醒。而当持有锁的线程释放锁的时候，会唤醒队列中的后继线程。
* CLH(Craig,Landin,and Hagersten) 队列是一个虚拟的双向队列（虚拟的双向队列即不存在队列实例，仅存在结点之间的关联关系）。AQS 是将每条请求共享资源的线程封装成一个CLH锁队列的一个结点（Node）来实现锁的分配。

## 6.多线程锁

#### synchronized 实现同步的基础

* java 中的每一个对象都可以作为锁
* 具体表现为下面三种形式
  * 对于普通方法，锁的是当前实例对象
  * 对于静态方法，所的是当前类的 Class 对象
  * 对于同步方法，锁的是 synchronized 括号里配置的对象

#### 公平锁和非公平锁

* ReentrantLock 默认是非公平锁，构造方法传入 true 可设置为公平锁 

  ```java
  // 公平锁
  ReentrantLock lock = new ReentrantLock(true);
  // 非公平锁
  ReentrantLock lock = new ReentrantLock();
  ```

* 非公平锁会出现，某一个线程完成了几乎所有工作，另外一些线程被**饿死**的情况

* 非公平锁
  * 线程饿死
  * 效率高
* 公平锁，在使用资源之前会轮询
  * 效率相对低
  * 在使用前会进行判断，AQS 的等待队列中是否有节点，有了就排队到后面，没有则进行获取锁
* 两种锁的区别
  * 非公平锁在调用 lock 后，首先就会调用 CAS 进行一次抢锁，如果这个时候恰巧锁没有被占用，那么直接就获取到锁返回了
  * 非公平锁在 CAS 失败后，和公平锁一样都会进入到 tryAcquire 方法，在 tryAcquire 方法中，如果发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁，但是公平锁会判断等待队列是否有线程处于等待状态，如果有则不去抢锁，乖乖排到后面。

#### 可重入锁

* synchronized 和 Lock 都是可重入锁
* 一个线程可以进入任何一个 该线程 已经拥有的锁所同步着的代码块
* 可重入锁会多两个属性（1、获得该锁的线程；2、获得该锁的次数），根据第一个属性判断，如果是持有该锁的那个线程又来lock，不会被阻塞（wait），而是在上锁的次数加一（表示这个线程又锁了一次（重入）），只有该线程unlock 的次数达到上锁的次数（即第二个属性等于0），才会唤醒其他线程。

##### 死锁

* 死锁产生的四个条件
  * 互斥，某种资源只能由一个线程同时持有
  * 请求保持，请求其他资源时，不释放自己的资源
  * 不剥夺，获取不到资源就等待，不会抢夺
  * 循环等待，几个线程互相等待，形成了环

## 7.Callable 接口

#### Runnalbe 和 Callable 的比较

* 相同点
  * 都是接口，可以编写多线程程序
  * 都采用Thread.start() 启动线程
* 不同点
  * Runnable 没有返回值；Callable 可以返回执行结果，是个泛型，和 Future、FutureTask 配合可以用来获取异步执行的结果
  * Callable 接口的 call() 方法允许抛出异常；Runnable 的run() 方法异常只能在内部消化，不能往上继续抛
* Callalble 接口支持返回执行结果，需要调用 FutureTask.get() 得到，此方法会阻塞主进程的继续往下执行，如果不调用不会阻塞。

#### FutureTask 创建 Callable 

```java
// FutureTask的使用
FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
// lambda 表达式实现
FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
    System.out.println(Thread.currentThread().getName() + " come in callable");
    return 1024;
});
// 开启任务
new Thread(futureTask2, "lucy").start();
```

#### FutureTask 特点

* 一个适配器，即实现了 Runnable 接口，也实现了 Future 接口，通过 Callable 执行线程任务

* 可以获取线程的执行情况

  ```java
   // 判断任务 2 是否完成
  while (!futureTask2.isDone()) {
      System.out.println("wait...");
  }
  
  // 调用 FutureTask 的 get 方法，获取线程执行结果
  System.out.println(futureTask2.get()); // 第一次 get
  System.out.println(futureTask2.get()); // 第二次 get
  ```

* 当用户第一次获取线程结果时，会等待线程执行完成后获得结果，如果未执行完，则阻塞等待；当第二次获取时，直接返回线程结果，无需再次执行线程

## 8.JUC 中的辅助类

#### CountDownLatch (减少计数)

* CountDownLatch 能够使一个线程在等待另外一些线程完成各自工作之后，再继续执行。使用一个计数器进行实现。计数器初始值为线程的数量。当每一个线程完成自己任务后，计数器的值就会减一。当计数器的值为 0 时，表示所有的线程都已经完成一些任务，然后在 CountDownLatch 上等待的线程就可以恢复执行接下来的任务。
* CountDownLatch的不足
  * 是一次性的，计算器的值只能在构造方法中初始化一次，之后没有任何机制再次对其设置值，当 CountDownLatch使用完毕后，它不能再次被使用。

* 例子，六个同学走完后，班长再锁门

  ```java
  // 6 个同学离开教室之后，班长锁门
  public static void main(String[] args) {
  
      for (int i = 0; i < 6; i++) {
          new Thread(() -> System.out.println(Thread.currentThread().getName() + " 号同学离开了！"), String.valueOf(i)).start();
      }
  
      System.out.println(Thread.currentThread().getName() + " 班长锁门");
  
  }
  /* 打印结果，即会造成6个线程并没有执行完毕，主线程就锁门的情况。
      5 号同学离开了！
      main 班长锁门
      0 号同学离开了！
      3 号同学离开了！
      2 号同学离开了！
      4 号同学离开了！
      1 号同学离开了！
  */
  ```

* 使用 CountDownLatch 解决上面的问题

  ```java
  // 创建 CountDownLatch 对象
  CountDownLatch count = new CountDownLatch(6);
  
  for (int i = 0; i < 6; i++) {
      new Thread(() -> {
          System.out.println(Thread.currentThread().getName() + " 号同学离开了！");
          // 初始值减一
          count.countDown();
      }, String.valueOf(i)).start();
  }
  count.await();
  System.out.println(Thread.currentThread().getName() + " 班长锁门");
  ```

#### CyclicBarrier (循环栅栏)

* 一个同步辅助类，它允许一组线程互相等待，直到达到某个公共屏障点（common barrier point）。在设计一组固定大小的线程的程序中，这些线程必须不时的相互等待，此时 CyclicBrrier 很有用。

* 因为该 barrier 在释放等待线程后可以重用。所有称它为循环的 barrier

* 例子，集齐七颗龙珠，召唤神龙

  ```java
  // 创建 CyclicBarrier 对象，设定固定值，并写出完成后实现什么功能
  CyclicBarrier cyclicBarrier = new CyclicBarrier(number, () -> {
      System.out.println("七颗龙珠，召唤神龙！！！");
  });
  
  for (int i = 1; i <= 7; i++) {
      new Thread(() -> {
          try {
              System.out.println(Thread.currentThread().getName() + "星球");
              // 等待
              cyclicBarrier.await();
          } catch (InterruptedException | BrokenBarrierException e) {
              e.printStackTrace();
          }
      }, String.valueOf(i)).start();
  }
  ```

* 上面的例子中，七个线程互相等待，即等待队列达到七个之后才会执行循环栅栏的代码。
* 如果不够七个线程，那么所有线程均会被阻塞
* 循环栅栏使用完毕后可以重置，并再次使用，CountDownLatch 不能重置，只能使用一次

#### Semaphore (信号灯)

* 一个计数器信号量。从概念上讲，信号量维护了一个许可集。如有必要，在许可可用之前，会阻塞每一个 acquire()，然后再获取该许可。

* 自我理解，像操作系统的 pv 操作

* 例子，6 辆车，三个停车位

  ```java
  // 创建 Semaphore，并初始化 3 个信号量
  Semaphore semaphore = new Semaphore(3);
  
  // 模拟六辆汽车
  for (int i = 1; i <= 6; i++) {
      new Thread(() -> {
          // 抢占车位
          try {
              // 抢到车位之后
              semaphore.acquire();
              System.out.println(Thread.currentThread().getName() + " 抢到了车位");
              // 停车了随机的时间
              TimeUnit.SECONDS.sleep(new Random().nextInt(10));
              System.out.println(Thread.currentThread().getName() + " 离开了车位");
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              // 释放车位
              semaphore.release();
          }
      }, String.valueOf(i)).start();
  }
  ```

## 9.ReentrantReadWriteLock 读写锁

#### 悲观锁和乐观锁

* 悲观锁
  * 总是假设最坏的情况，每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会阻塞直到它拿到锁（共享资源每次只给一个线程使用，其它线程阻塞，用完后再把资源转让给其它线程）。传统的关系型数据库里边就用到了很多这种锁机制，比如行锁，表锁等，读锁，写锁等，都是在做操作之前先上锁。Java中synchronized和ReentrantLock等独占锁就是悲观锁思想的实现。
* 乐观锁
  * 总是假设最好的情况，每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据，可以使用版本号机制和CAS算法实现。乐观锁适用于多读的应用类型，这样可以提高吞吐量，像数据库提供的类似于write_condition机制，其实都是提供的乐观锁。在Java中java.util.concurrent.atomic包下面的原子变量类就是使用了乐观锁的一种实现方式CAS实现的。

#### ReentrantReadWriteLock 读写锁

* 读写锁，一个资源可以被多个读线程访问，或者可以被一个写线程访问，但是不能同时存在读写线程，读写互斥，读读共享
  * 可能会导致的问题
    * 造成锁饥饿，一直读，没有写操作
    * 读操作时无法进行写，只有读完后才可以写

* 例子，五个线程写数据，五个线程读数据

  ```java
  class MyCache {
  
      // 创建 map 集合
      private volatile Map<String, Object> map = new HashMap<>();
  
      // 创建读写锁对象
      private ReadWriteLock rwLock = new ReentrantReadWriteLock();
  
      // 放数据
      public void put(String key, Object value) {
          // 加上写锁
          rwLock.writeLock().lock();
          System.out.println(Thread.currentThread().getName() + " 正在写");
  
          // 暂停一会
          try {
              TimeUnit.SECONDS.sleep(1);
              // 放数据
              map.put(key, value);
              System.out.println(Thread.currentThread().getName() + " 写完了 " + key);
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              // 解锁
              rwLock.writeLock().unlock();
          }
      }
  
      // 取数据
      public Object get(String key) {
  
          // 添加读锁
          rwLock.readLock().lock();
          Object result = null;
          System.out.println(Thread.currentThread().getName() + " 正在读取数据");
  
          // 暂停一会
          try {
              TimeUnit.SECONDS.sleep(1);
              // 读数据
              result = map.get(key);
              System.out.println(Thread.currentThread().getName() + " 取完了 " + key);
          } catch (InterruptedException e) {
              e.printStackTrace();
          } finally {
              // 释放锁
              rwLock.readLock().unlock();
          }
  
          return result;
      }
  }
  
  public static void main(String[] args) throws InterruptedException {
      MyCache cache = new MyCache();
  
      // 创建线程写数据
      for (int i = 1; i <= 5; i++) {
          final int num = i;
          new Thread(() -> {
              cache.put(num + "", num + "");
          }, String.valueOf(i)).start();
      }
  
      TimeUnit.SECONDS.sleep(1);
  
      // 创建线程读数据
      for (int i = 1; i <= 5; i++) {
          final int num = i;
          new Thread(() -> {
              cache.get(num + "");
          }, String.valueOf(i)).start();
      }
  }
  ```

#### 锁降级

* 将读写锁降级为读锁，主要是为了保证数据的可见性，如果当前线程不获取读锁而是直接释放写锁， 假设此刻另一个线程（记作线程T）获取了写锁并修改了数据，那么当前线程无法感知线程T的数据更新。
* 当前线程无法感知线程T的数据更新，指的是线程A使用数据时，并不知道别的线程已经更改了数据，所以使用的是线程T的修改结果。因此通过锁降级来保证数据每次修改后的可见性。

## 10.BlockingQueue 阻塞队列

#### 阻塞队列概述

* 阻塞队列是一个队列，通过一个共享的队列，可以使得数据由一端输入，另一端输出
* 当队空时，读取的线程会被阻塞
* 当队满时，写入的线程会被阻塞

#### 阻塞队列的架构

* 父接口，Collection，Queue等
* 子类，ArrayBlockingQueue，LinkedBlockingQueue，LinkedBlockingDeque，PriorityBlockingQueue

#### 阻塞队列的分类

* ArrayBlockingQueue（常用）
  * 基于数组的阻塞队列实现，在 ArrayBlockingQueue 内部维护了一个定长数组，以便缓存队列中的数据对象，这是一个常用的阻塞队列，出了一个定长的数组外，ArrayBlockingQueue 内部还保存了两个整形变量，表示队头和队尾
  * ArrayBlockingQueue 生产者放入数据和消费者读取数据时都是共用一个锁对象，即无法同时读写，这点和 LinkedBlockingQueue 不同
  * ArrayBlockingQueue 和 LinkedBlockingQueue 不同的地方还有，ArrayBlockingQueue 在插入或删除元素不会产生或销毁任何额外的对象实例，而 LinkedBlockingQueue 会生成一个额外的 Node 对象，在长时间内需要高并发的系统中，对 GC 的影响还有一定的区别
  * 在创建 ArrayBlockingQueue 时，可以选择锁为公平锁或者是非公平锁
* LinkedBlockingQueue（常用）
  * 基于链表的阻塞队列，同 ArrayBlockingQueue 一类似，其内部也维持着一个缓冲队列。该队列由链表构成。当生产者往队列中放入一个数据时，队列会从生产者手中获取数据，并缓存在队列尾部，而生产者立即返回；
  * 只有当缓冲区达到最大容量时（可以在构造时指定），才会阻塞生产者队列，直到消费者消费完后，生产者线程会被唤醒，反之对于消费者队列也一样
  * LinkedBlockingQueue 对于生产者和消费者采用了独立的锁来进行控制数据同步，因此有利于提高整个队列的并发性能
  * 队列的最大值默认为 Integer.MAX_VALUE
* DelayQueue
  * DelayQueue 中的元素只有当其指定的延迟时间到了，才能够从队列中获取该元素。DelayQueue 是一个没有大小限制的队列，因此插入数据永远不会被阻塞，而只有获取操作会被阻塞
  * 使用优先队列实现的延迟无界阻塞队列
* PriorityBlockingQueue
  * 基于优先级的阻塞对象，优先级的判断通过构造函数传入的 Compator 对象来决定
  * PriorityBlockingQueue 不会阻塞数据生产者，而只有在没有数据时，阻塞消费者
  * PriorityBlockingQueue 使用的是公平锁
  * 支持优先级排序的无界阻塞队列

#### 阻塞队列的核心方法

| 方法类型 | 抛出异常  | 特殊值   | 阻塞   | 超时                 |
| -------- | --------- | -------- | ------ | -------------------- |
| 插入     | add(e)    | offer(e) | put(e) | offer(e, time, unit) |
| 移除     | remove()  | poll()   | take() | poll(time, unit)     |
| 检查     | element() | peek()   | 不可用 | 不可用               |

* 抛出异常列，指的是，插入，删除，检查方法如果出现问题，会抛出异常
* 特殊值列。插入方法成功会返回 true，失败会返回 false，移除方法会返回出队的元素，队列无元素则返回 null
* 阻塞列，当队列满时，生产者 put 元素时会被阻塞，直到添加成功或者被中断，take元素时，如果队列为空，则消费者对队列会被阻塞直到生产者生产出元素才会继续运行
* 超时列，当阻塞队列满时，队列会阻塞生产者线程一段时间，超过限时后生产者线程退出

## 11.ThreadPool 线程池

#### 线程池概述

* 线程池，一种线程使用模式。线程过多会带来调度开销，进而影响缓存局部性和整体性能。而线程池维护这多个线程，等待着监督管理者分配可并发执行的任务。这避免了在处理短时间任务时创建线程的代价。线程池不仅能够保证内核的充分利用，还能防止过分调度
* 线程池做的优势，线程池做的工作只要是控制运行线程的数量，处理过程中将任务放入队列，然后在线程创建后启动任务，如果线程数量超过了最大数量，超出数量的线程排队等候，其他线程执行完毕，在从队列中取出来任务执行
* 线程池的特点
  * 降低资源消耗，通过重复利用已经创建的线程降低线程创建和销毁的消耗
  * 提高响应速度，当任务到达时，任务可以不需要等待线程创建就能立即执行
  * 提高线程的可管理性，线程时稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控

####  线程池架构

* Java 线程池时通过 Executor 框架实现的，该框架中用到了 Executor，Executors，ExecutorService ThreaPoolExecutor 这几个类

  ```text
                                          	Execrot(I)
                                          	     |
                                           ExecutorService(I)
                                                   |
                           ---------------------------------------------------
                          |													|
    Exectors(C)       AbstractExecutorService(C)		     ScheduledExectorService(C)
      (工具类）            |												    |
                      ThreadPoolExector(C)		    						|
                          |                                           		|
                          -----------------------------------------------------
                                                      |
                                          ScheduledThreadPoolExector(C)
  ```

#### 线程池使用方式

```java
// 一池 N 线程 
Executors.newFixedThreadPoll(int);
// 一个任务一个任务执行，一池一线程
Executors.newSingleThreadExecutor();
// 线程池根据需求创建，可扩容，遇强则强，如果线程不够用时，会申请新的线程
Executors.newCachedThreadPool();
```

* 例子，五个窗口服务 10个顾客

```java
public static void main(String[] args) {
    // 创建一池 N 线程
    ExecutorService threadPool1 = Executors.newFixedThreadPool(5);

    // 创建一池一线程
    ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

    // 创建一池可扩容线程
    ExecutorService threadPool3 = Executors.newCachedThreadPool();

    // 10 个顾客，模拟 5 个窗口进行处理
    try {
        for (int i = 1; i <= 10; i++) {
            threadPool3.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " 办理业务");
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // 结束当前任务，归还线程
        threadPool3.shutdown();
    }
}
```

#### 线程池底层原理

* 上面三种创建方式，最终都会由下面方式创建 `ThreadPoolExecutor`

  ```java
  public static ExecutorService newFixedThreadPool(int nThreads) {
      return new ThreadPoolExecutor(nThreads, nThreads,
                                    0L, TimeUnit.MILLISECONDS,
                                    new LinkedBlockingQueue<Runnable>());
  }
  
  public static ExecutorService newSingleThreadExecutor() {
      return new FinalizableDelegatedExecutorService
          (new ThreadPoolExecutor(1, 1,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>()));
  }
  public static ExecutorService newCachedThreadPool() {
      return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                    60L, TimeUnit.SECONDS,
                                    new SynchronousQueue<Runnable>());
  }
  ```

* 线程池的七个参数
  * int corePoolSize，线程池的核心线程数量，或者是常驻线程数量
  * int maximumPoolSize，最大线程数量
  * long KeepAliveTime，线程的存活时间，即多长时间线程不用，就恢复到常驻线程数量
  * TimeUnit unit，存活时间的单位
  * BlockingQueue<Runnable> workQueue 阻塞队列，线程不够使用时，会放入阻塞队列中
  * ThreadFactory threadFactory，线程工厂，用于创建线程
  * RejectedExecutionHandler handler，拒绝策略，即达到最大线程数量的使用数量时，拒绝新的服务的策略

#### 线程池的底层工作流程

* 创建线程池，常驻线程数 2，最大线程数 5，阻塞队列长度 3
  * 1.线程池执行 execute() 方法时，创建线程，定义线程池时不会创建线程
  * 2.首先从常驻线程中获取线程，获取到就执行任务，第二个任务依然从常驻线程中获取线程并执行
  * 3.第三个任务出现时，常驻线程已经用完，则进入阻塞队列，第4-6个任务出现时，阻塞队列已满，则创建新的三个线程，**此时并不会优先处理阻塞队列的任务，而是处理新的第六个任务**
  * 4.如果新创建的 3 个线程也已经处理完毕了，此时则根据拒绝策略来进行处理
* 线程池的拒绝策略
  * **AbortPolicy(默认策略)**：直接抛出 RejectedExecutionException 异常来阻止系统的正常运行
  * **CallerRunsPolicy**，”调用者运行“，一种调节机制，该策略下不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量
  * **DiscardOldestPolicy**，抛弃队列中等待最久的任务，然后把当前任务加入到队列中，尝试再次提交当前任务
  * **DiscardPolicy**，该策略直接丢弃无法处理的任务，即最后提交的任务，不予处理，也不抛弃异常。如果允许任务丢失，这是一种最好的策略

#### 自定义线程池

* 实际工作过程中，我们一般都不用 Executors. 的方式创建线程池，而是自己创建

* 阿里巴巴 Java 开发手册内容

  * 线程池不允许使用 Executors 创建，而是通过 ThreadPoolExecutor 的方式，这样的处理方式让写的人更加明确线程池的运行规则，规避资源耗尽的风险
  * 说明，Executors 返回的线程池对象的弊端
    * FixedThreadPool 和 SingleThreadPool
      * 允许的请求队列长度为 Integer.MAX_VALUE，可能会堆积大量请求，从而导致 OOM
    * CachedThreadPool
      * 允许创建的线程数量为 Integer.MAX_VALUE 可能会创建大量的线程，从而导致 OOM
    *  ScheduledThredPool
      * 允许的请求队列长度为 Integer.MAX_VALUE 可能会堆积大量请求，从而导致 OOM

* 自定义线程池的过程

  ```java
  // 自定义线程池
  ExecutorService threadPool = new ThreadPoolExecutor(
      2, // 常驻线程数量
      5, // 最大线程数量
      2L, // long 类型，最大存活时间
      TimeUnit.SECONDS, // 最大存活时间的单位，秒
      new ArrayBlockingQueue<>(3), // 阻塞队列
      Executors.defaultThreadFactory(), // 创建线程的工厂
      new ThreadPoolExecutor.AbortPolicy() // 拒绝策略
  );
  ```

## 12 Fork/Join 分支合并框架

#### Fork/Join简介

* Fork/Join 可以将一个大的任务拆分成多个子任务并行处理，最后将子任务结果合并成最后的计算结果，并进行输出。
* Fork/Join 要完成两件事
  * Fork，将一个复杂的任务拆分成小任务
  * Join，把分拆的任务结果进行合并
* ForkJoin 类结构

    ```text
                                                Execrot(I)
                                                     |
                                             ExecutorService(I)
                                                     |
                 Contended(I)           	AbstractExecutorService(C)
                    |        						 |
                    ---------------------------------
                                |
                        ForkJoinPool(C)
    ```

    ```text
                            Execrot(I)					Serializable(I)
                                |                      	     |
                                -----------------------------
                                             |
                                         ForkJoinTask(C)
                                             |
                                         RecursiveTah(C)
    ```

* 例子，1 + 2 + 3 + 4 + ... + 100

  ```java
  import java.util.concurrent.ExecutionException;
  import java.util.concurrent.ForkJoinPool;
  import java.util.concurrent.ForkJoinTask;
  import java.util.concurrent.RecursiveTask;
  
  class MyTask extends RecursiveTask<Integer> {
  
      // 拆分的差值，即最长计算不超过10个数
      private static final int value = 10;
  
      // 开始值，结束值
      private int begin;
      private int end;
  
      // 返回结果
      private int result;
  
      // 创建有参数构造函数
      public MyTask(int begin, int end) {
          this.begin = begin;
          this.end = end;
      }
  
      // 实现拆分和合并的逻辑
      @Override
      protected Integer compute() {
          // 判断，如果相加的两个数之差是否大于10
          if ((end - begin) <= 10) {
              // 计算
              for (int i = begin; i <= end; i++) {
                  result += i;
              }
          } else {
              // 继续拆分
              // 获取数据中间值
              int mid = (begin + end) / 2;
              // 拆分左边
              MyTask task01 = new MyTask(begin, mid);
  
              // 拆分右边
              MyTask task02 = new MyTask(mid + 1, end);
  
              // 拆分
              task01.fork();
              task02.fork();
  
              // 合并结果
              result = task01.join() + task02.join();
          }
          return result;
      }
  }
  public class Demo01ForkJoin {
  
      public static void main(String[] args) throws ExecutionException, InterruptedException {
  
          // 创建 task 对象
          MyTask task = new MyTask(1, 100);
  
          // 创建分支合并对象
          ForkJoinPool forkJoinPool = new ForkJoinPool();
  
          // 提交任务
          ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
          Integer result = submit.get();
          System.out.println(result);
  
          // 关闭
          forkJoinPool.shutdown();
      }
  
  }
  ```

## 13.CompletableFuture 异步回调



* 同步调用和异步调用的举例

  ```java
  import java.util.concurrent.CompletableFuture;
  import java.util.concurrent.ExecutionException;
  
  public class Demo01Async {
      public static void main(String[] args) throws ExecutionException, InterruptedException {
          // 异步调用没有返回值
          CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
              System.out.println(Thread.currentThread().getName() + " completableFuture1");
          });
          // 结束调用
          completableFuture1.get();
  
          // 异步调用有返回值
          CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
              System.out.println(Thread.currentThread().getName() + " completableFuture2");
              // 模拟异常
              // int i = 1 / 0;
              return 10;
          });
          // 判断是否完成
          completableFuture2.whenComplete((t, u) -> {
              System.out.println(t); // t 为方法的返回值
              System.out.println(u); // u 为异常信息
          }).get();
  
      }
  }
  ```
