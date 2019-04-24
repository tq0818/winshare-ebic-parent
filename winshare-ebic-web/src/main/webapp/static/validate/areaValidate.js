// 地区名称简称非空验证
jQuery.validator.addMethod("areaNameNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "地区名称简称不能为空");

// 地区代码非空验证
jQuery.validator.addMethod("areaCodeNotBlank", function(value, element) {
	return this.optional(element) || ($.trim(value) != "");
}, "地区代码不能为空");
