$(function(){
	
	 $.extend($.fn.validatebox.defaults.rules,{
         usernameLength:{
            validator:function(value,param){
                return value.length>=param[0] && value.length<=param[1];
            },
            message:"用户名最少{0}个字符,最多{1}个字符"
         }
         
     });
     
     $.extend($.fn.validatebox.defaults.rules,{
         passwordRegex:{
            validator:function(value,param){
                var passRegex=/^[a-zA-Z](\w|\d){5,11}$/;
                return passRegex.test(value);
            },
            message:"密码必须以字母开头,至少6个字符,最多12个字符"
         }
         
     });
     
       
	
});