# JVM

## JVM 与 JAVA 体系结构

#### JVM 的整体架构

* HotSpot VM 时目前市面上高性能虚拟机的代表作之一

* 采用解释器与即时编译器并存的架构
* HotSpot 是一种基于栈的指令集架构，另一种是基于寄存器的指令集架构（典型的如 x86）
* 基于栈式架构的特点
  * 设计和实现简单，适用于资源受限的系统
  * 避开了寄存器的分配难题，使用零地址指令分配方式
  * 指令流中的指令大部分是零地址指令，其执行过程依赖于操作栈，指令集更小，编译器容易实现
  * 不需要硬件支持，可移植性好，更好实现跨平台

#### Java代码的执行流程

* 1.Java 源码
* 2.编译，词法分析，语法分析，语义分析，字节码生成
* 3.得到字节码
* 4.类加载器
* 5.字节码校验器
* 6.翻译字节码，解析执行
* 7.JIT 编译器，编译为机器指令
* 8.操作系统

#### JVM 的生命周期

* **启动**，Java 虚拟机的启动时通过引导类加载器（bootstrap class loader）创建的一个初始类（initial class）来完成的，这个类时由虚拟机的集体实现指定的
* **执行**，一个运行中的 Java 虚拟机有一个清晰的任务，执行Java程序。程序开始执行时他才运行，程序结束时就停止。执行一个 Java 程序的时候，真真正正执行的是 一个 Java 虚拟机的进程
* **退出**，有以下几种情况
  * 程序正常执行退出
  * 执行过程中遇到了异常或错误而异常终止
  * 操作系统出现错误而导致 Java 虚拟机进程终止
  * 某线程调用 Runtime 类或 System 类的 exit 方法，或 Runtime 类的 halt 方法，并且 Java 安全管理器也允许这次 exit 或 halt 方法
  * 除此之外，JNI （Java Native Interface）规范描述了用 JNI Invocation API 来加载卸载 Java 虚拟机时，Java 虚拟机退出的情况

#### JVM 类的加载过程

* Loading，加载
  * 引导类加载器（BootStrapClassLoader）
  * 扩展类加载器（ExtensionClassLoader）
  * 应用类加载器（ApplicationClassLoader）
* Linking，链接
  * 验证（Verify）
  * 准备（prepare）
  * 解析（Resolve）
* Initialization，初始化

## 类加载器与类加载过程

#### 简介

* 类加载器子系统负责从文件系统或网络中加载 class 文件，class 文件在文件开头有特定的文件标识**（CAFE BABE)**
* ClassLoader 只负责 class 文件的加载，置于它是否可以运行，则由 ExecutionEngine 决定
* 加载的类信息存放于一块称为方法区的内存空间。除了类的信息外，方法区中还存放运行时的常量池信息，还可能包括字符串字面量和数字常量（这部分常量信息时class文件中常量池部分的内存映射）

* 类加载器 ClassLoader
  * 1.class file 存放在磁盘上，可以理解为设计师画在纸上的模板，而最终这个模板在执行的时候时要加载到 JVM 中，根据文件实例化出 n 个一模一样的示例
  * 2.class file 加载到 JVM 中，被称为 DNA 元数据模板，放在方法区
  * 3.在 .class 文件 -> JVM -> 最终称为元数据模板，此过程需要一个运输工具（类装载器 Class Loader）

* 类加载过程

  ```text
  		开始
  		 |
  装载类 HelloLoader了吗？   ---N--->  ClassLoader 装载   ---N--->   抛出异常
  		 | Y                              |
  		继续 <--------------Y--------------|
  		 |
  初始化 HelloLoader  ------> 调用 HelloLoader.main()
  									|
  								   结束
  ```

#### 加载

* 通过一个类的全限定类名获取定义此类的二进制字节流
  * 可以从本地系统中直接获取
  * 通过网络获取，典型场景 Web Applet
  * 从 zip 压缩包中获取，成为日后 jar，war 格式的基础
  * 运行时计算生成，使用最多的时动态代理技术
  * 由其他文件生成，典型场景：JSP 应用
  * 从专有数据库读取 .class 比较少见
  * 从加密文件中获取，典型的防 Class 文件被反编译的保护措施
* 将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构
* 在内存中生成一个代表这个类的 java.lang.Class 对象，作为方法区这个类的各种数据访问入口

#### 链接 — 验证

* 目的在于确保 Class 文件的字节流中包含信息符合当前虚拟机的要求，保证被加载类的正确性，不会危害虚拟机的安全
* 包括四种验证，文件格式验证，元数据验证，字节码验证，符号引用验证

#### 链接 — 准备

* 为变量分配内存并设置该类变量的默认初始值，即零值
* 这里不包含用 final 修饰的 static，因为 final 在编译的时候就分配了，准备阶段会显示初始化
* 这里不会为实例变量分配初始化，类变量会分配在方法区中，而实例变量会随着对象一起分配到 Java 堆中

#### 链接 — 解析

* 将常量池内的符号引用转换为直接引用的过程
* 事实上解析操作往往会伴随着 JVM 在执行完初始化之后在执行。
* 符号引用就是一组符号来描述所引用的目标符号引用的字面量形式明确定义在 《Java 虚拟机规范》的 Class 文件格式中。直接引用就是直接指向目标的指针、相对偏移量或一个间接定位到目标的句柄
* 解析动作主要针对类或接口、字段、类方法、接口方法、方法类型等。对应常量池中的 CONSTANT_Class_info、CONSTANT_Class_info、CONSTANT_Fieldref_info、CONSTANT_Methodref_info 等

#### 初始化 Initialization

* 初始化阶段就是执行类构造器方法<clinit>() 的过程
* 此方法不需要定义，是 javac 编译器自动收集所有类变量的赋值动作和静态代码块中的语句合并而来
* 构造方法中指令按语句在源文件中出现的顺序执行
* <clinit>() 不同于类的构造器（构造器是虚拟机视角下的<init>()）
* 若该类具有父类，JVM 会保证子类的 <clinit>()  执行前，父类的 <clinit>()  已经执行完毕
* 虚拟机必须保证一个类的 <clinit>()  在多线程下被同步加锁
* 例子 1

    ```java
    public clas ClassInitTest {
    
        private static num = 1;
    
        static {
            num = 2;
            number = 20;
            System.out.println(number); // 报错，非法的前向引用 
        }
        /*
             linking 之 prepare number = 0;
             initial 20 -> 10;
        */
        private static int number = 10;
    
        public static void main(String[] args) {
            System.out.println(ClassInitTest.num); // 20
            System.out.println(ClassInitTest.number); // 10
        }
    
    }
    ```

* 例子 2，子类加载前后先加载父类

  ```java
  public class ClassInitTest {
  	static class Father {
          public static int A = 1;
          static {
              A = 2;
          }
      }
      
      static class Son extends Father {
          public static int B = A;
      }
      
      public static void main (String[] args) {
          // 加载 Father 类，其次加载 Son 类
          System.out.println(Son.B) // 2
      }
  }
  ```

* 例子 3，多线程下的情况，static 中的代码块只会执行一次，因此会被加锁，两个线程只会加载一次

  ```java
  public class DeadThreadTest {
  	public static void main(String[] args) {
          Runnable r = () -> {
              System.out.println(Thread.currentThread.getName() + "start");
              DeadThread dead = new DeadThread();
              System.out.println(Thread.currentThread.getName() + "end");
          };
          
          Thread t1 = new Thread(r, "线程1");
          Thread t2 = new Thread(r, "线程2");
          
          t1.start();
          t2.start();
      }
  }
  
  class DeadThread {
      static {
          if (true) {
              System.out.println(Thread.currentThread.getName() + "初始化当前类");
              while (true) {
                  
              }
          }
      }
  }
  ```

#### 类加载器的分类

* JVM 支持两种类型的类加载器，分别为引导类加载器（Bootstrap ClassLoader）和自定义类加载器（User-Defained ClassLoader）

* 从概念上来讲，自定义类加载器一般指的是程序中由开发人员自定义的一类加载器，但 JVM 规范却没有这么定义，而是将所有派生于抽象类 ClassLoader 的类加载器都划分为自定义类加载器

* 无论类加载器的类型如何划分，在程序中我们最常见的始终只有三个，即，引导类加载器，扩展类加载器，应用类加载器

  ```java
  public static void main(String[] args) {
      // 获取系统类加载器
      ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
      System.out.println(systemClassLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@2437c6dc
      
      // 获取其上层，扩展类加载器
      ClassLoader extClassLoader = systemClassLoader.getParent();
      System.out.println(extClassLoader); // jdk.internal.loader.ClassLoaders$PlatformClassLoader@7c30a502
      
      // 试图获取引导类加载器，获取失败
      ClassLoader bootstrapClassLoader = extClassLoader.getParent();
      System.out.println(bootstrapClassLoader); // null
      
      // 对于用户自定义的类来说，即由系统类加载器加载
      ClassLoader classLoader = Test.class.getClassLoader();
      System.out.println(classLoader); // jdk.internal.loader.ClassLoaders$AppClassLoader@2437c6dc
      
      // String 类使用引导类加载器加载， --> java 的核心类库都是使用引导类加载器加载
      ClassLoader classLoader1 = String.class.getClassLoader();
      System.out.println(classLoader1); // null
      
  }
  ```

* 引导类加载器（BootStrap ClassLoader）
  * 这个类加载使用 C/C++ 实现的，嵌套在 JVM 内部
  * 它用来加载 Java 的核心类库（JAVA_HOME/jre/lib/rt.jar、resources.jar 或 sun.boot.class.path 路径下的内容），用于提供 JVM 自身需要的类
  * 并不继承自 java.lang.ClassLoader，没有父加载器
  * 加载扩展类和应用类加载器，并指定为他们的父类加载器
  * 出于安全考虑，Bootstrap 引导类家子气只加载包名为 java、javax、sun 等开头的类
* 扩展类加载器（Extension ClassLoader）
  * Java 语言编写，由 sun.misc.Launcher$ExtClassLoader 实现
  * 派生于 ClassLoader
  * 父类加载器为父类加载器
  * 从 java.ext.dirs 系统属性所指定的目录中加载类库，或从 JDK 的安装目录的 jre/lib/ext 子目录下载加载类看。如**果用户创建的 JAR 放在此目录下，也会自动由扩展类加载器加载**

* 应用程序类加载器
  * java 语言编写，由 sun.misc.Launcher$AppClassLoader 实现
  * 派生于 ClassLoader 类
  * 父类加载器为扩展类加载器
  * 它负责加载环境变量 classpath 或系统属性 java.class.path 指定的路径下的类库
  * 该类加载是程序中默认的类加载器，一般来说，Java 应用的类都是由它来完成加载
  * 通过 ClassLoader#getSystemClassLoader() 方法可以获取到该类加载器

* 用户自定义类加载器
  * 在 Java 的日常应用程序开发中，类的加载几乎是由上述 3 中类加载器相互配合执行的，在必要时，我们还可以自定义类加载器，来定制类的加载方式
  * 为什么要自定义类加载器
    * 隔离加载类
    * 修改类加载的方式
    * 扩展加载源
    * 防止源码泄露

* 关于 ClassLoader

  * ClassLoader 是一个抽象类，其后所有的类加载器都继承自 ClassLoader（不包括启动类加载器）

  * 方法：

    | 方法名称                                              | 描述                                                         |
    | ----------------------------------------------------- | ------------------------------------------------------------ |
    | getParent()                                           | 返回该类加载器的超类加载器                                   |
    | loadClass(String name)                                | 加载名称为 name 的类，返回结果为 java.lang.Class 类的实例    |
    | findClass(String name)                                | 查找名称为 name 的类，返回结果为 java.lang.Class 类的实例    |
    | findLoadedClass(String name)                          | 查找名为 name 的已经被加载过的类，返回结果为 java.lang.Class 类的实例 |
    | defineClass(String name, byyte[] b, int off, int len) | 把字节数组 b 中的内容转换为一个 java 类，返回结果为 java.lang.Class 类的实例 |
    | resolveClass(Class<?> e)                              | 连接指定的一个 Java 类                                       |

* 获取 ClassLoader 的方式

  ```java
  // 1.获取当前类的 ClassLoader
  class.getClassLoader();
  
  // 2.获取当前线程上下文的 ClassLoader
  ClassLoader classloader = Thread.currentThread.getContextClassLoader();
  
  // 3.获取当前系统的 ClassLoader
  ClassLoader.getSystemClassLoader();
  
  // 4.获取调用者的 ClassLoader
  DriverManager.getCallerClassLoader();
  ```

## 双亲委派机制

#### 介绍

* Java 虚拟机的对 class 文件采用的是按需加载的方式，也就是说当需要使用该类时才会将它的 class 文件加载到内存生成 class 对象。而且加载 某个类的 class 文件时，Java 采取的时双亲委派模式，即把请求交给父类处理，它是一种任务委派模式。

#### 工作原理

	* 如果一个类加载器收到了类加载请求，他不会自己先去加载，而是把这个请求委托给父类的加载器区执行
	* 如果父类加载器还存在其父类加载器，则进一步向上委托，依次递归，请求最终到达顶层的启动类加载器
	* 如果父类加载器可以完成类的加载，就成功返回，如果不能完成，子类加载器才会尝试自己加载，这就是双亲委模式。

* 一个小问题

  ```java
  package java.lang;
  public class String {
      
      /*
      	运行此方法会出现找不到 main 方法，因为双亲委派机制，String 类的加载被委派到引导类加载器，而jdk所带的String 类中是没有主方法的，所以无法运行
      */
      public static void main(String[] args) {
          System.out.println("hello, String");
      }
  }
  ```

#### 优势

* 避免类的重复加载
* 保护程序安全，防止核心 api 被随意篡改
  * 比如，自定义类 java.lang.String
  * 比如自定义 java.lang 包，java.lang.Joush，会报错误

* 沙箱安全机制
  * 自定义 String 类，但是在加载自定义 String 类的时候会率先使用引导类加载器加载，而引导类加载器在加载的过程中会先加载 jdk 自带文件，报错信息说没有main方法，就是因为加载的时 rt.jar 包中的 String 类，这样可以保证对 java 核心源代码的保护，这就是沙箱安全机制

#### 其他

* 在 JVM 中表示两个对象是否时为同一个类存在的两个必要条件
  * 类的完整名必须一致，包括包名
  * 加载这个类的 ClassLoader（指的是 ClassLoader 实例对象） 必须相同
* 换句话说，在 JVM 中，即使这两个类对象来源同一个 Class 文化，被同一个虚拟机所加载，但只要加载他们的 ClassLoader 实例对象不同，那么这两个类对象也是不相等的
* JVM 必须知道一个类型是由启动类加载器加载的还是由用户类加载器加载的。如果一个类型是由用户类加载器加载的，那么 JVM 会**将这个类加载器的一个引用作为类型信息的一部分保存在方法区中**。当解析一个类型到另一个类型的引用的时候，JVM 需要保证这两个类加载器是相同的

#### 类的主动使用和被动使用

* 主动使用分七种情况
  * 创建类的实例
  * 访问某个类或接口的静态变量，或者对该静态变量赋值
  * 调用类的静态方法
  * 反射（比如，Class.forName(com.joush.Test)）
  * 初始化一个类的子类
  * Java 虚拟机启动时被标明为启动类的类
  * JDK 7 开始提供的动态语言支持，java.lang.invoke.MethodHandle 实例的解析结果 REF_getStatic、REF_putStatic、REF_invokeStatic 句柄对应的类没有初始化，则初始化
