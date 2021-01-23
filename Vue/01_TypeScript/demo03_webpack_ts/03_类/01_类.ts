// 类 
(() => {
    // 定义类
    class Person {
        // 属性
        name: string;
        age: number;
        sex: string;

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

    const person = new Person("joush", 22, 'male');
    person.sayHi("");
})()