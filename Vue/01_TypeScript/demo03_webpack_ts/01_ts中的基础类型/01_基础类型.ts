/* 
    基础类型
    let 变量名: 数据类型 = 值
*/
(() => {
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
})()