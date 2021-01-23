// 泛型，在定义函数接口或者类时，不能确定使用的数据类型，只有在调用的时候才能确定，此时会用到泛型
(() => {
    // rqeuire 传入两个参数，第一个参数是数据，第二个参数是数量，根据数量产生对应个数的数据，放在一个数组中
    function getArr(value: number, count: number): number[] {
        let arr: number[] = [];

        for (let i = 0; i < count; i++) {
            arr.push(value);
        }
        return arr;
    }

    let arr1 = getArr(100.123, 3);
    console.log(arr1); // [100.123, 100.123, 100.123]

    // require 同上，需要改成字符串
    function getArr2(value: string, count: number): string[] {
        let arr: string[] = [];

        for (let i = 0; i < count; i++) {
            arr.push(value);
        }
        return arr;
    }

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
    console.log(arr2); // [100, 100]
    console.log(arr3); // ["aaa", "aaa"]

})()