* 除了以上七种情况，其他使用 Java 类的方式都被看做是对类的被动使用，都不会导师类的初始化

# 运行时数据区及线程

 #### 简介

* 内存是非常重要的系统资源，是硬盘和 CPU 的中间仓库及桥梁，承载着操作系统和应用程序的实时运行，JVM 内存布局规定了 Java 运行过程中内存申请、分配、管理的策略，保证了 JVM 的高效运行

* 不同的 JVM 对于内存的划分和管理机制存在部分差异，结合 JVM 虚拟机规范

* 内存图（1.8之前）

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/jdk1.8_before_memory.png?raw=true)

* 内存图（1.8之后）

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/jdk1.8_after_memory.png?raw=true)

* Java 虚拟机定义了若干种程序运行期间会使用到的运行时数据区，其中有一些会随着虚拟机启动而创建，随着虚拟机退出而销毁。另外一些则与线程一一对应，这些与线程对于的数据区会随这线程开始和结束而创建和销毁

  * 每个线程（灰色部分）：独立拥有程序计数器，栈，本地栈

  * 线程间共享（橙色部分）：堆，对外内存（永久代或元空间）

    ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/jvmmemory.jpg?raw=true)

#### 线程

* 线程时一个程序里的运行单元，JVM 允许一个应用有多个线程并行的执行。
* 在 Hotspot JVM 里，每个线程都与操作系统的本地线程直接映射。当一个 Java 线程准备好执行后，此时一个操作系统的本地线程也同时创建。Java 线程执行终止后，本地线程也会回收。
* 操作系统负责所有线程的安排调度到任何一个可用的 CPU 上，一旦本地线程初始化成功，它就会调用 Java 线程的 run() 方法。

* 如果你使用 jconsole 或者任何一个调试工具，都能看到在后台有许多线程在运行。这些后台线程不包括调用 public static void main(String[] args) 的 main 线程已经所有这个 mian 线程自己创建的线程
* 这些主要的后台系统线程在 Hotspot JVM 里主要是以下几个
  * **虚拟机线程**：这种线程的操作需要 JVM 达到安全点才会出现，这些操作必须在不同的线程中发生的原因是他们都需要 JVM 达到安全点，这样堆才不会变化，这种线程的执行类型包括 stop-the-world 的垃圾收集，线程栈收集，线程挂起以及偏向锁撤销
  * **周期任务线程**：这种线程是时间周期事件的体现（比如中断），他们一般用于周期性操作的调度执行
  * **GC 线程**：这种线程对在 JVM 里不死他种类的垃圾收集行为提供了支持
  * **编译线程**：这种线程在运行时会将字节码编译成本地代码。
  * **信号调度线程**：这种线程接受信号并发送给 JVM，在它内部通过调用适当的方法进行处理

## 程序计数器

#### 简介

* JVM 中的程序计数寄存器（Program Counter Register）中，Register 的命名源于 CPU 的寄存器，寄存器存储指令相关的现场信息，CPU 只有把数据装载到寄存器中才能够运行。
* 这里，并非广义上所指的物理寄存器，获取翻译为 PC 计数器会更加贴切（也成为程序钩子），JVM 中的 PC 寄存器是对物理 PC 寄存器的一种抽象模拟

* 作用，PC 寄存器同来存储向下一条指令的地址，也即将要执行的命令代码，由执行引擎读取下一条指令
* 程序计数器是一块很小的空间，几乎可以忽略不计，也是运行速度最快的存储区域
* 在 JVM 规范中，每个线程都有他自己的程序计数器，是线程私有的，生命周期与线程的生命周期保持一致
* 任何时间一个线程只有一个方法在执行，也就是**当前方法**。程序计数器会存储当前线程正在执行的 Java 方法的 JVM 指令地址；或者如果在执行 native 方法，则是未指定（undefined）
* 它是程序控制流的指示器，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成
* 字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令。
* 他是**唯一一个在 Java 虚拟机规范中没有规定任何 OutOfMemoryError 情况**的区域

#### 两个常见问题

* 使用 PC 寄存器存储字节码指令地址有什么用？
* 为什么使用 PC 寄存器记录当前线程的执行地址呢？
  * 因为 CPU 需要不停的切换各个线程，这时候切换回来以后，就得知道接着从哪开始继续执行。
  * JVM 的字节码解释器就需要通过改变 PC 寄存器的值来明确下一条应该执行什么样的字节码指令

* 程序计数器为什么会被设定为线程私有？
  * 多线程在一个特定的时间段内之后执行其中某一个线程的方法，CPU 会不停地做任务切换，这样必然导致经常中断或恢复，为了能够准确的记录各个线程正在执行的当前字节码指令的地址，最好的办法就是为每一个线程都分配一个程序计数器，这样各个线程直接可以独立计算，不会出现相互干扰的情况

## 虚拟机栈

#### 虚拟机栈概述

* 由于跨平台特性，Java 的指令都是根据栈来设计的。不同平台 CPU 架构不同，所以不能设计为基于寄存器的

* 优点是跨平台，指令集小，编译器容易实现，缺点是性能下降，实现同样的功能需要更多的指令

* 栈是运行时的单位，而堆时存储时的单位

* Java 虚拟机栈（Java Virtual Machine Stack）

  * 早期也叫 Java 栈，每个线程和在创建时都会创建一个虚拟机栈，其内部保存一个个栈帧（Stack Frame），对应着一次次 Java 方法的调用，虚拟机栈是线程私有的
  * 生命周期和线程保持一致
  * 主管 Java 程序的运行，他保存方法的局部变量，部分结果，并参与方法的调用和返回

* 栈的优点

  * 栈是一种快速有效的分配存储的方式，访问速度仅次于程序计数器
  * JVM 直接对 Java 栈的操作只有两个，每个方法的执行伴随着入栈、出栈
  * 对栈来说，不存在垃圾回收问题

* 栈中可能出现的异常

  * Java 虚拟机规范允许 **Java栈的大小是动态的**或者是**固定不变的**

  * 如果采用固定大小的 Java 虚拟机栈，那每一个线程的 Java 虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过 Java 虚拟机栈允许的最大容量，Java 会抛出 **StackOverFlowError** 异常

    ```java
    public class StackErrorTest {
        public static void main(Strin[] args) {
            // 调用自己，出现 StackOverFlowError 异常
            main(args);
        }
    }
    ```

  * 如果虚拟机栈可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新线程时没有足够的内存去创建对应的虚拟机栈，那 Java 虚拟机将会抛出 **OutOfMemoryError** 异常

  * 调整虚拟机栈的大小 `-Xss256k`，设置为 256 k

#### 栈的存储单位

![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/stackframe1.jpg?raw=true)

* 栈中存储了什么
  * 每个线程都有自己的栈，栈中的数据都是以栈帧的格式存在
  * 在这个线程上正在执行的每个方法都各自对应一个栈帧，方法执行结束，栈帧就会出战
  * 栈帧时一个内存区块，是一个数据集，维系着方法执行过程中的各种数据信息
* JVM 直接对 Java 栈的操作只有两个，就是对栈帧的压栈和出栈
* 在一条活动线程中，一个时间点上，只会有一个活动的栈帧。即只有当前在执行的方法的栈帧（栈顶）是有效的，这个栈帧被称为当前栈帧（Current Frame），与当前栈帧相对应的方法就是当前方法（Current Method），定义这个方法的类就是当前类（Current Class）
* 执行引擎允许的所有字节码指令只针对当前栈帧进行操作
* 如果该方法中调用了其他方法，对应的新的栈帧会被创建出来，放在栈的顶端，成为新的当前帧
* 不同线程中所包含的栈帧是不允许存在相互引用的，即不可能在一个栈帧中引用另外一个线程的栈帧
* 如果当前方法调用了其他方法，方法返回值及，当前栈帧会传回此方法的执行结果给前一个栈帧，接着虚拟机会丢弃当前栈帧，使得前一个栈帧重新成为当前栈帧。
* Java 方法有两种返回函数的方式，一种是正常返回，使用 return，另一种是抛出异常，不管哪种方式，都会导致栈帧被弹出

#### 局部变量表（Local Variables）

* 介绍
  * 局部变量表也被称为局部变量数组或本地变量表
  * 定义一个数字数组，主要用于存储方法参数和定义在方法体内的局部变量，这些数据类型包括各类基本数据类型、对象引用（refrence），以及 returnAddress 类型
  * 由于局部变量表是建立在线程的栈上，是线程的私有数据，因此不存在数据安全问题
  * 局部变量表所需要的容量大小是在编译器确定下来的，并保存在方法的 Code 属性的 maximun local variables 数据项中，在方法运行期间是不会改变局部变量表的大小的
  * 方法嵌套调用的次数由栈的大小决定。一般来说，栈越大，方法嵌套调用次数越多，对一个函数而言，它的参数和局部变量越多，使得局部变量表膨胀，它的栈帧就越大，以满足方法调用所需传递的信息增大的需求，进而函数调用就会占用更多的栈空间，导致其嵌套调用的次数就会减少
  * 局部变量表中的变量只在当前方法调用中有效。在方法执行时，虚拟机使用局部变量表完成参数值到参数变量列表的传递过程。当方法调用结束后，随着方法栈帧的销毁，局部变量表也会随之销毁

* 关于 Slot （变量槽）的理解

  * 局部变量表最基本的存储单元时 Slot
  * 存放的是编译器可知的各种基本数据类型（8种），引用类型（reference），returnAddress 类型的变量
  * 在局部变量表中，32 位以内的类型只占用一个 slot（包括 returnAddress ），64 位类型（long 和 double ）占用两个 slot
    * byte、short、char 在存储前被转换成 int，boolean 也被转换成 int，0 表示 false，非 0 表示 true
    * long 和 double 则占据两个 slot

* JVM 会位局部变量表中的每一个 slot 都分配一个访问索引，通过这个索引即可成功访问到局部变量表中指定的局部变量值。

* 如果当前帧是由构造方法或实例方法创建的，那么该对象的引用 this 将会存放在 index 位 0 的 slot 处，其余参数按照参数表顺序排序

* this 只会存在于构造方法和非静态方法的局部变量表中，因此在静态方法中使用 this 会发现无法使用，因为**静态方法的局部变量表中不存储 this 变量**

* 局部变量表的 slot 是可以重用的，如果某个变量过期了，那么新的变量会占据这个 slot 位置，达到节省资源的目的

* 静态变量和局部变量的对比

  ```java
  /*变量的分类：
  	按照数据类型分
  		1.基本数据类型
  		2.引用数据类型
      按照在类中声明的位置分
      	1.成员变量
      		在使用前，都经过默认初始化赋值
      		类变量， linking 的 prepare 阶段，给类变量赋默认值
      		实例变量，随着对象的创建，会在对空间中分配实例变量的空间，并进行默认赋值
      	2.局部变量
      		在使用前，必须要进行显示的赋值，否则编译不通过
   */
  public void testTemp() {
      int num;
      System.out.println(num); // 报错，num 使用时必须初始化
  }
  ```

* 在栈帧中，于性能调优关系最为密切的部分就是局部变量表，在方法执行时，虚拟机使用局部变量表完成方法的传递
* 局部变量表中的变量也是重要的垃圾回收根节点，只要被局部变量表中直接引用或间接引用的对象，都不会被回收

#### 操作数栈（Operand Stack）

* 每个独立栈帧中除了包含局部变量表之外，还包含一个操作数栈，也称为表达式栈（Expression Stack）
* 操作数栈，在方法执行过程中，根据字节码指令，往栈中写入数据或提取数据，即入栈/出栈

#### 栈顶缓存技术（Top Of Stack Cashing）

* 基于栈式架构的虚拟机所使用的零地址指令更加紧凑，但完成一项操作的时间必然需要使用更多的入栈和出栈指令，着同时也就意味着将需要更多的指令分配（instruction dispatch）次数和内存读/写次数
* 由于操作数是存储在内存中的，因此频繁的执行内存读/写操作必然会影响执行速度，为了解决这个问题，Hotspot JVM 的设计者们提出了栈顶缓存技术，将栈顶元素全部缓存在物理的 CPU 的寄存器中，以此降低对内存的读/写次数，提升执行引擎的效率

#### 动态连接（Dynamic Linking）或称为指向运行时常量池的方法引用

* 每一个栈帧内部都包含一个指向运行时常量池中该栈帧所属方法的引用，包含这个引用的目的就是为了支持当前方法的代码能够实现动态链接，比如 invokedynamic 指令
* 在 Java 源文件被编译到字节码文件中时，所有的变量和方法引用都作为符号引用（Symbolic Reference）保存在 class 文件的常量池里，比如，描述一个方法调用了另外一个方法时，就是通过常量池中指向方法的符号引用来表示的，那么**动态连接的作用就是将这些符号引用转换位调用方法的直接引用**
* 为什么需要运行时常量池
  * 常量池的作用，就是为了提供一些符号和常量，便于指令的识别，加快引用速度

#### 方法调用

* 在 JVM 中，将符号引用转换为调用方法的直接引用与方法的绑定机制有关
* 静态链接与动态链接
  * **静态链接**，当一个字节码文件被装在进 JVM 内部时，如果被调用的目标方法在编译期可知，且运行期保持不变。这种情况下将调用方法的符号引用转换为直接引用的过程称为静态链接
  * **动态链接**，如果**被调用的方法在编译器无法被确定下来**，也就是说，只能够在程序运行期将调用方法的符号引用转换为直接引用，由于这种引用转换的过程具备动态性，因此也就被称为动态链接
  * Java 中的任何一个普通方法都具备虚函数的特征，，如果在 Java 程序中不希望某个方法拥有虚函数的特征时，可以使用关键字 final 修饰，即无法被重写，不具有多态特性

* 虚方法和非虚方法
  * 如果方法在编译器就确定了具体的调用版本，这个版本在运行时时不可变的，这样的方法称为非虚方法
  * 静态方法、私有方法、final 方、实例构造器、父类方法都是非虚方法
  * 其他方法称为虚方法
* 方法重写的本质
  * 找到操作数栈顶的第一个元素所指向的对象的实际类型，记作 C，
  * 如果在类型 C 中找到与常量中的描述符合简单名称都相符的方法，则进行访问权限校验，如果通过，则返回这个方法的直接引用，查找结束，如果不通过，则返回 `java.lang.IllegalAccessError` 异常
  * 否则，按照继承关系，从下往上依次对 C 的各个父类进行第 2 步的搜索和验证过程。
  * 如果始终没有找到合适的方法，则抛出 `java.lang.AbstractMethodError` 异常，即没有找到重写的方法，找到了接口的抽象方法，无法调用
  * IllegalAccessError 介绍，程序试图访问或修改一个属性，或调用一个方法但没有权限访问该属性或方法，一般的，会引起编译器异常，这个错误如果发生在运行时，就说明一个类发生了不兼容改变
* 虚方法表
  * 在面向对象的编程中，会很频繁的使用到动态分派，如果每次动态分派的执行过程都要重新在类的方法元数据中搜索合适的目标的话就可能影响到执行效率。因此，为了提高性能，JVM 采用在类的方法区建立一个虚方法表（virtual method table）来实现，使用索引来代替查找
  * 每个类中都有一个虚方法表，表中存放着各个方法的实际入口。
  * 虚方法表会在类的链接阶段被创建并开始初始化，类的类变量初始值准备完毕之后，JVM 会把该类的方法也初始化完毕

#### 方法返回地址（Return Address）

