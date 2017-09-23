window.onload=function(){
	/*$("#validataPhone").on("click",function(){
		console.log("duijiao")
		 $(".err").show();
	})*/
	console.log("开始")
	
	 new Vue({
         data:{
        	 loginstate:"",
        	 //存储选择判断题目数据
        	 myXuan:[],
        	 myPan:[],
        	 myanswer:[],
        	 fenshu:"",
        	 myscore:'',
        	 userphone:'',
        	 userid:'',
        	 username:"",
        	 time:"",
        	 allAnswerStr:"",
        	 surplustime:"",
        	 //在线测试手机发送验证码状态
        	 yzmState:"",
        	 //存储题目信息
        	 questionMsg:[],
        	 //提交答案按钮
        	 submitAnswer:"false",
        	 //试题分页
        	 pageIndex:0,
        	 //试题数量
        	 questiontotal:20,
        	 //验证码按钮
        	 yzmbtn:'获取验证码',
        	//按钮状态
        	 setdis:false,
        	 setdisbystart:true,
        	 //yzm
        	 yzm:'',
        	//考试状态
        	Examinationstart:'true'
         },
         methods:{
        	 //开始答题
        	 login(){
        		if(this.yzmState){
        			console.log("需要验证码")
        			validator($('#validataPhone'))
        			$('#validataPhone').bootstrapValidator('validate');
        			if($('#validataPhone').data('bootstrapValidator').isValid()){
        				console.log("成功登录")
        				this.$http({
        					
        					url:"comfireyzm.action",  
        					data:{
        						yzm:this.yzm,
        						userphone:this.userphone
        					}
        				}).then(function(res){
        					var json=res.data
        					
        					if(json=="failure"){
        						$("#yzmerr").show();
        						$("input[name=yzmerr]").parent().parent().removeClass("has-success").addClass("has-error")
        						$("input[name=yzmerr]").next().removeClass("glyphicon-ok").addClass("glyphicon-remove")
        					}else{
        						if(json[0].score==undefined||json[0].score==0){
        							this.questionMsg=json[0];
        							console.log(this.questionMsg);
        							console.log(json[0].score+"----")
        							this.username=json[0].username, 
        							this.userid=json[0].userid, 
        							this.init();
        							this.loginstate='开始';
        							this.countdown();
        						}else{
        							$('#startexaminate').modal('show');
        						}
        						
        					}
        				}); 
        			}
        		}else{
        			validator($('#yzphone'))
	            	$('#yzphone').bootstrapValidator('validate');
	            	if($('#yzphone').data('bootstrapValidator').isValid()){
	            		this.$http({
		                	
		                     url:"nophoneyzm.action",  
		                     data:{
		                    	 userphone:this.userphone
		                     }
		                 }).then(function(res){
		                	 var json=res.data
		                	 console.log(json)
		                	 console.log(json==false)
		                	 if(json==false){
		                		$("#phoneyz").show();
	 		 	  				$("input[name=phone]").parent().parent().removeClass("has-success").addClass("has-error")
	 		 	  				$("input[name=phone]").parent().next().removeClass("glyphicon-ok").addClass("glyphicon-remove")
		                	 }else{
		                		 if(json[0].score==undefined||json[0].score==0){
		     						this.questionMsg=json[0];
		     						console.log(this.questionMsg);
		     						console.log(json[0].score+"----")
		     						this.username=json[0].username, 
		     						this.userid=json[0].userid, 
		     						this.init();
		     						this.loginstate='开始';
		     						this. countdown();
		     					}else{
		     						this.setdis=true;
		     						$('#startexaminate').modal('show');
		     					}
		                	 }
		                 }); 
		            	
            	}
        			
        		}
        	 },
        	 //下一页
        	 nextPage(pageSize){
        		 this.pageIndex+=pageSize;
        		 if(this.pageIndex>=this.questiontotal-pageSize){
        			 console.log("没有下一页了")
        			 this.submitAnswer="true";
        		 }
        	 },
        	 //选择题
        	 choose(answer,index,type){
        			 for(i=0; i<this.myanswer.length; i++){
        				 if(index==i)
	        				 {
	        				 if(type=="oneChoose"){
		        					 this.myanswer[i].myanswerWords=answer;
		        					 console.log( this.myanswer[i].myanswerWords)
		        					 if(answer!=this.myXuan[i].quesanswer)
		        					 {
		        						 this.myanswer[i].answerbyself="false"
		        					 }
		        					 else
		        					 {
		        						 this.myanswer[i].answerbyself="true"
		        					 }
	        				 }else  if(type=="moreChoose"){
	        					 this.myanswer[i].myanswerWords+=answer;
	        				 }else{
	        					 this.myanswer[i].myanswerWords=answer;
	        					 console.log( this.myanswer[i].myanswerWords)
	        					 if(answer!=this.myXuan[i].quesanswer)
	        					 {
	        						 this.myanswer[i].answerbyself="false"
	        					 }
	        					 else
	        					 {
	        						 this.myanswer[i].answerbyself="true"
	        					 }
	        				 }
        				 }
        				 
        			 }		 
        		
        		    		
        	 },
        	 //过滤答案
        	 filterStr(str){
        		 if(str.replace(/[^A]/g,"").length%2==0){
        			 str=str.replace(/[A]/g,"");
        		 }else{
        			 str=str.replace(/[A]/g,"")+'A'; 
        		 }
        		 if(str.replace(/[^B]/g,"").length%2==0){
        			 str=str.replace(/[B]/g,"");
        		 }else{
        			 str=str.replace(/[B]/g,"")+'B'; 
        		 }
        		 if(str.replace(/[^C]/g,"").length%2==0){
        			 str=str.replace(/[C]/g,"");
        		 }else{
        			 str=str.replace(/[C]/g,"")+'C'; 
        		 }
        		 if(str.replace(/[^D]/g,"").length%2==0){
        			 str=str.replace(/[D]/g,"");
        		 }else{
        			 str=str.replace(/[D]/g,"")+'D'; 
        		 }
        		 return str
        	 },
        	 //提交答案
        	 allAnswers(){
        		 for(i=0; i<this.myanswer.length; i++){
        			 if(this.myXuan[i].quesanswer.length>1){
        				 this.myanswer[i].myanswerWords=this.filterStr(this.myanswer[i].myanswerWords);
        				 if(this.myXuan[i].quesanswer==this.myanswer[i].myanswerWords)
        					 {
        					 this.myanswer[i].answerbyself='true'
        					 }
            		 }
                	if( this.myanswer[i].answerbyself=='true')
                		{
                		   this.myscore++; 
                		}

        		 }
        		 var data={
                		 userid:this.userid,
                		 myscore:this.myscore*5,
                		 myanswer:this.myanswer,
                		 time:this.surplustime+"",
                	 } 
                this.$http({
                 	
                     url:"insertdata.action", 
                     type:"post",
                     traditional:true,
                     dataType:"json",
                     data:{
                    	 data:JSON.stringify(data)
                    	 
                     }
                 }).then(function(res){
                	//答题完毕 跳页
                	 this.Examinationstart='false'
                      			
                 });
        		 
        		 
        		 
            },
            getYzm(){
            		validator($('#yzphone'))
	            	$('#yzphone').bootstrapValidator('validate');
	            	if($('#yzphone').data('bootstrapValidator').isValid()){
	            		this.$http({
		                	
		                     url:"start.action",  
		                     data:{
		                    	 userphone:this.userphone
		                     }
		                 }).then(function(res){
		                	 var json=res.data
		                	 console.log(json)
		                	 console.log(json==false)
		                	 if(json==false){
		                		$("#phoneyz").show();
	 		 	  				$("input[name=phone]").parent().parent().removeClass("has-success").addClass("has-error")
	 		 	  				$("input[name=phone]").parent().next().removeClass("glyphicon-ok").addClass("glyphicon-remove")
		                	 }else{
		                		 clearInterval(time);
			 		            	this.setdis=true;
			 		            	this.setdisbystart=false;
			 		            	this.yzmbtn=60;
			 		            	var _this=this;
			 		            	var time=setInterval(function(){
			 		            		_this.yzmbtn--;
			 		            		console.log(_this.yzmbtn)
			 		            		if(_this.yzmbtn==0){
			 		            			_this.setdis=false;
			 		            			_this.yzmbtn="重新发送验证码";
			 		            			clearInterval(time);
			 		            		}
			 		            	},1000)
		                	 }
		                 }); 
		            	
            	}
            },
            countdown(){
            	  var maxtime = 60*30 //一个小时，按秒计算，自己调整!  
            	  var maio=60;
            	  var _this=this;
            	  timer = setInterval(function(){
            		  if(maxtime>=0){  
                    	  minutes = Math.floor(maxtime/60);  
                    	  msg = "距离结束还有"+minutes+"分"+maio+"秒";  
                    	  if(maio==0)
                    		  {
                    		  maio=60;
                    		  }
                    	  console.log(msg)
                    	  _this.time=msg ;
                    	  _this.surplustime=30-minutes;
                    	  --maxtime;
                    	  --maio;
                    	  }  
                    	  else{  
                    	  clearInterval(timer);  
                    	  alert("时间到，考试结束!");  
                    	  this.allAnswers()
                    	  }  
            	  },1000);   

            },
            hiddenErr(){
            	console.log("对焦隐藏错误提示信息");
            	$(".err").hide();
            },
        	 init(){
            	var data=this.questionMsg;
                	console.log('试题初始化')
                      this.$http({
                         url:"getQuestions.action",   
                         data:{
                              data:JSON.stringify(data)
                         },
                         dataType:"json",
                 }).then(function(res){
                     var json=res.data;
                     console.log(json)
                    for(i=0; i<json['myqusetions'].length; i++){
                    	console.log(json['myqusetions'][i].quesanswer)
                    		this.myXuan.push({
                    			detail:json['myqusetions'][i].detail,
                    			A:json['myqusetions'][i].A,
                    			B:json['myqusetions'][i].B,
                    			C:json['myqusetions'][i].C,
                    			D:json['myqusetions'][i].D,
                    			quesanswer:json['myqusetions'][i].quesanswer,
                    			type:json['myqusetions'][i].type=="选择题"?1:(json['myqusetions'][i].type=="多选题"?2:3),
                    			
                    		});        
                    		this.myanswer.push({
                    			answerbyself:'false',
                    			myanswerWords:'',
                    			quesid:json['myqusetions'][i].quesid
                    		}); 
                    	}
                     for(i=0; i<json['otherqusetions'].length; i++){
                     	console.log(json['otherqusetions'][i].quesanswer)
                     		this.myXuan.push({
                     			detail:json['otherqusetions'][i].detail,
                     			A:json['otherqusetions'][i].A,
                     			B:json['otherqusetions'][i].B,
                     			C:json['otherqusetions'][i].C,
                     			D:json['otherqusetions'][i].D,
                     			quesanswer:json['otherqusetions'][i].quesanswer,
                     			type:json['otherqusetions'][i].type=="选择题"?1:(json['myqusetions'][i].type=="多选题"?2:3),
                     		});        
                     		this.myanswer.push({
                     			answerbyself:'false',
                     			myanswerWords:'',
                     			quesid:json['otherqusetions'][i].quesid
                     		}); 
                     	}
                    
                     
                    this.questiontotal=this.myXuan.length;
                    console.log(this.myXuan)
                 	});    
     			},
     			initPhoneYzmState(){
     				this.$http({
     					url:"selectyzmState.action"
     				}).then(function(res){
     					console.log(!res.data)
     					if(!res.data){
     						this.setdisbystart=false;
     					}
     					this.yzmState=res.data;
     				})
     			}
         	},
         	 created(){
                  console.log('主页实例已经创建完成');
                  this.initPhoneYzmState();
  
             }
     }).$mount('#box');;
}

	function validator(elemnt){
		elemnt.bootstrapValidator({
	    message: 'This value is not valid',
	    feedbackIcons: {
	        valid: 'glyphicon glyphicon-ok',
	        invalid: 'glyphicon glyphicon-remove',
	        validating: 'glyphicon glyphicon-refresh'
	    },
	    fields: {
	    	phone: {
	            message: '请输入手机号',
	            validators: {
	                notEmpty: {},
	               /* regexp: {
	                    regexp: /^[a-zA-Z0-9_\.]{6,}$/,
	                    message: '输入字母数字下划线长度6位以上'
	                },*/
	            }
	        },
	        yzmerr: {
	            message: '请输入验证码',
	            validators: {
	                notEmpty: {},
	               /* regexp: {
	                    regexp: /^[a-zA-Z0-9_\.]{6,}$/,
	                    message: '输入字母数字下划线长度6位以上'
	                },*/
	            }
	        }
	    
	  }
	})
	}