$(function(){
    var editing;
    var flag;
    $("#tt").datagrid({
            width:1200,
            height: 600,
            collapsible:true,
            //idField:"id",只能选中一个行
            fitColumns:true,
            rownumbers:true,
            loadMsg:"正在加载中....",
            url:"contact/contactList.do",
            striped:true,
            columns:[[
                {field:"ck",checkbox:true, width:50},
                {field:"id",title:"主键", width:0,hidden:true},


                {field:"contactname",title:"联系名称",width:50,align:"center"},
                {field:"contactcontent",title:"联系内容",width:80,align:"center"},
//                {field:"status",title:"轮播状态",width:20,align:"center",
//                    formatter:function(value,rowData,rowIndex){
//                        if(value==1)
//                        {
//                            return "开启";
//                        }else if(value==0){
//                            return "禁用";
//                        }
//                    },
        },
        {field:"contactcreate",title:"创建时间",width:50,align:"center",
            formatter:function(value,row,index){
                var unixTimestamp = new Date(value);
                return unixTimestamp.toLocaleString();
            }
        },
        {field:"contactupdate",title:"修改时间",width:50,align:"center",
            formatter:function(value,row,index){
                var unixTimestamp = new Date(value);
                return unixTimestamp.toLocaleString();
            }}
]],
    pagination:true,
        pageNumber:1,
        pageSize:5,
        pageList:[5,10,15,20],
        toolbar:[
        {
            iconCls:"icon-add",
            text:"编辑详情",
            handler:function(){
                if(editing==undefined)
                {
                    var rows=$("#tt").datagrid("getSelections");
                    //getChecked;
                    if(rows.length!=1)
                    {
                        $.messager.show({
                            title:"消息",
                            width:200,
                            height:100,
                            msg:"只能选择一行记录进行修改"
                        });

                    }else{
                        var ids="";
                        for(var i=0;i<rows.length;i++)
                        {
                            ids=ids+rows[i].r_id+",";
                        }
                        ids=ids.substring(0,ids.length-1);
                        var path = "role/role_edit.do?r_id="+ids;
                        window.location=path;
                    }
                    /*                                 //增加一行
                                                         $("#tt").datagrid("getRowIndex",rows[0]);
                                                         editing=$("#tt").datagrid("getRows").length-1;
                                                         //开启编辑状态
                                                         $("#tt").datagrid("beginEdit",editing); */
                }
            }
        },
        {
            iconCls:"icon-add",
            text:"查询",
            handler:function(){
                var keyword = document.getElementById("keyword").value;
                var cid = document.getElementById("cid").value;
                $("#tt").datagrid(//重定向url
                    {
                        url:"role/dimRole.do?keyword="+keyword+"&cid="+cid
                    }
                );
            }
        },


        {
            iconCls:"icon-add",
            text:"查看详情",
            handler:function(){
                if(editing==undefined)
                {
                    var rows=$("#tt").datagrid("getSelections");
                    //getChecked;
                    if(rows.length!=1)
                    {
                        $.messager.show({
                            title:"消息",
                            width:200,
                            height:100,
                            msg:"只能选择一行记录进行查看"
                        });

                    }else{
                        var ids="";
                        for(var i=0;i<rows.length;i++)
                        {
                            ids=ids+rows[i].r_id+",";
                        }
                        ids=ids.substring(0,ids.length-1);
                        var path = "role/role_look.do?r_id="+ids;
                        window.location=path;
                    }
                    /*                                 //增加一行
                                                         $("#tt").datagrid("getRowIndex",rows[0]);
                                                         editing=$("#tt").datagrid("getRows").length-1;
                                                         //开启编辑状态
                                                         $("#tt").datagrid("beginEdit",editing); */
                }




            }
        },
        {
            iconCls:"icon-remove",
            text:"删除",
            handler:function(){
                var rows=$("#tt").datagrid("getSelections");
                if(rows.length>0){
                    var ids="";
                    for(var i=0;i<rows.length;i++)
                    {
                        ids=ids+rows[i].id+",";
                    }
                    ids=ids.substring(0,ids.length-1);
                    $.messager.confirm("确认框","您确定要删除吗?",function(b){
                        if(b)
                        {
                            $.post("banner/"+ids+"/delete.do?",function(data){
                                $.messager.show({
                                    title:"消息",
                                    width:200,
                                    height:100,
                                    msg:data.msg
                                });
                                //刷新datagrid
                                $("#tt").datagrid("reload",{});
                                //取消所选中的行
                                $("#tt").datagrid("unselectAll");
                            },"json");
                        }
                    });

                }else{
                    $.messager.show({
                        title:"消息",
                        width:200,
                        height:100,
                        msg:"至少要选择一条记录进行删除"
                    });
                }
            }
        },
    ]
});

});