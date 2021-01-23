// 函数的可选参数和默认参数
(() => {
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

    // 函数调用
    // 什么也不传
    console.log(getFullName()); // rex

    // 只传入姓氏
    console.log(getFullName("Rex")); // Rex

    // 传入姓氏和名字
    console.log(getFullName("Rex", "Joush")); // Rex_Joush

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

    console.log(add(1, 2))
    console.log(add('a', 'b'))
})()