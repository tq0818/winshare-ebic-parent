<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>添加教材</title>
    <meta name="decorator" content="default"/>
</head>
<body>
    <div class="main"  id='newPopUp'>
        <div class="createContainer">
            <form method="post" id="editForm">
                <div>
                    <span class="divTitle">教材名称:</span><input id="name" name="name"   required="required" type="text" maxLength="100" class="inputStyle"/>
                    <span style="color:red;">*</span>
                </div>
            <div class="coverContainer">
                    <span class="divTitle">上传封面:</span>
                    <div class="coverContent">
                        <div class="previewCover">
                            预览图片
                        </div>
                        <span class="upload_imgInfo">图片尺寸：90*130<br> 图片格式：.jpg .gif .bmp .png</span>
                        <div class="uploadCon">

                        </div>
                    </div>
                    <span style="color: red;margin-left: 5px;">*</span>
                    <span id="bookImg-error" class="errorLabel fl" style="clear: inherit;margin: 90px 0 0 10px;">书籍封面为必填选择</span>
                </div>
                <div class="bookProp">
                    <span class="divTitle">教材属性:</span>
                    <div class="bookPropChoose">
                        <div class="choosedProps">

                        </div>
                        <ul id="propList" class="propList">

                        </ul>

                    </div>
                    <span style="color: red;margin-left: 5px;">*</span>
                    <span id="bookproop-error" class="errorLabel">教材属性信息不完整</span>
                </div>
                <div class="bookProp">
                    <span class="divTitle">版　　次:</span>
                    <select class="inputStyle" required="required" id="version" name="version" ></select>
                    <span style="color:red;">*</span>
                </div>

            </form>

        </div>
        <div class="rightCntr">
            <div class="editTreeCntr fl" style="width: 252px;">
            	<ul id="zTree2" class="ztree" style="width:auto;"></ul>
            </div>
            <span style="color: red;margin-left: 5px;" class="fl">*</span>
        </div>

    </div>
<script type="text/javascript">
    function doSubmit(){
        var choosedProps = $('.choosedProps span');
        if(!$("#newPopUp").validatorResult()){
            return false;
        }
        if(choosedProps.length!=5){
            $('#bookproop-error').show();
            return false;
        }else{
            $('#bookproop-error').hide();
        }

        var treeData = zTree2.getNodes();
        var treeDataList = zTree2.transformToArray(treeData);

        if(treeDataList.length==1){
            alert("目录树节点不能为空");
            return false;
        }
        var filePath=$(".uploadCon input[type=file]").val()
        if(filePath==null||filePath==""){
            alert("选择书籍封面");
            return false;
        }
        $('.uploadCon').oneFileUploader('startUpload');
    }
    var newUploadCb = function(res,filePath,fileId){
        if(!$("#zTree").validatorResult()){
            return false;
        }
        if($('#zTree2').find('input').length>0){
            alert('书籍章节不能为空！');
            return false;
        }

        if(  res instanceof Error){
            alert("书籍封面上传出错");
            return;
        }
        $('.choosedProps').empty;
        var props = {
            name:$('#name').val().trim(),
            subjectCode:$('.choosedProps span').eq(0).attr('code'),
            subjectName:$('.choosedProps span').eq(0).text(),
            gradeCode:$('.choosedProps span').eq(1).attr('code'),
            gradetName:$('.choosedProps span').eq(1).text(),
            publisherCode:$('.choosedProps span').eq(2).attr('code'),
            publisherName:$('.choosedProps span').eq(2).text(),
            fasciculeCode:$('.choosedProps span').eq(3).attr('code'),
            fasciculeName:$('.choosedProps span').eq(3).text(),
            version:$('#version :selected').attr('code'),
            versionName:$('#version :selected').val(),
            editionCode:$('.choosedProps span').eq(4).attr('code'),
            editionName:$('.choosedProps span').eq(4).text(),
            filePath:filePath,
            fileId:fileId
        }
        var treeData = zTree2.getNodes();
        var treeDataList = zTree2.transformToArray(treeData);

        $.each(treeDataList,function(i){
            var parentIds = '';
            var parentNodes = this.getPath();
            var me = this;
            this.parentId = this.pId;
            this.code = this.id;
            this.id = 0;
            this.sort = i-1;
            this.sortNum = this.chapter;
            $.each(this.getPath(),function(i){
                if(this.code=='-1'){
                    return;
                }
                if(parentNodes.length-1==i){
                    return;
                }else if(parentNodes.length-2==i){
                    parentIds += this.code;
                }else{
                    parentIds += this.code+'_';
                }
                me.parentIds = parentIds;
            });

        });
        var reqData = {
            paramsBook:props,
            paramsList:treeDataList.splice(1)
        }
        var loadIndex = top.layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.ajax({
            url:'${pageContext.request.contextPath}/TeachingMateria/editBook.do',
            type:'POST',
            data:JSON.stringify(reqData),
            contentType : 'application/json;charset=utf-8',
            dataType: 'json',
            success:function(data){
                top.bookmgr.reqData.pageNo = 1;
                top.bookmgr.reqData.pageSize = 50;
                top.bookmgr.getBooks(top.bookmgr.reqData, true);
                top.layer.close(loadIndex);
                top.layer.close(top.bookmgr.index);
            }
        })
    };
