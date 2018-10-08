define(['text!'+'/querydb/component/tableConditions.html'], function(template){

    const component = {
        template: template,
        data: function () {
            return {
                curTableConditions: this.tableConditions
            }
        },
        props: {
            //表格查询条件
            tableConditions: {
                type: Array,
                required: true
            },
            //判断是否是日期类型
            judgeIsDateType : {
                type: Function,
                required: true
            },
            //表格查询方法
            handleQueryTableDataByCondition: {
                type: Function,
                required: true
            }
        },
        watch: {
            tableConditions : function(newVal, oldVal){
                this.curTableConditions = newVal;
            },
            curTableConditions : function(newVal, oldVal){
                this.$emit("update:tableConditions", newVal);
            }
        },
        methods: {

        }
    };

    return component;
});