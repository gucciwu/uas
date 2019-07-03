var SystemMenu = [

    {
        title: '定时任务',
        icon: '&#xe61e;',
        href:'timingTask/taskList.html',
        children: []
    },{
		title: '产品管理',
		icon: '&#xe6bf;',
		isCurrent: false,
		children: [{
			title: '产品设计',
			icon: '&#xe6d9;',
			isCurrent: true,
			children: [{
				title: '产品要素',
				href: 'index.html',
				isCurrent: true
			},{
				title: '合同生成',
				href: 'index.html'
			}]
		},
		{
			title: '募集期',
			icon: '&#xe6a9;',
			isCurrent: true,
			children: [{
				title: '客户信息',
				href: 'index.html',
				isCurrent: true
			},{
				title: '信息查询',
				href: 'index.html'
			}]
		},
		{
			title: '产品运作',
			icon: '&#xe6eb;',
			isCurrent: true,
			children: [{
				title: '产品查询',
				href: 'index.html',
				isCurrent: true
			},{
				title: '划款指令',
				href: 'index.html'
			},{
				title: '报表管理',
				isCurrent: false,
				children: [{
					title: '基础报表',
					isCurrent: true,
					href: '../../pp/app/product/working/report/basic/list.html'
				},{
					title: '信息披露系统报表',
					isCurrent: true,
					href: '../../pp/app/product/working/report/pfid/list.html'
				},{
					title: '基金备案系统报表',
					isCurrent: true,
					href: '../../pp/app/product/working/report/ambers/list.html'
				}]
			}]
		},
		{
			title: '产品清盘',
			icon: '&#xe6fc;',
			isCurrent: false,
			children: []
		}
		]
	},
	{
		title: '管理人管理',
		icon: '&#xe60d;',
		isCurrent: false,
		children: []
	},
	{
		title: '投资人管理',
		icon: '&#xe6a7;',
		isCurrent: false,
		children: []
	},
	{
		title: '账户管理',
		icon: '&#xe63f;',
		isCurrent: false,
		children: []
	},
	{
		title: '文件管理',
		icon: '&#xe61e;',
		isCurrent: false,
		children: []
	},
	{
		title: '数据交互',
		icon: '&#xe6ac;',
		isCurrent: false,
		children: []
	},
	{
	title: '系统管理',
	icon: '&#xe674;',
	isCurrent: false,
	children: [{
		title: '权限管理',
		icon: '&#xe620;',
		isCurrent: true,
		children: [{
			title: '资源管理',
			href: 'permission/permission.html',
			isCurrent: false
		},{
			title: '角色管理',
			href: 'role/role.html'
		},{
			title: '用户管理',
			href: 'employee/user.html'			
		},{
			title: '组织结构',
			href: 'orgnization/orgnization.html'
		}]
	},{
		title: '数据字典',
		icon: '&#xe6bb;',
		href: 'dictionary/list.html',
		children: []
	},{
		title: '参数配置',
		icon: '&#xe6fa;',
		href:'config/list.html',
		children: []
	},
	{
		title: '定时任务',
		icon: '&#xe6c3;',
        href:'timingTask/taskList.html',
		children: []
	},
	{
		title: '日志查询',
		icon: '&#xe632;',
		children: []
	}
	]
}, {
        title: '首页',
        icon: '&#xe60d;',
        isCurrent: true,
        children: [{
            title: '首页',
            icon: '&#xe6d9;',
            isCurrent: true,
            children: [{
                title: '首页',
                href: 'workbench.html',
                isCurrent: true
            }]
        }]
    }];