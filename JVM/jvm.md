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

## 运行时数据区及线程

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

## 运行时数据区 — 程序计数器

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

## 运行时数据区 — 虚拟机栈

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

## 运行时数据区 — 本地方法栈

#### 简介

* Java 虚拟机栈是用于管理 Java 方法的调用，而本地方法栈用于管理本地方法的调用。
* 本地方法栈也是线程私有的
* 允许被实现成固定或者可动态扩展的内存大小（在内存溢出方面是相同的）
* 本地方法是使用 C 语言实现的
* 他的具体做法是，Native Method Stack 中登记 native 方法，在 Execution Engine 执行时加载本地方法
* 并不是所有 Java 虚拟机都拥有本地方法栈
* 在Hotspot 虚拟机中，直接将本地方法栈和虚拟机栈合二为一

## 运行时数据区 — 堆

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
* 年轻代又分为 Eden 空间，Surivivor0 空间（from 区）和 Surivivor1 空间 （to 区）

### 图解对象的分配过程

### Minor GC、Major GC、Full GC

### 堆空间分代思想

### 内存分配策略

### TLAB

### 小结堆空间的参数设置

### 堆时分配对象的唯一选择吗

## 本地方法

#### 什么是本地方法

* 简单讲，一个 Native Method 就是一个 Java 调用非 Java 代码的接口，一个 Native Method 的方法，该方法的实现是由非 Java 语言实现，比如 C，这个特征并非 Java 拥有，很多其他的编程语言都有这一机制，比如 C++ 中也有。

* 在定义一个 Native Method 时，并不提供实现体，因为其实现体时在外面实现的

  ```java
  private static native void registerNatives();
  ```

* Native 方法不能使用 abstract 修饰，因为 native 不是没有方法体，abstract 是没有方法体，两者矛盾

#### 为什么要使用 Native Method 

* 与 Java 环境外的交互
* 与 C/C++ 交互
* 与操作系统交互

