//用户管理模块  
var userAdministration={  
        template:'#model1',
        props: ['userstate'],
        data:() => ({
        	/*初始化所有数据的数组list  */
            myData:[],
            /*主页组件通信传过来的登录人的权限和名称信息  */
            username:"",
           /* 分页信息*/
            total:"",
            size:10,
            begin:1,
         /*   删除数据*/
            nameid:"",
            //权限加载
            deleteUser:''
        	
        }),
        methods:{
        	/*分页的方法*/
        	changPage(page){
        		if(page<=1){
        			this.begin=1;
        		}else if(page>=this.total){
        			this.begin=this.total;
        		}
        		this.myData=[];
    			this.init()
        		
        	},
        	 deleteMsg(){
            	 console.log(this.nameid)
            	this.$http({
                    url:"deletebyAdmin.action",
                   
                     data:{ //后台发送数据
                    	 nameid:this.nameid 
                    } 
                }).then(function(res){
                	this.myData=[];
                	this.init()
                 }); 
            },
        	init:function(){
        		 
        		console.log(this.mynameid+"-----------")
                //发送请求
                 this.$http({
                    url:"main.action",
                    data:{
                    	name:GetQueryString("username"),
                 		size:this.size,
                 		begin:this.begin
                    }
                }).then(function(res){
                    var json=res.data;
                    console.log(json)
                    //msgData绑定请求得到的数据
                    //用户列表数据
                   /* console.log(json[json.length-1][0].name)*/
                     for(var i=0; i<json['users'].length; i++){
                    	 this.myData.push({
                    		 phone:json['users'][i].phone,
                    		 sfzid:json['users'][i].sfzid,
                    		 name:json['users'][i].name,
                    		 nameid:json['users'][i].nameid,
                    		 email:json['users'][i].email,
                    	/*	 powerstate:json['users'][i].powerstate*/
                        }); 
                     } 
                    this.deleteUser=json['delete']
                    this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                }); 
        		
        		console.log("kaishi")
    		}
        },
        created(){
             this.init();
        }
    };  
  
//面试管理模块
var InterviewAdministration={  
        template:'#model2',
        data:() => ({
        	/*初始化所有数据的数组list  */
            myData:[],
            /*问题各项当前操作增和改的数据存值的地方，以及修改和删除的当前索引数据的存放*/
            /* 分页信息*/
            total:"",
            size:10,
            begin:1,
           
            nowIndex:-100,
            nowyzid:'',
            /*模态框状态*/
            modelstate:"",	
            username:'',
            userphone:'',
            usersource:'',
            email:'',
            position:'',
            userid:'',
            thisscore:"",
            //在线测试发送手机验证码状态
            yzmState:"",
            //查询数据存贮
            questions_diffculty:[],
            questions_position:[],
            questions_kpoint:[],

            thisDiffculty:"",
            thisquestions_count:"",
            thisquestions_position:"",
            
            otherDiffculty:"",
            otherquestions_count:"",
            otherquestions_position:"",
    /*        thisquestions_kpoint:"请选择考点",*/
            //权限
            deletepeople:"",
            addPeople:"",
            updatePeople:"",
            
        	
        }),
        methods:{
        	/*分页的方法*/
        	changPage(page){
        		if(page<=1){
        			this.begin=1;
        		}else if(page>=this.total){
        			this.begin=this.total;
        		}
        		this.questions_diffculty=[],
                this.questions_position=[],
             /*   this.questions_kpoint=[],*/
               /* this.questions_type=[],*/
        		this.myData=[];
    			this.init()
        		
        	},
        	nochangephoneYzmState(){
        		console.log("---"+this.yzmState)
        		$("#checkStateByphnoe").prop("checked",this.yzmState)
        	},
        	phoneYzmState(){
        		this.yzmState=!this.yzmState;
        		var str=this.yzmState+"";
        		console.log(this.yzmState)
	    		this.$http({
	    			url:"updateyzmState.action",
	        			data:{
	        				yzmstate:str,
	        			}
	        		}).then(function(res){
	        			$("#checkStateByphnoe").prop("checked",this.yzmState)
	        		})
        	},
        	addMsg(){
        		console.log(this.username+"---")
        		this.modelstate=""
        		validator($('.defaultForm'))
            	$('.defaultForm').bootstrapValidator('validate');
            	 if($('.defaultForm').data('bootstrapValidator').isValid()){
            		 console.log("验证通过")
            		this.modelstate="modal";
            		 var otherpos
            		 if(this.otherquestions_position==""){
            			 otherpos=0;
            		 }else{
            			  otherpos={
        					 position:this.otherquestions_position,
        					 count:this.otherquestions_count,
        					 thisdiffcult:this.otherDiffculty
            			 }
            		 }
                	this.$http({
                         url:"interviewinsert.action",
                         traditional:true,
                         dataType:"json",
                         data:{
                        	 username:this.username,
                             userphone:this.userphone,
                             usersource:this.usersource,
                             email:this.email,
                             position:this.thisquestions_position,
                             count:this.thisquestions_count,
                             thisdiffcult:this.thisDiffculty,
                        	 data:JSON.stringify(otherpos)
                        	 
                         }
                     }).then(function(res){
                    	  console.log(res);
                    	  this.myData=[];
                    	  
                    	  this.init();
                      }); 
                
            	 }
            },
            editineterview(){
            	this.questions_diffculty=[],
                this.questions_position=[],
        		console.log(this.username+"---")
        		this.modelstate=""
        		validator($('.defaultFormedit'))
            	$('.defaultFormedit').bootstrapValidator('validate');
            	 if($('.defaultFormedit').data('bootstrapValidator').isValid()){
            		 console.log("验证通过")
            		this.modelstate="modal";
                	this.$http({
                         url:"changeInterviews.action",
                         dataType:"json",
                         data:{
                        	 userid:this.userid,
                             userphone:this.userphone,
                             usersource:this.usersource,
                             email:this.email,
                             score:this.thisscore,
                             
                         }
                     }).then(function(res){
                    	  console.log(res);
                    	  this.myData=[];
                    	  this.init();
                      }); 
                
            	 }
            },
            deleteMsg:function(){
        
            	this.$http({
                    url:"interviewdelect.action",
                     data:{ //后台发送数据
                      userid:this.userid 
                    } 
                }).then(function(res){
                	this.myData=[];
                	this.init();
                 }); 
            },

            init:function(){
            	 //发送请求
                this.$http({
                   url:"interviewfind.action",
                   data:{
                   	name:GetQueryString("username"),
                		size:this.size,
                		begin:this.begin
                   }
               }).then(function(res){
                   var json=res.data;
                   console.log(json)
                   //msgData绑定请求得到的数据
                    for(var i=0; i<json['interview'].length; i++){
                   	
                   	 this.myData.push({
                             username:json['interview'][i].username,
                             userphone:json['interview'][i].userphone,
                             usersource:json['interview'][i].usersource,
                             score:json['interview'][i].score,
                             email:json['interview'][i].email,
                             position:json['interview'][i].position,
                             userid:json['interview'][i].userid,
                       }); 
                    }  
                   this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                   this.deletepeople=json['delete'];
                   this.addPeople=json['insert'];
                   this.updatePeople=json['update'];
                   this.yzmState=json['yzmState'];
                 //questions_diffculty绑定请求得到的数据
                   for(var i=0; i<json['diffcults'].length; i++){
                  	 this.questions_diffculty.push({
                           diffculty:json['diffcults'][i].diffculty,
                      }); 
                   } 
                   //pos绑定请求得到的数据
                   for(var i=0; i<json['pos'].length; i++){
                  	 this.questions_position.push({
                  		pos:json['pos'][i].pos,
                      }); 
                   } 
                   console.log(this.total+"--------total")
               }); 
            }
        },
        created(){
          /*   console.log('实例已经创建完成'); */
           this.init();
        }
    };  
   