* 存放调用该方法的 pc 寄存器的值
* 程序因为发生异常而退出时，返回地址需要通过异常表来确定，栈帧中一般不会保存此信息

#### 附加信息

* 栈帧中还允许携带与 Java 虚拟机实现相关的一些附加信息。例如：对程序调试提供支持的信息

#### 面试题相关

* 举例栈溢出的情况？（StackOverFlowError）

  * 递归调用不停止就会出现栈溢出

* 调整栈大小，就能保证不溢出吗？

  * 不能，栈空间并不是无限大的

* 分配的栈内存越大越好吗？

  * 并不是，栈内存变大，那么线程数就会变少

* 垃圾回收是否会涉及到虚拟机栈？

  * 不会的，程序计数器，本地方法栈，虚拟机栈都不会涉及垃圾回收，但两个栈会出现 OOM
  * 方法区和堆区会出现垃圾回收，同时也会出现 OOM

* 方法中定义的局部变量是否线程安全？

  ```java
  public static void method1() {
      // String builder 线程不安全，在方法中多线程使用时有可能会出现多线程问题
      StringBuilder s1 = new StringBuilder();
      s1.append("a");
      s1.append("b");
  }
  
  // 不安全，StringBuilder 的操作过程不安全
  public static void method2(StringBuilder s) {
      s.append("a");
      s.append("b");
  }
  
  // 不安全，StringBuilder 的操作过程不安全
  public static void method3(StringBuilder s) {
      s.append("a");
      s.append("b");
  }
  
  // 不安全，StringBuilder 的操作过程不安全，因为返回回去了，有可能会被其他线程使用
  public static StringBuilder method4(StringBuilder s) {
      StringBuilder s = new StringBuilder();
      s.append("a");
      s.append("b");
      return s;
  }
  
  // 安全，s 在内部已经消亡了，安全
  public static String method4(StringBuilder s) {
      StringBuilder s = new StringBuilder();
      s.append("a");
      s.append("b");
      return s.toString();
  }
  
  public static void main(String[] args) {
      StringBuilder s = new StringBuilder();
      
      // 此时就会发生线程不安全
      new Thread(() -> {
          s.append("a");
          s.append("b");
      })
      method2(s);
  }
  ```

## 本地方法栈

#### 简介

* Java 虚拟机栈是用于管理 Java 方法的调用，而本地方法栈用于管理本地方法的调用。
* 本地方法栈也是线程私有的
* 允许被实现成固定或者可动态扩展的内存大小（在内存溢出方面是相同的）
* 本地方法是使用 C 语言实现的
* 他的具体做法是，Native Method Stack 中登记 native 方法，在 Execution Engine 执行时加载本地方法
* 并不是所有 Java 虚拟机都拥有本地方法栈
* 在Hotspot 虚拟机中，直接将本地方法栈和虚拟机栈合二为一

## 本地方法

#### 什么是本地方法

* 简单讲，一个 Native Method 就是一个 Java 调用非 Java 代码的接口，一个 Native Method 的方法，该方法的实现是由非 Java 语言实现，比如 C，这个特征并非 Ja	va 拥有，很多其他的编程语言都有这一机制，比如 C++ 中也有。

* 在定义一个 Native Method 时，并不提供实现体，因为其实现体时在外面实现的

  ```java
  private static native void registerNatives();
  ```

* Native 方法不能使用 abstract 修饰，因为 native 不是没有方法体，abstract 是没有方法体，两者矛盾

#### 为什么要使用 Native Method 

* 与 Java 环境外的交互
* 与 C/C++ 交互
* 与操作系统交互 



## 堆

#### 堆的核心概述

* 一个 JVM 实例只存在一个堆内存，堆也是 Java 内存管理的核心区域
* Java 堆区在 JVM 启动时即被创建，其空间大小也就确定了，是 JVM 管理的最大的一块内存空间（堆内存大小可调节）
* 《Java 虚拟机规范》规定，堆可以处于物理上不连续的内存空间中，但逻辑上应该是连续的
* 所有线程共享 Java 堆，在这里还可以划分线程的私有缓冲区（Thread Local Allocation Buffer, TLAB） 
* 《Java 虚拟机规范》中堆 Java 堆的描述是，所有的对象和实例都应该在运行时分配在堆上
* 数组和对象可能永远不会存储在栈上，因为栈帧中保存引用，这个引用指向对象或数组在队中的位置
* 在方法结束会，堆中的对象不会马上被移除，仅仅在垃圾回收时被回收
* 堆，是 GC （Garbage Collection，垃圾收集器）执行垃圾回收的重点区域

#### 内存细分

* 现代垃圾收集器大部分都基于分代收集理论设计，堆空间细分为：

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/heap.jpg?raw=true)

* Java 1.7 之前，堆逻辑上分为三部分，新生区，养老区，永久区

  * Young Generation Space 新生区，新生代，年轻代都可以
    * 又被划分为 Eden 区和 Survivor 区
  * Tenure Generation Space 养老区，老年代，老年区
  * Permanent Space 永久区，永久代

* Java 1.8 之后，堆内存逻辑上分为三部分，新生区，养老区，元空间

  * Young Generation Space 新生区，新生代，年轻代都可以
    * 又被划分为 Eden 区和 Survivor 区
  * Tenure Generation Space 养老区，老年代，老年区
  * Meta Space 元空间

#### 设置堆内存大小与 OOM

* Java 堆区用于存储 Java 对象实例，那么堆的大小在 JVM 启动时就已经设置好了，可以通过 -Xmx 和 -Xms 来设置
  * -Xms （-X 是 JVM 运行参数，ms 指的是 memory start，单位为 k，m，g）堆的起始内存，等价于 -XX:InitialHeapSize
  * -Xmx （mx 指的是 memory max）堆的最大内存，等价于 -XX:MaxHeapSize
  * 通常会将两个值设置为一样，这样在 java 垃圾回收机制清零完堆后，不需要重新计算分配堆的大小
* 一旦堆区的内存大小超过 -Xmx 设置的大小时，将会抛出 OutOfMemoryError 异常

* 默认初始堆的大小是电脑物理内存的 1/64，最大内存是电脑物理内存的 1/4

  ```java
  public class Test {
      public static void main(String[] args) {
          // 获取运行时虚拟机的堆内存总量
          long init = Runtime.getRuntime().totalMemory() / 1024 / 1024;
  		// 获取堆试图使用的最大对内存容量
          long max = Runtime.getRuntime().maxMemory() / 1024 / 1024;
          System.out.println(init + " M"); // 246 M
          System.out.println(max + " M"); // 3934 M
  
          System.out.println("系统内存大小: " + init * 64.0 / 1024 + " G");
          System.out.println("系统内存大小: " + max * 4.0 / 1024 + " G");
      }
  }
  ```

  * 手动设置 `-Xms600m -Xmx600m`

#### OutOfMemory 举例

* 当老年区满了之后，会报 OOM 异常

```java
public class OOMTest {
    public static void main(String[] args){
        ArrayList<Picture> list = new ArrayList<>();
        while (true) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}
class Picture {
    private byte[] pixels;
    
    public Picture(int length) {
        pixels = new byte[length];
    }
}
```

#### 年轻代与老年代

* 在 JVM 中存储的数据被分为两类
  * 生命周期较短的瞬时对象，这类对象的创建和存亡都非常迅速
  * 另外一类的生命周期非常长，及短情况下能够与 JVM 的生命周期保持一致
* JVM 堆又进一步细分为新生代（YoungGen）和老年代（OldGen）
  * 新生代老年代的默认空间大小比例是 1：2
* 年轻代又分为 Eden 空间，Surivivor0 空间（from 区）和 Surivivor1 空间 （to 区）
  * 在 Hotspot 中，Eden 空间和两个 Surivivor 空间的默认比例是 8：1：1
  * 几乎所有 Java 对象都在 Eden 区 new 出来的
  * 绝大部分的对象都在新生代销毁了
* 关于堆的 JVM 参数
  * -XX:NewRatio: 设置新生代和老年代的空间比例，默认是2
  * -XX:SurivivorRatio: 设置新生代中 edan 和 surivivor 区的比例
  * -XX:-UseAdaptiveSizePolicy：关闭自适应的内存分配策略
  * -Xmn: 设置新生代的空间大小

#### 图解对象的分配过程

* 为新对象分配内存时一键非常严谨和复杂的任务，JVM的设计者们不仅需要考虑内存如何分配，在哪里分配等问题，并且由于内存分配算法与内存回收算法密切相关，所以还需考虑GC执行完内存回收后是否会在内存空间中产生碎片
* 具体步骤（一般情况）
  * 1.new 的对象先放在 Eden 区，此区有大小限制
  * 2.当 Eden 区填满时，程序又需要创建对象，JVM 垃圾回收器将对 Eden 区进行垃圾回收（Minor GC），将 Eden 区中不再被其它对象引用的对象销毁，在加载新的对象放到 Eden 区
  * 3.然后将 Eden 区的剩余对象移动到 Surivior0 区
  * 4.如果再次出现垃圾回收，此时上次幸存下来的放到幸存者 0 区，如果没有回收，就会放到 Surivivor1 区
  * 5.如果再次经历垃圾回收，此时会重新放回 Surivivor0 区，接着再去 Surivivor1 区
  * 6.什么时候去 Old 区呢，可以设置次数 `-XX:MaxTenuringThreshold=<N>` 进行设置，默认是 15 次
  * 7.在养老区悠闲，当养老区内存不足时，再次触发major GC，进行养老区内的垃圾回收
  * 8.如养老区进行 Major GC 之后，发现依然无法保存对象，那么就会产生 OOM 异常
* 总结
  * 针对幸存者 s0, s1 区的总结，复制之后有交换，谁空谁是 to
  * 关于垃圾回收，频繁在新生代收集，很少在老年代收集，几乎不在永久区/元空间收集

* 对象分配的特殊情况
  * Eden 区的大小不足以容纳对象的创建，则直接进入老年代，如果老年代依然放不下，报 OOM 异常
  * surivivor 区如果也出现了对象放不下的情况，则直接进入老年代

#### Minor GC、Major GC、Full GC

* JVM 在 GC 时，并不是每次都对所有三个区进行回收，大部分回收都是新生代
* 针对 Hostspot VM 的实现，GC 按照回收区域又分为两大类，部分收集器（Partial GC），整堆收集（Full GC）
  * 部分收集，不是完整的收集整个 Java 堆的垃圾，又分为：
    * 新生代收集（Minor GC / Young GC），只收集新生代的垃圾
    * 老年代收集（Major GC），只收集老年代的垃圾
      * 目前，只有 CMS GC 会有单独收集老年代的行为
      * 注意，很多时候 Major GC 会和 Full GC 混淆使用，需要具体分辩时老年代回收还是整堆回收
    * 混合收集（Mixed GC），收集整个新生代及部分老金没带的垃圾
      * 目前，只有 G1 GC 会有这种行为
  * 整堆收集（Full GC），收集整个 java 堆和方法区的垃圾
* 年轻代 GC（Minor GC）触发机制
  * 当年轻代空间不足时，出发 Minor GC，这里的年轻代满指的是 Eden 区满，Surivivor 满不会触发 GC，每次 Minor GC 会清理年轻代的内存
  * 因为 Java 对象大多都具备朝生夕灭的特性，所以 Minor GC 非常频繁，一般回收速度也比较快
  * Minor GC 会引发 STW（Stop The World），会暂停用户线程，等垃圾回收结束，用户线程才继续运行
* 老年代 GC（Major GC / Full GC）触发机制
  * 指发生了老年代 GC，对象从老年代消失时，我们会说 Major GC，或 Full GC 发生了
  * 出现了 Major GC，经常会伴随至少一次的 Minor GC（但非绝对，在 Parallel Scavenge 收集器的收集策略中就有直接进行 Major GC 的策略选择过程）
    * 也就是在老年代空间不足时，会先尝试出发 Minor GC，如果之后空间还不足，则会触发 Major GC
  * Major GC 的速度一般会比 Minor GC 慢 10 倍以上，STW 的时间更长
  * 如果 Major GC 之后，内存不足，就 OOM
* Full GC 触发机制（在后面细讲）
  * 调用 System.gc(); 时，系统建议执行 Full GC，但不是必然执行
  * 老年代空间不足
  * 方法区空间不足
  * 通过 Minor GC 后，进入老年代的平均大小大于老年代的可用内存
  * 由 Eden 区，survivor0 向survivor1 区复制时，对象大小大于可用内存，则把该对转到老年代，且老年代的可用内存小于该对象
  * 说明，Full GC 时开发调优中尽量要避免的，这样暂停的时间会短一些

#### 堆空间分代思想

* 分代的唯一理由就是**优化 GC 性能**

#### 内存分配策略

* 如果对象在 Eden 区出生并经过第一次 Minor GC 仍然存活，并且能被 Surivivor 区容纳，就被移动到 Surivivor 区，并将年龄设置为 1，每熬过一次 Monjor GC 就年龄+1，15岁时会被移到老年代
* 针对不同年龄的对象分配原则如下
  * 优先分配 Eden 区
  * 大对象直接分到老年代（字符串，数组都是大对象，因为需要连续的空间）
    * 尽量避免出现过多大对象
  * 长期存活的对象分配到老年代
  * 动态对象年龄判断
    * 如果 Surivivor 区相同年龄的所有对象的大小综合大于 Surivivor  空间的一半，年龄大于或等于该年龄的对象直接进入老年代，无需等到要求的年龄

#### TLAB（Thread Local Allocation Buffer）

* 为什么要有 TLAB
  * 堆区时线程共享区域，任何线程都可以访问到堆区中的共享数据
  * 由于对象实例创建在 JVM 中非常频繁，因此在并发的环境下从堆区中划分内存空间时线程不安全的
  * 为避免多个线程操作同一地址，需要加锁等影响分配速度
* 什么是 TLAB
  * 从内存模型而不是垃圾回收的角度，对 Eden 区进行划分，JVM 为**每个线程分配了一个私有缓冲区**，包含在 Eden 空间
  * 多线程同时分配内存是，使用 TLAB 可以不免一系列的线程安全问题，同时能提升内存分配的吞吐量，将这种内存分配方式称为**快速分配策略**
  * 所有 Open JDK 衍生出来的 JVM 都提供了 TLAB 的设计
* 说明
  * 尽管不是所有对象都能够在 TLAB 中成功分配内存，但 JVM 确实将 TLAB 作为内存分配的首选
  * 通过 `-XX:UseTLAB` 设置是否开启 TLAB，默认开启
  * 默认情况下，TLAB 空间非常小，只有 Eden 区的 1%，可以通过 `-XX:TLABWasteTargetPercent` 设置 TLAB 空间所占 Eden 区大小的百分比
  * 一旦对象在 TLAB 分配失败，JVM 就尝试通过加锁确保数据操作原子性，从而直接在 Eden 空间分配

#### 小结堆空间的参数设置

* 测试堆空间常用的 jvm 参数

```
-XX:+PrintFlagsInitial: 查看所有参数的默认值
-XX:+PrintFlagsFinal: 查看所有参数的最终值（可能会存在修改而不再是初始值）
-Xms: 初始堆空间内存（默认为物理内存的 1/64）
-Xmx: 最大堆内存空间（默认为物理内存的 1/4）
-Xmn: 设置新生代的大小（初始及最大值）
-XX:NewRatio: 配置新生代与老年代的大小比例（默认为 2:1）
-XX:SuivivorRatio: 设置新生代中 Eden 区和 s0、s1 的比例（默认 8:1:1）
-XX:MaxTenuringThreshold: 设置新生代垃圾的最大年龄
-XX:+PrintGCDetails: 输出详细的 GC 处理日志
-XX:+PrintGC -verbose:gc: 输出 GC 简要信息
-XX:HandlePromotionFailure: 是否设置空间分配担保
```

