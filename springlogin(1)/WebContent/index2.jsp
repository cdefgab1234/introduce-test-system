<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  	<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" type="text/css"></link>
  	<link rel="stylesheet" href="css/index.css" type="text/css"></link>
  	<script type="text/javascript" src="js/jq.js"></script>
  	<script type="text/javascript" src="js/common.js"></script>
  	<link rel="stylesheet" href="bootstrap/dist/css/bootstrapValidator.css"/>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="bootstrap/dist/js/bootstrapValidator.js"></script>
  	</head>
  <body class="loginBg">
  <div id="coverpage">
  <div id="onepics"><div class="onepic_wrap"><img src="images/ab.jpg" class="wrap_pic"></div></div>
</div> 
<div class="container" style="padding-top: 200px;" id="login">
	<form id="defaultForm"  >
	<div class="col-md-6 well login col-md-offset-3">
		<div class=" well-sm col-md-10  col-md-offset-1 h4 text-center">登录</div>
	   <div class="form-group col-md-8 col-md-offset-2">
                <div class=" input-group  ">
                   <label class=" input-group-addon"><span class=""><i class="glyphicon glyphicon-user"></i></span>  </label>
                    <input type="text" class="form-control" id="loginname" name="loginname" placeholder="用户名">
              </div>
              <div class="err help-block">用户名不存在</div>
          </div>
		<div class="form-group col-md-8 col-md-offset-2">
                <div class=" input-group  ">
                   <label class=" input-group-addon"><span class=""><i class="glyphicon glyphicon-lock"></i></span>  </label>
                    <input type="password" class="form-control" id="password" name="password"  placeholder="密码" >
              </div>
              <div class="err help-block">密码错误</div>
          </div>
         <div class="form-group col-md-8 col-md-offset-2" style=" padding-left: 0;">
		  <div class="form-group col-md-6 ">
                <div class=" input-group  ">
                    <input type="text" class="form-control" id="yzm" name="yzm" placeholder="验证码">
              </div>
              <div id="box" style="position: relative;">
			  	<canvas id="yzmcanvas" width="80px" height="80px">
			</map>
			</div>
          </div>
                    <input type="text" class="form-control" id="yzms" name="yzms" readonly = "readonly" style="display:none" >
           <div class="form-group col-md-6">
                <div class=" input-group  ">
                 <canvas id="canvas" width="100" height="34"></canvas>
                  <a href="javascript:;" id="img" class="pull-right" style="line-height: 34px;text-indent: 10px;">换一张</a>
              </div>
          </div>

            </div>
              <div class="well-sm  col-md-8  col-md-offset-2" style="padding-top: 0;" >
                   <a class="col-md-offset-1" href="Register.jsp">注册账号</a>
                   <a class="col-md-offset-5" href="forgetPass.jsp">忘记密码</a>
            </div> 
	     <div class="  col-md-6  col-md-offset-3" style="padding-top:5px">
	         <button id="loginBtn" class="btn btn-success btn-block  col-md-12">登录</button>
	    </div>

	</div>

	</form>
</div>
<div id="model" style="display: none;">
	 <span class="yzfont"></span>
</div>
</body>
<script type="text/javascript" src="js/validator.js"></script>
<script type="text/javascript">
var str="";
function getCh(){
	$.ajax({
		url:"yzmch.action",
		dataType:"json",
		success:function(k){
			str="";
		$(k).each(function(i,n){
			str+=n;
		})
		var yzmstr="";
		var allstr=str;
		do {
	        var index = Math.floor(Math.random() *4+1);
	        var flag = true;
	        yzmstr+=allstr.charAt(index);
	        allstr=allstr.substring(index)+allstr.substring(0,index-1)
	        if (allstr.length ==5) {
	            flag = false;
	        }
	    } while (flag);
		console.log(str)
		console.log(yzmstr)
			 $("#yzms").attr("value",yzmstr);
			 drawPic();
		}
	})
}

