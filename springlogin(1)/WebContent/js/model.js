window.onload=function(){
	
	//增删改查
            new Vue({
                el:'#box',
                /* 所有数据存放的地方 */
                data:{
                	/*初始化所有数据的数组list  */
                    myData:[],
                    /*问题各项当前操作增和改的数据存值的地方，以及修改和删除的当前索引数据的存放*/
                    wenti:'',
                    type:'',
                    nowIndex:-100,
                    nowyzid:'',
                    /*模态框状态*/
                    modelstate:""	
                },
                methods:{	
                	addMsg:function(){
                		validator($('.defaultForm'))
                    	$('.defaultForm').bootstrapValidator('validate');
	                	 if($('.defaultForm').data('bootstrapValidator').isValid()){
	                		 console.log("验证通过")
	                		this.modelstate="modal"
	                			console.log(this.wenti+"===="+this.type);
	                    	this.$http({
	                             url:"add.action",
	                              data:{ //后台发送数据
	                            	  wenti:this.wenti,
	                                  type:this.type
	                             } 
	                         }).then(function(res){
	                        	  console.log(res);
	                        	  this.myData=[];
	                        	  this.init();
	                          }); 
	                    	this.wenti='';
	                        this.type='';
	                        this.modelstate=""
	                	 }
                    },
                    deleteMsg:function(){
                    	console.log(this.nowyzid )
                    	console.log(this.nowIndex)
                    	this.$http({
                            url:"remove.action",
                             data:{ //后台发送数据
                              yzid:this.nowyzid 
                            } 
                        }).then(function(res){
                        	this.myData.splice(this.nowIndex,1);
                         }); 
                    },
                    editMsg:function(){
                    	validator($('.defaultForm1'))
                    	$('.defaultForm1').bootstrapValidator('validate');
	                	 if($('.defaultForm1').data('bootstrapValidator').isValid()){
	                		 this.modelstate="modal"
	                    	 this.$http({
	                    		url:"edit.action",
	                    		data:{
	                    			yzid:this.nowyzid,
	                    			wenti:this.wenti,
	                    			type:this.type
	                    		} 
	                    	}).then(function(res){
	                    		this.myData=[];
	                        	this.init();
	                        }); 
	                    	 this.wenti='';
	                         this.type='';
	                         this.modelstate=""
	                	 }
                    },
                    init:function(){
                        //发送请求
                         this.$http({
                            url:"testinit.action",
                        }).then(function(res){
                            var json=res.data;
                            console.log(json)
                            //msgData绑定请求得到的数据
                            for(var i=0; i<json.length; i++){
                            	 console.log(json[i].Security)
                            	 this.myData.push({
                                    yzid:json[i].yzid,
                                    Security:json[i].Security,
                                   	type:json[i].type
                                }); 
                             }  
                        }); 
                    }
                },
                created(){
                  /*   console.log('实例已经创建完成'); */
                   this.init();
                },
            });
            //前端校验  
            function validator(elemnt){
            	elemnt.bootstrapValidator({
    	        message: 'This value is not valid',
    	        feedbackIcons: {
    	            valid: 'glyphicon glyphicon-ok',
    	            invalid: 'glyphicon glyphicon-remove',
    	            validating: 'glyphicon glyphicon-refresh'
    	        },
    	        fields: {
    	        	question: {
    	                message: '请输入问题',
    	                validators: {
    	                    notEmpty: {},
    	                    regexp: {
    	                        regexp: /^[\u4E00-\u9FA5]{5,10}$/,
    	                        message: '输入长度必须在5~10之间汉字'
    	                    },
    	                }
    	            },
    	            type: {
    	                message: '请输入问题类型',
    	                validators: {
    	                    notEmpty: {},
    	                   /* regexp: {
    	                        regexp: /^[a-zA-Z0-9_\.]{6,}$/,
    	                        message: '输入字母数字下划线长度6位以上'
    	                    },*/
    	                }
    	            },
  
              }
    	    })
            }
            
        };
        
      