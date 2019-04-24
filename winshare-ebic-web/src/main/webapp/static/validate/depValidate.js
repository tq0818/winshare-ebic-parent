// 部门名称非空验证
jQuery.validator.addMethod("depNameNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "部门名称不能为空");
