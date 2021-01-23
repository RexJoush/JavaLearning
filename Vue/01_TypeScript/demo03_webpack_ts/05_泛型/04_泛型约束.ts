// 如果我们直接对一个泛型参数去 length 属性，会报错，因为这个泛型无法得知是否有此属性
(() => {
    // 定义一个接口，用来约束
    interface ILength {
        // 接口中有一个属性 length
        length: number;
    }

    function getLength <T extends ILength>(x: T): number {
        return x.length;
    }

    console.log(getLength<string>("What are you doing?"));
    // console.log(getLength<number>("What are you doing?")); // 因为 number 没有 length 属性，所以报错
})()