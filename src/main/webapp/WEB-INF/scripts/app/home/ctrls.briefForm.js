/**  Home - ctrls.projectForm.js */
define(['angular', 'app/home/module', 'app/requests/projectForm'],
function (angular, app) {
	app.controller('briefFormCtrl', ['$scope','srvProjectForm', '$rootScope','$stateParams','$state',
                '$location', 'customAlert', 'srvCookie','permissions',
    function ($scope, srv, $rootScope, $stateParams, $state, $location, $customAlert, $srvCookie, $permissions) {
        var userInfo = $srvCookie.get('userInfo'),
            curClient = $srvCookie.get('curClient');

        $scope.userid = userInfo.id;
        $scope.role = userInfo.role;
        $scope.client = (curClient && curClient.clientID !== -1)? curClient.client: userInfo.clientName;
        $scope.clientID = (curClient && curClient.clientID !== -1)? curClient.clientID: userInfo.defaultClientID;
        $scope.projectStatus = $stateParams.status;
        $scope.pageStatus = $stateParams.pageStatus;
        $scope.briefID = $stateParams.id - 0;
        if($scope.projectStatus !== null){
            $scope.projectStatus = $scope.projectStatus - 0;
            if($scope.projectStatus >= 0){
                $srvCookie.put('projectStatus',$scope.projectStatus);
            }
        }else{
            //use for manual refresh
            $scope.projectStatus = $srvCookie.get('projectStatus') - 0;
        }
        $scope.delBtnStatus = 'hidden';
        $scope.allBtnStatus = '';
        $scope.customText = '';
        $scope.canEditBrief = $permissions.hasPermission('projectBrief-edit');

        //init
        initializedBriefForm();

        //help popover
        $scope.helpText = {
                             'objective':
                             'Key concept or guiding principle for the project, in ten words or less.',
                             'business':'What are the business problems we are trying to solve?',
                             'communications':'What are we trying to achieve via communications strategies?',
                             'outcomes':
                             'What are the desired outcomes? (Strategic Idea, Key Performance Indicators (KPIs),'+
                              ' Goal Metrics)',
                             'context':
                             'What is the big picture surrounding the business problem?'+
                             ' What behavioral change is needed to address the business problem?'+
                             ' Does the partner/client have any inclination towards a certain direction?'+
                             ' Is there a hypothesis to prove/disprove?',
                             'targetAudience':'Who is your target audience?',
                             'createInsporation':
                             'images, videos, quotes, testimonials, media coverage, consumer insights or data,'+
                             ' brand keywords or descriptors',
                             'projectType':'Analytics, measurement, research, hybrid, other',
                             'deliverable':'what form will the deliverable take?',
                             'announcementType':'MAJOR or MINOR: Expected volume',
                             'additionalContent':'Broadcast, Social (Owned and/or Earned), Digital (MS Blogs), '+
                             'Facebook, YouTube, AR'
                          };

        $('[data-toggle="popover"]').popover();

        $scope.addSchedule = function($index) {
            var count = $scope.briefForm.scheduleTimeline.length,
                data = $scope.briefForm.scheduleTimeline;
            data.splice($index + 1,0,{'id': count+1, 'schedule':''});
        };
        $scope.deleteSchedule = function($index) {
            $scope.briefForm.scheduleTimeline.splice($index,1);
        };
        $scope.toggleTable = function($event) {
            var obj = angular.element($event.currentTarget),
                getClass = obj.attr('class');
            if(getClass.indexOf('active') > -1) {
                obj.removeClass('active');
                obj.parents('.form-row').find('ul').addClass('hidden');
            } else {
                obj.addClass('active');
                obj.parents('.form-row').find('ul').removeClass('hidden');
            };
        };
        $scope.saveBrief = function(){
            saveOrSumbitOrDeleteBriefForm('save');
        };
        $scope.submitBrief = function(){
            saveOrSumbitOrDeleteBriefForm('submit');
        };
        $scope.cancelBrief = function(){
            $customAlert.confirm('Confirm to cancel?', cancelBrief, '');
        };
        $scope.deleteBrief = function(){
            $customAlert.confirm('Confirm to delete?', saveOrSumbitOrDeleteBriefForm, 'delete');
        };
        function cancelBrief() {
            var linkParams = $scope.pageStatus? {status:$scope.pageStatus} :{};
            if($scope.pageStatus === 0) {
                linkParams = {status: 0};
            }
            $state.go('home.project.list',linkParams);
        };
        function initializedBriefForm() {
             $scope.briefForm = {
                                'projectBriefName':'',
                                'objectGuidingPrinciple':'',
                                'businessObjective':'',
                                'communicationObjective':'',
                                'outcomes':'',
                                'context':'',
                                'targetAudience':'',
                                'createInsporation':'',
                                'projectType':[],
                                'projectLeads':{
                                    'whoHasFinalApproval':'',
                                    'strategist':'',
                                    'accountPartner':'',
                                    'client':''
                                },
                                'projectID':'',
                                'deliverable':'',
                                'scheduleTimeline':[{
                                    'id':1, 'schedule':''},{'id':2, 'schedule':''
                                }],
                                'announcementType':[],
                                'keyMessage':'',
                                'additionalContent':'',
                                'usOnlyOrGolbalMetrics':'',
                                'searchTerms':'',
                                'anyAdditionalData':'',
                                'requestedBy':'',
                                'isNeedProposal':true
                            };
             briefPermission();
        };
        function getBriefFormData(id) {
            var briefID = parseInt(id, 10),
                params = {};
            params.userid = $scope.userid;
            params.projectBriefID = briefID;
            srv.getBriefInfo(params).then(function(result){
                if(result.code === 'NACK') {
                   $customAlert.warning(result.message);
                }
                if(result.code === 'ACK' && result.data && result.data.length > 0) {
                    var list = result.data[0], curFile = '',
                        parentID = list.parentID? list.parentID: list.projectBriefID,
                        updateParams = {clientID: list.clientID, projectBriefID: parentID, status:list.status};
                    $scope.briefForm = optimizeBriefParameter(list, 'get');
                    if($scope.briefForm.userid !== $scope.userid && $scope.role !== 'Admin') {
                        closeBriefPermission();
                    }
                    $srvCookie.put('projectParams', updateParams);
                    //get brief name and get proposal name
                    var briefName = $scope.briefForm.projectBriefName,
                        proposalName = $scope.briefForm.projectProposalName;
                    curFile = proposalName? proposalName: briefName;
                    $rootScope.$emit('updatePathway',{type:'project', curFile:curFile});
                    $rootScope.$emit('toggleProposal',{isNeedProposal:$scope.briefForm.isNeedProposal});
                }
            },function(result){
                $customAlert.alert(result.message + ' Please contact the admin.');
            });
        };
        function saveOrSumbitOrDeleteBriefForm(type) {
            var promise, params = {};
            if(type !== 'delete') {
                if($scope.briefForm.projectType[4] === 'Other' && $scope.customText) {
                    $scope.briefForm.projectType[4] = $scope.customText;
                }
                params = optimizeBriefParameter($scope.briefForm, type);
                if(params.projectBriefID && !params.parentID) {
                    params.parentID = params.projectBriefID;
                }
                if(!params.clientID) {
                    params.clientID = $scope.clientID;
                }
            } else {
                params.projectBriefID = $scope.briefID;
            }
            params.userid = $scope.userid;
            if(conditionalValidationForBrief($scope.briefForm) && $scope.allBtnStatus !== 'disabled') {
                $scope.allBtnStatus = 'disabled';
                if(type === 'save') {
                    promise = srv.saveBrief(params);
                } else if(type === 'submit') {
                    promise = srv.submitBrief(params);
                } else if(type === 'delete') {
                    promise = srv.deleteBrief(params);
                }
                promise.then(function(result){
                    if(result.code === 'ACK') {
                        $rootScope.$emit('updateMenu',{type:'project'});
                        cancelBrief();
                    } else {
                       $scope.allBtnStatus = '';
                       $customAlert.warning(result.message);
                    }
                },function(result){
                    $scope.allBtnStatus = '';
                    $customAlert.alert(result.message + ' Please contact the admin.');
                });
            };
        };
        function optimizeBriefParameter(obj, type) {
            var data = angular.extend({},obj);
            if(type === 'get') {
                data.projectType = JSON.parse(data.projectType);
                if(data.projectType[4]) {
                    $scope.customText = data.projectType[4];
                    data.projectType[4] = 'Other';
                }
                data.projectLeads = JSON.parse(data.projectLeads);
                data.scheduleTimeline = JSON.parse(data.scheduleTimeline);
                data.announcementType = JSON.parse(data.announcementType);
            } else {
                data.projectType = JSON.stringify(data.projectType);
                data.projectLeads = JSON.stringify(data.projectLeads);
                data.scheduleTimeline = JSON.stringify(data.scheduleTimeline);
                data.announcementType = JSON.stringify(data.announcementType);
            }
            return data;
        };
        function briefPermission() {
            if($scope.briefID) {
                getBriefFormData($scope.briefID);
                $scope.delBtnStatus = '';
            }
            $scope.isRequestByHidden = 'hidden';
            if($scope.projectStatus && parseInt($scope.projectStatus, 10) > 0) {
                closeBriefPermission();
            }
        };
        function closeBriefPermission() {
            $scope.isRequestByHidden = '';
            $scope.allBtnStatus = 'hidden';
            $scope.canEditBrief = false;
        };
        function conditionalValidationForBrief(formData) {
            if(!formData.projectBriefName) {
                $customAlert.warning('Please add project brief name.');
                return false;
            }
            return true;
        };
	}]);
});
