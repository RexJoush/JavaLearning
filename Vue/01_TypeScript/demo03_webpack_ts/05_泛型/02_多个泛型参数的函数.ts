// 函数的泛型中有多个参数
(() => {
    // 多泛型参数函数
    function getMsg <K, V>(key: K, value: V): [K, V] {
        return [key, value];
    }

    // 泛型接口
    interface IBaseCRUD <T> {
        data: Array<T>; // 存储数组，为 T 类型
        add: (t: T) => T; // 传入参数为 T 类型，返回值也是 T 类型
        getUserId: (id: number) => T | undefined; // 传入数字类型，返回值是数字类型
    }

    class User {
        id: number;
        name: string;
        age: number;
        constructor(id: number, name: string, age: number){
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

    class UserCRUD implements IBaseCRUD<User> {
        // 用来保存多个 User 对象
        data: Array<User> = [];
        
        // 添加用户方法
        add (user: User): User {
            // 产生 id
            user.id = Date.now() + Math.random();
            // 把用户信息添加到 data 数组中
            this.data.push(user);
            return user;
        } 
        // 根据 id 查询 User
        getUserId(id: number): User | undefined{
            return this.data.find(user => user.id === id);
        }
    }

    // 实例化id
    const userCRUD: UserCRUD = new UserCRUD();

    // 添加数据
    userCRUD.add(new User(1,"rex",23));
    userCRUD.add(new User(2,"joush",24));
    let {id} = userCRUD.add(new User(3,"Rex",25)); // 获取返回值的 id
    userCRUD.add(new User(4,"Joush",26));

    console.log(userCRUD.data);
    console.log(userCRUD.getUserId(id));

})()