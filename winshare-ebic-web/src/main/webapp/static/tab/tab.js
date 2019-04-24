(function($) {

    var methods = {
        init: function(conf) {
            var me = this;
            var dfconf = {
                tabList: [
                    { name: '全部', code: '1' },
                    { name: '月考试卷', code: '2' },
                    { name: '期中试卷', code: '3' },
                    { name: '中考试卷', code: '4' },
                    { name: '竞赛试卷', code: '5' },
                    { name: '单元测试', code: '6' },
                    { name: '入学测试', code: '7' },
                    { name: '小升初试卷', code: '8' },
                    { name: '中考试卷', code: '9' },
                    { name: '综合试卷', code: '10' },
                    { name: '高考试卷', code: '11' }
                ],
                onClick: function(data, dom) {
                    console.log(data);
                    // console.log(evt);
                    console.log(dom);
                }
            }
            var me = this;
            var compCfg = $.extend(dfconf, conf);
            me.data('compCfg', compCfg);
            var map = compCfg.map;

            var htmlStr = '<div class="tab_cntr"><div class="tab_list"><ul></ul></div><div class="tab_controller"><div class="tab_left_btn"></div><div class="tab_right_btn"></div></div></div>';

            me.append(htmlStr);

            me.find('ul').on('click', function(e) {
                me.tab('checkTabByDom', e.target, true);
            });

            me.tab('setData', compCfg.tabList);
            me.tab('checkTabByIndex', 0, false);

            //绑定控制按钮事件
            me.find('.tab_left_btn').on('click', function() {
                if ($(this).hasClass('tab_btn_active')) {
                    me.tab('scroll', -1);
                }

            });
            me.find('.tab_right_btn').on('click', function() {
                if ($(this).hasClass('tab_btn_active')) {
                    me.tab('scroll', 1);
                }
            })
        },
        setData: function(data) {
            var me = this;
            var compCfg = me.data('compCfg');

            for (var i = 0; i < data.length; i++) {
                var htmlTemp = $('<li>' + data[i].name + '</li>');
                me.find('ul').append(htmlTemp);
                htmlTemp.data('data', data[i]);
            }
            //获取tab的总宽度/初始化右侧控制按钮
            var lastLi = me.find('li').last();
            var ul = me.find('ul');
            compCfg.tabWidth = ul.width();
            ul.css('margin-left', 0);
            if (compCfg.tabWidth > me.find('.tab_list').width()) {
                me.find('.tab_controller').show();
                me.find('.tab_right_btn').addClass('tab_btn_active');
            } else {
                me.find('.tab_controller').hide();
            }
            me.data('compCfg', compCfg);


        },
        checkTabByIndex: function(index, evtFlag) {
            var me = this;
            var compCfg = me.data('compCfg');
            var dom = me.find('li').eq(index);
            me.tab('checkTabByDom', dom, evtFlag);
        },
        checkTabByPrama: function(name, val, evtFlag) {
            var me = this;
            var compCfg = me.data('compCfg');

            for (i = 0; i < compCfg.tabList.length; i++) {
                if (compCfg.tabList[i][name] && compCfg.tabList[i][name] == val) {
                    me.tab('checkTabByIndex', i, evtFlag);
                    return;
                }
            }
        },
        checkTabByDom: function(dom, evtFlag) {
            var me = this;
            var compCfg = me.data('compCfg');
            dom = $(dom);
            me.find('li').removeClass('active');
            dom.addClass('active');

            if (evtFlag) {
                var data = dom.data('data');
                compCfg.onClick(data, dom);
            }
        },
        scroll: function(dirction) {
            var me = this;
            var compCfg = me.data('compCfg');
            var ul = me.find('ul');
            var viewWidth = me.find('.tab_list').width();
            var curLeft = parseInt(ul.css('left'));

            if (dirction == 1) {
                curLeft = curLeft - viewWidth;
                me.find('.tab_left_btn').addClass('tab_btn_active');
            } else if (dirction == -1) {
                curLeft = curLeft + viewWidth;
                me.find('.tab_right_btn').addClass('tab_btn_active');
            }
            ul.css('left', curLeft);
            // debugger;
            me.find('.tab_controller div').removeClass('tab_btn_active');

            setTimeout(function() {

                me.find('.tab_controller div').addClass('tab_btn_active');

                if (curLeft == 0) {
                    me.find('.tab_left_btn').removeClass('tab_btn_active');
                }
                if ((-curLeft + viewWidth) >= compCfg.tabWidth) {
                    me.find('.tab_right_btn').removeClass('tab_btn_active');
                }
            }, 310);

        }
    }

    $.fn.tab = function(conf) {
        var method = arguments[0];
        if (methods[method]) {
            method = methods[method];
            arguments = Array.prototype.slice.call(arguments, 1);
        } else if (typeof(method) == 'object' || !method) {
            method = methods.init;
        } else {
            $.error('Method ' + method + ' does not exist on jQuery.fileItem');
            return this;
        }

        return method.apply(this, arguments);
    }
})(jQuery)