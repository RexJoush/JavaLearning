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
