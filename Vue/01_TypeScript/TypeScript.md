## 初识 TypeScript

#### TypeScript 的介绍

* TypeScript是一种由微软开发的开源、跨平台的编程语言。它是JavaScript的超集，最终会被编译为JavaScript代码。
* 2012年10月，微软发布了首个公开版本的TypeScript，2013年6月19日，在经历了一个预览版之后微软正式发布了正式版TypeScript
* TypeScript的作者是安德斯·海尔斯伯格，C#的首席架构师。它是开源和跨平台的编程语言。
* TypeScript扩展了JavaScript的语法，所以任何现有的JavaScript程序可以运行在TypeScript环境中。
* TypeScript是为大型应用的开发而设计，并且可以编译为JavaScript。
* TypeScript 是 JavaScript 的一个超集，主要提供了类型系统和对 ES6+ 的支持**，它由 Microsoft 开发，代码开源于 GitHub 上
* TypeScript 是 JavaScript 的一个超集，主要提供了类型系统和对 ES6+ 的支持，它由 Microsoft 开发，代码开源于 GitHub (opens new window)上

#### TypeScript 的特点

* 始于JavaScript，归于JavaScript
    - TypeScript 可以编译出纯净、 简洁的 JavaScript 代码，并且可以运行在任何浏览器上、Node.js 环境中和任何支持 ECMAScript 3（或更高版本）的JavaScript 引擎中。
* 强大的类型系统
    - 类型系统允许 JavaScript 开发者在开发 JavaScript 应用程序时使用高效的开发工具和常用操作比如静态检查和代码重构。
* 先进的 JavaScript
    - TypeScript 提供最新的和不断发展的 JavaScript 特性，包括那些来自 2015 年的 ECMAScript 和未来的提案中的特性，比如异步功能和 Decorators，以帮助建立健壮的组件。

#### 总结

* TypeScript 在社区的流行度越来越高，它非常适用于一些大型项目，也非常适用于一些基础库，极大地帮助我们提升了开发效率和体验

## 安装 TypeScript

```shell
# 运行命令，全局安装
npm install -g typescript

# 查看安装版本
tsc -V
```

## 第一个 TypeScript 程序

```typescript
(function () {
    function sayHi(str: string) {
        return "hello" + str;
    }
    var text = "kite";
    console.log(sayHi(text));
})();
```

#### 手动编译代码
```shell
# 使用 tsc 命令，生成对应的 js 文件
tsc hello.ts
```

#### vscode 自动编译
```shell
# 生成配置文件 tsconfig.json
tsc --init

# 修改 tsconfig.json 配置
    "outDir": "./js",
    "strict": false,
# 启动监视任务
    Terminal -- Run Task -- Show All Task... -- Watch tsconfig.json
```

## webpack 打包 ts

#### 打包过程
* 下载依赖
```shell
npm i -D typescript
npm i -D webpack@4.41.5 webpack-cli@3.3.10
npm i -D webpack-dev-server@3.10.2
npm i -D html-webpack-plugin clean-webpack-plugin
npm i -D ts-loader
npm i -D cross-env
```
* 配置打包文件选项
```javascript
// ./build/webpack.config.js

const {CleanWebpackPlugin} = require('clean-webpack-plugin')
const HtmlWebpackPlugin = require('html-webpack-plugin')
const path = require('path')

const isProd = process.env.NODE_ENV === 'production' // 是否生产环境

function resolve (dir) {
  return path.resolve(__dirname, '..', dir)
}

module.exports = {
  mode: isProd ? 'production' : 'development',
  entry: { // 程序主入口文件
    app: './src/main.ts'
  },
  // 类型名按照 name + 八位哈希值 + .js 命名
  output: {
    path: resolve('dist'),
    filename: '[name].[contenthash:8].js'
  },

  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        include: [resolve('src')]
      }
    ]
  },

  plugins: [
    new CleanWebpackPlugin({
    }),

    new HtmlWebpackPlugin({
      template: './public/index.html'
    })
  ],

  resolve: {
    extensions: ['.ts', '.tsx', '.js']
  },

  devtool: isProd ? 'cheap-module-source-map' : 'cheap-module-eval-source-map',

  devServer: {
    host: 'localhost', // 主机名
    stats: 'errors-only', // 打包日志输出输出错误信息
    port: 8081,
    open: true // 自动打开浏览器
  },
}
```
* 配置打包命令
```shell
# 生成 package.json 文件
npm init -y
```
``` javascript
// 在 package.json 中添加下面代码
"scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "dev": "cross-env NODE_ENV=development webpack-dev-server --config build/webpack.config.js",
    "build": "cross-env NODE_ENV=production webpack --config build/webpack.config.js"
},
```
* 运行与打包
```shell
# 运行
npm run dev

# 打包
npm run build
```
## TypeScript 的数据类型