getCh()
  $("#img").on("click",function(){
	  $("#yzm").val("")
	  getCh();
	}) 	
  /**生成一个随机数**/
  function randomNum(min,max){
    return Math.floor( Math.random()*(max-min)+min);
  }
  /**生成一个随机色**/
  function randomColor(min,max){
    var r = randomNum(min,max);
    var g = randomNum(min,max);
    var b = randomNum(min,max);
    return "rgb("+r+","+g+","+b+")";
  }
	
  /**绘制验证码图片**/
  function drawPic(){
    var canvas=document.getElementById("canvas");
    var width=canvas.width;
    var height=canvas.height;
    var ctx = canvas.getContext('2d');
    ctx.textBaseline = 'bottom';

    /**绘制背景色**/
    ctx.fillStyle = randomColor(180,240); //颜色若太深可能导致看不清
    ctx.fillRect(0,0,width,height);
    /**绘制文字**/

  /*   for(var i=0; i<4; i++){ */
      var txt = $("#yzms").attr("value");
      ctx.fillStyle = randomColor(50,160);  //随机生成字体颜色
      ctx.font = randomNum(15,20)+'px SimHei'; //随机生成字体大小
      var x = 20;
      var y = randomNum(20,30);
      var deg = randomNum(-45, 45);
      //修改坐标原点和旋转角度
      ctx.translate(x,y);
     /*  ctx.rotate(deg*Math.PI/180); */
      ctx.fillText(txt, 0,0);
   /*    //恢复坐标原点和旋转角度
      ctx.rotate(-deg*Math.PI/180); */
      ctx.translate(-x,-y);
 /*    } */
    /* /**绘制干扰线**/
     for(var i=0; i<3; i++){
      ctx.strokeStyle = randomColor(40,180);
      ctx.beginPath();
      ctx.moveTo( randomNum(0,width), randomNum(0,height) );
      ctx.lineTo( randomNum(0,width), randomNum(0,height) );
      ctx.stroke();
    } 
    /**绘制干扰点**/
   /*  for(var i=0; i<100; i++){
      ctx.fillStyle = randomColor(0,255);
      ctx.beginPath();
      ctx.arc(randomNum(0,width),randomNum(0,height), 1, 0, 2*Math.PI);
      ctx.fill();
    }  */
  }
	
  function getCanvas(str){
	  	var canvas = document.getElementById("yzmcanvas");
			//获取对应的CanvasRenderingContext2D对象(画笔)
			var ctx = canvas.getContext("2d");
			ctx.clearRect(0,0,canvas.width,canvas.height);  
			//设置字体样式
			ctx.font = "14px Courier New";
			//设置字体填充颜色
			 ctx.fillStyle = "#fff";
			 ctx.fillRect(0,0,100,100);
			//绘制背景色		    
		     ctx.fillStyle = "red";
			var x=10;
			var y=20;
			$("#box  .yzfont").remove();
			for(i=0;i<9;i++){
				ctx.fillText(str.charAt(i),x,y);
				var a=x;
				var b=y-10;
				$("#model .yzfont").css({"top":b+"px","left":a+"px","cursor":"pointer"})
				$("#model .yzfont").attr("val",str.charAt(i));
				var areahtml=$("#model").html();
				$("#box").append(areahtml);
				x+=20;
				if((i+1)%3==0){
					y+=20;
					x=10;
				}
		}
	}
	
$("#yzm").on("focus",function(){
	getCanvas(str);
	$("#yzmcanvas").show();	
})	
$(document).click(function(){
	if($("#yzm").val().length==4)
		$("#yzmcanvas").hide();
})
$("#box").each(function(){
	console.log($(this).attr("val"))
	$(this).on("click",".yzfont",function(){
		$("#yzm").val($("#yzm").val()+$(this).attr("val"))
		console.log($(this).attr("val"))
	})
})	
	
	
	
	
	$(":input").on("focus",function (){
		$(".err.help-block").hide();
	}) 
	 $("#loginBtn").on("click",function(){
		  $('#defaultForm').bootstrapValidator('validate');
		  	if($('#defaultForm').data('bootstrapValidator').isValid()){  
		  		$.ajax({
		  		  url: "login.action",
		  		  data:{
		  			  username:$("input[name=loginname]").val(),
		  			  password:$("input[name=password]").val()
		  		  },
		  		  success: function(response){
		  			  console.log(response)
		  			if(response==1){
		  			  var  url="model.html?username="+$("input[name=loginname]").val();
		  			  window.open(url,"_parent")
		  			}else if(response==2){
		  				$(".err.help-block").eq(0).hide();
		  				$(".err.help-block").eq(1).show();
		  				$("#password").parent().parent().removeClass("has-success").addClass("has-error")
		  				$("#password").parent().next().removeClass("glyphicon-ok").addClass("glyphicon-remove")
		  			}else{
		  				$(".err.help-block").eq(1).hide();
		  				$(".err.help-block").eq(0).show();
		  				$("#loginname").parent().parent().removeClass("has-success").addClass("has-error")
		  				$("#loginname").parent().next().removeClass("glyphicon-ok").addClass("glyphicon-remove")
		  			}
		  		  }
		  		});
			}
	  })
</script>

</html>