$("#newPopUp").validator({
	msgDirection:"right",
	msgShow:false
	});
	var map = ['subject','gared','publishing','book',"book_edition"];
	$('#propList').bind('click',function(e){
	    var target = $(e.target);
        if(!target.attr('code')) return;
	    if(target.attr('code').length==0){
	    	return;
	    }
	    $('.choosedProps').append('<span code="'+target.attr('code')+'">'+target.text()+'</span>');
	    $(this).empty();
	    if($('.choosedProps span').length>4){
	    	$('#bookproop-error').hide();
	    	return;
	    }

	    getPorpItem({
	    	key:map[$('.choosedProps span').length]
	    });
	})
	$('.choosedProps').bind('click',function(e){

	    var target = $(e.target);
	    var index = target.index('.choosedProps span');
        if(index==-1){
            return false;
        }
	    target.nextAll().remove();
	    target.remove();
	    getPorpItem({
	    	key:map[index]
	    });
	})

    //获取元数据
	function getPorpItem(data){
	    $.ajax({
	        url:'${pageContext.request.contextPath}/TeachingMateria/getdic.do',
	        data:data,
	        success:function(data){
	            $('#propList').empty();
	            var propList = [];
	            $.each(data,function(){
	                var str = '<li class="propItem" code="'+this.code+'">'+this.name+'</li>';
	                propList.push(str);
	            })
	            $('#propList').append(propList.join(''));

	        }
	    })
	}
    getPorpItem({
        key:'subject'
    });

    //获取版次
	function getEdition(){
	    $.ajax({
	        url:'${pageContext.request.contextPath}/TeachingMateria/getdic.do',
	        data:{key:'edition'},
	        success:function(data){
	            $('#version').empty();
	            var propList = [];
	            $.each(data,function(){
	                var str = '<option code="'+this.code+'">'+this.name+'</option>';
	                propList.push(str);
	            })
	            $('#version').append(propList.join(''));
	        }
	    })
	}
	getEdition();




    $('.uploadCon').oneFileUploader({
        title:'选择图片',
        ossInfoUrl:"${pageContext.request.contextPath}/TeachingMateria/getOssInfo.do",
        imgPreviewDom:'.previewCover',
        accept:'image/png,image/bmp,image/jpeg,image/gif',
        uploadEnd:newUploadCb
    });