#### 堆时分配对象的唯一选择吗

* 在《深入理解 Java 虚拟机中》，随着 JTI 编译期的发展与逃逸分析技术逐渐成熟，栈上分配、标量替换优化技术将会导致一些微妙的变化，所有对象分配到堆上也变得不再“绝对”
* 在 Java 虚拟机中，对象在堆上分配，这是一个常识，但，如果经过逃逸分析（Escape Analysis）后发现，一个对象并没有逃逸出方法的话，那么就可能被优化成栈上分配。这就无需在堆上分配内存，也无需进行垃圾回收了，这也是最常见的堆外存储技术

#### 逃逸分析概述

* 逃逸分析的基本行为就是分析对象动态作用域
  * 当一个对象在方法中被定义后，对象只在方法内部使用，则认为没有发生逃逸
  * 当一个对象在方法中被定义后，被外部方法所引用，则认为发生逃逸，如作为调用参数传到其方法
* 没有发生逃逸的对象，则可以分配到栈上，随着方法执行的结束，栈空间就被移除
* 只需关注 new 的对象是否有可能在方法外被引用，就可以确定是否会发生逃逸
* 在 jdk 6u23 之后，Hotspot 就默认开启了逃逸分析
  * 通过`-XX:+PrintEscapeAnalysis` 可查看逃逸分析的筛选结果
* 在开发中，能使用局部变量的，尽量不要在方法外定义
* 使用逃逸分析，编译器会对代码做以下优化
  * **栈上分配**，将堆分配转化为栈分配，如果对象子程序中被分配，要是只是该对象的指针永远不会逃逸，对象可能是站分配的后续，而不是堆分配
  * **同步省略**，如果一个对象被发现只能从一个线程被访问到，那么对于这个对象的操作可以不考虑同步
  * **分离对象或标量替换**，有的对象可能不需要一个连续的内存结构存在也可以被访问到，那么对象的部分（或全部）可以不存储在堆，而是存储在 栈中
    * 标量（Scalar）只一个无法再分解的数据，java 中的原始数据类型就是标量
    * 相对的，还可以再分的数据叫聚合量（Aggregate），java 对象就是居合量
    * 在 JIT 阶段，如更改经过逃逸分析，发现一个对象不会被外界访问，那么经过 JIT 的优化，会把这个对象拆分成若干个成员的变量来代替，这个过程就是标量替换，则数据即可放在栈帧中，减少堆的占用
    * 打开标量替换（默认打开）`-XX:+EliminateAllocations`
* 实际上，逃逸分析并不成熟，因为逃逸分析本身也需要资源消耗，不一定会减少，特别的，Hotspot 虚拟机就没有实现逃逸分析，而**实现了标量替换**

# 方法区

![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/runtimeregion.jpg?raw=true)

## 栈、堆、方法区的交互

```java
// 其中，小括号 (Person) 指的是，当前类的 .class 文件存储在方法区/元空间
// [person] 指的是，本地变量存储在 Java 虚拟机栈中
// {new Person()} 指的是，对象存储在 堆 上
(Person) [person] = {new Person();}
```

## 方法区的理解

#### 方法区在哪

* 《Java虚拟机规范》中说明，方法区在逻辑上属于堆的一部分，但有一些简单的实现可能不会选择去进行垃圾收集，但对于 Hotspot 虚拟机而言，方法区还有一个别名叫做 Non-Heap（非堆），目的就是和堆分开
* 所以方法区可以看作时一块独立于 Java 堆的内存空间

#### 方法区的基本理解

* 方法区（Method Area）和堆于洋，是各个线程共享的区域
* 方法区在 JVM 启动时被创建，并且实际物理内存空间和堆一样，可以不连续
* 方法区的大小和堆一样，可以 选择固定大小或者可扩展
* 方法区的大小决定了系统可以保存多少个类，如果定义太多，导致方法区溢出，虚拟机同样会抛出 OOM PermGen （jdk 7 及以前）space 或者 OOM Metespace（jdk 8 及之后）
  * 加载大量的第三方 jar 包
  * Tomcat 部署的工程过多（30-50个）
  * 大量的动态生成反射类
* 关闭虚拟机就会释放这个区域的内存

#### Hotspot 虚拟机的演进

* 在 jdk7 之前，习惯上把方法区称为永久代，jdk8 开始，使用元空间取代了永久代
* 本质上方法区和永久代并不等价，但在 hotspot 中可以认为等价。《Java虚拟机规范》对如何实现方法区，不做统一要求
* 现在来看，永久代会导致 Java 程序更容易 OOM（超过 -XX:MaxPermSize 上限）
* 元空间与永久代类似，都是对 JVM 规范中方法区的实现，但元空间与永久代最大的区别在于**元空间不在虚拟机设置的内存中，而使用本地内存**
* 根据 JVM 规范的要求，元空间满了也会出现 OOM

## 设置方法区大小与 OOM

#### 设置方法区的大小

* 方法区的大小不固定，可以动态调整
  * jdk7 之前
    * -XX:PermSize 设置初始分配空间，默认是 20.75M
    * -XX:MaxPermSize 设置最大空间，32 位机器默认 64M，64位机器默认 82M
  * jdk8 之后
    * -XX:MetaspaceSize 指的初始参数，默认 21 M
    * -XX:MaxMetaspaceSize 设置最大空间，默认值为 -1，即没有限制
* 与永久代不同，如果不指定大小，虚拟机会用尽所有可用物理内存，如果元数据区发生溢出，会抛出 OOM
* -XX:MetaspaceSize 设置初始值并不是 21M 固定不变的，当第一次达到 GC 之后，会根据 GC 收回的垃圾数量更新当前值

#### 如何解决 OOM

* 要解决 OOM 或 heap space 异常，一般是通过内存分析工具对 dump 出来的堆转存储快照进行分析，重点是确认内存中所的对象是否是必要的，也就是要分清楚到底是出现了内存泄漏（Memory Leak）还是内存溢出（Memory Overflow）
* 如果是内存泄漏，可通过工具（JProfile）查看到对象到 GC Roots 的引用链，然后就可以查看到对象是为什么不能被回收
* 如果不存在内存泄漏，即内存中的对象必须都存活，那么久调整堆的大小 -Xmx 与 -Xms

## 方法区内部结构

* .class字节码文件
* 类信息
* 运行时常量池
* 字符串常量

#### 方法区存储了什么

* 《深入理解 Java 虚拟机》中堆方法区的存储内容描述如下
  * 存储已被虚拟机加载的类型信息，常量，静态变量，即时编译器编译后的代码缓存等
* 类型信息，对每个加载的类型（类 class，接口 interface，枚举 enum，注解 annotation），JVM 必须在方法区中存储以下类型信息
  * 这个类型的完整有效名称（全名）
  * 这个类型的直接父类的完整有效名
  * 这个类型的修饰符（public, abstract, final...）
  * 这个类型直接接口的一个有序列表
* 域（Field）信息，即平常所说的成员变量等
  * JVM 必须在放擦去包类型所有域相关信息以及域的声明顺序
  * 域的相关信息包括：域名称，域类型，域修饰符（public, private, protected, static, final, volatile, transient...）
* 方法信息，包括声明顺序等
  * 方法名称
  * 方法的返回类型
  * 方法参数的数量和类型（按顺序）
  * 方法的修饰符（public, private, protected, static, final, synchronized, native, abstract）
  * 方法的字节码、操作数栈、局部变量表及大小（abstract 和 native 方法除外）
  * 异常表（abstract 和 native 方法除外）
    * 每个异常处理的开始位置，结束位置，代码处理在程序计数器中的便宜地址，被捕获的异常类的常量池索引
  * non-final 的类变量
    * 静态变量和类关联在一起，随着类的加载而加载，他们成为类数据在逻辑上的一部分
    * 类变量被类的所有实例共享，即使没有类实例，也可以访问

#### 运行时常量池和常量池

* 方法区，内部包含了运行时常量池
* 字节码文件，内部包含了常量池
* 要弄清方法区，需要理解清楚 ClassFile，因为类加载信息都在方法区
* 要弄清楚方法区的运行时常量池，需要理解清楚 ClassFile 中的常量池
* 一个有效的字节码文件中除了包含类的版本信息，字段，方法以及接口等描述信息外，还包含一项信息那就是常量池表（Constant Pool Table），包括各种字面量和对类型，域、方法的符号引用

#### 常量池中有什么

* 数量值
* 字符串值
* 类引用
* 字段引用
* 方法引用
* 常量池，可以看作是一张表，虚拟机根据这张表找到要执行的类名，方法名，参数类型、字面量等类型

#### 运行时常量池

* 运行时常量池（Runtime Constant Pool）时方法区的一部分
* 常量池表（Constant Pool Table）是 Class 文件的一部分，用于存放编译期生成的各种字面量与符号引用，这部分内容将在类加载后存放到方法区的运行时常量池中
* 运行时常量池，在加载类和接口到虚拟机后，就会创建对应的运行时常量池
* JVM 为每个已加载的类都维护一个常量池。池中的数据项就像数组项一样，通过索引访问
* 运行时常量池中包含多种不同的常量，包括编译器就已经明确的数值型常量，也包括到运行期解析后才能够获得的方法或字段引用。此时不再是常量池中的符号地址了，这里换位真实地址
  * 运行时常量池相对于 Class 文件常量池的另一重要特征就是**具备动态性**
* 运行时常量池类似于传统编程语言中的符号表，但是它所包含的数据却比符号表丰富
* 当创建类或接口的运行时常量池时，如果构造运行时常量池所需的内存空间超过了方法区所能提供的最大值，则 JVM 会抛出 OOM 异常

## 方法区的演进

#### 方法区的演进细节

* 首先明确，只有 Hotspot 才有永久代，JRockit，J9 等不存在永久代的概念

* HotSpot 方法区的变化

  | 版本         | 细节                                                         |
  | ------------ | ------------------------------------------------------------ |
  | jdk 1.6 之前 | 有永久代，静态变量存储在永久代上                             |
  | jdk 1.7      | 有永久代，但已逐步”去永久代”，字符串常量池、静态变量移除，保存在堆中 |
  | jdk 1.8 之后 | 无永久代，类型信息、字段、方法常量保存在本地内存的元空间，但字符串常量静态变量仍在堆中 |

#### StringTable （字符串常量池）为什么要调整

* jdk 7 中，将 StringTable 放到了堆空间中，因为永久代的回收效率很低，在 full gc 的时候才会出发，而 full gc 是老年的空间不足，永久代不足时才会触发
* 这就导致 StringTable 回收效率不高。而我们开发中会有大量的字符串被创建，回收效率低，导致永久代内存不足，放到堆中能够及时回收内存

#### 静态变量存在哪

* 1.7 之前存在方法区
* 1.8及之后放在堆上

```java
public class StaticObject {
    static class Test {
        /* 
        staticObj 随着 Test 的类型信息存放在方法区
        instanceObj 随着 Test 的对象实例存放在 Java 堆
        localObject 则是存放在 foo() 方法的局部变量表中
        
        测试发现，三个对象的数据在内存中的地址都落在 Eden 区，所以，只要时对象实例，必然会在 Java 堆中分配
        */
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();
        
        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }
    }
    
    private static class ObjectHolder{
        
    }
    
    public static void main(String[] args) {
        
    }
}
```

## 方法区的GC

#### 方法区的垃圾回收

* 一般来说，方法区的垃圾回收效果比较难以令人满意，尤其是类型的卸载，条件非常苛刻。但这部分区域的垃圾回收又很有必要
* 方法区的垃圾回收主要回收两部分内容，常量池中废弃的常量和不再使用的类型
* 方法区中常量池主要存放的两大类常量，字面量和符号引用
  * 字面两比较接近 Java 语言层次的常量概念，如文本字符串、被声明为 final 的常量值等，而符号引用则属于编译原理方面的概念，包括下面三种常量
    * 类和接口的全限定类名
    * 字段的名称和描述符
    * 方法的名称和描述符
* HotSpot 虚拟机堆常量池的回收策略很明确，只要常量池中的常量没有被任何地方引用，就可以回收
* 回收废弃常量与回收 Java 堆中的对象非常类似

# StringTable

#### String 的基本特性

* String 类被定义为 final 类型

* String 的底层存储

  * 1.8 之前，char[]
  * 1.9之后，byte[]

* String 代表不可变的字符序列，简称不可变性

  * 当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的 value 进行赋值
  * 当对现有字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的 value
  * 当调用 String 的 replace() 方法修改指定字符或字符串时，也需要重新指定内存区域赋值

* 通过字面量的方式（区别于 new）给一个字符串赋值，此时字符串值生命在字符串常量池中

  ```java
  public class StringTest {
      public static void main(String[] args) {
  		String s1 = "abc"; // 字面量定义的方式，“abc”存储在字符串常量池中
          String s2 = "abc";
      }
  }
  ```

* 字符串常量池中不会存储相同内容的字符串
* String 的 String Pool（字符串常量池）是一个固定大小的 HashTable，默认大小长度是 1009，如果放进 String Pool 的 String 非常多，就会造成 hash 冲突严重，从而导致链表会很长，而链表长了之后，会造成调用 String.intern() 性能下降
* 使用`-XX:StrigTableSize` 设置长度
* 在 jdk 6 中，StringTable 是固定的，1009长度
* 在 jdk 7 中，StringTable 长度默认值是 60013
* jdk8 之后，要求 1009 是可设置的最小值

#### String 的内存分配

* 为了使得 Java 中8中数据类型和 String 类型在运行过程中速度更快，更节省内存，都提供了一共常量池的概念
* 常量池就类似一个 Java 系统级提供的缓存，8 中基本数据类型的常量池都是系统协调的，String 类型的常量池比较特殊，它的主要使用方法有两种
  * 直接使用双引号声明出来的 String 对象会直接存储在常量池中
  * 如果不是用 String 声明的，可以使用 String 提供的 intern() 方法
* 在 jdk 6之前，字符串常量池存在永久代
* jdk 7 之后，将字符串常量池的位置调整到堆
* jdk 8 元空间，字符串常量池在堆

#### String 的基本操作

* Java 语言要求完全相同的字符串字面量应该包含同样的 Unicode字符序列，并且必须指向同一个 String 类实例

#### 字符串拼接操作

* 常量与常量的拼接结果在常量池，原理是编译器优化

* 常量池中不会存在相同的内容常量

* 只要其中一个是变量，结果就在堆中，变量拼接的原理是 StringBuilder

