<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <%@ include file="/lmis/yuriy.jsp" %>
    <title>LMIS</title>
    <meta name="description" content="overview &amp; stats" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="<%=basePath %>/css/tree.css" type="text/css">
    <link rel="stylesheet" href="<%=basePath %>/css/metroStyle/metroStyle.css" type="text/css">
    <script type="text/javascript" src="<%=basePath %>/js/jquery-1.4.4.min.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="<%=basePath %>/js/jquery.ztree.exedit.js"></script>
    <script type="text/javascript">
        if("ontouchend" in document) document.write("<script src='<%=basePath %>assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
    </script>
</head>

<body>
<div class="page-header">
    <h1>
        绑定物流商
    </h1>
</div>
<div class="col-xs-12">
    <form class="form-horizontal" id="menuform" role="form" action="${root}control/roleController/update.do">
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 店铺名称&nbsp;: </label>
            <div class="col-sm-9" style="margin-top: 5px;">
                ${store.storeName}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 店铺编码&nbsp;: </label>
            <div class="col-sm-9" style="margin-top: 5px;">
                ${store.storeCode}
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 店铺保价&nbsp;: </label>
            <input type="hidden" value="${store.storebj}" id="bj" name="bj" />
            <div class= "col-md-1" >
                <input id="storebj" name="storebj" type="checkbox"  ${store.storebj==true?"checked='checked'":""} class="ace ace-switch ace-switch-5" />
                <span class="lbl"></span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right blue" for="form-field-1"> 物流商选择&nbsp;: <input type="hidden" value="${store.storeCode}" id="storeCode" name="storeCode" /></label>
            <div class="col-sm-9">
                <div class="content_wrap">
                    <div class="zTreeDemoBackground left">
                        <ul id="menuTree" class="ztree"></ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clearfix form-actions">
            <div class="col-md-offset-3 col-md-9">
                <button class="btn btn-info" type="button" id="subins" name="subins">
                    <i class="icon-ok bigger-110"></i>提交
                </button>
                &nbsp; &nbsp; &nbsp;
                <button class="btn" type="reset" onclick="openDiv('${root}control/storeController/dataView.do?pageName=main&isQuery=true')">
                    <i class="icon-undo bigger-110"></i>返回
                </button>
            </div>
        </div>
    </form>
</div>
<div class="space-4"></div>
</body>
<script type="text/javascript">
    var setting = {
        view: {
            addHoverDom: false,
            removeHoverDom: false,
            selectedMulti: false
        },
        check: {
            enable: true
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        edit: {
            enable: false
        }
    };
    //树_数据
    var zNodes =${transport};
    debugger
    $(document).ready(function(){
        debugger
        $.fn.zTree.init($("#menuTree"), setting, zNodes);
    });
    function getTreeTransportCodeS(){
        debugger
        var treeObj=$.fn.zTree.getZTreeObj("menuTree"),
            nodes=treeObj.getCheckedNodes(true),
            v="";
        for(var i=0;i<nodes.length;i++){
            if(i+1==nodes.length){
                v+=nodes[i].id
            }else{
                v+=nodes[i].id + ",";
            }

        }
        return v;
    }
   $("#subins").click(function(){
        $.ajax({
            type: "POST",
            url: "${root}/control/storeController/addStoreTransport.do",
            dataType: "json",
            data:  {"transportCodes":getTreeTransportCodeS(),
                "storebj":$("#bj").val()=="true"?"1":"0",
                "storeCode":$("#storeCode").val()
            },
            success: function (result) {
                debugger
                alert(result.msg);
            }
        });
    });
</script>
</html>