//专业管理模块
var ProfessionalAdministration={  
        template:'#model3',
        data:()=>({
            	/*初始化所有数据的数组list  */
                myData:[],
                /*问题各项当前操作增和改的数据存值的地方，以及修改和删除的当前索引数据的存放*/
                pos:'',
                /*专业的创建时间*/
                edittime:"",
                nowIndex:-100,
                /*模态框状态*/
                modelstate:"",
            	/* 分页信息*/
                total:"",
                size:10,
                begin:1,
                //权限
                deletpos:"",
                addpos:""
        }),
        methods:{
        	/*分页的方法*/
        	changPage(page){
        		if(page<=1){
        			this.begin=1;
        		}else if(page>=this.total){
        			this.begin=this.total;
        		}
        		this.myData=[];
    			this.init()
        		
        	},
        	addMsg(){
        		this.modelstate=""
        		validator($('.defaultForm'))
            	$('.defaultForm').bootstrapValidator('validate');
            	 if($('.defaultForm').data('bootstrapValidator').isValid()){
            		 console.log("验证通过")
            		this.modelstate="modal"
                	this.$http({
                         url:"proinsert.action",
                          data:{ //后台发送数据
                        	  pos:this.pos
                              
                         } 
                     }).then(function(res){
                    	  console.log(res);
                    	  this.myData=[];
   
                    	  this.init();
                    	 
                      }); 
            	 }
            	
            },
            deleteMsg(){
            	console.log(this.nowyzid )
            	console.log(this.nowIndex)
            	this.$http({
                    url:"prodelect.action",
                     data:{ //后台发送数据
                    	 pos:this.pos 
                    } 
                }).then(function(res){
                	 this.myData=[];
                	this.init();
                	this.pos ="";
                 }); 
            },
            init(){
            	  //发送请求
                this.$http({
                   url:"profind.action",
                   data:{
                	    name:GetQueryString("username"),
                		size:this.size,
                		begin:this.begin
                   }
               }).then(function(res){
                   var json=res.data;
                   console.log(json)
                   //msgData绑定请求得到的数据
                    for(var i=0; i<json['pos'].length; i++){
                   	 this.myData.push({
                           yzid:json['pos'][i].yzid,
                           Security:json['pos'][i].Security,
                          	type:json['pos'][i].type,
                          	pos:json['pos'][i].pos,
                          	edittime:json['pos'][i].edittime,
                          	qusetionsCount:json['posNums'][i].Quescounts
                       }); 
                    } 
                 
                   this.deletpos=json['delete'];
                   this.addpos=json['insert'];
                /*   qusetionsCount:<json['pos']*/
                   this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                }); 
               
            },
           
        },
        created(){
            console.log('专业管理模块实例已经创建完成'); 
            this.init();
        }
    };   	