* 如果拼接的结果调用 inern() 方法，则主动将常量池中还没有的字符串对象放入池中，并返回此对象的地址

  ```java
  public void test1() {
      String s1 = "a" + "b" + "C";
      String s2 = "abc"; // abc 一定是放在字符串长常量池中的，将此地址赋值给 s2
      
      System.out.println(s1 == s2); // true
      System.out.println(s1.equals(s2)); // true
  }
  
  public void test2() {
      String s1 = "javaEE";
      String s2 = "hadoop";
      
      String s3 = "javaEEhadoop";
      String s4 = "javaEE" + "hadoop";
      
      // 只要拼接符号出现了变量，则相当于在堆空间中 new String()，因此都不一样
      String s5 = s1 + "hadoop";
      String s6 = "javaEE" + s2;
      String s7 = s1 + s2;
      
      System.out.println(s3 == s4); // true
      System.out.println(s3 == s5); // false
      System.out.println(s3 == s6); // false
      System.out.println(s3 == s7); // false
      System.out.println(s5 == s6); // false
      System.out.println(s5 == s7); // false
      System.out.println(s6 == s7); // false
      
      // 判断字符串常量池中是否有当前字符串，如果有，则返回此地址，如果没有，则在常量池中加载一份，并返回此地址
      String s8 = s6.intern();
      System.out.println(s3 == s8); // true   
  }
  
  public void test3() {
      String s1 = "a";
      String s2 = "b";
      
      // 下面的拼接过程
      /*
      	1. StringBuilder s = new StringBuilder();
      	2. s.appand("a");
      	3. s.appand("b");
      	4. s.toString(); --> 约等于 new String("ab");
      */
      String s3 = s1 + s2;
  }
  
  public void test4() {
      final String s1 = "a";
      final String s2 = "b";
      
      String s3 = "ab";
      String s4 = s1 + s2;
      System.out.println(s3 == s4); // true，因为 s1+s2 虽然是变量，但加了 final 关键字，在编译期就认为是常量了
  }
  ```

* 字符串拼接操作不一定使用 StringBuilder，如果拼接符号左右两边都是常量或常量引用，则使用编译期优化方式

#### intern() 的使用

* 如果不是用双引号声明的 String 对象，可以使用 String 提供的 intern() 方法。intern() 方法会熊字符串常量池中查询当前字符串是否存在，若不存在就会将当前字符串放入常量池中

* 任意字符串调用 String.intern() 方法，那么其返回的结果所指向的那个类的实例，必须和直接以常量形式出现的字符串实例完全相同，因此 `("a" + "b" + "c").intern() == "abc"` 必定为 true

* 题目 

  ```java
  new String("ab") //  到底创建了几个对象？
  // 两个，一个 new，出现在堆中，一个字符串常量 "ab"，在字符串常量池中
  // 深入思考 
  new String("a") + new String("b") //  创建了几个对象？
  /*
  对象 1, new StringBuilder()
  对象 2, new String("a")
  对象 3, 常量池中的 "a"
  对象 4, new String("b")
  对象 5, 常量池中的 "b"
   深入刨析 StriingBuilder 的 toString()
  对象 6，new String("ab");
  强调，此处，toString() 的调用，没有在字符串常量池中生成 "ab"
  */
  ```

* jdk6 vs jdk 7/8

  ```java
  public class StringIntern1 {
      public void test1() {
          String s = new String("1");
          s.intern();
          String s2 = "1";
          System.out.println(s == s2); // jdk6 false, jdk 7/8 false
          
          String s3 = new String("1") + new String("1"); // 执行完毕后，字符串常量池中没有 ”11“,s3 记录的是堆中的对象地址
          s3.intern(); // 在字符串常量池中生成 "11"，如何理解，在 jdk6 中，创建了一个新的对象，"11",在 jdk7 中，常量池并没有创建 "11",而是创建了一个指向堆空间 new 的对象的地址
          String s4 = "11"; // 
          System.out.println(s3 == s4); // jdk6 false, jkd7/8 true
      }
      
      // 测试新的情况
      public void test2() {
          String s3 = new String("1") + new String("1"); // 执行完上一步之后，常量池中不存在"11"
          String s4 = "11"; // 在字符串常量池中生成对象 "11"
          s3.intern();
          System.out.println(s3 == s4); // jdk7/8 false，因此不一样，为 false
      }
  }
  ```

* 总结 intern() 的使用
  * jdk 1.6 中，将这个字符串尝试放入串池
    * 如果串池中有，则并不会放入。返回已有的串池中的对象的地址
    * 如果没有，此对象复制一份，放入串池，并返回串池中的对象地址
  * jdk 1.7 之后
    * 如果串池中有，则并不会放入。返回已有的串池中的对象的地址
    * 如果没有，则会把对象的引用地址复制一份，放入串池，并返回串池中的引用地址

#### G1 中的 String 去重

* 背景许多 Java 应用做的测试如下
  * 堆存活数据聚合里面 String 对象占了 25%
  * 堆存活数据集合里面重复的 String 对象有 13.5%
  * String 对象的平均长度是 4 5
* 许多大规模的 Java 应用瓶颈在于内存，测试表明，Java 堆中存活的数据集合差不多 25% 是 String 对象。更进一步，这里面差不多一半的 String 对象是重复的，即 string1.equals(string2) = true，堆上存在重复对象必然是对内存的一种浪费。G1 垃圾收集器中实现自动持续对重复的 String 对象进行去重，避免浪费内存
* 实现
  * 当垃圾收集器工作时，会访问堆上存活的对象。对每一个访问的对象都会检查是否是候选的要去重的对象
  * 如果时，将此对象引入队列后续处理，去重的线程在后台运行，处理此队列。处理队列的一个元素一位置从队列删除这个元素，然后尝试区重它的引用 String 对象
  * 使用一个 hashtable 来记录所有的被 String 对象使用的不重复的 char 数组。去重时，会查此 hashtable，来看对上是否存在一样的 char 数组
  * 如果存在，String 对象会被调整引用的数组，释放原来的数组引用，最终会被垃圾回收器回收
  * 如果查找失败，char 数组会被插入 hashtable，这样以后的时候就可以共享这个数组了

# 垃圾回收

## 垃圾回收简介

#### 什么是垃圾

* 垃圾指的是在运行程序中没有任何指针指向的对象，这个对象就是需要被回收的垃圾
* 如果不及时堆内存中的垃圾进行清理，那么这些垃圾会一直保留到应用程序结束，被保留的空间无法被其它对象使用，甚至导致内存溢出

#### 为什么需要 GC

* 对于一个高级语言来说，如果不进行垃圾回收，内存迟早会被用完
* 除了释放没用的对象，垃圾回收也可以清楚内存的记录碎片，将所占内存移动到堆的一端，以便 jvm 将整理出的内存分配给新对象
* 随着应用程序的越来越大，没有GC 就不能保证程序的正常运行，经常会造成 STW 的 GC 又跟不上实际的需求，所以才会不断尝试对 GC 进行优化

#### 早期垃圾回收

* 在 C/C+++ 时期，垃圾回收和空间申请需要程序员手动进行
* 忘记时会造成内存泄漏

#### Java 垃圾回收机制

* 自动内存管理，无需手动参与内存的分配和回收，降低内存泄漏和内存溢出的风险
* 自动内存管理机制，将程序员从内存管理中释放出来，专注于业务开发

* 应该关心哪些区域的回收，方法区和堆
  * Java 堆时垃圾收集器的工作重点
  * 频繁收集 young 区
  * 较少收集 Old 区
  * 基本不动永久区（元空间）

## 垃圾回收相关算法

#### 标记阶段，引用计数法

* 对象存活判断
  * 在堆中放着几乎所有 Java 对象，GC 之前，首先需要区分出哪些存活，哪些死亡，被标记死亡的才会被回收。这个过程称为垃圾标记阶段
  * 当一个对象已经不再被任何存活的对象继续引用时，认为该对象已经死亡
  * 判断对象存活一般两种方式，引用计数法和可达性分析算法
* 引用计数算法
  * 引用计数算法（Reference Counting）比较简单，对每一个对象保存一个整形的引用计数器属性，用于记录对象被引用的情况。
  * 对于一个对象 A，只要有任何一个对象引用了 A ，则 A 的计数器就+1，当引用失效时，计数器-1，A的引用计数器为0，表示对象 A 不可被再使用，可进行回收
  * 优点，实现简单，垃圾对象便于辨识，判定效率高，回收没有延迟性
  * 缺点
    * 需要单独的字段存储计数器，增加了存储空间的开销
    * 每次赋值都需要更新计数器，伴随加法减法操作，增加时间开销
    * 引用计数器有一个严重的问题，无法处理循环引用的情况，这是一条致命缺陷，导致Java的垃圾回收器中没有使用这类算法

#### 标记阶段，可达性分析算法

* 可达性分析（或根搜索算法，追踪性垃圾收集）
  * 相较于引用计数算法而言，本算法不仅具备实现简单，执行高效的特点。更重要的是，可以解决循环引用的问题，防止内存泄漏的发生
  * 相较于引用计数算法，可达性分析算法就是 Java 和 C# 选择的。这种类型的垃圾收集通常也叫做追踪性垃圾收集（Tracing Garbage Collection）
* 所谓 "GC Roots" 根集合就是一组必须活跃的引用
* 基本思路
  * 可达性分析算法是以根对象集合 GC Roots 为起始点，按照从上至下的方式搜索被根对象集合所连接的目标对象是否可达
  * 使用可达性分析之后，内存中存活对象都会被跟对象集合直接或简介引用，搜索搜过的路径称为引用链（Reference Chain）
  * 如果目标对象没有被任何引用链相连，那么就被回收
  * 在可达性分析计算中，只有被根对象集合直接或间接连着的对象才是存活对象
* GC Roots 包括以下几类元素
  * 虚拟机栈中引用的对象
    * 各个线程被调用的方法中使用的参数，局部变量等
  * 本地方法栈内 JNI用于对象
  * 方法区中类静态属性引用的对象
  * 方法区中常量引用的对象
    * 字符串常量池里的引用
  * 所有被同步锁 synchronized 持有的对象
  * Java 虚拟机内部的引用
    * 基本数据类型对应的 Class 对象，一些常驻的异常对象，系统类加载器
  * 反应 java 虚拟机内部情况的 JMXBean，JVMTI 中注册的回调，本地代码缓存等
  * 除了这些固定的 GC Roots 集合以外，根据用户选用的垃圾收集器以及当前回收的内存区域不同，还可以有其他对象临时性的加入，共同构成完整的 GC Roots 集合。比如：分代收集和局部回收（Partial GC）
    * 如果只针对 Java 堆中的某一刻区域进行垃圾回收，（典型的只针对新生代）必须考虑到内存区域时虚拟机自己的实现细节，更不是孤立封闭的，这个区域的对象完全有可能被别的区域所引用，这是后就需要一并将关联的区域对象也加入 GC Roots 集合，才能保证准确性
  * 由于 Root 采用栈方式存放变量和指针，所以如果一个指针，它保存了堆内存里面的对象，但自己又不存放在对立面，那他就是一个 Root

* 如果要使用可达性分析算法来判断内存是否可回收，那么分析工作必须再一个能保障一致性的快照中进行。这点不满足的话，分析结果的准确性就无法保证
* 这点也是导致 GC 进行时必须 Stop The World 的原因，即使是号称（几乎）不会发生停顿的 CMS收集器中，枚举根节点时也是必须要停顿的

#### 对象的 finalization 机制

* Java 语言提供看对象终止（finalization）机制来允许开发任意提供对象销毁之前的自定义处理逻辑
* 当垃圾回收器发现没有引用指向对象时，几垃圾回收之前，总会先调用这个对象的 finalize 方法
* finalize 方法允许再子类中被重写，用于再对象被回收时进行资源释放。通常这个方法中进行一些资源释放和清理的工作，比如关闭文件、套接字和数据库连接等
* 用于不要主动调用方法的 finalize 方法，应交给垃圾回收机制调用
  * 再 finalize() 时可能会导致对象复活
  * finalize 方法的执行时间时没有保障的，完全由 GC 决定，极端情况下，若不发生 GC，则永远不会执行
  * 一个在搞的 finalize 会严重影响 GC 的性能
* 从功能上来说，finalize 方法与 C++ 中的析构函数比较相似，但是 Java 采用的时基于垃圾回收器的自动内存管理机制，所以 finalize 方法再本质上不同于 C++ 的析构函数
* 由于 finalize 方法的存在，虚拟机中的对象一般处于三种可能的状态

* 生存还是死亡？
  * 如果从所有根节点都无法访问到某个对象，说明对象以及不再使用了，一般来说，此对象需要被回收，但事实上，也并不是非死不可，一个无法触及的对象有可能再某一个条件下复活自己，如果这样那么对他的回收就是不合理的，因此，定义虚拟机中对象的三种状态
    * 可触及的，从根节点开始，可以到达这个对象
    * 可复活的，对象的所有引用都被释放，但对象有可能再 finalize 中复活
    * 不可触及的，对象的 finalize 被调用，且没有复活，那么就会进入不可触及，不可触及的对象不可能复活，因为 finalize 只会执行一次
  * 以上三种状态时由于 finzlize 方法的存在，进行的区分，只有在对象不可触及时才会被回收
* 具体过程
  * 如果对象 A 到 GC Roots 没有引用链，则进行第一次标记
  * 进行筛选
    * 没有重新 finalize 方法，或者 finalize 以及被调用过，则直接死亡
    * 重写了 finalize 方法，且未被执行过，会插入 F-Queue 对象，一个由虚拟机自动创建的低优先级的 Finalizer 线程触发其 finalize 方法执行
    * finalize 是对象逃脱死亡的最后机会，稍后 GC 会对 F-Queue 队列中的对象进行第二次标记，如果此对象再 finalize 方法中与引用链上的任何一个对象建立联系后，那么第二次标记，会被移除即将会收集和，之后，对象会再次出现没有引用存在的情况，在这个情况下，finalize 方法不会被再次调用，直接死亡，一个对象的 finalize 方法只会被调用一次

#### MAT 与 JProfiler 的 GC Roots 溯源

* MAT 是 Memory Analyzer 的简称，它是一款功能强大的 Java 堆内存分析器，用于查找内存泄漏及查看内存消耗情况
* MAT 是基于 Eclopse 开发的，是一款免费的性能分析工具
* JProfiler 也可以实现 GC Roots 的溯源
* JProfiler 也可以查看 OOM 问题

#### 清除阶段，标记-清除算法

* 当成功区分出存活对象和死亡对象后，GC 接下来就是执行垃圾回收，释放无用的内存空间
* 目前 JVM 中比较常见的三种垃圾收集算法，标记-清除（Mark-Sweep）、复制算法（Copying）、标记-压缩算法（Mark-Compact）
* 背景
  * 标记-算法是一种非常基础和常见的垃圾收集算法，在1960年提出
* 执行过程
  * 标记，Collector 从引用根节点开始遍历，标记所有被引用的对象，一般是在都想的 Header 记录为可达对象
  * 清除，Collector 对堆内存从头到尾进行线性的遍历，如果发现某个对象在其 Headewr 中没有被标记为可达，则进行回收
* 缺点
  * 效率一般
  * 在进行 GC 的时候，需要停止整个应用程序，导师用户体验差
  * 这种方式清理出来的空闲内存是不连续的，产生内存碎片，需要维护一个空闲列表
* 注意，何为清除？
  * 这里所谓的清除不是指置空，而是把需要清除的对象地址保存在空闲的地址列表里，下次有新对象需要加载时，判断垃圾的位置空间是否够，如果够，就存放

#### 清除阶段，复制算法

* 为了解决标记清楚算法的效率不高的问题，1963 年提出了复制算法
* 核心思想，将活着的内存空间分为两块，每次只使用其中一款，在垃圾回收是，将正在使用的内存中的存活对象复制到未被使用的内存块中，之后清楚正在使用的内存块中的所有对象，交换内存块的角色，完成垃圾回收
* 优点
  * 没有标记清除过程，效率高
  * 保证了空间的连续性，不会产生内存碎片