#### 基本数据类型
```typescript
// 布尔类型 boolean
let falg: boolean = false;
console.log(falg);

// 数字类型 number
let a1: number = 10; // 十进制
let a2: number = 0b1010;  // 二进制
let a3: number = 0o12; // 八进制
let a4: number = 0xa; // 十六进制

// 字符串类型 string
let str1: string = "hello";
let str2: string = "world";
console.log(`${str1}, ${str2}`);

/*
    undefind 和 null
    默认情况下，undefined 和 null 是所有类型的子类型，即可以将 undefined 赋值给 number 类型的变量
    这里，需要将严格模式设置为 false，才能使用
*/
let und: undefined = undefined;
let nul: null = null;

/*
    数组
    两种定义方式
    let 变量名: 数据类型[] = [值1, 值2, 值3];
    let 变量名: Array<数据类型> = [值1, 值2, 值3];
*/
let arr1: number[] = [10, 20, 30, 40];
let arr2: Array<number> = [100, 200, 300];
console.log(arr1);
console.log(arr2);

/* 
    元组
    需要在定义的时候初始化给定的类型，可以在数组中存储不同类型的数据
*/
let arr3: [string, number, boolean] = ['aa', 100, true];

/*
    枚举
    枚举变量从 0 开始，也可指定第一个元素的起始值
*/
enum Color {
    RED,
    GREEN,
    BLUE
}
let color: Color = Color.RED;
console.log(Color[2]); // GREEN
console.log(color); // 0

/*
    any
    可以存储任何类型
*/
let notSure: any = 4;
notSure = "aaa";
notSure = false;
console.log(notSure);
let arr: any[] = [100, '200', true, { "aa": "aa" }];

/*
    void
    不表示任何类型
*/
// 表示没有任何返回值
function show(): void {
    console.log('aaa');
}
// 定义一个 void 类型的变量，可以存入 undefined嘛，但意义不大
let v: void = undefined;

/*
    object
*/
// 定义一个参数和返回值都是 obj 类型的函数
function getObj(obj: object): object {
    console.log(obj);
    return {
        name: 'aaa',
        age: 30
    };
}
console.log(getObj({ name: 'aa', age: 12 }));

/*
    联合类型 Union Types
    类型断言
        方式一: <类型>值
        方式二: 值 as 类型  tsx 中只能用这种方式
*/
// 返回数字或者字符串的长度
function getString(str: number | string): number {
    if((<string>str).length){
        return (<string>str).length;
    }
    else {
        return str.toString().length;
    }
}

// 即两种类型均可
console.log(getString("aaa"));
console.log(getString(11));
```
#### 接口

