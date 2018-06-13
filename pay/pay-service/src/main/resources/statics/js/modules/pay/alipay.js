var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "account",
            rootPId: -1
        },
        key: {
            url:"nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        config:{
        	account:null,
        	appid:'',
        	privateKey:'',
        	publicKey:'',
        	mercNo:'',
        	returnUrl:'',
        	notifyUrl:'',
        	queueName:''
        }
    },
    methods: {
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.config = {
                	account:null,
                	appid:'',
                	privateKey:'',
                	publicKey:'',
                	mercNo:'',
                	returnUrl:'',
                	notifyUrl:'',
                	queueName:''
                };
        },
        update: function () {
            var account = getConfigAccount();
            if(account == null){
                return ;
            }

            $.get(baseURL + "alipay/config/info/", {account:account}, function(r){
            	if(r.errcode == 0){
            		vm.showList = false;
	                vm.title = "修改";
	                vm.config = r.data;
                }else{
                	alert(r.errmsg);
                }
            });
        },
        del: function () {
            var account = getConfigAccount();
            if(account == null){
                return ;
            }

            confirm('确定要删除选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "alipay/config/delete",
                    data: {account:account},
                    success: function(r){
                        if(r.errcode == 0){
                            alert('操作成功', function(){
                                vm.reload();
                            });
                        }else{
                            alert(r.errmsg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            if(vm.validator()){
                return ;
            }

            var url = "alipay/config/save";
            $.ajax({
                type: "POST",
                url:  baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function(r){
                    if(r.errcode == 0){
                        alert('操作成功', function(){
                            vm.reload();
                        });
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        },
        reload: function () {
            vm.showList = true;
            config.table.refresh();
        },
        validator: function () {
            if(isBlank(vm.config.account)){
                alert("名称不能为空");
                return true;
            }
            if(isBlank(vm.config.appid)){
                alert("开发者的应用ID不能为空");
                return true;
            }
            if(isBlank(vm.config.privateKey)){
                alert("私钥不能为空");
                return true;
            }
            if(isBlank(vm.config.publicKey)){
                alert("公钥不能为空");
                return true;
            }
            if(isBlank(vm.config.mercNo)){
                alert("商户号不能为空");
                return true;
            }
            if(isBlank(vm.config.returnUrl)){
                alert("支付成功用户返回URL不能为空");
                return true;
            }
            if(isBlank(vm.config.notifyUrl)){
                alert("支付结果异步通知URL不能为空");
                return true;
            }
        }
    }
});


var config = {
    id: "configTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
config.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '定义名称', field: 'account', visible: false, align: 'center', valign: 'middle', width: '80px'},
        {title: '开发者的应用ID', field: 'appid', visible: false, align: 'center', valign: 'middle', width: '180px'},
        {title: '私钥', field: 'privateKey', align: 'center', valign: 'middle', sortable: true, width: '360px'},
        {title: '公钥', field: 'publicKey', align: 'center', valign: 'middle', sortable: true, width: '360px'},
        {title: '商户号', field: 'mercNo', align: 'center', valign: 'middle', sortable: true, width: '160px'},
        {title: '支付成功返回URL', field: 'returnUrl', align: 'center', valign: 'middle', sortable: true, width: '280px'},
        {title: '异步回调URL', field: 'notifyUrl', align: 'center', valign: 'middle', sortable: true, width: '280px'},
		{title: '队列名', field: 'queueName', align: 'center', valign: 'middle', sortable: true, width: '160px'},
		{title: '创建时间', field: 'createTime',align: 'center', valign: 'middle', sortable: true}
	];
    return columns;
};


function getConfigAccount () {
    var selected = $('#'+config.id).bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return null;
    } else {
        return selected[0].id;
    }
}


$(function () {
    var colunms = config.initColumn();
    var table = new TreeTable(config.id, baseURL + "alipay/config/list", colunms);
    table.setExpandColumn(2);
    table.setIdField("account");
    table.setCodeField("account");
    table.setExpandAll(false);
    table.init();
    config.table = table;
});