* 缺点
  * 内存空间折半，每次只能用一半
  * 每次回收需要修改引用指针的地址
  * 特别的，如果系统垃圾对象过多，效果就不会很理想。复制算法需要复制的存活对象数量并不会太大，或者说非常低才行

#### 清除阶段，标记-压缩算法

* 背景
  * 复制算法的高效建立在存活对象少，垃圾对象多的前提下，这种情况在新生代容易出现，但在老年代，更常见的时大部分都存活，复制的成本就很高
  * 标记清除算法可以应用在老年代，但效率低下，还会产生碎片，因此老年代使用标记压缩算法，1970年发布

* 执行过程
  * 第一阶段和标记-清除算法一样，从根节点开始标记所有被引用的对象
  * 第二阶段将所有存活的对象压缩到内存的一端，按顺序存放
  * 第三步，清理边界之外的所有空间
* 标记压缩算法的最终效果等同于标记清除并进行了内存整理，二者的本质差异是标记清除算法属于是非移动式，标记压缩算法属于移动式的
* 指针碰撞
  * 如果内存空间以规整和有序的方式分布，即已用和未用的内存都各自存放一遍边彼此之间只维系着一个记录下一次分配起始点的指针，当为新对象分配内存时，只需要通过修改之中偏移量将新对象分配在第一个空闲位置上，这种分配方式就叫做指针碰撞（Bump the Pointer）
* 优点
  * 解决了标记清除的缺点
  * 消除了复制算法内存减半的高额代价
* 缺点
  * 从效率上说，标记整理要低于复制算法
  * 移动对象的同时，还需调整引用地址
  * 移动过程中，需要 STW

#### 小结

* 对比三种算法

  | 算法      | 速度 | 空间开销                        | 移动对象 |
  | --------- | ---- | ------------------------------- | -------- |
  | 标记-清理 | 中等 | 少，但会产生碎片                | 否       |
  | 复制算法  | 最快 | 通常需要活对象的2倍，不产生碎片 | 是       |
  | 标记-整理 | 最慢 | 少，不会产生碎片                | 是       |

#### 分代收集算法

* 前面所有算法中，并没有一个算法可以完全替代其他算法，都各有优点和缺点，分代收集算法就出现了
* 分代收集算法，是基于不同对象的生命周期是不一样的，因此不同生命周期的对象可以采取不同的收集方式，提高效率，一般把java堆分为新生代和老年代，这样就可以根据哥哥年代的特点使用不用的算法，提高回收的效率
* 在 Java 程序运行过程中，会产生大量对象，其中与业务相关的 Session 都行，Socket 连接对象，这类对象跟业务挂钩，生命周期较长，但还有一些对象，主要是临时变量，如 String，生命周期较短
* 目前几乎所有 GC 都是采用分代垃圾收集算法。在 Hotspot 中，基于分代的概念，GC 所使用的内存回收算法必须结合年轻代和老年代各自的特点
  * 年轻代
    * 区域小，对象生命周期短，存活率低，回收频繁
    * 适合于复制算法
  * 老年代
    * 区域大，对象生命周期长，存活率高
    * 适合于标记清除或标记整理，一般是混合使用
    * 以 Hotspot 的 CMS 回收器为例，CMS 基于 Mark-Sweep（标记清理） 实现的，对于对象的回收效率很高，对于碎片问题，采用 Mark-Compact（标记-整理）算法的 Serial Old 回首器作为补偿，当内存回收不佳时（碎片导致 Concurrent Mode Failure时），将采用 Serial Old 执行 Full GC 以达到老年代的内存整理

#### 增量收集算法

* 上述现有的算法，在垃圾回收过程中，应用软件将处于 STW 状态，在此状态下，应用程序所有线程都将被挂起，暂停一切的正常工作，等待垃圾回收完成，如果垃圾回收时间过长，应用程序会被挂起很久，严重影响用户体验和系统稳定性。为了解决这个问题，即对实时垃圾收集算法的研究直接导致增量收集（Incremental Collecting）算法的诞生
* 基本思想
  * 如果一次将所有垃圾进行处理，需要造成系统长时间的停顿，那么就可以让垃圾收集线程和应用线程交替执行，每次垃圾收集线程只收集一小片区域，接着切换到应用线程。以此反复，直到收集完毕
  * 总的来说，增量收集算法依然是传统的标记清除和复制算法，增量收集算法通过对线程间冲突的妥善处理，允许垃圾手机线程以分阶段的方式完成标记清理或复制工作
* 缺点
  * 使用这种方式，由于在垃圾回收过程中，间断性执行了应用程序代码，所以减少系统停顿时间。但是没因为线程上下文切换的消耗，会使得垃圾回收的成本上升，造成系统吞吐量的下降

#### 分区算法

* 一般来说，相同条件下，堆空间越大，一次 GC 所需要的时间就越长，有关 GC 产生的停顿就越长，为了更好控制 GC 产生的停顿，将一块大的内存区域分割成多个小块，根据目标的停顿时间，每次合理地回收若干个小区间，而不是整个堆空间，从而减少一次 GC 的停顿
* 分代算法将按照对象的生命周期长短分为两个部分，分区算法将整个堆空间划分成连续不同的小区间，每个小区间独立使用，独立回收，好处时可以控制一次回收多少个小区间

* 注意，这些只是基本思路，实际 GC 实现过程要复杂得多，目前还在发展中的前沿 GC 都是复合算法，并且并行和并发兼备

## 垃圾回收的相关概念

#### System.gc() 的理解

* 在默认情况下，通过 System.gc() 或者 Runtime.getRuntime().gc() 的调用，会显示出发 Full GC，同时对老年代和新生代进行回收，尝试释放被丢弃对象占用的内存
* 然而 System.gc() 调用附带一个免责声明，无法保证对垃圾收集器的调用，即并不是调用了就马上会发生垃圾回收，而是合适了才会发生
* JVM 实现着可以通过 System.gc() 调用来决定 JVM 的 GC 行为，而一般情况下，垃圾回收应该是自动进行的，无须手动触发，否则过于麻烦，在一些特殊情况下，如我们编写一个性能基准，就可以在运行直接调用 System.gc()

#### 内存溢出

* 内存溢出相对于内存泄漏来说，尽管更容易理解，但同样的，内存溢出也是引发程序崩溃的罪魁祸首
* 由于 GC 一直在发展，所有一般情况下，除非应用程序占用的内存增长速度非常快，造成垃圾回收以及跟不上内存消耗的速度，否则不太容易出现 OOM 的情况
* 大多数情况下，GC 会进行各种年龄段的垃圾回收，实在不行了就来一次 Full GC，这时会回收大量的内存
* javadoc 对 OOM 的解释是，没有空闲内存并且垃圾回收也无法提供更多的内存
* 首先说没有空闲内存的情况，说明 java 虚拟机的堆内存不够，原因有二
  * java 虚拟机堆内存设置太小
  * 代码中创建了大量对象，并且长时间不能被垃圾回收
  * 这里面隐含着一层意思，在抛出 OOM之前，通常垃圾收集器会尽可能的清理空间
    * 在引用分析中，涉及到 JVM 会尝试回收软引用指向的对象
    * 在 java.nio.BITs.reserveMemory() 方法中，可以看到，System.gc() 被调用
  * 当然也不是在任何情况下都会触发
    * 比如，当我们去分配一个超大对象，超过了堆的最大值，JVM 可以判断出垃圾收集并不能解决问题，就直接抛出 OOM

#### 内存泄漏

* 也称作存储渗漏，严格来说，只有对象不会被程序用到，但 GC 又无法收回，才叫做内存泄漏
* 实际情况很多时候一些不好的实践会导致对象的生命周期变长，甚至导致 OOM，也可以叫做宽泛意义上的内存泄漏
* 尽管内存的泄漏并不会立刻引起程序崩溃，但是一旦发生内存泄漏，程序中的可用内存就会被逐步蚕食，直至耗尽所有内存，最终出现 OOM
* 这里的存储空间并不指物理内存，而是指虚拟内存大小，这个虚拟内存大小取决于磁盘交换区的大小
* 举例
  * 单例模式，单例的生命周期和应用程序一样长，所以单例程序中，如果持有堆外部对象的引用的话，那么这个外部对象是不能被回收的，会导致内存泄漏
  * 一些提供 close 的资源未管理导致内存泄漏，如数据库连接，网络 socket 连接和 io 连接必须手动 close

#### Stop The World

* 指的是 GC 实际发生过程中，会产生应用程序的停顿，停顿产生时，整个应用程序都会被暂停，没有响应有点像卡死的感觉
  * 可达性分析算法枚举根节点时，会导致所有 Java 执行线程停顿
* 被 STW 中断的应用程序会在GC 完成之后恢复，频繁中断会让用户感觉像是网路不快造成卡一样，所以需要减少STW的产生
* STW 事件和哪款 GC 无关，所有的 GC 都会发生 GC 事件
* G1 也不能完全避免，只能是尽量缩短了暂停时间
* STW 是 JVM 在后台自动发起和完成的e，在用户不可见的情况下发生
* 开发中不要用 System.gc()，会导致 STW 的产生

#### 垃圾回收的并行与并发

* 并行（Parallel），指多条垃圾回收线程并行工作但此时用户线程仍处于等待中，如 ParNew，Parallel Scavenge，Parallel Old
* 串行（Serial），相较于并行，单线程执行，如果内存不够，则程序暂停，启动 JVM 垃圾后记，回收完在启动程序线程
* 并发（Concurrent），指用户线程和垃圾回收线程同时执行，垃圾回收线中执行时不会停顿用户程序的运行，用户程序继续运行，二垃圾收集程序运行在另一个 CPU 上，如 CMS，G1

#### 安全点与安全区域

* 安全点（Safe Point）
  * 程序执行时并不是在所有地方都可以停下来进行 GC，只有在特定的位置才能停下来 GC，这些位置称为安全点
  * 安全点的选择很重要。如果太少，可能导致 GC 等待时间太长，如果太频繁，可能导致运行时性能问题，大部分指令的执行都非常短暂，通常会根据**是否具有让程序长时间执行的特征**为标准，比如选择一些执行时间长的执行作为安全点，如方法调用，循环跳转，异常跳转等
* 如何在 GC 发生时，检查所有线程都跑到了最近的安全点停下来了呢
  * **抢先式中断**（目前，没有虚拟机采用），首先中断所有线程，如果有线程不在安全点就恢复线程，让线程跑到安全点
  * **主动式中断**，设置一个中断标志，各个线程运行到安全点主动轮询这个标志，如果标志为真，就主动将自己挂起
* 安全区域（Safe Region）
  * 安全点机制保证了程序执行时，在不太长的时间就会遇到可以进入 GC 的安全点，但程序不执行的时候呢，即线程处于 Sleep 状态，或 Blocked 状态这时线程无法响应 JVM 的中断请求，走到安全点去挂起，JVM 也不太可能等待线程被唤醒，这种情况就需要安全区域来解决
  * 安全区域指的时一段代码片段中，对象的引用关系不会发生变化，在这个区域中的任何位置发生 GC 都是安全的，我们可以把安全区域堪称时扩展了的安全点
  * 实际执行时
    * 当线程运行到 Safe Region 的代码时，先标识自己进入了安全区，如果发生 GC，JVM 会忽略标识为安全区的线程
    * 当线程离开安全区时，检查 JVM 是否完成GC，如果完成就继续运行，否则就等到收到可以安全离开的信号时再离开安全区

#### 引用

* 我们希望能描述这样一类对象，当空间内存还足够的时候，则保留在内存中；如果内存空间再进行垃圾回收之后还是很紧张，则可以抛弃这些对象
* 既偏门又高频的面试题：强引用，软引用，弱引用，虚引用有什么区别？具体使用场景是什么
  * 在 jdk 1.2 版本之后，Java 对引用的概念进行了扩充，将引用分为强引用（Strong Reference）、软引用（Soft Reference）、弱引用（Weak Reference）和虚引用（Phantom Reference）4种，这四种引用强度依次减弱
  * 除了强引用之外，其他三种类型均可以在 java.lang.ref 中找到

* Reference  子类中只有终结器引用是包内可见的，其他三种类型均为 public，可以在应用程序中直接使用
  * **强引用（Strong Reference）**，最传统的引用的定义，是指在程序代码中普遍存在的引用赋值，无论在任何情况下，只要强引用关系还在，垃圾收集器就不会回收该对象
  * **软引用（Soft Reference）**，在系统将要发生内存溢出之前，将会把这些对象列入回收范围进行二次回收，如果这次回收后还没有足够的内存，才会抛出 OOM
  * **弱引用（Weak Reference）**，被弱引用关联的对象只能生存到下一次垃圾收集之前，当垃圾收集器工作时，无论内存空间是否足够，都会回收掉弱引用对象
  * **虚引用（Phantom Reference）**，一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来获得一个对象的实例，为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知

#### 强引用 - 不回收

* 在 Java 程序中，最常见的引用类型就是强引用（普通系统99%以上都是），也就是我们最常见的普通对象引用，也是默认引用类型
* 当 Java 使用 new 关键字创建新对象，赋值给一个变量时，这个变量就成为指向该对象的一个强引用，强引用的对象是可触及的，垃圾收集器永远不会回收掉被引用的对象
* 对于一个普通的对象，如果没有其他的引用关系，只要超过了引用的作用域或者显示的将强引用赋值为 null，就可以被回收了
* 相对的，软引用、、弱引用、虚引用的对象都是软可触及，弱可触及和虚可触及的，在一定条件下都可以被回收，所以，强引用时造成 Java 内存泄漏的主要原因之一
* 强引用具备的特点
  * 可以指将访问目标对象
  * 所指向的对象在任何时候都不会被回收，虚拟机宁愿判处OOM，也不会回收
  * 强引用可能导致内存泄漏

#### 软引用 - 内存不足即回收

* 软引用是用来描述一些还有用，但非必须的对象。只被软引用关联着的对象，在系统将要发生内存溢出异常之前，，会把这些对象列进回收范围之中进行第二次回收，如果这次回收还没有足够的内存，才会抛出 OOM

* 软引用通常用来实现内存敏感的缓存。比如：高速缓存就有用到软引用，如果还有空闲内存，就可以暂时保留缓存，当内存不足时清理掉，这样就保证了使用缓存的同时，不会耗尽内存

* 垃圾回收器在某个时刻决定回收软可达的对象的时候，会清理软引用，并可选地把引用存放到一个引用队列（Reference Queue）。

* 类似弱引用，只不过 Java 虚拟机会尽量让软引用的存活时间长一些，迫不得已才清理

  ````java
  User u1 = new User(1, "joush");
  SoftReference<User> userSoft = new SoftReferencr<>(u1); // 建立一个软引用
  u1 = null; // 取消强引用
  
  // 从软引用中获取强引用对象
  System.out.println(userSoft.get());
  System.gc();
  System.out.println("After GC: ");
  // 垃圾回收之后获得软件引用中的对象
  System.out.println(userSoft.get());
  ````

#### 弱引用 - 发现即回收