* 普通接口
```typescript
interface IPerson {
    // 普通类型
    readonly id: number; // 只读的属性，不能修改
    name: string;
    age: number;
    sex?: string; // 可以为空，加上 ?
}
```
* 函数类型接口
```typescript
interface ISearchFunc {
    /*
        函数类型
        为了使用接口表示函数类型，我们需要给接口定义一个调用签名。
        它就像是一个只有参数列表和返回值类型的函数定义。参数列表里的每个参数都需要名字和类型

        通过接口的方式作为函数的类型来使用
    */
    // 定义一个调用签名,查找第一个字符串中是否包含第二个字符串
    (source: string, subString: string): boolean
}

// 定义一个函数，类型就是上面定义的接口
const searchString: ISearchFunc = function (source: string, subString: string): boolean {
    // 在 source 字符串中查找 subString 字符串
    return source.search(subString) > -1;
}

// 调用函数
console.log(searchString("abcdefg", "abc"));
```
* 类类型接口
```typescript
// 定义一个接口
interface IFly {
    // 该方法没有实现
    fly(): void;
};

// 定义一个类，类的类型是上面定义的接口
class Person implements IFly {
    fly() {
        console.log("I can fly");
    }
}
// 实例化对象
const person = new Person();
person.fly();
```

#### 类

* 定义类
```typescript
// 此处的类和 java 的基本一样允许继承和多态，静态等特性
class Person {
    // 属性
    name: string;
    age: number;
    sex: string;
    static con: number; // 静态成员，用类名访问

    // 构造方法
    constructor(name: string, age: number, sex: string) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    };

    // 定义实例方法
    sayHi(str: string){
        console.log(`hello, my name is ${this.name}, I am ${this.age} year old, I am a ${this.sex}`);
    };
}
```
* 存取器
```typescript
// 外部传入姓氏和名字数据，同时使用 set 和 get 控制姓名数据
class Person {
    firstName: string;
    lastName: string;

    constructor(firstName: string, lastName: string) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // 读取
    get fullName() {
        return this.firstName + "_" + this.lastName;
    }

    // 设置
    set fullName(val: string) {
        let names = val.split('_');
        this.firstName = names[0];
        this.lastName = names[1];
    }
    
}

const person: Person = new Person("rex", "joush");
console.log(person);
// 获取 get 方法
console.log(person.fullName);

// 设置名字
person.fullName = "Rex_Joush";
console.log(person.fullName);
```

#### 函数
```typescript
// 函数声明，命名函数
function add(x: number, y: number): number {
    return x + y;
}

// 函数表达式，匿名函数
let add2 = function (x: number, y: number): number {
    return x + y;
}

// 函数的完整写法
// let 函数名: (参数列表) => 返回值类型 = function (参数列表): 返回值类型 {}
let add3: (x: number, y: number) => number = function (x: number, y: number): number {
    return x + y;
}

// 可选参数
/*
    定义一个函数，传入姓氏和姓名，可以得到（姓氏 + 名字 = 姓名）
    1.如果不穿任何内容，就返回默认的姓氏
    2.如果只传入姓氏，就返回姓氏
    3.如果传入姓氏和名字，那么返回的就是姓名
*/
let getFullName = function (firstName: string = 'rex', lastName?: string): string {
    // 判断名字是否传入了
    if (lastName) {
        return firstName + "_" + lastName;
    }
    else {
        return firstName;
    }
};

/*
    剩余参数，即参数列表
    通过 ...args 来接受变长参数
*/
function info(x: string, ...args: string[]) {
    console.log(x, args)
}
info('abc', 'c', 'b', 'a');

/*
    函数重载，通过类型区分，但这里的函数重载可以不像 java 那样写两个函数，参数列表通过 | 来区分即可
*/

// 定义函数实现
function add(x: string | number, y: string | number): string | number | undefined {

    // 在实现上我们要注意严格判断两个参数的类型是否相等，而不能简单的写一个 x + y
    if (typeof x === 'string' && typeof y === 'string') {
        return x + y
    } else if (typeof x === 'number' && typeof y === 'number') {
        return x + y
    }
}
```

