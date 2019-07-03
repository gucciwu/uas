//-----------------------------以下为综合报表处理的js---------------------------------------------------
var currentBusinessDataId;//当前待办对应的业务数据id，全局变量，给驳回使用
function changeStatus(id,status){
	if(status == 99){
		$.ajax({
            type:"get",
            dataType:"json",
            url:"/workflow/queryWorkflowBusinessData",
            data:{ "businessDataIds":id,"businessTableName":"PP_REPORT_PERIOD" },
            error:function(request) {
            	$.messager.alert("操作提示", "发生异常！", "error"); 
            },
            success:function(result) {
            	if (result.length > 0) {
            		graphTrace(null,null,result[result.length-1].processInstanceId,id);
                 }
            	 else {
                 	$.messager.alert("操作信息", '请先启动流程！',"info");
                 }
                }
            });
	}else{
		$.ajax({
		       type:"post",
		       dataType:"json",
		       url:"/periodReport/changeStatus",
		       data:{ "id":id,
		      	 	"status":status,
		      	    "rejectReason":$("#rejectReason").val()
		       },
		       error:function(request) {
		       	$.messager.alert("操作提示", "发生异常！", "error"); 
		       },
		       success:function(result) {
		       	if (result.status == 1) {
		               $.messager.alert("操作信息", "操作成功","info",function(){
		            	   $("#dlgReportReject").dialog("close");
		                   $('#dlgReportDeal').dialog('close'); 
		                   //重新加载页面数据
		            	   document.location.reload();
		               });
		              
		           } else {
		           	$.messager.alert("操作信息", result.msg,"error");
		           }
		       }
		   });
	}
	

}

function rejectReport(){
  	if(!$("#rejectReason").val()){
  		$.messager.alert("操作提示", "请填写驳回原因！", "error"); 
  	}
		changeStatus(currentBusinessDataId,5);
}


function operateForReport(id,status){
   if(status == 5){
	   	currentBusinessDataId=id;
		$("#dlgReportReject").dialog("open");
	}else{
		changeStatus(id,status);
	}
}
//--------------------------------其他工作流处理js---------------------------------------------------------------------------------------------
   