* 在 jdk 1.2之后，java提供了弱引用的实现
* 弱引用对象与软引用对象最大的区别就在于，GC 回收时，需要通过算法检查是否回收软引用对象，而对于弱引用对象，GC 总是进行回收。若引用对象更容易、更快被 GC 回收
* 弱引用也是用来描述那些非必须的对象，只被弱引用关联的对象只能生存到下一次垃圾收集发生为止。在系统GC时，只要发现弱引用，不管系统堆空间使用是否充足，都会回收被弱引用关联的对象
* 但是由于垃圾回收器的线程优先级通常很低，因此，并不一定能很快地发现持有弱引用地对象，在这种情况下，弱引用对象可以存在较长时间
* 弱引用和软引用一样，在构造弱引用时，也可以指定一个引用队列，当弱引用对象被回收时，就会加入指定地引用队列，通过这个队列可以跟踪对象地回收情况。
* 软引用、弱引用都非常适合并存那些可有可无地缓存数据。如果这么做，当系统内存不足时，这些缓存数据会被回收，不会导致内存溢出。而当内存资源充足时，这些缓存数据又可以存在相当长的时间，从而起到加速的作用

#### 虚引用 - 对象回收跟踪 

* 也成为**幽灵引用**或**幻影引用**，是所有引用类型中最弱的一个

* 一个对象是否有虚引用的存在，，完全不决定对象的生命周期。如果一个对象仅有虚引用，那么他和没有引用几乎一样，随时会被就垃圾回收器回收

* 他不能单独使用，也无法通过虚引用来获取被引用对象，当试图通过虚引用的 get() 方法取得对象时，总是 null

* 为一个对象设置虚引用关联的唯一目的在于跟踪垃圾回收过程。比如：能在这个对象被收集器回收时收到一个系统通知

* 虚引用必须和引用队列一起使用。虚引用在创建时必须提供一个引用队列作为参数，当垃圾回收器准备回收一个对象时，如果发现他还有虚引用，就会在回收对象后，将这个虚引用加入引用队列，以通知应用程序对象的回收情况

* 由于虚引用可以跟踪对象的回收时间，因此，也可以将一些资源释放操作放在虚引用中执行和记录

* 在 jdk 1.2 之后提供了PhantomReference 类来实现虚引用

  ```java
  Object obj = new Object();
  ReferenceQueue rq = new ReferenceQueue();
  PhantomReference<Object> pf = new PhantomReferencr<>(Obj, rq);
  obj = null; // 回收对象
  ```

# 垃圾回收器

## 垃圾回收器的分类与性能指标

#### 垃圾收集器概述

* 垃圾收集器没有在规范中进行过多的规定，可以由不同厂商、不同版本的 JVM 来实现
* 由于 JDK 的版本处于高速迭代中，因此 Java 发展直接已经衍生了众多的 GC 版本
* 从不同角度分析垃圾收集器，可以将 GC 分为不同的类型
  * 按线程分，可以分为串行垃圾回收器和并行垃圾回收器
  * 串行回收是指同一时间段只允许有一个垃圾回收线程执行垃圾回收工作，此时工作现场被暂停，直至垃圾回收结束，在单 CPU 处理器或者较小应用内存等硬件平台不是特别优越的场合，串行回收器的性能表现可以超过并行回收器，所以**串行回收默认被应用在客户端的 Client 模式下的 JVM 中**
  * 在并发能力较强的 CPU 上，并行回收器产生的停顿时间要短于串行回收器

#### 评估 GC 的性能指标

* **吞吐量**，运行用户代码的时间占总运行时间的比例
* 垃圾收集开销，吞吐量的补数，垃圾收集所用时间与总运行时间的比例
* **暂停时间**，执行垃圾收集时，程序的工作线程被暂停的时间
* 收集频率，相对于应用程序的执行，收集操作发生的频率
* **内存占用**，Java 堆区所占的内存大小
* 快速，一个对象从诞生到被收所经历的时间
* 三个加粗项的共同构成了，不可能三角，三者的表现会随着技术进步而越来越好，一款优秀的收集器通常最多同时满足两项，主要抓住两点，**吞吐量**和**暂停时间**

#### 吞吐量

* 吞吐量就是用 CPU 运行用户代码的时间与 CPU 总消耗时间的比值。即`吞吐量 = 运行用户代码的时间 / （运行用户代码的时间 + 垃圾收集时间）`
* 着这情况下，应用程序能够容忍较高的暂停时间，因此，高吞吐量的应用程序有更长的时间基准，快速响应是不必考虑的
* 吞吐量优先，意味着在单位时间内，STW 的时间最短 0.2 + 0.2 = 0.4

## 不同垃圾回收器概述

#### 垃圾收集器发展史

* 1999 年，jdk1.3.1 发布了串行方式的 Serial GC，是第一款 GC，ParNew 垃圾收集器是 Serial 的多线程版本
* 2002 年，Parallel GC 和 Concurrent Mark Sweep 跟随 jdk 1.4.2 一起发布
* Parallel 在 jdk 1.6之后成为 hotspot 默认 GC
* 2012 年，jdk1.7 u4 版本，G1 可用
* 2017 年，jdk9 中，G1 变成默认垃圾收集器，替代 CMS
* 2018 年 3月，jdk10中，G1 并行完整垃圾回收，实现并行
* 2018 年 9  月，jdk11发布，引入Epsilon垃圾回收，同时引入 ZGC，可伸缩性的低延迟垃圾回收器（Experimental）
* 2019 年 3 月，jdk12 发布，增强 G1，自动返回未使用堆内存给操作系统，引入 Shenandoah GC，低停顿时间的GC
* 2020 年 3 月，jdk 14 发布，删除 CMS，扩展了 ZGC 的应用
* jdk15版本，ZGC 正式进入生产版本

#### 七款经典了垃圾收集器

* 串行回收器，Serial，Serial Old
* 并行回收器，ParNew，Parallel Scavenge，Parallel Old
* 并发回收器，CMS，G1

#### 七款经典收集器与分代之间的关系

* 新生代收集器，Serial，ParNew，Parallel Scavenge

* 老年代收集器，Serial Old，Paraller Old，CMS

