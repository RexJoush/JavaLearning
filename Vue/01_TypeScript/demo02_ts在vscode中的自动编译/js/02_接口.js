// 接口，一种约束
(function () {
    // 输出姓名
    function showFullName(person) {
        return person.fisrstName + "_" + person.lastName;
    }
    var person = {
        fisrstName: 'Rex',
        lastName: 'Joush'
    };
    console.log(showFullName(person));
})();
