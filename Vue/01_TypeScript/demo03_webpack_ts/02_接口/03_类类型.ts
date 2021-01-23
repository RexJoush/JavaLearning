// 类 类型，类的类型可以通过接口来实现
(() => {
    // 定义一个接口
    interface IFly {
        // 该方法没有实现
        fly(): void;
    };

    // 定义一个类，类的类型是上面定义的接口
    class Person implements IFly {
        fly() {
            console.log("I can fly");
        }
    }
    // 实例化对象
    const person = new Person();
    person.fly();



    interface ISwim {
        swim(): void;
    };

    // 可以实现多个接口
    class Person2 implements IFly, ISwim {
        fly() {
            console.log("I can fly");
        }
        swim() {
            console.log("I can swimming");
        }
    };

    // 接口可以继承
    interface IMyFlySwim extends IFly, ISwim {

    };

    class Person3 implements IMyFlySwim {
        fly(){
            console.log("fly");
        };

        swim(){
            console.log("swim");
        };

    };
    const person3 = new Person3();
    person3.fly();
    person3.swim();
})()