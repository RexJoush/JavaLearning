/*
    vue 的插件库
 */

(() => {
    // 向外暴露的对象
    const MyPlugin = {};

    // 插件对象必须有一个 install 函数
    MyPlugin.install = function (Vue, options) {

        // 1.添加全局方法或属性
        Vue.myGlobalMethod = function () {
            // 逻辑
            console.log('Vue 函数对象方法 myGlobalMethod()');
        };

        // 2.添加全局资源，即自定义指令
        Vue.directive('my-directive', {
            bind(el, binding) {
                // 逻辑
                el.textContent = binding.value.toUpperCase();
            }
        });

        // 3.注册组件
        // Vue.mixin({
        //     created: function () {
        //         // 逻辑
        //     }
        // })

        // 4.添加实例方法
        Vue.prototype.$myMethod = function () {
            console.log("Vue 实例对象的方法 $myMethod()");
        }
    }

    // 向外暴露
    window.MyPlugin = MyPlugin;
})()
