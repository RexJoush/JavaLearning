// ts 中书写 js 的类
(function () {
    ;
    // 定义一个类
    var Person = /** @class */ (function () {
        // 定义构造器函数
        function Person(firstName, lastName) {
            // 更新属性信息
            this.firstName = firstName;
            this.lastName = lastName;
            this.fullName = firstName + "_" + lastName;
        }
        return Person;
    }());
    // 定义函数显示全名
    function showFullName(person) {
        return person.firstName + "_" + person.lastName;
    }
    // 实例化对象
    var person = new Person('rex', 'joush');
    console.log(showFullName(person));
})();
