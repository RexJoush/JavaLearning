## 数据库连接池
    1.概念，一个容器，存放数据库连接的容器
        当系统初始化好后，容器被创建，容器会申请一些连接对象，当用户访问数据库时，从容器中获取连接对象，访问完后将连接对象归还容器
    2.好处
        - 节约资源
        - 用户访问高效
    3.实现
        - 标准接口 DataSource  java.sql 包下的
            - 方法
                获取连接：getConnection()
                归还连接：Connection.close() 如果对象 Connection 是从连接池中获取的，那么调用 Connection.close() 则不会关闭，代表归还
        - 一般不自己实现，由数据库厂商实现
            - C3P0：数据库连接池技术
            - Druid：数据库连接池技术，阿里提供
    4.C3P0: 数据库连接池技术
        - 步骤
            - 导入jar包（两个） c3p0-0.9.5.2.jar , mchange-commons-java-0.2.12.jar
                不要忘记导入数据库的驱动 jar 包
            - 定义配置文件
                - 名称：c3p0.properties 或者 c3p0-config.xml
                - 路径：直接将文件放在 src 目录下即可
            - 创建核心对象，数据库连接池对象 ComboPooledDataSource
            - 获取连接 getConnection()
    5.Druid: 数据库连接池
        - 步骤
            - 导入 jar 包 druid-1.0.9.jar
            - 定义配置文件
                - 是 properties 形式的
                - 可以叫任意名称，放在认识目录下
            - 加载配置文件 Properties
            - 获取数据库连接池对象，通过工厂类来获取 DruidDataSourceFactory
            - 获取连接 getConnection()
        - 定义工具类
            - 定义一个类 JDBCUtils
            - 提供静态代码块加载配置文件，初始化连接对象
            - 提供方法  
                - 获取连接方法
                - 释放资源
                - 获取连接池方法