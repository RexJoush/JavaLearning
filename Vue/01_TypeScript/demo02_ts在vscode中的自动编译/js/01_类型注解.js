// 类型注解，是一种轻量级的为函数或变量添加的约束
(function () {
    function showMsg(str) {
        return "hello vue" + str;
    }
    // let msg = [10, 20, 30];
    var msg = "aaa";
    console.log(showMsg(msg));
})();