//权限管理模块
var JurisdictionAdministration={  
        template:'#model4',
        data:()=>({
            	/*初始化所有数据的数组list  */
                myData:[],
                userstate:"",
                thisuserpowerstate:[],
                username:"",
                name:"",
               /* 分页信息*/
                total:"",
                size:10,
                begin:1,
                active:false,
                nameid:"",
              //权限选中与非选中
            	thisactive:0,
           
                //权限管理
            	Nopowers:[],
                mypower:[],
                //delpowers
                delpowers:[],
                //addpowers
                addpowers:[],
                //编辑权限模态框

        }),
        methods:{
        	//增删权限改变class
        	changeClass(index){
        		this.thisactive = index;
        	},
        	/*分页的方法*/
        	changPage(page){
        		if(page<=1){
        			this.begin=1;
        		}else if(page>=this.total){
        			this.begin=this.total;
        		}
        		this.myData=[];
    			this.init()
        		
        	},
        	
        	editPower(thisname){
        		this.thisuserpowerstate=[]
        		this.Nopowers=[]
        		this.nameid=thisname;
                	 this.$http({
                		url:"findOnePower.action",
                		data:{
                			nameid:this.nameid,
                		} 
                	}).then(function(res){
                		var json=res.data;
                		for(var i=0; i<json['mypower'].length; i++){
                			console.log(json['mypower'][i].powerdetail)
      	                   	 this.thisuserpowerstate.push({
      	                   		show:false,
      	                   		powerdetails:json['mypower'][i].powerdetail,
      	                   		powerids:json['mypower'][i].powerid,
      	                   		powermodels:json['mypower'][i].powermodel		 
      	                       }); 
                          }
                		
                		
               		 for(var i=0; i<json['Nopowers'].length; i++){
                         	 this.Nopowers.push({
                         		show:false,
                         		powerdetails:json['Nopowers'][i].powerdetail,
                         		powerids:json['Nopowers'][i].powerid,
                         		powermodels:json['Nopowers'][i].powermodel	 
                             }); 
                          }
                		 this.Nopowers.sort()
                    });     

            },
            delpowerLi(item){
            	console.log("删除Li")
            	item.show = !item.show;
            	if(item.show){
            		this.delpowers.push({
            			show:false,
                   		powerdetails:item.powerdetails,
                   		powerids:item.powerids,
                   		powermodels:item.powermodels
            		})
            		
            	}else{
            		/*this.delpowers.splice(item, 1);*/
            		 for(var i=0; i<this.delpowers.length; i++){
            			 if(item.powerids==this.delpowers[i].powerids){
            				 this.delpowers.splice(i, 1);
            			 }
            		 }
            	}
            	console.log(this.delpowers)
            },
            addpowerLi(item){
            	
            	item.show = !item.show;
            	if(item.show){
            		/*console.log(item)*/
            		this.addpowers.push({
            			show:false,
                   		powerdetails:item.powerdetails,
                   		powerids:item.powerids,
                   		powermodels:item.powermodels
            		})
            	}else{
            		 for(var i=0; i<this.addpowers.length; i++){
            			 if(item.powerids==this.addpowers[i].powerids){
            				 this.addpowers.splice(i, 1);
            			 }
            		 }
            	}
            	console.log(this.addpowers)
            },
            deletePower(){
            	console.log("删除")
            	for(j=0;j<this.delpowers.length;j++){
    				this.Nopowers.push({
    					show:this.delpowers[j].show,
                   		powerdetails:this.delpowers[j].powerdetails,
                   		powerids:this.delpowers[j].powerids,
                   		powermodels:this.delpowers[j].powermodels	
    				});
    				for(i=0;i<this.thisuserpowerstate.length;i++){
    					if(this.thisuserpowerstate[i].powerids==this.delpowers[j].powerids){
           				 this.thisuserpowerstate.splice(i, 1);
           			 }
        			}
    			}
            	this.delpowers=[]
        	},
           addPower(){
            	console.log("添加")
    			for(j=0;j<this.addpowers.length;j++){
    				this.thisuserpowerstate.push({
    					show:this.addpowers[j].show,
                   		powerdetails:this.addpowers[j].powerdetails,
                   		powerids:this.addpowers[j].powerids,
                   		powermodels:this.addpowers[j].powermodels	
    				});
    				for(i=0;i<this.Nopowers.length;i++){
    					if(this.Nopowers[i].powerids==this.addpowers[j].powerids){
           				 this.Nopowers.splice(i, 1);
           			 }
        			}
    			}
            	this.addpowers=[]
            },
            updatePower(){
            	console.log(this.nameid)
            	var data={
            		nameid:this.nameid,
            		thispower:this.thisuserpowerstate
            	}
            	this.$http({
            		url:"changepowersByNameid.action",
                    type:"post",
                    traditional:true,
                    dataType:"json",
                    data:{
                   	 data:JSON.stringify(data)
                   	 
                    }
                }).then(function(res){
               	 var json=res.data
               	 console.log(json)
                         			
                });
            },
        	init:function(){
                //发送请求
                 this.$http({
                    url:"powerEditInit.action",
                    data:{
                    	name:GetQueryString("username"),
                 		size:this.size,
                 		begin:this.begin
                    }
                }).then(function(res){
                    var json=res.data;
                    //msgData绑定请求得到的数据
                    //用户列表数据
                     for(var i=0; i<json['users'].length; i++){
                    	 this.myData.push({
                    		 phone:json['users'][i].phone,
                    		 sfzid:json['users'][i].sfzid,
                    		 name:json['users'][i].name,
                    		 nameid:json['users'][i].nameid,
                    		 email:json['users'][i].email,
                    	
                        }); 
                     }
                    for(var i=0; i<json['mypower'].length; i++){
	                   	 this.mypower.push({
	                   		powerdetails:json['mypower'][i].powerdetail,
	                   		powerids:json['mypower'][i].powerid,
	                   		powermodels:json['mypower'][i].powermodel		 
	                       }); 
                    }
                   
                    this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                    console.log( this.powes)
                }); 
    		}
        },
        created(){
            /*   console.log('实例已经创建完成'); */
        	
             this.init();
        },
    };  


