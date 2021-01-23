// 静态成员，和java一样

(() => {
    class Person {
        name1: string = "name";
        static name2: string = 'staticName';
    }

    console.log(Person.name2); // 类名调用
    console.log(new Person().name1); // 对象调用

})()