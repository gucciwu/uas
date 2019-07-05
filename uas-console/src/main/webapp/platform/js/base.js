$.extend($.fn.validatebox.defaults.rules,{
			NotEmpty : { // 非空字符串验证。 easyui 原装required 不能验证空格
				validator : function(value, param) {
					return $.trim(value).length>0;
				}, 
				message : '该输入项为必输项'
			},
			PhoneOrMobile : {//非空电话号码 匹配 移动与固定电话号码
				validator : function(value, param) {
					return /^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$|(^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$)/.test(value);
				},
				message : '格式不正确,请输入正确的电话格式。'
			},
			minLength : { // 判断最小长度
				validator : function(value, param) {
					return value.length >= param[0];
				},
				message : '最少输入 {0} 个字符。'
			},
			maxLength : { // 判断最大长度
				validator : function(value, param) {
					return value.length <= param[0];
				},
				message : '最多输入 {0} 个字符。'
			},
			length : {
				validator : function(value, param) {
					var len = $.trim(value).length;
					return len >= param[0] && len <= param[1];
				},
				message : "输入内容长度必须介于{0}和{1}之间."
			},
			phone : {// 验证电话号码
				validator : function(value) {
					return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
							.test(value);
				},
				message : '格式不正确,请使用下面格式:020-88888888'
			},
			mobile : {// 验证手机号码
				validator : function(value) {
					return /^(13|15|18)\d{9}$/i.test(value);
				},
				message : '手机号码格式不正确'
			},
			idcard : {// 验证身份证
				validator : function(value) {
					return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
				},
				message : '身份证号码格式不正确'
			},
			intOrFloat : {// 验证整数或小数
				validator : function(value) {
					return /^\d+(\.\d+)?$/i.test(value);
				},
				message : '请输入数字，并确保格式正确'
			},
			currency : {// 验证货币
				validator : function(value) {
					return /^\d+(\.\d+)?$/i.test(value);
				},
				message : '货币格式不正确'
			},
			qq : {// 验证QQ,从10000开始
				validator : function(value) {
					return /^[1-9]\d{4,9}$/i.test(value);
				},
				message : 'QQ号码格式不正确'
			},
			integer : {// 验证整数
				validator : function(value) {
					return /^[+]?[0-9]+\d*$/i.test(value);
				},
				message : '请输入整数'
			},
			number : {
			        validator : function(value) {  
			            return /^[0-9]+\d*$/.test(value);  
			        },  
			        message : '请输入数字'  
			},
			chinese : {// 验证中文
				validator : function(value) {
					return /^[\u0391-\uFFE5]+$/i.test(value);
				},
				message : '请输入中文'
			},
			english : {// 验证英语
				validator : function(value) {
					return /^[A-Za-z]+$/i.test(value);
				},
				message : '请输入英文'
			},
			classValidator : {// 验证英语
				validator : function(value) {
					return /^[A-Za-z][A-Za-z.]+$/i.test(value);
				},
				message : '请输入字母或者“.”'
			},
			unnormal : {// 验证是否包含空格和非法字符
				validator : function(value) {
					return /.+/i.test(value);
				},
				message : '输入值不能为空和包含其他非法字符'
			},
			username : {// 验证用户名
				validator : function(value) {
					return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
				},
				message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
			},
			specialCharacter: {  
		        validator: function(value, param){  
		            var reg = new RegExp("[`~!@#$^&*()=|{}':;'\\[\\]<>~！@#￥……&*（）——|{}【】‘；：”“'、？]");  
		            return !reg.test(value);  
		        },  
		        message: '不允许输入特殊字符'  
		    },
		    englishLowerCase  : {// 验证英语小写  
		        validator : function(value) {  
		            return /[a-z]+$/.test(value);  
		        },  
		        message : '请输入小写字母'  
		    },
		    englishUpperCase  : {// 验证英语大写  
		        validator : function(value) {  
		            return /^[A-Z0-9]+$/.test(value);  
		        },  
		        message : '请输入大写字母和数字组合'  
		    },
			faxno : {// 验证传真
				validator : function(value) {
					// return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[
					// ]){1,12})+$/i.test(value);
					return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
							.test(value);
				},
				message : '传真号码不正确'
			},
			zip : {// 验证邮政编码
				validator : function(value) {
					return /^[1-9]\d{5}$/i.test(value);
				},
				message : '邮政编码格式不正确'
			},
			ip : {// 验证IP地址
				validator : function(value) {
					return /^((\d|\d\d|[0-1]\d\d|2[0-4]\d|25[0-5])\.(\d|\d\d|[0-1]\d\d|2[0-4]\d|25[0-5])\.(\d|\d\d|[0-1]\d\d|2[0-4]\d|25[0-5])\.(\d|\d\d|[0-1]\d\d|2[0-4]\d|25[0-5]))$/i.test(value);
				},
				message : 'IP地址格式不正确'
			},
			name : {// 验证姓名，可以是中文或英文
				validator : function(value) {
					return /^[\u0391-\uFFE5]+$/i.test(value)
							| /^\w+[\w\s]+\w+$/i.test(value);
				},
				message : '请输入姓名'
			},
			carNo : {
				validator : function(value) {
					return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
				},
				message : '车牌号码无效（例：粤J12350）'
			},
			carenergin : {
				validator : function(value) {
					return /^[a-zA-Z0-9]{16}$/.test(value);
				},
				message : '发动机型号无效(例：FG6H012345654584)'
			},
			email : {
				validator : function(value) {
					return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
							.test(value);
				},
				message : '请输入有效的电子邮件账号(例：abc@126.com)'
			},
			msn : {
				validator : function(value) {
					return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
							.test(value);
				},
				message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
			},
			same : {
				validator : function(value, param) {
					if ($("#" + param[0]).val() != "" && value != "") {
						return $("#" + param[0]).val() == value;
					} else {
						return true;
					}
				},
				message : '两次输入的密码不一致！'
			},
			ipv6 : {//验证ipv6
				validator : function(value){
					return /^\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?\s*$/.test(value);
				},
				message:'请输入正确的ipv6地址!'
			},
			date : {
				validator : function(value){
					return /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))$/.test(value);
				},
				message:'请输入正确的日期格式：YYYY-MM-DD'
			},
			datetimes : {
				validator : function(value){
					return /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))(\s){1}?([0-1]{1}\d{1}|[2]{1}[0-4]{1})(?::)?([0-5]{1}\d{1})$/.test(value);
				},
				message:'请输入正确的日期格式：YYYY-MM-DD HH:MM'
			},
			lessThanToday : {
				validator : function(value){
					var now = new Date();  
		            var d1 = new Date(now.getFullYear(),now.getMonth(),now.getDate()); 
		            return value.toDate("yyyy-MM-dd") <= d1;  
				},
				message:'请选择今天或今天之前的日期'
			},
			datetime : {
				validator : function(value){
					return /^([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))(\s){1}?([0-1]{1}\d{1}|[2]{1}[0-4]{1})(?::)?([0-5]{1}\d{1})(?::)?([0-5]{1}\d{1})$/.test(value);
				},
				message:'请输入正确的日期格式：YYYY-MM-DD HH:MM:SS'
			},
			workNoAjax:{//验证员工管理界面，工号是否重复
				validator:function(value){
					var a=true;
					var doctorId=$('#doctor_id').val();
					$.ajax({
						type:"post",
						async:false,
						url:_basePath+'/base/bas/queryDoctorWorkNo.html',
						data:"workno="+value+"&doctor_id="+doctorId,
						dataType:"text",
						success:function(data){
							if(data=="true"){
								a=false;
							}
						}
					});
					return a;
				},
				message:'该工号已经存在'
			}
		});