//试题管理模块
var QuestionsAdministration={  
        template:'#model5',
        data:()=>({
            	/*初始化所有数据的数组list  */
                myData:[],
                /*问题各项当前操作增和改的数据存值的地方，以及修改和删除的当前索引数据的存放*/
                wenti:'',
                detail:'',
                A:'',
                B:'',
                C:'',
                D:'',
                quesanswer:'',
                diffculty:'',
                position:'',
                kpoint:'',
                type:"",
                quesid:'',
                //表格信息
                tableName:"试题管理信息表",
                
                nowIndex:-100,
                nowquesid:'',
                /*模态框状态*/
                modelstate:"",
            	/* 分页信息*/
                qusetionTotal:"",
                total:"",
                size:10,
                begin:1,
                //查询数据存贮
                questions_diffculty:[],
                questions_position:[],
                questions_kpoint:[],
                questions_type:[],
                thisDiffculty:"请选择试题难度",
                thisquestions_type:"请选择试题类型",
                thisquestions_position:"请选择专业",
                thisquestions_kpoint:"请选择考点",
                //权限
                addqusetions:"",
                delqusetions:"",
                searchquestions:"",
                updatequestions:"",
                ImportExcel:"",
                outExcel:"",
                //所有问题的个数
                allQusetionsCount:"",
                //分页控制
                searchControll:false,
                //搜索之后的分页页数
                searchThisPageIndex:0,
                searchtotal:"",
                //导出excel不选中的列
                notExported:[],
        		//导入数据失败结果集
        		errImportData:[]
            }),
            methods:{
            	change(){
            		console.log(this.type+"----")
            	},
            	outExcelColum(_this){
            		if(!_this.target.checked){
            			this.notExported.push(_this.currentTarget.getAttribute("index"));
            		}else{
            			for(i=0;i<this.notExported.length;i++){
            				if(this.notExported[i]==_this.currentTarget.getAttribute("index")){
            					this.notExported.splice(i, 1);
            				}
            			}
            		}
            		console.log(this.notExported)
            		this.outexcel()
            	},
            	/*dataOutPutSelect(_this){
            		if(_this.target.selectedIndex==1){
            			this.searchMsg();
            		}else{
            		   this.searchControll=false;
                  	   console.log("全部数据启用初始化分页");
                  	   this.questions_diffculty=[],
	                   this.questions_position=[],
	                   this.questions_kpoint=[],
	                   this.questions_type=[];
                  	   this.myData=[];
                  	   this.init();
            		}
            	},*/
            	changePos(){
            		console.log(this.thisquestions_position);
            		this.questions_kpoint=[]
            			this.$http({
            				url:"findKpoint.action",
            				data:{
            					pos:this.thisquestions_position=="请选择专业"?null:this.thisquestions_position,
            				}
            			}).then(function(res){
            				console.log(res['data'])
            				//questions_kpoint绑定请求得到的数据
            				for(var i=0; i<res['data'].length; i++){
            					this.questions_kpoint.push({
            						kpoint:res['data'][i].kpoint,
            					}); 
            				} 
            				
            			})
            			console.log(this.thisquestions_kpoint)
            	},
            	searchMsg(){
                	console.log("搜索")
                	//发送请求
                    this.$http({
                       url:"searchPaper.action",
                       data:{
                    	   size:this.allQusetionsCount,
                     	   begin:1,
                    	   diffculty:this.thisDiffculty=='请选择试题难度'?null:this.thisDiffculty,
                    	   position:this.thisquestions_position=='请选择专业'?null:this.thisquestions_position,
                    	   kpoint:this.thisquestions_kpoint=='请选择考点'?null:this.thisquestions_kpoint,
                    	   type:this.thisquestions_type=='请选择试题类型'?null:this.thisquestions_type,
                       },
                   }).then(function(res){
                       var json=res.data;
                       console.log(res)
                        this.myData=[];
                       //msgData绑定请求得到的数据
                       for(var i=0; i<json['searchQusetions'].length; i++){
                      	 this.myData.push({
                      		 quesid:json['searchQusetions'][i].quesid,
                      		 detail:json['searchQusetions'][i].detail,
                               type:json['searchQusetions'][i].type,                                  
                               A:json['searchQusetions'][i].A,
                               B:json['searchQusetions'][i].B,
                               C:json['searchQusetions'][i].C,
                               D:json['searchQusetions'][i].D,
                               quesanswer:json['searchQusetions'][i].quesanswer,
                               diffculty:json['searchQusetions'][i].diffculty,
                               position:json['searchQusetions'][i].position,
                               kpoint:json['searchQusetions'][i].kpoint,
                          }); 
                       } 
                       this.searchtotal=json['searchtotal'];
                     /*  this.searchPageNum=parseInt(json['searchtotal']%this.size==0?(json['searchtotal']/this.size):(json['searchtotal']/this.size+1));*/
                       console.log(this.searchtotal+"-----"+this.qusetionTotal)
                    
                       
                       
                    if(this.searchtotal!=this.qusetionTotal){
                    	   this.searchControll=true;
                    	   console.log("部分数据 启用搜索分页")
                    	   this.outexcel();
                       }else{
                    	   this.searchControll=false;
                    	   console.log("全部数据启用初始化分页");
                    	   this.questions_diffculty=[],
	                   	   this.questions_position=[],
	                   	   this.questions_kpoint=[],
	                   	   this.questions_type=[],
                    	   this.myData=[];
                    	   this.init();
                       }
                   });
                },
            	/*分页的方法*/
            	changPage(page,searchControll,searchChangeSize){
                	if(!searchControll){
                		//初始化分页处理
                		if(page<=1){
                			this.begin=1;
                		}else if(page>=this.total){
                			this.begin=this.total;
                		}
                		this.questions_diffculty=[],
                		this.questions_position=[],
                		this.questions_kpoint=[],
                		this.questions_type=[],
                		this.myData=[];
                		this.init()
                	}else{
                		//点击搜索内容分页处理
                		console.log(this.searchThisPageIndex+"开始")
                		if(searchChangeSize==10){
                			if(this.searchThisPageIndex>=this.searchtotal-searchChangeSize){
                				console.log("没有下一页了")
                				return false;
                			}else{
                				this.searchThisPageIndex+=searchChangeSize;
                			}
                		}
                		if(searchChangeSize==-10){
                			if(this.searchThisPageIndex<=0){
                				console.log("没有上一页了")
                				return false;
                			}else{
                				this.searchThisPageIndex+=searchChangeSize;
                			}
                		}
                		if(searchChangeSize==0){
                			console.log("第一页")
                			this.searchThisPageIndex=0;
                		}
                		if(searchChangeSize==this.searchtotal){
                			console.log("最后一页")
                			this.searchThisPageIndex=parseInt(this.searchtotal%10==0?this.searchtotal:(this.searchtotal-this.searchtotal%10));
                		}
                		 
                	}
            		
            	},
        	destroyBox(){
            	$('#editQusetionsBox').on('hidden.bs.modal',
    				function() {
    				    $(".defaultForm1").data('bootstrapValidator').destroy();
    				    $('.defaultForm1').data('bootstrapValidator', null);
    					validator($('.defaultForm1'))
    				});	
        	},
        	outexcel(){
        		if(this.outExcel){
        			$("table").find("caption").remove();
        			var _this=this;
        			var arr=[]
        			for(var i in this.notExported){
        				arr.push(parseInt(this.notExported[i]));
        			}
        			console.log(arr)
        			setTimeout(function(){
        				$("table").tableExport({
        					headings: true, 
        					footers: true, 
        					formats: ["xls", "csv", "txt"],
        					fileName:_this.tableName,
        					bootstrap: true,
        					position: "bottom",
        					//ignoreRows: 11,//设置不被导出的表格行，可以是数值或一个数值数组。
        					ignoreCols:arr//设置不被导出的表格列，可以是数值或一个数值数组。
        				});
        			},100)
        		}
        	},
        	addMsg(){
        		 this.modelstate=""
        		validator($('.defaultForm'))
            	$('.defaultForm').bootstrapValidator('validate');
            	 if($('.defaultForm').data('bootstrapValidator').isValid()){
            		 console.log("验证通过")
            		this.modelstate="modal"
                	this.$http({
                         url:"papersinsert.action",
                          data:{ //后台发送数据
                              detail:this.detail,
                              A:this.A,
                              B:this.B,
                              C:this.C,
                              D:this.D,
                              quesanswer:this.quesanswer,
                              diffculty:this.diffculty,
                              position:this.position,
                              kpoint:this.kpoint,
                              type:this.type
                         } 
                     }).then(function(res){
                    	  console.log(res);
                    	 /* $("#addqusetion").on("hidden", function() {$('.defaultForm')[0].reset();});*/
                    	  this.myData=[];
                    	  this.init();
                      }); 
                   
            	 }
            },
            deleteMsg(){
            	console.log(this.nowquesid )
            	console.log(this.nowIndex)
            	this.$http({
                    url:"papersdelect.action",
                     data:{ //后台发送数据
                    quesid:this.nowquesid
                    } 
                }).then(function(res){
                	this.myData=[];
                	this.init();
                 }); 
            },
            editQusetions(index,detail,A,B,C,D,quesanswer,diffculty,position,kpoint,type,quesid){
            	$('#editQusetionsBox').modal()
            	this.detail=detail;
            	this.A=A;
            	this.B=B;
            	this.C=C;
            	this.D=D;
            	this.quesanswer=quesanswer;
            	this.diffculty=diffculty;
            	this.position=position;
            	this.kpoint=kpoint;
            	this.type=type;
            	this.quesid=quesid;
            	   
            	 $('#editQusetionsBox').modal('show');
            },
            fileChange(){
            	$('#ImportDataBox').modal('show');
            },
            ImportData(){
            	var formData = new FormData($( "#uploadForm" )[0]);  
            	var _this=this;
            	$.ajax({  
                    url: 'ImportExcel.action',  
                    type: 'post',  
                    data: formData,  
                    /* async: false,  
                    cache: false,  */ 
                    contentType: false,  
                    processData: false,
                    dataType:'json',
                    success:function(res){
                    	console.log(res);
                       for(var i=0; i<res.length; i++){
                          	_this.errImportData.push({
                          		 errData:res[i]
                          	 })
                        }
                    	$('#notImportDataMsg').modal('show');	
                    	_this.myData=[];
                    	_this.init();
                    }
               });  
            },
            editMsg(){
            	/*console.log(this.type)*/
            	  this.questions_diffculty=[],
                  this.questions_position=[],
                  this.questions_kpoint=[],
                  this.questions_type=[],
            	validator($('.defaultForm1'))
            	$('.defaultForm1').bootstrapValidator('validate');
            	 if($('.defaultForm1').data('bootstrapValidator').isValid()){
                	 this.$http({
                		url:"papersupdate.action?",
                		data:{
                              detail:this.detail,
                              A:this.A,
                              B:this.B,
                              C:this.C,
                              D:this.D,
                              quesanswer:this.quesanswer,
                              diffculty:this.diffculty,
                              position:this.position,
                              kpoint:this.kpoint,
                              type:this.type,
                              quesid:this.quesid
                              
                		} 
                	}).then(function(res){
                		 $("#editQusetionsBox").modal('hide');
                		 $("#editQusetionsBox").on("hidden", function() {$('.defaultForm1')[0].reset();});
                		this.myData=[];
                    	this.init();
                    }); 
            	 }
            	 this.destroyBox()
            },
            init(){
            	
            	//发送请求
                this.$http({
               	 url:"papersfind.action",
                    data:{
                   	name:GetQueryString("username"), 
                 		size:this.size,
                 		begin:this.begin,
                    }
               }).then(function(res){
                   var json=res.data;
                   console.log(json)
                   //msgData绑定请求得到的数据
                   for(var i=0; i<json['questions'].length; i++){
                	 
                   	 this.myData.push({
                   		 quesid:json['questions'][i].quesid,
                   		 detail:json['questions'][i].detail,
                        type:json['questions'][i].type,                                  
                        A:json['questions'][i].A,
                        B:json['questions'][i].B,
                        C:json['questions'][i].C,
                        D:json['questions'][i].D,
                        quesanswer:json['questions'][i].quesanswer,
                        diffculty:json['questions'][i].diffculty,
                        position:json['questions'][i].position,
                        kpoint:json['questions'][i].kpoint,
                            
                       }); 
                    } 
                   //questions_diffculty绑定请求得到的数据
                   for(var i=0; i<json['diffcults'].length; i++){
                  	 this.questions_diffculty.push({
                           diffculty:json['diffcults'][i].diffculty,
                      }); 
                   } 
                   //type绑定请求得到的数据
                   for(var i=0; i<json['type'].length; i++){
                  	 this.questions_type.push({
                  		type:json['type'][i].type,
                      }); 
                   } 
                   //pos绑定请求得到的数据
                   for(var i=0; i<json['pos'].length; i++){
                  	 this.questions_position.push({
                  		pos:json['pos'][i].pos,
                      }); 
                   } 
                   this.addqusetions=json['insert'];
                   this.delqusetions=json['delete'];
                   this.searchquestions=json['select'];
                   this.updatequestions=json['update'];
                   this.ImportExcel=json['ImportExcel'];
                   this.outExcel=json['outExcel'];
                   this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                   this.qusetionTotal=json['total'];
                   this.allQusetionsCount=json['total']
                   this.outexcel();
               }); 
            }
        },
        created(){
            /*   console.log('实例已经创建完成'); */
        	
             this.init();
          
        },
        updated(){
         	$('#myfile').filestyle({
 				htmlIcon : '<span class="glyphicon glyphicon-folder-open"></span> ',
 				text: '  导入试题数据',
 				btnClass : 'btn-success'
 			});
        },
        mounted(){
            //var _this=this;
            //接收A组件的数据
            Event.$on({
            	
            })
        }
    };
  
