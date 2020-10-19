####Maven的核心概念
	* 约定的目录结构
	* POM
	* 坐标
	* 依赖
	* 仓库
	* 生命周期
	* 继承
	* 聚合
####常用的Maven命令
######注意：执行与构建过程相关的Maven命令，必须进入pom.xml所在的目录
	* 与构建过程相关的：编译、测试、打包...
	* 常用命令
		- mvn clean 清理
		- mvn compile 编译主程序
		- mvn test-compile 编译测试程序
		- mvn test 执行测试程序
		- mvn package 打包

######关于联网的问题
	* Maven本地仓库的位置 [家目录]\.m2\repository
	* 修改本地默认仓库的位置
		- 找到Maven解压目录\conf\settings.xml
		- 在settings.xml中找到localRepository标签
		- 将 <localRepository>xxxx</localRepository> 从注释中取出
		- 将标签内容改为自己的仓库位置

####POM
######含义 Project Object Model 项目对象模型
	* pom.xml对于Maven工程核心配置文件，与构建过程相关的设置都在此文件配置

####坐标
######Maven的坐标
	* 使用三个向量在仓库中唯一定位一个Maven工程
		- grouid: 公司或组织域名倒序+项目名
			` <groupid>cob.nuw.com.maven</groupid> `
		- artifactid: 模块名
			` <artifactid>Hello</artifaceid> `
		- version: 版本
			` <version>1.0.0</version> `
####仓库
######仓库分类
	* 本地仓库
	* 远程仓库
		- 局域网仓库，在局域网中，为局域网服务
		- 中央仓库，所有Maven工程都可以提供服务
		- 中央仓库镜像，
######仓库中保存的内容，Maven工程
	* Maven自身所需要的插件
	* 第三方框架或者工具jar包
	* 自己开发的Maven工程
####依赖
	* 依赖的范围
		- compile
			- 对主程序是否有效: 有效
			- 对测试程序是否有效: 有效
			- 是否参与打包: 参与
		- test
			- 对主程序是否有效: 无效
			- 对测试程序是否有效: 有效
			- 是否参与打包: 不参与
		- provided
			- 对主程序是否有效: 有效
			- 对测试程序是否有效: 有效
			- 是否参与打包: 不参与
			- 是否参与部署: 不参与
####生命周期
	* 各个构建环节的顺序