/**combobx扩展，添加默认信息。
 * 
 */
$(function() {
	///扩展默认值信息
	$(".easyui-combobox").each(function(tager){
				 try{
					 $(this).combobox({onLoadSuccess : function(data){
			    			if($(this).combobox("options").multiple||$(this).combobox("options").mode=="remote"){
			    				return ;
			    			}
			    			var msg =data;
			    			var text =$(this).combobox("options").textField;
			    			var value =$(this).combobox("options").valueField;
			    			if(data!=null &&data.length>0&&data[0].value!=""){
			    				msg.unshift({ text: "",value: "" });	    			
					    		$(this).combobox('loadData', msg);	
			    			}
			    		 }}); 
				 }catch(e){
					 
				 }
	    		 
	    	});
	//add by qsyanga :
	//动态模糊匹配combobx缺陷
	$(".easyui-combobox").each(function(tager){
		//动态模糊匹配时候,确保搜索值在combobox下拉列表中存在
		if($(this).combobox("options").mode=="remote"){
			$(this).combobox({
				onHidePanel: function(){
		            var valueField = $(this).combobox("options").valueField;
		            var textField = $(this).combobox("options").textField;
		            var val = $(this).combobox("getText");  //当前combobox的值
		            var allData = $(this).combobox("getData");   //获取combobox所有数据
		            var result = true;      //为true说明输入的值在下拉框数据中不存在
		            for (var i = 0; i < allData.length; i++) {
		                if (val == allData[i][textField]) {
		                	$(this).combobox("setValue",allData[i][valueField]);
		                	result = false;
		                }
		            }
		            if (result) {
		                $(this).combobox("setValue","");
		                //刷新页面
		                $(this).combobox("reload");
		            }
				}
			});
		}
		
	});
	//add by qsyanga:
	//添加日期控件清空按钮
	//设计按钮
	var buttons = $.extend([], $.fn.datebox.defaults.buttons);
	buttons.splice(1, 0, {
		text: '清空',
		handler: function(target){
			$(target).datebox("setValue","");
			$(target).datebox("hidePanel");
			
		}
	});
	//绑定当前页面的日期控件
	$(".easyui-datebox").each(function(tager){
    	$(this).datebox({
    		buttons: buttons
    	});
	});
});
/**
 * 扩展单元格合并
 * add by qsyanga 
 *使用用例 $('#grid).datagrid("autoMergeCells", ['field1', 'field2','field3',……]);
 * 
*/
$.extend($.fn.datagrid.methods, {
autoMergeCells: function (jq, fields) {

    return jq.each(function () {

        var target = $(this);

        if (!fields) {

            fields = target.datagrid("getColumnFields");

        }

        var rows = target.datagrid("getRows");

        var i = 0,

        j = 0,

        temp = {};

        for (i; i < rows.length; i++) {

            var row = rows[i];

            j = 0;

            for (j; j < fields.length; j++) {

                var field = fields[j];

                var tf = temp[field];

                if (!tf) {

                    tf = temp[field] = {};

                    tf[row[field]] = [i];

                } else {

                    var tfv = tf[row[field]];

                    if (tfv) {

                        tfv.push(i);

                    } else {

                        tfv = tf[row[field]] = [i];

                    }

                }
            }

        }

        $.each(temp, function (field, colunm) {

            $.each(colunm, function () {

                var group = this;



                if (group.length > 1) {

                    var before,

                    after,

                    megerIndex = group[0];

                    for (var i = 0; i < group.length; i++) {

                        before = group[i];

                        after = group[i + 1];

                        if (after && (after - before) == 1) {

                            continue;

                        }

                        var rowspan = before - megerIndex + 1;

                        if (rowspan > 1) {

                            target.datagrid('mergeCells', {

                                index: megerIndex,

                                field: field,

                                rowspan: rowspan

                            });

                        }

                        if (after && (after - before) != 1) {

                            megerIndex = after;

                        }

                    }

                }

            });

        });

    });

}

});