</script>
<SCRIPT type="text/javascript">

        var setting = {
            view: {
                addHoverDom: addHoverDom,
                removeHoverDom: removeHoverDom,
                selectedMulti: false
            },
            edit: {
                enable: true,
                editNameSelectAll: true,
                removeTitle: "删除",
                renameTitle: "修改",
                drag:{
                	isCopy:true,
                	isMove:true,
                	prev:true,
                	next:true,
                	inner:true
                },
                showRemoveBtn: showRemoveBtn,
                showRenameBtn: showRenameBtn
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                beforeDrop:beforeDrop,
                beforeDrag: beforeDrag,
                beforeEditName: beforeEditName,
                beforeRemove: beforeRemove,
                beforeRename: beforeRename,
                onRemove: onRemove,
                onRename: onRename
            }
        };

        var isFirst = false;
        var zNodes =[
            { id:-1, pId:0, name:"根节点",chapter:'', edit:false, open:true},
        ];
        var log, className = "dark";
        function beforeDrag(treeId, treeNodes) {
            return true;
        }
        function beforeDrop(treeId, treeNodes, targetNode, moveType, isCopy){
            if(targetNode==null||targetNode.id == -1){
                return false;
            }
            return true;
        }
        function beforeEditName(treeId, treeNode) {
            className = (className === "dark" ? "":"dark");
            // showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
            var zTree = $.fn.zTree.getZTreeObj("zTree2");
            zTree.selectNode(treeNode);
            setTimeout(function() {
            	isFirst = true;
                zTree.editName(treeNode);

                $("#zTree2").validator({
                    msgDirection:"right",
                    msgShow:false
                });
            }, 0);

            return false;
        }
        function beforeRemove(treeId, treeNode) {
            className = (className === "dark" ? "":"dark");
            var zTree = $.fn.zTree.getZTreeObj("zTree2");
            zTree.selectNode(treeNode);
            setTimeout(function() {
				$.messager.confirm('确认',"确认删除 节点 -- " + treeNode.name + " 吗？",function(r){
					if(r){
						setTimeout(function() {
							zTree.removeNode(treeNode);
						}, 0);
					}
				});
			}, 0);
			return false;
        }
        function onRemove(e, treeId, treeNode) {
        }
        function beforeRename(treeId, treeNode, newName, isCancel) {

            className = (className === "dark" ? "":"dark");
            var zTree = $.fn.zTree.getZTreeObj("zTree2");
            newName=$.trim(newName);
            if (newName.length == 0) {
                setTimeout(function() {
                	if(isFirst){//只能进一次获取焦点，不然会出现死循环
						$("#zTree2 input").focus().blur();
						isFirst = false;
					}
                }, 0);
                return false;
            }
            if (newName.length >100) {
                alert("节点名称过长.");
                return false;
            }
            return true;
        }
        function onRename(e, treeId, treeNode, isCancel) {
        	var zTree = $.fn.zTree.getZTreeObj("zTree2");
        	treeNode.name = $.trim(treeNode.name);
        	zTree.updateNode(treeNode);
        }
        function showRemoveBtn(treeId, treeNode) {
            if(treeNode.id == -1){
            	return false;
            }
            return true
        }
        function showRenameBtn(treeId, treeNode) {
        	if(treeNode.id == -1){
            	return false;
            }
            return true;
        }

        var newCount = 1;
        function addHoverDom(treeId, treeNode) {
            var sObj = $("#" + treeNode.tId + "_span");
            if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='新增' onfocus='this.blur();'></span>";
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                var zTree = $.fn.zTree.getZTreeObj("zTree2");
                zTree.addNodes(treeNode, {id:uuid(32,16), pId:treeNode.id,drag:true, name:"新节点" + (newCount++)});
                return false;
            });
        };
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        };
        function selectAll() {
            var zTree = $.fn.zTree.getZTreeObj("zTree2");
            zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
        }
		function uuid(len, radix) {
			var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
			var uuid = [], i;
			radix = radix || chars.length;

			if (len) {
				// Compact form
				for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
			} else {
				// rfc4122, version 4 form
				var r;

				// rfc4122 requires these characters
				uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
				uuid[14] = '4';

				// Fill in random data. At i==19 set the high bits of clock sequence as
				// per rfc4122, sec. 4.1.5
				for (i = 0; i < 36; i++) {
					if (!uuid[i]) {
						r = 0 | Math.random()*16;
						uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
					}
				}
			}

			return uuid.join('');
		}

        zTree2 = $.fn.zTree.init($("#zTree2"), setting, zNodes);
        //$("#selectAll").bind("click", selectAll);
    </SCRIPT>
</body>

</html>