(function ($) {
	/**
	 * 左右多选框
	 */
	$.fn.multiSelect = function (options){
		var opts = $.extend({}, $.fn.multiSelect.defaults, options);

		this.fill = function () {
			//debugger
			var left = '';
			var leftSelected = '';
			var right = '';
			var rightSelected = '';
			$.each(opts.leftData, function (key, val) {
				var searchValue = val.search;
				if(!searchValue || searchValue == null){
					searchValue = "";
				}
				if(val.selected == true){
					if(opts.leftSingle == true && leftSelected != ''){
						left += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
					}else{
						leftSelected += '<div class="item choosed" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
					}
				}else{
					left += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
				}
			});
			this.find('#leftList').append(leftSelected + left);
			$.each(opts.rightData, function (key, val) {
				var searchValue = val.search;
				if(!searchValue || searchValue == null){
					searchValue = "";
				}
				
				if(val.selected == true){
					if(opts.rightSingle == true && rightSelected != ''){
						right += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
					}else{
						rightSelected += '<div class="item choosed" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
					}
				}else{
					right += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
				}
				
			});
			this.find('#rightList').append(right);
		};
      
		this.controll = function () {
			var $this = this;

			/**
			 * 给选择绑定点击事件
			 */
			$this.find("#leftList .item").on('click', function(){
				if(opts.leftSingle == true){
					$this.find("#leftList .item").each(function(){
						$(this).removeClass('choosed');
					});
					$(this).addClass('choosed');
				}else{
					if($(this).hasClass('choosed')){
						$(this).removeClass('choosed');
					}else{
						$(this).addClass('choosed');
					}
				}
			});
			/**
			 * 给选择绑定点击事件
			 */
			$this.find("#rightList .item").on('click', function(){
				if(opts.rightSingle == true){
					$this.find("#rightList .item").each(function(){
						$(this).removeClass('choosed');
					});
					$(this).addClass('choosed');
				}else{
					if($(this).hasClass('choosed')){
						$(this).removeClass('choosed');
					}else{
						$(this).addClass('choosed');
					}
				}
			});
			
			// 左边搜索
			$this.find("#leftSearch").on('click', function(){
				var searchContent = $this.find("#leftSearchInput").val();
				var options = $this.find("#leftList .item");
				for(var i = 0; i < options.length; i++){
					var p = options[i];
					if(searchContent == null || searchContent == ""){
						$(p).show();
					} else {
						$(p).hide();
						var search = $(p).attr("value-search");
						if(search){
							if(search.indexOf(searchContent) != -1){
								$(p).show();
							}
						}
					}
				}
			});
			// 左边搜索
			$this.find("#leftSearchInput").on('keypress',function(event){ 
				if(event.keyCode == 13) {  
					var searchContent = $this.find("#leftSearchInput").val();
             	 
					var options = $this.find("#leftList .item");
					for(var i = 0; i < options.length; i++){
						var p = options[i];
						if(searchContent == null || searchContent == ""){
							$(p).show();
						} else {
							$(p).hide();
							var search = $(p).attr("value-search");
							if(search){
								if(search.indexOf(searchContent) != -1){
									$(p).show();
								}
							}
						}
					}
				} 
			});
			// 右边搜索
			$this.find("#rightSearch").on('click', function(){
				var searchContent = $this.find("#rightSearchInput").val();
				
				var options = $this.find("#rightList .item");
				for(var i = 0; i < options.length; i++){
					var p = options[i];
					if(searchContent == null || searchContent == ""){
						$(p).show();
					} else {
						$(p).hide();
						var search = $(p).attr("value-search");
						if(search){
							if(search.indexOf(searchContent) != -1){
								$(p).show();
							}
						}
					}
				}
			});
			// 右边搜索
			$this.find("#rightSearchInput").on('keypress',function(event){ 
				if(event.keyCode == 13) {  
					var searchContent = $this.find("#rightSearchInput").val();
					
					var options = $this.find("#rightList .item");
					for(var i = 0; i < options.length; i++){
						var p = options[i];
						if(searchContent == null || searchContent == ""){
							$(p).show();
						} else {
							$(p).hide();
							var search = $(p).attr("value-search");
							if(search){
								if(search.indexOf(searchContent) != -1){
									$(p).show();
								}
							}
						}
					}
				} 
			});
		};

		this.getValues = function () {
			var result = {};
			var left = [];
			var leftSelected = this.find("#leftList .choosed");
			leftSelected.each(function () {
				var id = $(this).attr('value-id');
				var text = $(this).html();
				left.push({
					id: id,
					text: text
				});
			});
			result.leftSelected = left;
			var right = [];
			var rightSelected = this.find("#rightList .choosed");
			rightSelected.each(function () {
				var id = $(this).attr('value-id');
				var text = $(this).html();
				right.push({
					id: id,
					text: text
				});
			});
			result.rightSelected = right;
			return result;
		};
		
		this.init = function () {
			var choiceboxHtml =
				"<div class='row choicebox'>" +
				"	<div class='col-xs-6 leftPanel'>" +
				"		<div class='panel'>" +
				"			<div class='panel-heading choicebox-header-left' >" +
					opts.leftTitle +
				"			</div>" +
				"			<div class='panel-body choicebox-panel'>";
				if(opts.leftSearch){
					choiceboxHtml +=
						"				<div id='leftSearchPanel' class='input-group search-panel'>" +
						"					<input class='col-xs-12 form-control' placeholder='" + opts.leftPlaceholder + "' id='leftSearchInput' maxlength='10' >" +
						"					<div class='input-group-btn'>" +
						"						<button id='leftSearch' type='button' class='btn btn-primary'>查询</button>" +
						"					</div>" +
						"				</div>";
				}
				choiceboxHtml +=
				"				<div id='leftList' class='choicebox-data-list'>" +
				"				</div>" +
				"			</div>" +
				"		</div>" +
				"	</div>" +
				"	<div class='col-xs-6 rightPanel'>" +
				"		<div class='panel'>" +
				"			<div class='panel-heading choicebox-header-right'>" +
					opts.rightTitle +
				"			</div>" +
				"			<div class='panel-body choicebox-panel'>";
				if(opts.rightSearch){
					choiceboxHtml +=
				"				<div id='rightSearchPanel' class='input-group search-panel'>" +
				"					<input class='col-xs-12 form-control' placeholder='" + opts.rightPlaceholder + "' id='rightSearchInput' maxlength='10' >" +
				"					<div class='input-group-btn'>" +
				"						<button id='rightSearch' type='button' class='btn btn-primary'>查询</button>" +
				"					</div>" +
				"				</div>";
				}
				choiceboxHtml +=
				"				<div id='rightList' class='choicebox-data-list'>" +
				"				</div>" +
				"			</div>" +
				"		</div>" +
				"	</div>" +
				"</div>";

			this.html(choiceboxHtml);

			$(".choicebox").height(opts.height);
			
			var leftHeight = $(".choicebox").height();
			if($("#leftSearchPanel").height()){
				leftHeight = leftHeight - $("#leftSearchPanel").height() - 10;
			}
			var rightHeight = $(".choicebox").height();
			if($("#rightSearchPanel").height()){
				rightHeight = rightHeight - $("#rightSearchPanel").height() - 10;
			}
			$("#leftList").css("height", leftHeight);
			$("#rightList").css("height", rightHeight);
			this.fill();
			this.controll();
		};

		this.init();
		return this;
	};
	/**
	 * 左右选择框
	 */
	$.fn.choicebox = function (options) {

		var opts = $.extend({}, $.fn.choicebox.defaults, options);

		this.fill = function () {
			var left = '';
			var right = '';
			$.each(opts.data, function (key, val) {
				var searchValue = val.search;
				if(!searchValue || searchValue == null){
					searchValue = "";
				}
				if(val.selected === true){
					right += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
				}else{
					left += '<div class="item" value-search="' + searchValue + '" value-id="' + val.id + '">' + val.text + '</div>';
				}
			});
			this.find('#leftList').append(left);
			this.find('#rightList').append(right);
		};
      
		this.controll = function () {
			var $this = this;

			/**
			 * 给选择绑定点击事件
			 */
			$this.find(".item").on('click', function(){
				if($(this).hasClass('choosed')){
					$(this).removeClass('choosed');
				}else{
					$(this).addClass('choosed');
				}
			});
			
			// 左边搜索
			$this.find("#leftSearch").on('click', function(){
				var searchContent = $this.find("#leftSearchInput").val();
				var options = $this.find("#leftList .item");
				for(var i = 0; i < options.length; i++){
					var p = options[i];
					if(searchContent == null || searchContent == ""){
						$(p).show();
					} else {
						$(p).hide();
						var search = $(p).attr("value-search");
						if(search){
							if(search.indexOf(searchContent) != -1){
								$(p).show();
							}
						}
					}
				}
			});
			// 左边搜索
			$this.find("#leftSearchInput").on('keypress',function(event){ 
				if(event.keyCode == 13) {  
					var searchContent = $this.find("#leftSearchInput").val();
             	 
					var options = $this.find("#leftList .item");
					for(var i = 0; i < options.length; i++){
						var p = options[i];
						if(searchContent == null || searchContent == ""){
							$(p).show();
						} else {
							$(p).hide();
							var search = $(p).attr("value-search");
							if(search){
								if(search.indexOf(searchContent) != -1){
									$(p).show();
								}
							}
						}
					}
				} 
			});
			// 右边搜索
			$this.find("#rightSearch").on('click', function(){
				var searchContent = $this.find("#rightSearchInput").val();
				
				var options = $this.find("#rightList .item");
				for(var i = 0; i < options.length; i++){
					var p = options[i];
					if(searchContent == null || searchContent == ""){
						$(p).show();
					} else {
						$(p).hide();
						var search = $(p).attr("value-search");
						if(search){
							if(search.indexOf(searchContent) != -1){
								$(p).show();
							}
						}
					}
				}
			});
			// 右边搜索
			$this.find("#rightSearchInput").on('keypress',function(event){ 
				if(event.keyCode == 13) {  
					var searchContent = $this.find("#rightSearchInput").val();
					
					var options = $this.find("#rightList .item");
					for(var i = 0; i < options.length; i++){
						var p = options[i];
						if(searchContent == null || searchContent == ""){
							$(p).show();
						} else {
							$(p).hide();
							var search = $(p).attr("value-search");
							if(search){
								if(search.indexOf(searchContent) != -1){
									$(p).show();
								}
							}
						}
					}
				} 
			});
         
			$this.find(".pAdd").on('click', function () {
				var p = $this.find("#leftList .choosed");
				var cp = p.clone();
				cp.on('click', function(){
					if($(this).hasClass('choosed')){
						$(this).removeClass('choosed');
					}else{
						$(this).addClass('choosed');
					}
				});
				cp.removeClass('choosed');
				cp.appendTo($this.find("#rightList"));
				p.remove();
				/**
				 * 防止在form里点击button后提交表单，需加上return false；
				 */
				return false;
			});

			$this.find(".pAddAll").on('click', function () {
				var options = $this.find("#leftList .item");
				for(var i = 0; i < options.length; i++){
					var p = $(options[i]);
					if(p.is(':visible')){
						var cp = p.clone();
						cp.removeClass('choosed');
						cp.on('click', function(){
							if($(this).hasClass('choosed')){
								$(this).removeClass('choosed');
							}else{
								$(this).addClass('choosed');
							}
						});
						cp.appendTo($this.find("#rightList"));
						p.remove();
					}
				}
				return false;
			});

			$this.find(".pRemove").on('click', function () {
				var p = $this.find("#rightList .choosed");
				var cp = p.clone();
				cp.removeClass('choosed');
				cp.on('click', function(){
					if($(this).hasClass('choosed')){
						$(this).removeClass('choosed');
					}else{
						$(this).addClass('choosed');
					}
				});
				cp.appendTo($this.find("#leftList"));
				p.remove();
				return false;
			});

			$this.find(".pRemoveAll").on('click', function () {
				var options = $this.find("#rightList .item");
				for(var i = 0; i < options.length; i++){
					var p = $(options[i]);
					if(p.is(':visible')){
						var cp = p.clone();
						cp.removeClass('choosed');
						cp.on('click', function(){
							if($(this).hasClass('choosed')){
								$(this).removeClass('choosed');
							}else{
								$(this).addClass('choosed');
							}
						});
						cp.appendTo($this.find("#leftList"));
						p.remove();
					}
				}
				return false;
			});
		};

		this.getValues = function () {
			var right = [];
			var rightList = this.find("#rightList .item");
			rightList.each(function () {
				var id = $(this).attr('value-id');
				var text = $(this).html();
				right.push({
					id: id,
					text: text
				});
			});
			return right
		};
		
		this.init = function () {
			var choiceboxHtml =
				"<div class='row choicebox'>" + 
				"	<div class='col-xs-5 leftPanel'>" + 
				"		<div class='panel'>" +
				"			<div class='panel-heading choicebox-header-left' >" +
					opts.leftTitle +
				"			</div>" +
				"			<div class='panel-body choicebox-panel'>";
				if(opts.leftSearch){
					choiceboxHtml +=
						"				<div id='leftSearchPanel' class='input-group search-panel'>" +
						"					<input class='col-xs-12 form-control' placeholder='" + opts.leftPlaceholder + "' id='leftSearchInput' maxlength='10'>" +
						"					<div class='input-group-btn'>" +
						"						<button id='leftSearch' type='button' class='btn btn-primary'>查询</button>" +
						"					</div>" +
						"				</div>";
				}
				choiceboxHtml +=
				"				<div id='leftList' class='choicebox-data-list'>" +
				"				</div>" +
				"			</div>" +
				"		</div>" +
				"	</div>" +
				"	<div class='col-xs-2 btn-group-vertical choice-select-button' role='group'>" +
                "		<button  class='pAdd btn btn-primary btn-sm'>" + opts.add + "</button>";
				if(opts.multiple){
					choiceboxHtml +=
                "      <button  class='pAddAll btn btn-primary btn-sm'>" + opts.addAll + "</button>";
				}
				choiceboxHtml +=
                "		<button  class='pRemove btn btn-primary btn-sm'>" + opts.remove + "</button>";
				if(opts.multiple){
					choiceboxHtml +=
                "		<button  class='pRemoveAll btn btn-primary btn-sm'>" + opts.removeAll + "</button>";
				}
				choiceboxHtml +=
                " 	</div>" +
				"	<div class='col-xs-5 rightPanel'>" +
				"		<div class='panel'>" +
				"			<div class='panel-heading choicebox-header-right'>" +
					opts.rightTitle +
				"			</div>" +
				"			<div class='panel-body choicebox-panel'>";
				if(opts.rightSearch){
					choiceboxHtml +=
				"				<div id='rightSearchPanel' class='input-group search-panel'>" +
				"					<input class='col-xs-12 form-control' placeholder='" + opts.rightPlaceholder + "' id='rightSearchInput'>" +
				"					<div class='input-group-btn'>" +
				"						<button id='rightSearch' type='button' class='btn btn-primary'>查询</button>" +
				"					</div>" +
				"				</div>";
				}
				choiceboxHtml +=
				"				<div id='rightList' class='choicebox-data-list'>" +
				"				</div>" +
				"			</div>" +
				"		</div>" +
				"	</div>" +
				"</div>";

			this.html(choiceboxHtml);

			$(".choicebox").height(opts.height);
			
			var leftHeight = $(".choicebox").height();
			if($("#leftSearchPanel").height()){
				leftHeight = leftHeight - $("#leftSearchPanel").height() - 10;
			}
			var rightHeight = $(".choicebox").height();
			if($("#rightSearchPanel").height()){
				rightHeight = rightHeight - $("#rightSearchPanel").height() - 10;
			}
			$("#leftList").css("height", leftHeight);
			$("#rightList").css("height", rightHeight);
			this.fill();
			this.controll();
		};

		this.init();
		return this;
	};

	$.fn.choicebox.defaults = {
		leftTitle:'',
		leftPlaceholder: '',
		leftSearch: true,

		rightTitle:'',
		rightSearch: true,
		rightPlaceholder: '',
		server: false,
		multiple: true,
		height: 300,
		url:'',
		add: '>',
		addAll: '>>',
		remove: '<',
		removeAll: '<<'
	};
	
	$.fn.multiSelect.defaults = {
		leftTitle:'',
		leftPlaceholder: '',
		leftSearch: true,
		leftSingle: false,
		
		rightTitle:'',
		rightSearch: true,
		rightPlaceholder: '',
		rightSingle: false,
		server: false,
		height: 300,
		url:''
	};
}(jQuery));
