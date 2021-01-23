// ts 中书写 js 的类
(() => {
    // 定义一个接口
    interface IPerson {
        firstName: string;
        lastName: string;
    };

    // 定义一个类
    class Person {
        // 定义属性
        firstName: string;
        lastName: string;
        fullName: string;

        // 定义构造器函数
        constructor (firstName: string, lastName: string) {
            // 更新属性信息
            this.firstName = firstName;
            this.lastName = lastName;
            this.fullName = firstName + "_" + lastName;
        }
    }

    // 定义函数显示全名
    function showFullName(person: IPerson) {
        return person.firstName + "_" + person.lastName;
    }

    // 实例化对象
    const person = new Person('rex', 'joush');
    console.log(showFullName(person));
})()