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
  * 新生代老年代的默认空间大小比例是 2：1
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

#### 对象的 finalization 机制

#### MAT 与 JProfiler 的 GC Roots 溯源

#### 清除阶段，标记-清除算法

#### 清除阶段，复制算法

#### 清除阶段，标记-压缩算法

#### 小结

#### 分代收集算法

#### 增量收集算法、分区算法