//给easyui form 添加  getData 方法
$.extend($.fn.form.methods, {
    getData: function(jq, params){
        var formArray = jq.serializeArray();
        var oRet = {};
        for (var i in formArray) {
            if (typeof(oRet[formArray[i].name]) == 'undefined') {
                if (params) {
                    oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                }
                else {
                    oRet[formArray[i].name] = formArray[i].value;
                }
            }
            else {
                if (params) {
                    oRet[formArray[i].name] = (formArray[i].value == "true" || formArray[i].value == "false") ? formArray[i].value == "true" : formArray[i].value;
                }
                else {
                    oRet[formArray[i].name] += "," + formArray[i].value;
                }
            }
        }
        return oRet;
    }
});



jQuery(function($){
    // 备份jquery的ajax方法  
    var _ajax=$.ajax;
    // 重写ajax方法，
    $.ajax=function(opt){
        var _success = opt && opt.success || function(a, b){};
        var _error = opt && opt.error || function(a, b){};
        var _opt = $.extend(opt, {
            success:function(data, textStatus){
                // 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要判断(登录页面一般是源码，所以这里只判断是否有html标签)
               /* if(data.indexOf('html') != -1) {
                    window.top.location.href= "/cms";
                    return;
                }*/
                _success(data, textStatus);  
          },
          error:function(data, textStatus){
        	 if(data.status==401){
                     window.top.location.href= "/uas/platform/app/login.html";
                     return;
        	 }
            _error(data, textStatus);
          } 
        });
        return _ajax(_opt);
    };
});