//统计管理模块
var StatisticsAdministration={  
        template:'#model6',
        data:()=>({
            	/*初始化所有数据的数组list  */
                myData:[],
                /*模态框状态*/
                modelstate:"",
                //表格信息
                tableName:"考试结果统计表",
                //统计管理数据
                allposition:[],
                fromwhere:[],
                //查询数据存贮
                statistic_name:"",
                statistic_pos:"请选择专业",
                statistic_fromwhere:"请选择来源",
                statistic_score:"请选择成绩范围",
            	/* 分页信息*/
                total:"",
                size:10,
                begin:1,
                //权限
                selectResult:"",
                //分页控制
                searchControll:false,
                //搜索之后的分页页数
                searchThisPageIndex:0,
                searchtotal:"",
                //导出excel不选中的列
                notExported:[],
                //初始化数据总数
                initTotal:"",
                //搜索到的数据总数
                searchTotal:""
            }),
            methods:{
            	outExcelColum(_this){
            		if(!_this.target.checked){
            			this.notExported.push(_this.currentTarget.getAttribute("index"));
            		}else{
            			for(i=0;i<this.notExported.length;i++){
            				if(this.notExported[i]==_this.currentTarget.getAttribute("index")){
            					this.notExported.splice(i, 1);
            				}
            			}
            		}
            		console.log(this.notExported)
            		this.outexcel()
            	},
            	/*分页的方法*/
            	changPage(page,searchControll,searchChangeSize){
                	if(!searchControll){
                		//初始化分页处理
                		if(page<=1){
                			this.begin=1;
                		}else if(page>=this.total){
                			this.begin=this.total;
                		}
                		this.myData=[];
                		this.allposition=[]
            			this.fromwhere=[]
            			this.init()
                	}else{
                		this.begin=1;
                		//点击搜索内容分页处理
                		console.log(this.searchThisPageIndex+"开始")
                		if(searchChangeSize==10){
                			if(this.searchThisPageIndex>=this.searchTotal-searchChangeSize){
                				console.log("没有下一页了")
                				return false;
                			}else{
                				this.searchThisPageIndex+=searchChangeSize;
                			}
                		}
                		if(searchChangeSize==-10){
                			if(this.searchThisPageIndex<=0){
                				console.log("没有上一页了")
                				return false;
                			}else{
                				this.searchThisPageIndex+=searchChangeSize;
                			}
                		}
                		if(searchChangeSize==0){
                			console.log("第一页")
                			this.searchThisPageIndex=0;
                		}
                		if(searchChangeSize==this.searchTotal){
                			console.log("最后一页")
                			this.searchThisPageIndex=parseInt(this.searchtotal%10==0?this.searchtotal:(this.searchtotal-this.searchtotal%10));
                		}
                		 
                	}
            		
            	},
            /*	分页的方法
            	changPage(page){
            		if(page<=1){
            			this.begin=1;
            		}else if(page>=this.total){
            			this.begin=this.total;
            		}
            		this.myData=[];
            		this.allposition=[]
        			this.fromwhere=[]
        			this.init()
            		
            	},*/
        	pos_change(){
        		console.log(this.statistic_pos)
        	},
        	outexcel(){
        	 if(this.outExcel){
	       		 $("table").find("caption").remove();
	       		 var _this=this;
	       		 var arr=[]
	       		 for(var i in this.notExported){
	       			 arr.push(parseInt(this.notExported[i]));
	       		 }
	       		 console.log(arr)
	                setTimeout(function(){
		                   	$("table").tableExport({
	                		 	headings: true, 
	                		    footers: true, 
	                		    formats: ["xls", "csv", "txt"],
	                		    fileName:_this.tableName,
	                		    bootstrap: true,
	                		    position: "bottom",
	                		    //ignoreRows: 11,//设置不被导出的表格行，可以是数值或一个数值数组。
	                		    ignoreCols:arr//设置不被导出的表格列，可以是数值或一个数值数组。
		                   	});
	                },100)
	        	}
        	},
            init:function(){
            	  //发送请求
                this.$http({
                   url:"selectNotNull.action",
                   data:{
                   	name:GetQueryString("username"),
                		size:this.size,
                		begin:this.begin
                   }
               }).then(function(res){
                   var json=res.data;
                   console.log(json)
                   //msgData绑定请求得到的数据
                   for(var i=0; i<json['statics'].length; i++){
                   	 this.myData.push({
                   		 username:json['statics'][i].username,
                           usersource:json['statics'][i].usersource,
                           position:json['statics'][i].position,
                          	score:json['statics'][i].score,
                          	costtime:json['statics'][i].costtime
                       }); 
                    }
                 /*  面试者专业数组存储*/
                   //console.log(json['pos'][0])
                   for(var i=0; i<json['pos'].length; i++){
	                   this.allposition.push({
	                	   pos:json['pos'][i].pos,
	               
	                   }); 
                   }
                   //面试者来源数据存在
                   for(var i=0; i<json['source'].length; i++){
	                   this.fromwhere.push({
	                	  usersource:json['source'][i].usersource,
	               
	                   }); 
                    }
                   this.selectResult=json['select'];
                   this.outExcel=json['outExcel'];
                   this.total=parseInt(json['total']%this.size==0?(json['total']/this.size):(json['total']/this.size+1));
                   this.initTotal=json['total'];
                   this.outexcel();
               }); 
            },
            searchMsg(){
            	console.log("搜索")
            	//发送请求
                this.$http({
                /*	 //查询数据存贮
                    statistic_name:"",
                    statistic_pos:"请选择专业",
                    statistic_fromwhere:"请选择来源",
                    statistic_score:"请选择成绩范围",*/
                   url:"selectInterview.action",
                   data:{
                	   size:this.initTotal,
                	   begin:this.begin,
                	   score:this.statistic_score=='请选择成绩范围'?null:this.statistic_score,
                	   position:this.statistic_pos=='请选择专业'?null:this.statistic_pos,
                	   usersource:this.statistic_fromwhere=='请选择来源'?null:this.statistic_fromwhere,
                	   username:this.statistic_name==''?null:this.statistic_name,
                   },
               }).then(function(res){
                   var json=res.data;
                   console.log(res)
                    this.myData=[];
                   //msgData绑定请求得到的数据
                   for(var i=0; i<json.length; i++){
                   	 this.myData.push({
                   		 username:json[i].username,
                           usersource:json[i].usersource,
                           position:json[i].position,
                          	score:json[i].score,
                          	costtime:json[i].costtime
                       }); 
                    }
                   this.searchTotal=json.length;
                   
                   console.log(this.searchTotal+"-----"+this.initTotal)
                   
                   
                   
                   if(this.searchTotal!=this.initTotal){
                   	   this.searchControll=true;
                   	   console.log("部分数据 启用搜索分页")
                   	   this.outexcel();
                      }else{
                   	   this.searchControll=false;
                   	   console.log("全部数据启用初始化分页");
                   	   this.allposition=[]
                   	   this.fromwhere=[]
                   	   this.myData=[];
                   	   this.init();
                   	   console.log(this.myData)
                      }
                   
               }); 
            },
          
        },
        created(){
            console.log('专业管理模块实例已经创建完成'); 
            this.init();
        }
};  