* 整堆收集器，G1

  ![image](https://github.com/RexJoush/JavaLearning/blob/master/public/image/JVM/gc.jpg?raw=true)

* 因为 Java 的使用场景很多，移动端，服务器等，所以需要针对不同场景，提供不同的垃圾收集器，提高垃圾收集性能
* 我们选择的只是对具体应用最合适的收集器

#### 如何查看默认的垃圾回收器

* `-XX:+PrintCommandLineFlags` 查看命令行相关参数（包含使用的垃圾收集器）
* 使用命令行执行 `jinfo -flag 相关垃圾回收器参数 进程 ID`
  * `jps` 重看进程号
  * `jinfo -flag UseParallelGC 924`

## Serial 回收器，串行回收

#### 简介

* Serial 收集器是最基本、历史最悠久的垃圾收集器，在 jdk1.3之前，回收新生代的唯一选择
* Serial 收集器作为 Hotspot 中 client 模式下默认的新生代垃圾收集器
* Serial 采用复制算法、串行回收和 STW 机制的方式执行内存回收
* 除了年轻代之外，Serial 还提供老年代的垃圾回收器 Serial Old，Serial Old 也同样采用串行回收和 STW 机制，只不过内存回收算法使用的是标记压缩算法
  * Serial Old 是运行在 client 模式下默认的老年代的垃圾回收器
  * Serial Old 在 Server 模式下有两个用途
    * 与新生代的 Parallel Scavenge 配合使用
    * 作为老年代 CMS 收集器的后备垃圾收集方案
* 此收集器是一个单线程收集器，但单线程的意义不仅仅说明它只会使用一个 CPU或一条收集线程去完成垃圾收集工作，更重要的是他在进行垃圾收集时，必须暂停其他工作线程，直到它收集结束
* 优势
  * 简单高效，单个CPU没有线程切换的开销，专心做垃圾回收
  * 在桌面的应用场景中，可用内存不打，可以在较短时间内完成垃圾回收，因此串行也是可行的

## ParNew 回收器，并行回收

#### 简介

* 如果说 Serial GC 是年轻代中的单线程垃圾收集器，那么 ParNew 收集器则是 Serial 收集器的多线程版本
  * Par 是指 Parallel 的缩写，new 指的是新生代
* ParNew 收集器除了采用并行收集外，和 Serial 几乎没有区别，在年轻代中采用复制算法，仍然需要 STW
* ParNew 是很多 JVM 运行在 Server 模式下新生代的默认垃圾收集器

#### 特点

* 对于新生代，回收次数频繁，并行效率高
* 对于老年代，回收频率低，串行节省资源
* 在多核情况下，ParNew 比 Serial 要效率高，但在单 CPU 下，Serial 更高效

## Paraller 回收器，吞吐量优先

#### 简介

* Hospot 的年轻代中，Parallel Scavenge 收集器同样采用复制算法、并行回收和 STW 机制，基本上 ParNew 一样
* 那么 Parallel 是否多此一举
  * 和 ParNew 不同，Parallel Scavenge 收集器的目标则是达到一个可控制的吞吐量（Throughput），他被称为吞吐量优先的垃圾收集器
  * 自适应调节策略也是 Parallel Scavenge 和 ParNew 的一个重要区别（即，选择低延迟优先还是高吞吐量优先）
* 高吞吐量可以高效的利用 CPU 时间，尽快完成运算任务，主要适合后台运算，而不需要太多交互的任务，因此常在服务器中使用，比如批量处理，订单处理，工资支付，科学计算等应用程序
* Parallel 收集器在 jdk1.6时提供了用于执行老年代垃圾收集的 Parallel Old 收集器，用来替代 Serial Old 收集器
* Parallel Old 收集器采用标记压缩，但同样基于并行回收和 STW 机制
* 在吞吐量优先的应用场景中，Parallel 和 Parallel Old 组合，在 Server 模式下的内存回收性能很好
* **jdk8中，此组和也是默认收集器组合**

## CMS 回收器，低延迟

#### 简介

* 在 jdk 1.5 时期，Hotspot 推出了一款在强交互应用中几乎可以认为有划时代意义的垃圾收集器 CMS（Concurrent-Mark-Sweep）收集器，**这款收集器是 Hotspot 虚拟机中第一款真正意义上的并发收集器，第一次实现了让垃圾收集线程与用户线程同时工作**
* CMS 收集器的关注点是尽可能缩短垃圾收集时用户线程的停顿时间。停顿时间越短，就越适合与用户交互的程序，良好的响应速度能提升用户体验
  * 目前很大一部分的java应用程序集中在互联网站或者  B/S 系统的服务端上，这类应用尤其重视服务的响应速度，希望停顿时间最短，提升用户体验，CMS 收集器就非常符合
* CMS 垃圾收集算法采用标记清除算法，并且也会 STW
* 不幸的是，CMS 作为老年代的收集器，却无法与 jdk 1.4 中存在的新生代收集器 Parallel Scavenge 配合，所以在jdk1.5中，使用 CMS 来收集老年代的时候，新生代只能选择 ParNew 或者 Serial 收集器中的一个
* 在 G1 出现之前，CMS 使用还是非常广泛的，一直到今天，仍然有很多系统使用 CMS GC

#### CMS 工作原理

* 初始标记，STW之后，进行初始标记，单线程
* 然后进行并发标记，即用户线程和CMS线程同时工作
* STW，重新标记（remark），多线程
* 并发清理，和用户线程一起，清理垃圾
* 重置线程

#### CMS 工作流程

* **初始标记（Initial-Mark）**阶段，在这个阶段中，程序中所有的工作线程都会因为 STW 而出现短暂的暂停，这个阶段的主要任务仅仅是标记处 GC Roots 能直接关联到的对象。一旦标记完成之后，就会恢复之前被暂停的所有应用线程，由于直接关联对象较小，这里速度很快
* **并发标记（Concurrent-Mark）**阶段，从 GC Roots 的直接关联对象开始遍历整个对象图的过程，此过程耗时较长，但不需要停顿用户线程，可以与垃圾收集线程一起并发运行
* **重新标记（Remark）**阶段，STW 发生，由于并发标记阶段中，程序的工作线程回合垃圾现场同时或交叉运行，因此为了**修正并发标记期间，因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录**，这个阶段的停顿时间通常会比初始标记稍长一些，但也远比并发标记时间短
* **并发清除（Concurrent-Sweep）**阶段，此阶段清理删除掉标记阶段判断死亡的对象，释放内存空间，由于不需要移动存活对象，因此这个阶段也可以与用户线程并发执行

#### 特点

* 尽管 CMS 收集器采用的是并发回收，但是在其初始化标记和再次标记两个阶段仍然需要执行 STW 机制暂停工作线程，不过暂停时间不会太长，因此可以说明目前所有垃圾收集器都不能避免 STW，只是尽可能减少 STW 的时间
* 由于最耗时间的并发标记和并发清除阶段都不需要暂停工作，所以整体的回收是低停顿的
* 另外，偷鱼垃圾收集阶段没有用户线程中断，所以 **CMS 回收过程中，应确保用户线程有足够的内存可用**，因此，CMS 不能像其他收集器一样，等老年代几乎被填满了在进行，而是**当堆内存使用率达到某一阈值时，便开始进行回收**，以确保应用程序在 CMS 工作期间依然有足够的空间支持应用程序的运行
* 如果在 CMS 运行期间预留的内存无法满足需要，就会出现一次 **Concurrent Mode Failure** 失败，这时虚拟机将启动后被预案，临时启用 Serial Old 收集器来重新收集老年的垃圾，这样会导致停顿时间变长
* 为了保证在并发清除阶段不会影响用户线程，因此必须使用标记清除，而不是标记整理

优点

* 并发收集，低延迟

#### 缺点

* 会产生内存碎片
* CMS 收集器对 COU 资源非常敏感，在并发阶段，它虽然不会导致用户停顿，但会因为占用了一部分线程而导致应用程序变慢，总吞吐量变低
* CMS 收集器无法处理浮动垃圾，可能会出现 Concurrent Mode Failure 失败而导致另一次 Full GC 的产生
* 在并发标记阶段，由于程序的工作线程和垃圾收集线程时同时运行或交叉运行的，那么并发清理阶段如果产生新的垃圾对象，CMS 将无法对这些垃圾进行回收，只能等到下一次执行 GC 时才能回收

#### 参数设置

* 默认启动的线程数时 （ParallelGCThreads + 3）/ 4，其中 ParallelGCThreads 是年轻代并行收集器的线程数，即CPU个数，如果 5 核 CPU 会有 2/5 的线程进行垃圾回收】
* CMS 开启回收的堆内存使用阈值。jdk1.5之前 是68%，jdk1.6及之后默认值为 92%

#### 小结

* 如果想要最小化地使用内存和并行开销，选 Serial GC
* 最大化应用程序和吞吐量，选 Parallel GC
* 最小化 GC 地中断或停顿时间，选 CMS

## G1 回收器，区域化分代式

#### 认识 G1

* 既然我们已经有了前面那么多 GC 了，为什么还要发布 Garbage First（G1）GC？
* 原因就在于应用程序所应对向业务越来越庞大，复杂，用户越来越多，没有 GC就不能保证应用程序正常进行，而经常造成 STW 地 GC 又跟不上实际地需求，在 jdk1.7 update 4 之后，，引入了G1 垃圾收集器
* 与此同时，为了适应现在不断增大地内存和不断增加的处理器数量，进一步降低暂停时间，同时兼顾良好的吞吐量，官方给 G1 设定地目标是在延迟可控地情况下，获得尽可能高地吞吐量，所以才担当的起全功能收集器的重任

#### 为什么叫 Garbage First

* 因为 G1 是一个并行回收器，它把堆内存分割为很多不相关的区域（Region）（物理上不连续）。使用不同的 Region 来标识 Eden 区，幸存者 0 区，幸存者 1 区，老年代等
* G1 GC 有计划地避免在整个 Java 堆中进行全区域的垃圾收集，G1 跟踪各个 Region 里面的垃圾堆积的价值大小（回收所获得的空间大小以及回收所需时间的经验值），在后台维护一个优先列表，**每次根据允许收集的时间，优先回收价值最大的 Region**
* 由于这种方式侧重点在于回收垃圾最大量的区间，所以我们给 G1 一个名字，垃圾优先
* G1 是一款面向服务端应用的垃圾收集器，主要针对配备多核 CPU 及大容量内存的机器，以极高的概率满足 GC 停顿时间的同时，还兼顾高吞吐量的性能特性
* 在 jdk 1.7 正式启用，是 JDK 9以后默认的垃圾回收器，取代了 CMS，以及 Parallel Scavenge 和 Parallel Old 组合，被称为全能垃圾收集器
* 与此同时，CMS 在 JDK9 中被标记为废弃，在jdk8中不是默认的垃圾收集器，如果需要使用 G1，需要配置 `-XX:+UseG1GC`

#### G1 的特点

* 与其他GC相比，G1 使用了全新的分区算法
* 并行与并发
  * 并行性，在G1 回首器就，可以有多个 GC 线程同时工作，有效利用多核计算能力，此时用户线程 STW
  * 并发性，G1 拥有与应用程序交替执行的能力，部分工作可以和应用程序同时执行，因此，一般来说，不会再整个回收阶段发生完全阻塞应用程序的情况
* 分代收集
  * 从分代上看，G1 依然属于分代垃圾回收器，区分老年代和年轻代，年轻代依然有 Eden 区和 Survivor区，但从堆结构上看，他不要求整个 Eden 区、年轻代或者老年代都是连续的，也不再坚持固定大小和数量
  * 将堆空间分为若干个区域（Region），这些区域中包含了逻辑上的老年代和年轻代
  * 和之前各类回收器不同，它同时兼顾老年代和新生代，对比其他回收器的不同
* 空间整合
  * CMS 标记清除算法，内存碎片、若干次GC 之后进行一次碎片整理
  * G1 将内存划分为一个个 Region，但整体可以看作是标记压缩算法，两种算法都可以避免内存碎片，这种特性有利于程序长时间允许，分配大对象时不会因为无法找到连续空间而进行下一次 GC，尤其当 Java 堆非常大的时候，G1 的优势更加明显
* 可预测的停顿时间模型（软实时 soft real-time）
  * 这是 G1 相对于 CMS 的另一大优势，G1除了追求地停顿之唯恐，还能建立可预测的停顿时间模型，能让使用者明确指定在一个长度为 M 毫秒的时间片段内，消耗在垃圾收集上的时间不超过 N 毫秒
    * 由于分区的原因吗G可以只选取部分区域进行回收，这样缩小了回收的范围，因此对于全局停顿情况的发生也能得到较好的控制
    * G1 跟踪各个 Region 里面的垃圾堆积的价值大小（回收所获得的空间大小以及回收所需时间的经验值），在后台维护一个优先对象，每次根据允许收集的时间， 优先回收价值最大的 Region，保证了 G1 收集器在有限时间内获取尽可能高的收集效率
    * 相比于 CMS GC，G1 未必能做好 CMS 在最好情况下的延时停顿，但是最差情况要好很多

* 相比较于CMS，G1 还不具备全方位压倒性的优势，比如在用户程序运行过程中，G1 无论是为了垃圾收集产生的内存占用（Footprint）还是程序运行时的额外执行负载（Overload）都要比 CMS 高
* 从经验来说，在小内存应用上 CMS 的表现大概率会优于 G1，而 G1 在大内存应用上则发挥其优势，平衡点在 6-8GB之间

#### G1 回收器的参数设置

* `-XX:UseG1GC` 指定G1收集器
* `-XX:G1HeapRegionSize` 设置每个 Region 的大小，值是 2 的次幂，范围是 1MB - 32MB 之间，目标是根据最小的 Java 堆大小划分出 2048 个区域。默认是堆内存的 1/2000
* `-XX:ParallelGCThread` 设置期望达到的最大 GC 停顿时间指标（JVM 会尽力实现，但不保证）。默认是 200ms
* `-XX:ConcGCThreads` 设置并发标记的线程数，将 n 设置为并行垃圾回收线程的 1/4 左右
* `-XX:InitiatingHeapOccupancyPercent` 设置出发 GC 周期的 Java 堆占用阈值，超过此值，就出发 GC，默认是45

#### G1 回收器的常见操作步骤

* G1 的设计原则就是简化 JVM 性能调优，开发人员只需要散步即可完成
  * 开启 G1 收集器
  * 设置堆的最大内存
  * 设置最大停顿时间
* G1 提供了三种垃圾回收模式 Young GC、Mixed GC 和 Full GC，在不同条件下触发

#### G1 的适用场景

* 面向服务的应用，针对具有大内存、多处理器的机器（在普通大小的堆里表现并不惊喜）
* 最主要的应用是需要低 GC 延迟，并为具有大堆的应用程序提供解决方案（G1 通过每次只清理一部分而不是全部 Region 的增量式清理来保证 GC 停顿时间不会过长）
* 用来替换掉 jdk 1.5 中的 CMS 收集器，在下面情况，使用 G1 可能比 CMS 好
  * 超过 50% 的 Java 堆被活动数据占用
  * 对象分配频率或年代提升频率变化很大
  * GC 停顿时间过长（长于0.5-1秒）
* Hotspot垃圾收集器里，除了 G1 外，其他的垃圾收集器使用内置的 JVM 线程执行 GC 的多线程操作，而 G1 GC 可以采用应用线程承担后台运行的 GC 工作，及当 JVM 的 GC 线程处理速度慢时，系统会调用应用程序的线程帮助加速垃圾回收的过程 

#### Region 

* 使用 G1收集器时，他将整个堆划分成 2048 个大小相同的独立 Region 块，每个 Region 块大小根据堆空间的时间大小而定，整体被控制所在 1-32 MB 之间，且为 2 的 N 次幂，所有的 Region 大小相同，在 JVM 生命周期内不会被改变
* 虽然还保留有新生代老年代的概念，但新生代和老年代不再是物理隔离，搜然都是一部分 Region的集合，他国 Region的动态分配的方式实现逻辑上的连续
* 一个 Region 有可能属于 Eden Survivor，或者 Old 区，但一个 region 只能属于一个角色
* G1 垃圾收集器还增加了一种新的内存区域，叫做 Humongous 内存区域，主要用于存储大对象，如果超过 1.5 个 Region，就放到 H 中

#### G1 回收器垃圾回收的过程

* G1 GC 垃圾回收过程主要包括以下三个环节
  * 年轻代 GC
  * 老年代并发标记（Concurrent Mark）
  * 混合回收（Mixed GC）
  * （如果需要，单线程，独占式，高强度的 Full GC 还是继续存在的，他针对 GC 的评估失败提供了一种失败保护机制，即强力回收。）
* 当年轻代的 Eden 区空间用尽后，开始 YoungGC，即将存活对象移动到 幸存者区
* 当堆内存使用达到 45% 时，开始并发标记，标记完成。G1 的老娘了不需要整个老年代回收，一次只需扫描/回收一小部分老年代的区域就可以了。同时**这个老年代 Region 是和年轻代一起被回收的** 
* Remembered Set
  * 一个对象被不同区域引用的问题（即，Eden 区的对象被 old 区的对象引用）
  * 一个 Region 不可能是孤立的，一个 Region 中的对象可能被其他任意 Region 中对象引用，判断对象存活时，是否需要扫描整个 Java 堆才能保证准确？
  * 在其他分代收集器，也存在这样的问题（G1 更突出），即回收新生代也不得不同时扫描老年代，这样的话会降低 Minor GC 的效率
  * 解决方法，无论G1 还是其他分代收集器，JVM 都是使用 Remembered Set 来避免全局扫描
    * 每个 Region 都有一个对应的 Remembered Set（记忆集，记录了当前 Region 中的对象，有哪些另外的 Region 所引用） 
    * 每次 Reference 类型数据写操作时，都会产生一个 Write Barrier 暂时中断操作
    * 然后检查将要写入的引用指向对象是否和该 Reference 类型数据在不同的 Region（其他收集器，检查老年代对象是否引用了新生代对象）
    * 如果不同，通过 CardTable 把相关引用信息记录到引用指向对象所在 Region 对应的 Remembered Set 中
    * 当进行垃圾收集时，在 GC 根节点的枚举范围加入 Remembered Set，就可以保证不进行全局扫描也不会有遗漏

#### G1 回收过程一： 年轻代

* JVM 启动时，G1 先准备好 Eden 区，程序在运行过程中不断创建对象到 Eden 区，当 Eden 区空间耗尽时，G1 会启动一次年轻代的垃圾回收过程
* 年轻代垃圾回收只会回收 Eden 区和 Survivor 区
* Young GC 时，首先 G1 停止应用程序的执行（STW），G1 创建回收集（Collection Set），会收集是指需要被回收的内存分段的集合，年轻代回收过程的回收集包含年轻代 Eden 区和 Survivor 区所有的内存分段
* 然后开始如下的回收过程
  * **第一阶段，扫描根**，根指的是 static 变量指向的对象，正在执行的方法调用链条上的局部变量，根引用连同 RSet 记录的外部引用作为扫描对象的入口
  * **第二阶段，更新 RSet**，处理 dirty card queue 中的 card，更新 RSet，此阶段完成后， RSet 可以准确的反应老年代所在的内存分段中对象的引用
  * **第三阶段，处理 RSet**，识别被老年代对象指向的 Eden 中的对象的，这些被指向的 Eden 中的对象被认为是存活的对象
  * **第四阶段，复制对象**，此阶段，对象树被遍历，Eden 区内存段中存活的对象会被复制到 Survivor 区中的空的内存分段，Survivor 区内存段中存活的对象如果年龄未达阈值，年龄加1，达到阈值会被复制到 Old 区的内存分段，如果Survivor 空间不够，Eden 空间的部分数据会直接晋升到老年代
  * **第五阶段，处理引用**，处理 Soft，Weak，Phantom，Final，JNI Weak等引用，最终 Eden 空间的数据为空，GC 停止工作，而目标内存中的对象都是连续存储的，没有碎片，所以复制过程可以达到内存整理的效果，减少碎片

#### G1 回收过程二：并发标记

* **1.初始标记阶段**，标记从根节点直接可达的对象，这个阶段是 STW 的，并且会触发一下 Young GC
* **2.根区域扫描**，G1 GC 扫描 Survivor 区直接可达的老年代区域对象，并标记被引用的对象，这一过程必须在 Young GC 之前完成
* **3.并发标记（Concurrent Marking）**，在整个堆中进行并发标记（和应用程序并发执行），此过程可能会被 Young GC 中断，在并发标记阶段，若发现区域对象中的所有对象都是垃圾，那么这个区域会被立即回收。同时，并发标记过程中，会计算每个区域的对象活性（即存活对象的比例）
* **4.再次标记**，由于应用程序持续进行，需要修正上一次的标记结果，STW 发生，G1 中采用了比 CMS 更快的快照算法snapshot-at-the-beginning（SATB）
* **5.独占清理**，计算各个区域的存活对象和 GC 回收比例，并进行排序，识别可以混合回收的的区域，为下阶段做铺垫，此阶段为 STW
* **6.并发清理阶段**，识别并清理完全空闲的区域

#### G1 回收过程三：混合回收

* 当越来越多的对象晋升到老年代，为了避免内存堆被耗尽，虚拟机会触发一个混合垃圾收集器，即 Mixed GC，该算法不是一个 Old GC，除了回收整个 Young Region，还会回收一部分 Old Region
* 此处需要注意，是一部分老年代，而不是全部老年代，可以选择哪些Old Region 进行收集，从而可以对垃圾回收的耗时时间进行控制，也需要注意的 Mixed GC 并不是 Full GC
* 并发标记结束后，来看年代中百分百为垃圾的内存分段被回收了，部分为垃圾的内存分段被计算了出来，默认情况下，这些分段分 8 次进行回收
* 混合回收的收集包括 1/8 的老年代和 Young 区，混合回收算法和年轻代的回收算法一样，只是收集了老年代的内存分段
* 由于老年代中的内存分代是默认 8 次回收，G1 会优先回收垃圾多的内存分段，垃圾占内存分段比例越高，越会被先回收。并且有一个阈值会决定内存分段是否被回收，默认为 65%。意思是垃圾占内存分段比例太低的话，意味着存活对象过多，在复制的时候会花费更多时间
* 混会回收不一定要进行 8 次，有一个阈值，默认为 10%，意思是允许整个堆空间有 10% 空间被浪费，意外这如果发现可以回收的垃圾占堆内存的比例低于 10%，则不再进行混合回收，因为 GC 会花费更多的时间，但回收的内存却很少

#### G1 回收可选过程 Full GC

* G1 的初衷就是要避免 Full GC 的出现，但是如果上述方式不能正常工作，G1 会停止应用程序的执行，STW，使用单线程的内存回收算法进行垃圾回收，性能会非常差，应用程序停顿时间会很长
* 要避免 Full GC 的发生，一旦发生需要进行调整，什么时候会发生 Full GC 呢，比如堆内存太小，当 G1 在复制存活对象时候没有空的内存分段可以用，则会回退到 Full GC，这种情况可以通过增大内存解决
* 导致 Full GC 的原因可能有两个
  * 复制的时候没有足够的 to-space 来存放晋升对象
  * 并发处理过程完成之前空间耗尽

#### G1 回收过程补充

* 从 Oracle 官方透露出来的信息可知，回收阶段其实本有想过设计成和用户程序一起并发执行，当这件事做起来比较复杂，考虑到 G1 只是回收一部分 Region，停顿时间时用户可控的，所以不迫切去实现，而**选择把这个特性放到了 G1 之后出现的低延迟垃圾收集器 ZGC 中**
* 考虑到 G1 不仅仅时面向低延迟，停顿用户线程能够总大幅度提高垃圾收集效率，为了保证吞吐量所以才选择了完全暂停用户线程的实现方案

## 垃圾回收器总结

* 截至 jdk 1.8 ，一共有7款不同的垃圾收集器，每一款垃圾收集器都有不同的特点，在机体使用的时候，需要根据具体情况使用不同的垃圾收集器

| 垃圾收集器   | 分类       | 作用位置       | 使用算法           | 特点         | 适用场景                             |
| ------------ | ---------- | -------------- | ------------------ | ------------ | ------------------------------------ |
| Serial       | 串行       | 新生代         | 复制算法           | 响应速度优先 | 单 CPU 环境下的 client 模式          |
| ParNew       | 并行       | 新生代         | 复制算法           | 响应速度优先 | 多CPU环境Server 模式下于CMS 配合使用 |
| Parallel     | 并行       | 新生代         | 复制算法           | 吞吐量优先   | 适用于后台运算而不需要太多交互的场景 |
| Serial Old   | 串行       | 老年代         | 标记整理           | 响应速度优先 | 适用于单 CPU 环境下的 client 模式    |
| Parallel Old | 并行       | 老年代         | 标记整理           | 吞吐量优先   | 适用于后台运算而不需要太多交互的场景 |
| CMS          | 并发       | 老年代         | 标记清除           | 响应速度优先 | 适用于互联网或 B/S 业务              |
| G1           | 并发，并行 | 新生代，老年代 | 标记压缩，复制算法 | 响应速度优先 | 面向服务端应用                       |

* GC 发展阶段
  * Serial --> Parallel（并行） --> CMS （并发） --> G1 --> ZGC
