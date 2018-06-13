var vm = new Vue({
    el:'#rrapp',
    data:{
        showList: true,
        title: null,
        config:{
        	type: 1
        },
        selected: null ,  
        options: [  
          { text: '请选择', value: '' },  
        ],
        word:{
        	id:null,
        	name:null,
        	words:null
        },
        cword: null,
        checkResult: null
    },
    methods: {
    	onUpload: function(e,extension){
    		var target = e.target;
    		var name=target.value;
    	    var fileName = name.substring(name.lastIndexOf(".")+1).toLowerCase();
    	    if(fileName !="txt"){
	           alert("请选择txt格式文件上传！");
	           return
	        }
    	    var fileSize = 0;         
    	    if ( !target.files) {     
    	       var filePath = target.value;     
    	       var fileSystem = new ActiveXObject("Scripting.FileSystemObject");        
    	       var file = fileSystem.GetFile (filePath);     
    	       fileSize = file.Size;    
    	    } else {    
    	    	fileSize = target.files[0].size;     
    	    }
    	    var reader = new FileReader();//新建一个FileReader
            reader.readAsText(target.files[0], "GBK");//读取文件 
            reader.onload = function(evt){ //读取完文件之后会回来这里
            	var words = evt.target.result.split("\r\n");
            	vm.word.words = words;
            }
    	},
        loadType: function(){
            $.ajax({
                type: "GET",
                url: baseURL + "word/type/getAll",
                success: function(r){
                    if(r.errcode == 0){
                    	var options = [{text:"请选择",value:-1}];
                    	for(var i in r.data){
                    		options[options.length] = { text: r.data[i].name, value: i };
                    	}
                    	vm.word.id = options[0].value;
                    	vm.word.name = options[0].text;
                    	vm.options=options;
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        },
        typeSelect: function(e){
        	var target = e.target;
        	vm.word.id = target.value;
        	vm.word.name = $(target).find("option:selected").text().trim();
        },
        del: function () {
        	if(vm.word.id == -1){
        		alert("请先选择类型");
        		return;
        	}
            confirm('确定要删除选中的记录？', function(){
            	var data = {
            		type: vm.word.id,
            		word: vm.cword
        		};
                $.ajax({
                    type: "GET",
                    url: baseURL + "word/remove",
                    data: data,
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
        wordsImport: function(){
        	if(vm.word.id == -1){
        		alert("请先选择类型");
        		return;
        	}
        	confirm('确定添加此敏感词吗？', function(){
        		var data = {
        			id: vm.word.id,
        			name: vm.word.name,
        			words: vm.word.words.toString()
        		};
                $.ajax({
                    type: "POST",
                    url: baseURL + "word/add",
                    data: data,
                    success: function(r){
                        if(r.errcode == 0){
                            alert('操作成功', function(){
                            });
                        }else{
                            alert(r.errmsg);
                        }
                    }
                });
            });
        },
        wordsWxport: function(){
        	if(!vm.word.id || vm.word.id==null || vm.word.id == -1 || vm.word.id.length ==0){
        		alert("请先选择词汇类型");
        		return;
        	}
        	window.open("/word/export/"+vm.word.id); 
        },
        check : function(){
        	var data = {
        		types:"",
        		text:vm.cword
        	};
        	
        	$.ajax({
                type: "GET",
                url: baseURL + "word/check",
                data: data,
                success: function(r){
                    if(r.errcode == 0){
                    	console.info(JSON.stringify(r.data,null,2));
                    	vm.checkResult = JSON.stringify(r.data,null,2);
                    }else{
                        alert(r.errmsg);
                    }
                }
            });
        	
        },
        validator: function () {
        }
    }
});

$(function(){
	vm.loadType();
});