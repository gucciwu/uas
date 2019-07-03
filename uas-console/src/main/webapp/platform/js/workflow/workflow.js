/** 2017-9-18 zhangyong
 * 由于一条业务数据可能启动-结束，再启动再结束....,因此，流程跟踪图则只显示最近的那一个流程实例对应的流程图，流程跟踪则需要显示与当前这条业务数据对应的所有流程日志。
 */


var ctx="/cms";
function graphTrace(options,pdid,pid,businessKey) {
    var _defaults = {
        srcEle: this,
        pid:options,
        pdid:pdid,
        pid:pid,
        businessKey:businessKey
    };
    var opts = $.extend(true, _defaults, options);

    // 处理使用js跟踪当前节点坐标错乱问题
    $('document').on('click','#changeImg',function() {
        $('#workflowTraceDialog').dialog('close');
        if ($('#imgDialog').length > 0) {
            $('#imgDialog').remove();
        }
        $('<div/>', {
            'id': 'imgDialog',
            title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点<button id="diagram-viewer">Diagram-Viewer</button>',
            html: "<img src='" + ctx + '/workflow/process/trace/auto/' + opts.pid + "' />"
        }).appendTo('body').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });
    });

	/*
	用官方开发的Diagram-Viewer跟踪
	 */
	$('document').on('click','#diagram-viewer',function() {
		$('#workflowTraceDialog').dialog('close');

		if ($('#imgDialog').length > 0) {
			$('#imgDialog').remove();
		}

		var url = ctx + '/diagram-viewer/index.html?processDefinitionId=' + opts.pdid + '&processInstanceId=' + opts.pid;

		$('<div/>', {
			'id': 'imgDialog',
			title: '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点',
			html: '<iframe src="' + url + '" width="100%" height="' + document.documentElement.clientHeight * 0.90 + '" />'
		}).appendTo('body').dialog({
			modal: true,
			resizable: false,
			dragable: false,
			width: document.documentElement.clientWidth * 0.95,
			height: document.documentElement.clientHeight * 0.95
		});
	});

    // 获取图片资源
    var imageUrl = ctx + "/workflow/resource/process-instance?pid=" + opts.pid + "&type=image";
    $.getJSON(ctx + '/workflow/process/trace/image?pid=' + opts.pid, function(infos) {

        var positionHtml = "";

        // 生成图片
        var varsArray = new Array();
        $.each(infos, function(i, v) {
            var $positionDiv = $('<div/>', {
                'class': 'activity-attr'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 2),
                height: (v.height-20),
                backgroundColor: 'black',
                opacity: 0,
                zIndex: $.fn.qtip.zindex - 1
            });

            // 节点边框
            var $border = $('<div/>', {
                'class': 'activity-attr-border'
            }).css({
                position: 'absolute',
                left: (v.x - 1),
                top: (v.y - 1),
                width: (v.width - 4),
                height: (v.height - 3),
                zIndex: $.fn.qtip.zindex - 2
            });

            if (v.currentActiviti) {
                $border.addClass('ui-corner-all-12').css({
                    border: '3px solid red'
                });
            }
            if(v.isEnd){
            	  $border.addClass('ui-corner-all-12').css({
                      border: '3px solid green',
                      borderRadius:'25px'
                  });
            }
            positionHtml += $positionDiv.outerHTML() + $border.outerHTML();
            varsArray[varsArray.length] = v.vars;
        });

        if ($('#workflowTraceDialog').length == 0) {
            $('<div/>', {
                id: 'workflowTraceDialog',
                title: '流程图',
                html: "<div><img src='" + imageUrl + "'id='workflow-img'/>" +
                "<div id='processImageBorder'>" +
                positionHtml +
                "</div>" +
                "</div>"+
                "<div>" +
                "</div>" 
            }).appendTo('body');
            //
            
            //$("#traceTb").datagrid('reload');
        } else {
            $('#workflowTraceDialog img').attr('src', imageUrl);
            $('#workflowTraceDialog #processImageBorder').html(positionHtml);
        }

        // 设置每个节点的data
        $('#workflowTraceDialog .activity-attr').each(function(i, v) {
            $(this).data('vars', varsArray[i]);
        });
        // 打开对话框
        $('#workflowTraceDialog').dialog({
            modal: true,
            resizable: false,
            dragable: false,
            open: function() {
                $('#workflowTraceDialog').dialog('option', 'title', '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button><button id="diagram-viewer">Diagram-Viewer</button>');
                $('#workflowTraceDialog').css('padding', '0.2em');
                $('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

                // 此处用于显示每个节点的信息，如果不需要可以删除
                $('.activity-attr').qtip({
                    content: function() {
                        var vars = $(this).data('vars');
                        var tipContent = "<table class='need-border'>";
                        $.each(vars, function(varKey, varValue) {
                            if (varValue) {
                                tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
                            }
                        });
                        tipContent += "</table>";
                        return tipContent;
                    },
                    position: {
                        at: 'bottom left',
                        adjust: {
                            x: 3
                        }
                    }
                });
                // end qtip
            },
            close: function() {
                $('#workflowTraceDialog').remove();
            },
            width: document.documentElement.clientWidth * 0.95,
            height: document.documentElement.clientHeight * 0.95
        });

    });
}

//查询流程的历史执行记录,然后再dialog中显示
function queryWorkflowTrack(pid){
	
}