var Event=new Vue();


    window.onload=function(){
    	//配置路由
        const routes=[
            {path:'/userAdministration', component:userAdministration},
            {path:'/InterviewAdministration', component:InterviewAdministration},
            {path:'/ProfessionalAdministration', component:ProfessionalAdministration},
            {path:'/JurisdictionAdministration', component:JurisdictionAdministration},
            {path:'/QuestionsAdministration', component:QuestionsAdministration},
           {path:'/StatisticsAdministration', component:StatisticsAdministration},
            {path:'*', redirect:'/userAdministration'}  //404
        ];

        //生成路由实例
        const router=new VueRouter({
            routes
        });
        new Vue({
        	router,
            data:{
            	 username:"",
                 userlogo:"",
                 userstate:"",
                 /*模态框状态*/
                 modelstate:"",
                 //修改数据存储
                 phone:"",
                 email:"",
                 sfzid:"",
                 logo:"images/1.jpg",
                 nameid:"",
            },
            methods:{
            	/*组件通信*/
            /*	 send(){
                     Event.$emit({
                    	 'state-msg':this.userstate,
                    	 'username-msg':this.username
                     });
                 },*/
                 init(){
                	  this.$http({
                          url:"initUser.action",
                          data:{
                          	name:GetQueryString("username")
                          }
                      }).then(function(res){
                          var json=res.data;
                          console.log(res)
                          //msgData绑定请求得到的数据
                          //用户列表数据

                         this.userlogo='images/'+json[0].logo+'.jpg';
                         this.phone=json[0].phone;
                         this.email=json[0].email;
                         this.username=json[0].name;
                         this.nameid=json[0].nameid
                         this.sfzid=json[0].sfzid

    
                      }); 
                 },
                 editMsg(){
                  	validator($('.defaultForm1'))
                  	$('.defaultForm1').bootstrapValidator('validate');
                  	 if($('.defaultForm1').data('bootstrapValidator').isValid()){
                  		 this.modelstate="modal"
                      	 this.$http({
                      		url:"updatePowerstate.action",
                      		data:{
                      			nameid:this.nameid,
                      			phone:this.phone,
                      			email:this.email,
                      			sfzid:this.sfzid,
                      			logo:this.logo.replace(/[^0-9]/g,""),
                      		} 
                      	}).then(function(res){
                      		this.myData=[];
                          	this.init();
                          }); 
                     
                  	 }
                  },
                  showListNav(){
              		console.log(1)
              		if ($('.left-sidebar').is('.active')) {
              	          $('.left-sidebar').removeClass('active');
              	      } else {
              	          $('.left-sidebar').addClass('active');
              	      }
              		},
            	},
            	
            	 created(){
                     console.log('主页实例已经创建完成');
                     this.init();
                },
             
	           components:{
	            	
	                'model1':userAdministration,
	                'model2':InterviewAdministration,
	                'model3':ProfessionalAdministration,
	                'model4':JurisdictionAdministration,
	                'model5':QuestionsAdministration,
	                'model6':StatisticsAdministration
	            }
        }).$mount('#cont');
    };
    
 
    
    
    function validator(elemnt){
    	elemnt.bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	username: {
                message: '请输入姓名',
                validators: {
                    notEmpty: {},
                   /* regexp: {
                        regexp: /^[a-zA-Z0-9_\.]{6,}$/,
                        message: '输入字母数字下划线长度6位以上'
                    },*/
                }
            },
            email: {
                validators: {
                	 notEmpty: {
                		 message: '邮箱不能为空'
                	 },
                	  regexp: {
	                        regexp: /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/,
	                        message: '邮箱格式不正确'
	                    }
                }
            },
            phone: {
                validators: {
                	 notEmpty: {
                		 message: '手机号不能为空'
                	 },
                	  regexp: {
	                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
	                        message: '手机格式输入不正确'
	                    }
                }
            },
            sfzid: {
                validators: {
                	 notEmpty: {
                		 message: '身份证号不能为空'
                	 },
                	  regexp: {
	                        regexp: /^[0-9]{18}$/,
	                        message: '身份证格式输入不正确'
	                    }
                }
            },
        	posaa: {
                message: '请输入专业',
                validators: {
                    notEmpty: {},
                   /* regexp: {
                        regexp: /^[a-zA-Z0-9_\.]{6,}$/,
                        message: '输入字母数字下划线长度6位以上'
                    },*/
                }
            },
            
        	username: {
                message: '请输入姓名',
                validators: {
                    notEmpty: {},
                   /* regexp: {
                        regexp: /^[a-zA-Z0-9_\.]{6,}$/,
                        message: '输入字母数字下划线长度6位以上'
                    },*/
                }
            },
            
            
            userphone: {
                message: '请输入电话',
                validators: {
                    notEmpty: {},
                    regexp: {
                        regexp: /^[1][3,4,5,7,8][0-9]{9}$/,
                        message: '手机格式输入不正确'
                    }
                }
            },
          //试题管理
        	choicedetail: {
                message: '请输入问题',
                validators: {
                    notEmpty: {},
                }
            },
            choicea: {
                message: '请输入选项A的内容',
                validators: {
                    notEmpty: {},
                }
            },
            choiceb: {
            	message: '请输入选项B的内容',
            	validators: {
            		notEmpty: {},
            	}
            },
            choicec: {
            	message: '请输入选项C的内容',
            	validators: {
            		notEmpty: {},
            	}
            },
            choiced: {
            	message: '请输入选项D的内容',
            	validators: {
            		notEmpty: {},
            	}
            },
            choicequesanswer: {
            	message: '请输入选择题正确答案',
            	validators: {
            		notEmpty: {},
            		/* regexp: {
                         regexp: /^(A|B|C|D){1}$/,
                         message: '请输入正确的选择题答案格式,如:A'
                     }*/
            	}
            },
            morechoicequesanswer: {
            	message: '请输入多选题正确答案',
            	validators: {
            		notEmpty: {},
            		/* regexp: {
                         regexp: /^(A|B|C|D|AB|AC|AD|ABC|ABD|ACD|ABCD|BC|BD|BCD|CD){1}$/,
                         message: '请输入正确的多选题答案格式,如:AB'
                     }*/
            	}
            },
            judageQuesanswer:{
            	message: '请输入判断题答案',
            	validators: {
            		notEmpty: {},
            		/* regexp: {
                         regexp: /^(正确|错误){1}$/,
                         message: '请输入正确或者错误'
                     }*/
            	}
            },
            choicediffcult: {
            	message: '请输入难度',
            	validators: {
            		notEmpty: {},
            	}
            },
            choiceposition: {
            	message: '请输入专业',
            	validators: {
            		notEmpty: {},
            	}
            },
            choicekpoint: {
            	message: '请输入考点',
            	validators: {
            		notEmpty: {},
            	}
            },
            choicetype: {
            	message: '请输入试题类型',
            	validators: {
            		notEmpty: {},
            	}
            },
            score: {
            	message: '请输入分数',
            	validators: {
            		notEmpty: {},
            	}
            },
            //专业管理
            professionpro: {
            	message: '请输入要增添的专业名称',
            	validators: {
            		notEmpty: {},
            	}
            },
            que_position:{
            	message: '请选择专业',
            	validators: {
            		notEmpty: {},
            	}
            },
            que_diffculty:{
            	message: '请选择试题难度',
            	validators: {
            		notEmpty: {},
            	}
            },
            usersource:{
            	message: '输入用户来源',
            	validators: {
            		notEmpty: {},
            	}
            },
            que_count:{
            	message: '请输入试题数量',
            	validators: {
            		notEmpty: {},
            		 regexp: {
                         regexp: /^[1-9][0-9]*$/,
                         message: '试题数量输入格式不正确'
                     }
            	}
            }
      }
    })
    }
  //获取链接地址栏参数
    function GetQueryString(name) {  
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");  
	    var r = window.location.search.substr(1).match(reg);  
	    if (r != null) {   
	        return unescape(r[2]);  
	
	    }  
	    return null;  
	
	}
      