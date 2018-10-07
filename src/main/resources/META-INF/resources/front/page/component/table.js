define(['text!'+'/front/page/component/table.html', "/front/js/common/paginationTool.js"], function(template){

    const component = {
        template: template,
        mixins: [commonPageBar],
        data: function () {
            return {
                autoLoad: false,
                pageSize: 10,
                url: urls.getTableDataUrl
            }
        },
        props: {
            tableColumns: {
                type: Array,
                required: true
            }
        },
        methods: {
            clearTable(){
                this.pageData = [];
                this.total = 0;
                this.field = null;
                this.direction = null;
                this.currentPage = 0;
            }
        }
    };

    return component;
});