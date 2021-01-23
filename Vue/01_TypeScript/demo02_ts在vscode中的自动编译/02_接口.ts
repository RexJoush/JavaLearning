// 接口，一种约束

(() => {
    // 定义一个接口
    interface IPerson {
        fisrstName: string; // 姓氏
        lastName: string;   // 姓名
    }

    // 输出姓名
    function showFullName(person: IPerson) {
        return person.fisrstName + "_" + person.lastName;
    }

    const person: IPerson = {
        fisrstName: 'Rex',
        lastName: 'Joush'
    }

    console.log(showFullName(person));

})()