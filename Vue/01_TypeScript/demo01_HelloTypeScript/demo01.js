(function () {
    function sayHi(str) {
        return "hello" + str;
    }
    var text = "kite";
    console.log(sayHi(text));
})();
// ts 如果直接书写 js 语法代码，那么 html 文件直接引入 ts 文件，在 Chrome 中可以直接使用
// 如果 ts 文件中有了 ts 的语法，那么需要将 ts 编译为 js 后，引入 js 文件才能运行
// ts 文件中的函数形参，使用了某个类型进行修饰，那么在最终的 js 编译中是没有的
// ts 中的变量是 let 修饰，那么编译后的 js 中就是 var 了
