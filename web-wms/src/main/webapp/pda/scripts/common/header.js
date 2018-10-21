//Resource
wms.addResource("zh-CN",{
    'frame_mustchooseorg': '必须选择一个和现有组织不同的组织'
});

//Variables
var orgtree= eval(unitTree);

//Ready Function
wms.addReadyFunc(function(){
    console.log("ready function in header");

    $('#orgpicker-modal').on('shown.bs.nifty-modal', function(e){
        $('#org-tree').treeview({data: prepareTreeStyle(orgtree),
            levels:99,
            nodeIcon: 'icon-layers',
            showBorder: false,
            enableLinks:true,
            onNodeSelected: function(event, node){
                $("input[name='orgpicker.org.id']").val(node.id);
                $("input[name='orgpicker.org.name']").val(node.text);
            }});
    });

    $('#orgpicker-modal .btn-primary').click(function(evt){
        evt.preventDefault();
        var id = $("input[name='orgpicker.org.id']").val();
        if(id != ""){
            $("input[name='orgpicker.org.id']").val("");
            $("input[name='orgpicker.org.name']").val("");
            $('#orgpicker-modal button.md-close').trigger("click");
            var url = pagebase+"/auth/org/change";
           window.location.href=pagebase +"/auth/org/change?ouId="+id;
        }else{
            $.notify({
                //title: '必须选择一个和现有组织不同的组织',
                text: i18n.t("frame_mustchooseorg"),
                image: "<i class='fa fa-exclamation'></i>"
            }, {
                style: 'metro',
                className: 'error',
                /*globalPosition:'top center',*/
                hideAnimation: "fadeOut",
                showDuration: 0,
                hideDuration: 1000,
                autoHideDelay: 3000,
                autoHide: true,
                clickToHide: true
            });
        }
    });
});

//Functions

function prepareTreeStyle(tree){
    var result = [];
    if(tree != undefined){

        if(!$.isArray(tree)){
            result.push(_prepareTreeItem(tree));
        }else{
            $.each(tree, function(){
                result.push(_prepareTreeItem(this));
            });
        }
    }
    return result;
}

function _prepareTreeItem(treeItem){
    if(treeItem){
        if(treeItem.selectable != undefined && treeItem.selectable == false)
            $.extend(treeItem, {color: "#ABB7B7"});
        if($.isArray(treeItem.nodes)){
            $.extend(treeItem, {icon: 'fa fa-home'});
            treeItem.nodes = prepareTreeStyle(treeItem.nodes);
        }
    }
    return treeItem;
}


