// 字典名称非空验证
jQuery.validator.addMethod("dicNameNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "字典名称不能为空");

// 字典编码非空验证
jQuery.validator.addMethod("dicCodeNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "字典编码不能为空");
