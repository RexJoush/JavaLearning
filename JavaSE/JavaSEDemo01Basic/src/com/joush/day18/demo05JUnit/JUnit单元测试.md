## Junit 单元测试
    - 测试分类：
        1.黑盒测试，不需要写代码，给输入值，看程序能否输出期望的值
        2.白盒测试，需要写代码，关注程序的具体执行流程
    - Junit 使用：白盒测试
        步骤：
            1.定义一个测试类（测试用例）
                建议
                    1.测试类名，被测试的类Test CalclatorTest
                    2.包名，xxx.xxx.xx.test
            2.定义一个测试方法，可以独立运行
                建议
                    1.方法名，test测试的方法名 testAdd()
                    2.返回值，void
                    3.参数列表，空参
            3.给方法加注解 @ExperimentSecure
            4.导入 Junit 的依赖环境
        判定结果：
            红色，失败
            绿色，成功
            一般会使用断言操作来处理结果  
                Assert.assertEquals(expected, acual);
        补充
            @Before
                修饰的方法会在测试方法运行之前自动执行
            @After
                修饰的方法会在测试方法之后自动执行
            注意：
                以上两个注解无论测试方法是否出现异常，均会执行

## 反射，框架的设计灵魂
    - 框架：半成品软件。可以在框架的基础上进行开发，简化编码
    - 反射：将类的各个部分封装为其他对象，这就是反射机制
        好处
            1.可以在程序运行过程中操作对象
            2.可以解耦，提高程序的可扩展性
    - 获取类对象的方式
        1.Class.forName("全类名"); 将字节码文件加载进内存，返回 Class 对象
            多用于配置文件，将类名定义在配置文件中。读取文件，加载类
        2.类名.class; 通过类名的属性，class 获取
            多用于参数的传递
        3.对象.getClass(); getClass() 方法定义在 Object 类中
            多用于对象的获取字节码方式
        
        结论
            同一个字节码文件（*.calss）在一次程序运行过程中，只会被加载一次，不论通过哪种方式获得的 Class对象，都是同一个
    - Class 对象的功能
        获取功能
            1.获取成员变量
                Filed[] getFields(); 获取所有 public 修饰的成员变量
                Filed getField(String name); 获取指定名称 public 修饰的成员变量
                
                Filed[] getDeclaredFields(); 获取所有的成员变量
                Filed getDeclaredField(String name);
            2.获取构造方法
                Constructor<?>[] getConstructors();
                Constructor<?> getConstructor(类<?>... parameterTypes);
                
                Constructor<?>[] getDeclaredConstructors();
                Constructor<?> getDeclaredConstructor(类<?>... parameterTypes);
            3.获取成员方法
                Method[] getMethods();
                Method getMethod(String name, 类<?>... parameterTypes)
                
                Method[] getDeclaredMethods();
                Method getDeclaredMethod(String name, 类<?>... parameterTypes)
            4.获取类名
                String getName();
        Filed 成员变量
            操作：
                1.设置值
                    void set(Object obj, Object value);
                2.获取值
                    void get(Object obj);
                3.忽略访问权限修饰符的安全检查
                    setAccessible(boolean b); 称为暴力反射
        Constrector 构造方法
            创建对象
                T newInstance(Object... initargs)
                如果使用空参构造方法创建对象，操作可以简化：Class 对象的 newInstance 方法
        Method 方法
            执行方法
                Object invoke(Object obj, Object... args)
            获取方法名称
                String getName(); 获取方法名
## 注解
    - 概念 说明程序，给计算机看的
    - 注释 用文字描述程序，给程序员看的 
