define(['text!'+'/querydb/component/customConditions.html'], function(template){

    const component = {
        template: template,
        data: function () {
            return {
                inputConditionEnums : ["in", "notIn", "equalTo", "notEqualTo",
                    "greaterThan", "greaterThanOrEqualTo", "lessThan", "lessThanOrEqualTo",
                    "like", "notLike"],
                dateConditionEnums : ["between", "notBetween"],

                curCustomConditionTableColumnSelect: this.customConditionTableColumnSelect,
                curCustomConditionEnumSelect: this.customConditionEnumSelect
            }
        },
        props: {
            /**
             * 表字段选择的值
             */
            customConditionArray: {
                type: Array,
                required: true
            },
            /**
             * 所有的表字段的选项
             */
            allTableColumnOptions: {
                type: Array,
                required: true
            },
            /**
             * 所有的查询条件的选项
             */
            customConditionEnumOptions: {
                type: Array,
                required: true
            },
            /**
             * 删除自定义查询条件
             */
            handleDelCustomCondition: {
                type: Function,
                required: true
            },
            /**
             * 表格查询方法
             */
            handleQueryTableDataByCondition: {
                type: Function,
                required: true
            }
        },
        watch: {
            customConditionTableColumnSelect : function(newVal, oldVal){
                this.curCustomConditionTableColumnSelect = newVal;
            },
            curCustomConditionTableColumnSelect : function(newVal, oldVal){
                this.$emit("update:customConditionTableColumnSelect", newVal);
            },
            customConditionEnumSelect : function(newVal, oldVal){
                this.curCustomConditionEnumSelect = newVal;
            },
            curCustomConditionEnumSelect : function(newVal, oldVal){
                this.$emit("update:customConditionEnumSelect", newVal);
            }
        },
        methods: {
            /**
             * 处理切换查询条件类型时清空数据
             * @param conditionObj
             */
            handleCustomConditionEnumChange(conditionObj){
                if(this.dateConditionEnums.indexOf(conditionObj.customConditionEnumCode) >= 0){
                    conditionObj.columnVal = "";
                }else{
                    conditionObj.dateValArray = [];
                }
            }
        }
    };

    return component;
});