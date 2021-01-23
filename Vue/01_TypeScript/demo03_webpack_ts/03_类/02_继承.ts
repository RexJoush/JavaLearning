// 继承，类与类之间的关系

(() => {
    // 定义一个类
    class Person {
        public name: string; // 可以添加修饰符，和java一样
        private age: number;
        protected sex: string;
        // readonly time: string; // 只读属性，需要在定义时或者构造函数里初始化

        constructor(name: string, age: number, sex: string, readonly time: string) {
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.time = time;
        }

        // 定义函数
        sayHi(str: string): void {
            console.log("hello" + str);
        }
    }

    // 定义第二个类继承第一个类
    class Student extends Person {
        // 调用父类的构造方法
        constructor(name: string, age: number, sex: string, time: string) {
            super(name, age, sex, "aaa");
        };

        // 定义函数
        sayHi(): void {
            console.log("son's sayHi");
            super.sayHi("aa");
        };
    }

    const person = new Person("joush", 23, "male", "bbb");
    person.sayHi("aaaa");

    const stu = new Student("rex", 23, "male", "ccc");
    stu.sayHi();

})()