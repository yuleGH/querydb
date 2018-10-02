/**
 * 变量
 */
var constants = {

};

/**
 * 公共方法
 */
var commonFun = {
    /**
     * 公用ajax提交方法
     * $vue ： 当前vue对象，控制是否显示屏蔽层
     * vueLoadingTarget : 当前vue对象显示屏蔽层时的需要覆盖的 DOM 节点
     * url：方法地址,
     * datas：传入后端参数,
     * msg：成功提示信息,
     * backFunction：回调函数
     * backFailFunction : 失败后的回调函数
     * async : 是否异步 true 异步  false 同步  默认异步
     */
    ajaxSubmit : function(obj){
        if(obj.$vue){
            var loading = obj.$vue.$loading({
                target : obj.vueLoadingTarget || document.body,
                lock: true,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
        }

        $.ajax({
            url :  obj.url,
            type: "POST",
            dataType : 'json',
            data : obj.datas,
            async: (obj.async !== null && obj.async !== undefined) ? obj.async : true,
            success : function(data){
                if(obj.backFunction){obj.backFunction(data);}
                obj.$vue && loading.close();
            },
            failAlertCallback : function(data){
                if(obj.backFailFunction){obj.backFailFunction(data);}
                obj.$vue && loading.close();
            }
        });
    },
    /**
     * json转换为obj
     * @param jsonStr
     * @returns {Object}
     */
    jsonToObj : function(jsonStr){
        if(!jsonStr) return [];
        var obj = eval("("+jsonStr+")");
        return obj;
    },
};