Date.prototype.format = function(fmt) {
    var o = {
        "M+" : this.getMonth()+1,                 //月份 
        "d+" : this.getDate(),                    //日 
        "h+" : this.getHours(),                   //小时 
        "m+" : this.getMinutes(),                 //分 
        "s+" : this.getSeconds(),                 //秒 
        "q+" : Math.floor((this.getMonth()+3)/3), //季度 
        "S"  : this.getMilliseconds()             //毫秒 
    }; 
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
    }
    return fmt;
}

String.prototype.toDate = function(format) {
    pattern = format.replace("yyyy", "(\\~1{4})").replace("yy", "(\\~1{2})")
.replace("MM", "(\\~1{2})").replace("M", "(\\~1{1,2})")
.replace("dd", "(\\~1{2})").replace("d", "(\\~1{1,2})").replace(/~1/g, "d");

    var returnDate;
    if (new RegExp(pattern).test(this)) {
        var yPos = format.indexOf("yyyy");
        var mPos = format.indexOf("MM");
        var dPos = format.indexOf("dd");
        if (mPos == -1) mPos = format.indexOf("M");
        if (yPos == -1) yPos = format.indexOf("yy");
        if (dPos == -1) dPos = format.indexOf("d");
        var pos = new Array(yPos + "y", mPos + "m", dPos + "d").sort();
        var data = { y: 0, m: 0, d: 0 };
        var m = this.match(pattern);
        for (var i = 1; i < m.length; i++) {

            if (i == 0) return;
            var flag = pos[i - 1].split('')[1];
            data[flag] = m[i];
        };

        if (data.y.toString().length == 2) {
            data.y = parseInt("20" + data.y);
        }
        data.m = data.m - 1;
        returnDate = new Date(data.y, data.m, data.d);
    }
    if (returnDate == null || isNaN(returnDate)) returnDate = new Date();
    return returnDate;
}



function formatCurrency(val, row) {
	
	var formator=function fmoney(s, n)   {   
		   n = n > 0 && n <= 20 ? n : 2;   
		   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
		   var l = s.split(".")[0].split("").reverse(),   
		   r = s.split(".")[1];   
		   t = "";   
		   for(i = 0; i < l.length; i ++ )   
		   {   
		      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
		   }   
		   return t.split("").reverse().join("") + "." + r;   
    }
    return formator(val,2)
}
/**
 * 表单统一展示，隐藏方法。
 * add by qsyanga
 * 表单参数：formName：表单名称，没有的情况下以 MessageForm统一操作
 */
