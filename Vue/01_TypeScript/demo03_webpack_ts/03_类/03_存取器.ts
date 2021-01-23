// 存取器，类的 get 和 set 方法
(() => {
    // 外部传入姓氏和名字数据，同时使用 set 和 get 控制姓名数据
    class Person {
        firstName: string;
        lastName: string;

        constructor(firstName: string, lastName: string) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        // 读取
        get fullName() {
            return this.firstName + "_" + this.lastName;
        }

        // 设置
        set fullName(val: string) {
            let names = val.split('_');
            this.firstName = names[0];
            this.lastName = names[1];
        }
        
    }

    const person: Person = new Person("rex", "joush");
    console.log(person);
    // 获取 get 方法
    console.log(person.fullName);

    // 设置名字
    person.fullName = "Rex_Joush";
    console.log(person.fullName);
})()