#### 泛型
* 基础泛型
```typescript
// 可以传入任意类型的数组，使用泛型
function getArr3 <T> (value: T, count: number): T[] {
    let arr: T[] = [];

    for (let i = 0; i < count; i++) {
        arr.push(value);
    }
    return arr;
}
let arr2 = getArr3<number>(100, 2);
let arr3 = getArr3<string>("aaa", 2);
```
* 多泛型参数和泛型接口
```typescript
// 多泛型参数函数
function getMsg <K, V>(key: K, value: V): [K, V] {
    return [key, value];
}

// 泛型接口
interface IBaseCRUD <T> {
    data: Array<T>; // 存储数组，为 T 类型
    add: (t: T) => T; // 传入参数为 T 类型，返回值也是 T 类型
    getUserId: (id: number) => T | undefined; // 传入数字类型，返回值是数字类型
}
```
* 泛型类
```typescript
// 定义一个类，类的属性值是不确定的，方法中的参数及返回值的类型也是不确定的

// 定义一个泛型类
class GenericNumber<T> {
    defaultValue: T;
    add: (x: T, y: T) => T;
}

// 在实例化对象的时候，再确定泛型的类型
let g1: GenericNumber<number> = new GenericNumber<number>();
// 设置属性值
g1.defaultValue = 100;
g1.add = function (x, y) {
    return x + y;
}
```
* 泛型约束
```typescript
// 定义一个接口，用来约束
interface ILength {
    // 接口中有一个属性 length
    length: number;
}

function getLength <T extends ILength>(x: T): number {
    return x.length;
}

console.log(getLength<string>("What are you doing?"));
```
#### 其他
* 声明文件
    - 当使用第三方库时，我们需要引用它的声明文件，才能获得对应的代码补全、接口提示等功能
    - 什么是声明语句。假如我们想使用第三方库 jQuery，一种常见的方式是在 html 中通过标签引入 jQuery，然后就可以使用全局变量 $ 或 jQuery 了。但是在 ts 中，编译器并不知道 $ 或 jQuery 是什么东西
    ```javascript
    /* 
        当使用第三方库时，我们需要引用它的声明文件，才能获得对应的代码补全、接口提示等功能。
        声明语句: 如果需要ts对新的语法进行检查, 需要要加载了对应的类型说明代码
        declare var jQuery: (selector: string) => any;
        声明文件: 把声明语句放到一个单独的文件（jQuery.d.ts）中, ts会自动解析到项目中所有声明文件
        下载声明文件: npm install @types/jquery --save-dev
    */

    jQuery('#foo');
    // ERROR: Cannot find name 'jQuery'.

    // 这时，我们需要使用 declare var 来定义它的类型
    declare var jQuery: (selector: string) => any;
    jQuery('#foo');

    // declare var 并没有真的定义一个变量，只是定义了全局变量 jQuery 的类型，仅仅会用于编译时的检查，在编译结果中会被删除。它编译结果是：
    jQuery('#foo');

    // 一般声明文件都会单独写成一个 xxx.d.ts 文件
    // 创建 01_jQuery.d.ts, 将声明语句定义其中, TS编译器会扫描并加载项目中所有的TS声明文件
    declare var jQuery: (selector: string) => any;

    /*
        很多的第三方库都定义了对应的声明文件库, 库文件名一般为 @types/xxx, 可以在 https://www.npmjs.com/package/package 进行搜索
        有的第三库在下载时就会自动下载对应的声明文件库(比如: webpack),有的可能需要单独下载(比如jQuery/react)
    */
    ```

#### 内置对象
* Boolean
* Number
* String
* Date
* RegExp
* Error
```typescript
/*
    ECMAScript 的内置对象 
*/
let b: Boolean = new Boolean(1)
let n: Number = new Number(true)
let s: String = new String('abc')
let d: Date = new Date()
let r: RegExp = /^1/
let e: Error = new Error('error message')
b = true
// let bb: boolean = new Boolean(2)  // error

/*
    BOM 和 DOM 的内置对象
    Window
    Document
    HTMLElement
    DocumentFragment
    Event
    NodeList
*/
const div: HTMLElement = document.getElementById('test')
const divs: NodeList = document.querySelectorAll('div')

document.addEventListener('click', (event: MouseEvent) => {
    console.dir(event.target)
})
const fragment: DocumentFragment = document.createDocumentFragment()
```