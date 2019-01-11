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
    $("#e_name").validatebox({
        required:true,
        missingMessage:"用户姓名必填",
        validType:"usernameLength[2,12]"
    });
    $("#e_age").numberbox({
        required:true,
        missingMessage:"年龄必填",
        min:0,
        max:200,
    });
    $("#e_phone").numberbox({
        required:true,
        missingMessage:"电话必填",
        validType:"usernameLength[6,11]"
    });
    $("#e_startdate").datebox({
        required:true,
        missingMessage:"入职时间必填",
        editable:false,
    });
    $("#e_idcard").numberbox({
        required:true,
        missingMessage:"身份证号必填",
        validType:"usernameLength[18,18]"
    });
    $("#e_comment").validatebox({
        required:false,
        validType:"usernameLength[0,120]"
    });
    $("#submit").click(function (){

        var flag=$("#form2").form("validate");

        if(!flag){
            $.messager.show({
                title:"消息1",
                width:200,
                height:100,
                msg:"验证未通过!!"
            }); }else{
            document.getElementById("form2").submit();
        }
    });
});