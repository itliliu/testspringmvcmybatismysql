/**  Config - urlConfig.js */
define(['angular', 'app/help/module'], function (angular, module) {
	module.factory('urlConfig', function () {
	    var baseUrl = 'http://172.17.17.99:8082/ReportingTool/';
	    return {
	    	'common': {
	    		'uploadpptbase64' : baseUrl+'file/uploadpptbase64.json',
	    		'uploadwordbase64' : baseUrl+'file/uploadwordbase64.json',
	    		'uploadpbitbase64' : baseUrl+'file/uploadpbitbase64.json',
	    		'roles' : baseUrl+'roles/list',
	    		'downloadFile': baseUrl + 'file/download.json',
	    		'uploadFile': baseUrl + 'file/upload.json',
	    		'getFilePath': baseUrl + 'preview/get.json'
	    	},
	        'account': {
	            'login': baseUrl + 'login.json',
	            'loginForLocal': baseUrl + 'login/login.json',
	            'logout':'https://login.windows.net/zhonghongcloudbridgepro.onmicrosoft.com/oauth2/logout'
	        },
	        'menu': {
	        	'getClientList': baseUrl + 'client/parent.json',
	        	'getChildClient': baseUrl + 'client/child.json',
	        	'setDefaultClient': baseUrl + 'client/setdefault.json',
	        	'recentProjectList': baseUrl + 'project/recent.json',
	        	'recentUserLibrary': baseUrl + 'template/recent.json'
	        },
	        'project': {
	        	'getProjectList': baseUrl + 'request/search.json',
	        	'updateProjectOperatorDate': baseUrl + 'request/updateoperatordate.json',
	        	//brief form
	        	'saveBrief': baseUrl + 'request/projectbrief/save.json',
	        	'submitBrief': baseUrl + 'request/projectbrief/submit.json',
	        	'deleteBrief': baseUrl + 'request/projectbrief/delete.json',
	        	'getBriefInfo': baseUrl + 'request/projectbrief/get.json',
	        	//proposal form
	        	'getProposalInfo': baseUrl + 'request/projectproposal/get.json',
	        	'saveProposal': baseUrl + 'request/projectproposal/save.json',
	        	'submitProposal': baseUrl + 'request/projectproposal/submit.json',
	        	'approveProposal': baseUrl + 'request/projectproposal/approve.json',
	        	'rejectProposal': baseUrl + 'request/projectproposal/reject.json',
	        	'exportProposal': baseUrl + 'generate/generate.json',
	        	//request form
	        	'saveRequest': baseUrl + 'request/projectform/save.json',
	        	'commitRequest': baseUrl + 'request/report/commit.json',
	        	'getRequestInfo': baseUrl + 'request/projectform/get.json',
	        	//report form
	        	'saveReport': baseUrl + 'request/report/save.json',
	        	'saveReportFile': baseUrl + 'request/report/savereportfile.json',
	        	'rejectReport': baseUrl + 'request/report/reject.json',
	        	'completeReport': baseUrl + 'request/report/complete.json',
	        	'createBriefDoc': baseUrl + 'generate/generatebrief.json',
	        	'getReportInfo': baseUrl + 'request/report/get.json',
	        	'getTemplateList':baseUrl + 'template/templatenamelist.json'
	        },
			'client' : {
				'list'   : baseUrl + 'client/getclienttree.json',
				'all'    : baseUrl + 'client/getall.json',
				'create' : baseUrl + 'client/add.json',
				'update' : baseUrl + 'client/edit.json',
				'deactivate' : baseUrl + 'client/deactivate.json'
			},
			'user' : {
				'list'   : baseUrl + 'user/searchuser.json',
				'create' : baseUrl + 'user/add.json',
				'update' : baseUrl + 'user/edit.json',
				'delete' : baseUrl + 'user/delete.json'
			},
	        'template': {
	        	'templateList':baseUrl+'template/more.json',
	        	'templateDetail':baseUrl+'template/detailed.json',
	        	'selectType':baseUrl+'template/getformatereporttype.json',
	        	'deleteTemplate': baseUrl + 'template/delete.json',
	        	'getPowerbi': baseUrl + 'template/getpowerbi.json',
	        	'saveTemplate':baseUrl +'template/save.json',
	        	'getClientInfo':baseUrl + 'template/getclientinfo.json'
	        },
	        'brief' : {
				'save' : baseUrl + 'brief/add.json',
			},
		};
	});
});
