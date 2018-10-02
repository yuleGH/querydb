//====================分页组件封装=====================
var commonPageBar = {
    data: function () {
        return {
            //需要覆盖的属性
            pageSize: 10,//一页显示多少条数据
            url: '',//访问后台的地址
            autoLoad: true,//是否自动加载

            currentPage: 1,//第几页
            currentPageNum: '1',

            param: {},//应该只包含form表单参数
            queryParams : {},//所有查询参数，包括分页信息
            total: 0,//总条目数
            pageData: [],//查询出来的数据

            field: null,//排序字段
            direction: null,//排序规则  ASC DESC
            loading: false//屏蔽层
        };
    },
    methods: {
        //监听 Table 的 sort-change 事件
        sortChange : function(e){
            this.sortLoad(e.prop, e.order);
        },
        //pageSize 改变时会触发
        currentChange: function (pageNum) {
            var _self = this;
            pageNum = pageNum == undefined ? 1 : pageNum;
            _self.currentPageNum = pageNum;
            _self.currentPage = pageNum;
            _self.$_loadData();
        },

        $_buildPaginationParam : function(params){
            var _self = this;
            var defaultParams = {
                start : (_self.currentPage - 1) * _self.pageSize,
                limit : _self.pageSize,
                field : _self.field,
                direction : _self.direction
            };
            if(!params){
                _self.queryParams = $.extend({}, defaultParams, _self.params);
            }else{
                _self.queryParams = $.extend({}, defaultParams, _self.$_dealParamsByTrim(params));
            }

        },
        //查询条件都trim
        $_dealParamsByTrim : function(params){
            var data = {};
            $.each(params, function(i, x){
               data[i] = x ? String(x).trim() : x;
            });
            this.params = data;
            return data;
        },
        //加载分页数据后，回调方法
        $_callbackAfterLoadData:function(data){

        },
        $_loadData: function (params) {
            var _self = this;
            _self.loading = true;
            _self.$_buildPaginationParam(params);
            commonFun.ajaxSubmit({
                url : _self.url,
                datas: _self.queryParams,
                backFunction : function(data){
                    _self.pageData = data.pageData;
                    _self.total = Number(data.total);
                    _self.$_callbackAfterLoadData(data);
                    _self.loading = false;
                },
                backFailFunction : function(){
                    _self.loading = false;
                }
            });
        },

        sortLoad : function(prop, order){
            this.field = prop;
            this.direction = (order==="ascending" ? "asc" : "desc");
            this.$_loadData();
        },
        reload: function (params) {
            this.currentPage = 1;
            this.$_loadData(params);
        },
        refresh: function (params) {
            this.currentPage = this.currentPageNum;
            this.$_loadData(params);
        },
        refreshByDeleteRow: function (params) {
            if(this.pageData.length == 1 && this.currentPageNum != 1){
                this.currentPage = this.currentPageNum - 1;
                this.currentPageNum = this.currentPage;
                this.$_loadData(params);
            }else{
                this.refresh(params);
            }
        }

    },
    created: function () {
        if (this.autoLoad) {
            this.currentChange();
        }else{
            this.loading = false;
        }
    }
};
