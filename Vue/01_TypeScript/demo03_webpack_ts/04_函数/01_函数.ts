// 函数
(() => {

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
})()