(() => {
    // 定义一个类，类的属性值是不确定的，方法中的参数及返回值的类型也是不确定的

    // 定义一个泛型类
    class GenericNumber<T> {
        defaultValue: T;
        add: (x: T, y: T) => T;
    }

    // 在实例化对象的时候，再确定泛型的类型
    let g1: GenericNumber<number> = new GenericNumber<number>();
    // 设置属性值
    g1.defaultValue = 100;
    g1.add = function (x, y) {
        return x + y;
    }

    console.log(g1.add(2, 5));

    // 在实例化对象的时候，再确定泛型的类型
    let g2: GenericNumber<string> = new GenericNumber<string>();
    // 设置属性值
    g2.defaultValue = "bbb";
    g2.add = function (x, y) {
        return x + y;
    }
    console.log(g2.add("rex", "joush"));
})()