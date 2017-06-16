//requirejs.config
var require = { //this sets it as global so it won't be loaded asynch
    baseUrl: "../",
    waitSeconds: 60,
    urlArgs: "bust=@version@",// + (new Date()).getTime(),
    paths:
    {
        MSTBase: "scripts/Libs/MSTBase",
        CommonUtils: "scripts/ChartCore/Utils/CommonUtils",
        jquery: "scripts/Libs/jquery",
        backbone: "scripts/Libs/backbone",
        underscore: "scripts/Libs/underscore",
        json2: "scripts/Libs/json2",
        Model: "scripts/ChartCore/Model",
        View: "scripts/ChartCore/View",
        Controller: "scripts/ChartCore/Controller",
        Template: "scripts/ChartCore/Template",
        Scalers: "scripts/ChartCore/Scalers",
        Utils: "scripts/ChartCore/Utils",
        CoordinateSystem: "scripts/ChartCore/CoordinateSystem",
        StepEnumerator: "scripts/ChartCore/StepEnumerator",
        ChartCore: "scripts/ChartCore",
        WONChart: "WONChartImpl",
        MSGlobal: "MSGlobalImpl",
        canvg: "scripts/Libs/canvg",
        angular: "Scripts/Libs/angular"
    },
    shim:
    {
        "CommonUtils":
        {
            exports: "CommonUtils"
        },
        "MSTBase":
        {
            exports: "MSTBase"
        },
        "jquery":
        {
            exports: "$"
        },
        "backbone": {
            deps: ["underscore", "jquery", "json2", "CommonUtils", "MSTBase"],
            exports: "Backbone"
        },
        "underscore": {
            exports: "_"
        },
        "json2": {
            exports: "JSON"
        },
        "canvg": {
            exports: "canvg"
        },
        "angular": {
            deps: ["jquery"],
            exports: "angular"
        }
    }
};