//全部置灰操作 
function allhide(formName){
	var form=formName==null||formName==undefined?"MessageForm":formName;
	$("#"+form+" .easyui-combobox").combobox({ readonly: true });
	$("#"+form+" .easyui-combobox").each(function(){
		var span=$(this).next();
 		$(span).addClass("view");
 		
	});
	$("#"+form+" .easyui-combotree").combotree({ readonly: true });
	$("#"+form+" .easyui-combotree").each(function(){
		var span=$(this).next();
 		$(span).addClass("view");
 		
	});
	$("#"+form+" .easyui-numberbox").numberbox({ readonly: true });
	$("#"+form+" .easyui-numberbox").each(function(){
		var span=$(this).next();
 		$(span).addClass("view");
 		
	});
 	$("#"+form+" :input[type='radio']").attr("disabled", "disabled");
 	$("#"+form+" .easyui-datebox").datebox({ readonly: true });
 	$("#"+form+" .easyui-datebox").each(function(){
		var span=$(this).next();
 		$(span).addClass("view");
 		
	});
 	$("#"+form+" .easyui-textbox").textbox({ readonly: true });
 	$("#"+form+" .easyui-textbox").each(function(){
 		var span=$(this).next();
 		$(span).addClass("view");
 	});
 	$("#"+form+" .easyui-validatebox").validatebox({ disabled: true });
 	$("#"+form+" .easyui-validatebox").each(function(){
 		var span=$(this).next();
 		$(span).addClass("view");
 	});
 	$("#"+form+" .easyui-combogrid").combogrid({ readonly: true });
 	$("#"+form+" .easyui-combogrid").each(function(){
 		var span=$(this).next();
 		$(span).addClass("view");
 	});
 	
    $("#dlg-save").hide();
    $("#dlg-cancel").show();
}
/**
 * 表单统一展示，展示方法。
 * add by qsyanga
 * 表单参数：formName：表单名称，没有的情况下以 MessageForm统一操作
 */
function allshow(formName){
	var form=formName==null||formName==undefined?"MessageForm":formName;
	$("#"+form+" .easyui-combobox").combobox({ readonly: false });
   	$("#"+form+" .easyui-combobox").each(function(){
 		var span=$(this).next();
  		$(span).removeClass("view");

 	});
   	$("#"+form+" .easyui-combotree").combotree({ readonly: false });
  	 $("#"+form+" .easyui-combotree").each(function(){
		var span=$(this).next();
 		$(span).removeClass("view");
 		
	});
 	$("#"+form+" .easyui-numberbox").each(function(){
		var span=$(this).next();
		$(span).removeClass("view");
		
		//标签只读则不受统一展现控制
		if($(this).attr("readonly")!="readonly"){
			$(this).numberbox({ readonly: false });
		}
	});
	$("#"+form+" .easyui-datebox").datebox({ readonly: false });
	$("#"+form+" .easyui-datebox").each(function(){
 		var span=$(this).next();
  		$(span).removeClass("view");
  		
 	});
	$("#"+form+" .easyui-textbox").textbox({ readonly: false });
	$("#"+form+" .easyui-textbox").each(function(){
 		var span=$(this).next();
 		$(span).removeClass("view");
 	});
	$("#"+form+" :input[type='radio']").attr("disabled", false);
	$("#"+form+" .easyui-validatebox").validatebox({ disabled: false });
	$("#"+form+" .easyui-validatebox").each(function(){
 		var span=$(this).next();
 		$(span).removeClass("view");
 	});
	$("#"+form+" .easyui-combogrid").combogrid({ readonly: false });
	$("#"+form+" .easyui-combogrid").each(function(){
 		var span=$(this).next();
 		$(span).removeClass("view");
 	});
    $("#dlg-save").show();
    $("#dlg-cancel").show();
}


function toggleMoreSearchConditions(op){
    $(op).parent().closest(".conditions").siblings(".conditions").toggleClass("hide");
};










