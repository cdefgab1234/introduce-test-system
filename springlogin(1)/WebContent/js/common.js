$(function(){
	$("#content").height($(document).height());
	//登录界面背景图片尺寸处理
	var win_height; 
    var win_width; 
    var original_width = $(document).width(); 
    var original_height = $(document).height(); 
    var pic_width, pic_height, pic_left ,pic_top; 

	  OnePicAction();

    function OnePicAction(){
        win_height = $(window).height(); 
        win_width = $(window).width(); 

        //裁剪图片
        if(Math.ceil(win_height * original_width / original_height) < win_width ){
            pic_width = win_width ;
            pic_height = Math.ceil(win_width * original_height / original_width);
            pic_left = 0;
            pic_top = - Math.ceil((pic_height - win_height) / 2);
        }else{
            pic_height = win_height;
            pic_width = Math.ceil(win_height * original_width / original_height);
            pic_left = - Math.ceil((pic_width - win_width) / 2);
            pic_top = 0;
        }
        $("#onepics .wrap_pic").css("width",pic_width+"px").css("height",pic_height+"px").css("margin-top",pic_top+"px").css("margin-left",pic_left+"px");

    }
    autoLeftNav()
    $(window).resize(function() {
        autoLeftNav();
        OnePicAction();
        console.log($(window).width())
    });
    function autoLeftNav() {
	    if ($(window).width() < 768) {
	        $('.left-sidebar').addClass('active');
	    } else {
	        $('.left-sidebar').removeClass('active');
	    }
	}
    
    
})

