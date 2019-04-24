// 机构名称非空验证
jQuery.validator.addMethod("orgNameNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "机构名称不能为空");

// 机构代码非空验证
jQuery.validator.addMethod("orgCodeNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "机构代码不能为空");

// 机构标识非空验证
jQuery.validator.addMethod("orgFlagNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "机构标识不能为空");

// 机构标识中文字符验证
jQuery.validator.addMethod("orgFlagChinese", function(value, element) {
	return this.optional(element) || (!(/[\u4e00-\u9fa5]/.test($.trim(value))));
}, "机构标识不能含有中文字符");
