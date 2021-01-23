// 接口是对象的属性和方法的抽象
// 接口是一种类型，一种规范，一种能力，一种约束
(() => {

    interface IPerson {
        // 普通类型
        readonly id: number; // 只读的属性，不能修改
        name: string;
        age: number;
        sex?: string; // 可以为空，加上 ?
    }
})()