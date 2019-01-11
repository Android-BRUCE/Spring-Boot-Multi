function imgFormatter(value,row,index){
    if('' != value && null != value)
        value = '<img style="width:50px; height:50px" src="' + value + '">';
    return  value;
}


function download(img){
    var simg =  "http://....com/"+ img;
    $('#dlg').dialog({
        title: '预览',
        width: 600,
        height:750,
        resizable:true,
        closed: false,
        cache: false,
        modal: true
    });
    $("#image").attr("src",simg);

}