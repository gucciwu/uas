/**
 * 首页待办跳转至工作流相关模块
 * 目前已完成首页跳转至业务模块，但无法实现在业务模块的页面中再打开对应的记录。
 * 暂时废弃这种做法，改为在首页中直接打开及处理。
 */

var indexDoc=window.parent.document;
var workflowObject;
//首页待办跳转到报表审核页面
	var toReport=function(){
		
		$('.pf-nav-item', indexDoc).removeClass('current');
		
		$('.pf-nav-item', indexDoc).eq(0).addClass('current');
				
		var menu=parent.SystemMenu[5];
		var index=5;
		_createSiderMenu(menu,index);
		if($('#pf-bd[class]', indexDoc)){
			$('#pf-bd', indexDoc).removeClass('bodydiv');

		}
	    if($(".tabs-header[class*='tabs-headernone']",indexDoc)){
	    	$(".tabs-header", indexDoc).removeClass('tabs-headernone');
	    }
	    
	    //要打开工作流页面，然后再打开工作流处理的dialog
	    
	    
	}
	
	var _createSiderMenu= function(menu, index){
		/*alert("createSiderMenu"+index);*/
		$('.pf-sider', indexDoc).hide();
            createPageContainer(index);

		if($('.pf-sider[arrindex='+ index +']', indexDoc).size() > 0) {			
			$('.pf-sider[arrindex='+ index +']',indexDoc).show();
			//触发左侧工作流的菜单点击事件，添加tab
			workflowObject=$('.pf-sider[arrindex='+ index +']',indexDoc).find('.sider-nav li[data-href="testwf/list.html"]');
			//clickSiderMenu(workflowObject);
			return false;
		};
		var menuStr = '<h2 class="pf-model-name">'+
                    '<span class="iconfont">'+ menu.icon+'</span>'+
                    '<span class="pf-name">'+ menu.title +'</span>'+
                    '<span class="toggle-icon"></span>'+
                '</h2><ul class="sider-nav">';

        for(var i = 0, len = menu.children.length; i < len; i++){
        	var m = menu.children[i],
        		mstr = '';
        	var str = '';

        	if(m.isCurrent){
        		if(m.children && m.children.length > 0) {
        			str = '<li class="current">';
        		}else{
        			str = '<li class="current no-child" data-href="'+ m.href +'">';
        		}
        	}else{
        		if(m.children && m.children.length > 0) {
        			str = '<li>';
        		}else{
        			str = '<li class="no-child" data-href="'+ m.href +'">';
        		}
        	}
        	//str = m.isCurrent ? '<li class="current">' : '<li>';

           str += '<a href="javascript:;" class="pf-menu-title">'+
                '<span class="iconfont sider-nav-icon">'+ m.icon +'</span>'+
                '<span class="sider-nav-title">'+ m.title +'</span>'+
                '<i class="iconfont">&#xe642;</i>'+
            '</a>'+
            '<ul class="sider-nav-s">';
            var childStr = '';
            for(var j = 0, jlen = m.children.length; j < jlen; j++){
            	var child = m.children[j];
            	var dataHref = (child.children && child.children.length > 0) ? '' : ('data-href="' + child.href + '"'); 
            	if(child.isCurrent){
            		childStr += '<li class="active ' + (!dataHref ? 'no-child' : '') + '" text="'+ child.title +'" ' + dataHref + '><a href="#">'+ child.title +'</a>' + renderThreeLevelMenu(child.children) + '</li>';
            		$('.easyui-tabs1[arrindex='+ index +']', indexDoc).tabs('add',{
						title: child.title,
						content: '<iframe class="page-iframe" src="'+ child.href +'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto"></iframe>',
						closable: true
					});
            	}else {
            		childStr += '<li class="' + (!dataHref ? 'no-child' : '') + '" text="'+ child.title +'" ' + dataHref + '><a href="#">'+ child.title +'</a>' + renderThreeLevelMenu(child.children) + '</li>';
            	}
            }

            mstr = str + childStr + '</ul></li>';

            menuStr += mstr;

            
        }
        $('.pf-sider-wrap', indexDoc).append($('<div class="pf-sider" arrindex="'+ index +'"></div>',indexDoc).html(menuStr + '</ul>'));

	}
	
	var createPageContainer=function(index){
		/*alert('createPageContainer' + index);*/
		$('.easyui-tabs1', indexDoc).hide();
		if($('.easyui-tabs1[arrindex='+ index +']', indexDoc).size() > 0){
			$('.easyui-tabs1[arrindex='+ index +']', indexDoc).show();
			return false;
		}

          var $tabs = $('<div class="easyui-tabs1" arrindex="'+ index +'" style="width:100%;height:100%;">');//动态创建dom节点

		$('#pf-page',indexDoc).append($tabs);
		$tabs.tabs({
	      tabHeight: 44,
	      onSelect:function(title, index){
	        var currentTab = $tabs.tabs("getSelected");
	        var $active, $parent;
	        if(currentTab.find("iframe") && currentTab.find("iframe").size() && !currentTab.find("iframe").attr("src")){
	            currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
	        }
	        currentTab.find("iframe").resize();
	        $('.pf-sider:visible',indexDoc).find('.sider-nav-s li').removeClass('active');
	        var $active = $('.pf-sider:visible',indexDoc).find('.sider-nav-s li[text='+ title +']').addClass('active');
	        var $parent = $active.parent();
	        if($parent.hasClass('sider-nav-t')) {
	        	$parent.closest('li').addClass('active');
	        }
	      }
	    });

	    $tabs.find('.tabs-header',indexDoc).on('contextmenu', function(e){
	    	e.preventDefault();
	    	if($(e.target,indexDoc).closest('li').size() > 0 || $(e.target,indexDoc).is('li')){
	    		$('#mm',indexDoc).menu('show', {
		             left: e.pageX,
		             top: e.pageY,
		         });
	    		var subtitle = $(e.target,indexDoc).closest('li').size() ? $(e.target,indexDoc).closest('li') : $(e.target,indexDoc);
        		$('#mm',indexDoc).data("currtab",subtitle.text());
	    	}
	    })
	}
	
	var renderThreeLevelMenu=function(list) {

		var html = '<ul class="sider-nav-t">',
			i = 0,
			len,
			one;

		if(!list || len === 0) {

			return '';

		}

		len = list.length;

		for(;i<len;i++) {

			one = list[i];

			html += '<li text="' + one.title +'" data-href="' + one.href + '"><a href="#">' + one.title + '</a></li>';

		}

		html += '</ul>';

		return html;

	}
	
	var clickSiderMenu=function(menuObject){
    	var index = menuObject.closest('.pf-sider').attr('arrindex');
    	var titleName = menuObject.find('a').eq(0).text();
    	var url = menuObject.data('href');
    	var $this = menuObject;
    	var $target = menuObject.find('a');

    	if($target.closest('ul').hasClass('sider-nav-t')) {
    		return;
    	}

    	menuObject.closest('.pf-sider').find('.active').removeClass('active');
    	menuObject.addClass('active');

    	if(!url) {

    		$this.toggleClass('no-child');
    		return;

    	}

    	if($('.easyui-tabs1[arrindex='+ index +']',indexDoc).tabs('exists', titleName)){
    		$('.easyui-tabs1[arrindex='+ index +']',indexDoc).tabs('select', titleName)
    		return;
    	}
    	
    	$('.easyui-tabs1[arrindex='+ index +']',indexDoc).tabs('add',{
			title: titleName,
			content: '<iframe class="page-iframe" src="'+ $(this).data('href') +'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto"></iframe>',
			closable: true
		});
	}
	
	
	
	