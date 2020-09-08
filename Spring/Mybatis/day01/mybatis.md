##Mybatis入门
* Mybatis环境搭建
    - 第一步：创建Maven工程，导入坐标
    - 第二步：创建实体类和dao接口
    - 第三步：创建Mybatis的主配置文件
            SqlMapConfig.xml
    - 第四步：创建映射配置文件
            IUserDao.xml
* 环境搭建的注意事项
    - 创建 IUserDao.xml 和 IUerDao.java 是名称为了和之前保持一致，在Mybatis中把持久层操作接口名称和映射也叫做Mapper，IUserDao和IUserMapper是一样的
    - 在idea中创建目录时，需要按照包结构创建三级目录
    - Mybatis的映射配置文件必须和dao接口的包结构相同
    - 映射配置文件的mapper标签namespace属性必须是dao接口的全限定类名
    - 操作配置（select）的id属性取值必须是dao接口的方法名