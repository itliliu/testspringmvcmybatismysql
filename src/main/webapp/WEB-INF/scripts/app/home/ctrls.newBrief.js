/**  Home - ctrls.briefForm.js */
define(['angular', 'app/home/module', 'bootStrap', 'dateTimePicker', 'angularUIRoute' ,'app/requests/brief','app/config/urlConfig'],
function (angular, app) {
	app.controller('newBriefCtrl', ['$scope','srvBrief', '$rootScope', '$stateParams',
        '$location', 'customAlert', 'srvCookie',
    function ($scope, srvBrief, $rootScope, $stateParams, $location, $customAlert, $srvCookie) {
        $scope.alerts = [
            { type: 'info', msg: 'warning' },
            { type: 'success', msg: 'Well done! You successfully read this important alert message.' },
            { type: 'warning', msg: 'warning' },
            { type: 'danger', msg: 'Oh snap! Change a few things up and try submitting again.' }
        ];
      
        $scope.reportName ='';
        var item = {
            isAllSelected:false,
            isAnyOneSelected:false,
            firstInput:'',
            secondInput:'',
            thirdInput:'',
            isDelete:false
        };
        var brandtog=0;
        var producttog=0;
        var peopletog=0;
        var competitortog=0;
        $scope.toggleBrand=function (item) {
            if(brandtog<4){
                for(var i=0; i<1; i++){
                    $scope.briefForm.brandList.push($.extend({},item));
                    brandtog=brandtog+1;
                }
            }
            else{
                $('#myModal').modal();
            }
        };
        $scope.toggleProduct=function (item) {
            if(producttog<4){
                for(var i=0; i<1; i++){
                    $scope.briefForm.productList.push($.extend({},item));
                    producttog=producttog+1;
                }
            }
            else{
                $('#myModal').modal();
            }
        };
        $scope.togglePeople=function (item) {
            if(peopletog<4){
                for(var i=0; i<1; i++){
                    $scope.briefForm.peopleList.push($.extend({},item));
                    peopletog=peopletog+1;
                }
            }
            else{
                $('#myModal').modal();
            }
        };
        $scope.toggleCompetitor=function (item) {
            if(competitortog<4){
                for(var i=0; i<1; i++){
                    $scope.briefForm.competitorList.push($.extend({},item));
                    competitortog=competitortog+1;
                }
            }
            else{
                $('#myModal').modal();
            }
        };
        $scope.removeBrand=function(index){
            $scope.briefForm.brandList.splice(index,1);
            brandtog=brandtog-1;
        };
        $scope.removeProduct=function(index){
            $scope.briefForm.productList.splice(index,1);
            producttog=producttog-1;
        };
        $scope.removePeople=function(index){
            $scope.briefForm.peopleList.splice(index,1);
            peopletog=peopletog-1;
        };
        $scope.removeCompetitor=function(index){
            $scope.briefForm.competitorList.splice(index,1);
            competitortog=competitortog-1;
        };

        $scope.saveBrief=function(){
            requestForm();
        };
        initializedBriefForm();
        function initializedBriefForm() {
            $scope.briefForm = {
                                'searchStartDate':'',
                                'searchEndDate':'',
                                'eventOverview':'',
                                'isConfidential':'false',
                                'eventItem':$.extend({},item),
                                'brandList' :[$.extend({},item)],
                                'productList': [$.extend({},item)],
                                'peopleList': [$.extend({},item)],
                                'competitorList' :[$.extend({},item)],
                                'wordsOrTerms':{},
                                'keyMessages':[],
                                'priorityProducts':{},
                                'sentiment':{},
                                'mediaSource':{},
                                'finalDetermine':'',
                                'mostSignificant':'',
                                'influencers':'',
                                'reportTemplate':'',
                                'projectId':'',
                                'activityCode':'',
                                'dueDate':''
                            };
        };
        function requestForm() {
            var params = {
                userId:1,
                clientId:2,
                briefName:"test",
                briefContext:"json",
            };
            srvBrief.saveBrief(params).then(function(result){
                if(result.code === 'ACK') {
                    if(result.data) {
                       
                    }
                } else {
                    $customAlert.warning('error');
                }
            },function(result){
                $customAlert.alert(result + ' Please contact the admin.');
            });
        };
    }]);
});
