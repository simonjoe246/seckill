// 存放主要的交互逻辑
// javascript 模块化
//seckill.detail.init(params)
var seckill = {
    // 封装秒杀相关 ajax 的 url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer';
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution';
        }
    },
    // 验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        }
        else {
            return false;
        }
    },
    // 获取秒杀地址，控制显示逻辑，执行秒杀
    handlerSeckill: function(seckillId, node) {
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');

        $.get(seckill.URL.exposer(seckillId), {}, function (result) {
            if (result && result['success']) {
                var exposer = result['data'];
                if (exposer['exposed']) {
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId, md5);
                    console.log('killUrl: ' + killUrl);
                    // 使用 one 可以保证用户只能点击一次，避免服务器接受大量重复请求, 使用 click 为重复绑定
                    $('#killBtn').one('click', function () {
                        // 1. 点击后控制按钮变灰
                        $(this).addClass('disabled');
                        // 2. 发送秒杀请求执行秒杀
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                // 显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                            else {
                                console.log('success: ' + result['success'] + ' error: ' + result['error']);
                            }
                        });
                    });
                    node.show();
                }
                // 未开启秒杀(浏览器计时偏差)
                else {
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    seckill.countDown(seckillId, now, start, end);
                }
            }
            else {
                console.log('result: ' + result);
            }
        });
    },
    // 倒计时
    countDown: function (seckillId, nowTime, startTime, endTime) {
        console.log(seckillId + '_' + nowTime + '_' + startTime + '_' + endTime);
        var seckillBox = $('#seckill-box');
        // 秒杀结束
        if (nowTime > endTime) {
            seckillBox.html('秒杀结束');
        }
        // 秒杀未开始，计时事件绑定
        else if (nowTime < startTime) {
            var killTime = new Date(startTime + 1000); // 防止时间偏移
            seckillBox.countdown(killTime, function (event) {
                // 时间格式
                var format = event.strftime('秒杀倒计时：%D天 %H时 %M分 %S秒 ');
                seckillBox.html(format);
            }).on('finish countdown', function () {
                console.log('______finish countdown');
                seckill.handlerSeckill(seckillId, seckillBox);
            });
        }
        // 秒杀已经开始
        else {
            seckill.handlerSeckill(seckillId, seckillBox);
        }
    },
    // 详情页秒杀逻辑
    detail: {
        // 详情页初始化
        init: function (params) {
            // 用户手机验证和登录，计时交互
            // 规划交互流程
            // 验证手机号流程：在 cookie 中查找手机号，如果未查到(手机号无效)，则弹出 modal 层提示填写，
            // 填写错误(手机号无效)则提示，填写正确则写入 cookie，刷新页面
            var killPhone = $.cookie("userPhone");
            // cookie 中未查到，则用户未登录
            if (!seckill.validatePhone(killPhone)) {
                var killPhoneModal = $('#killPhoneModal');
                killPhoneModal.modal({
                    show:true, // 弹出显示层，默认为 fade 隐藏状态
                    backdrop: 'static', // 不允许关闭
                    keyboard: false // 关闭键盘事件
                });

                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val();
                    // 若填入的电话无效，显示警告信息
                    if (!seckill.validatePhone(inputPhone)) {
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                    }
                    // 填入电话正确，则写入 cookie (7 天过期，只在 seckill 页面下有效)，刷新页面
                    else {
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'});
                        window.location.reload();
                    }
                });
            }
            // 从 cookie 获取到正确手机号，则表明已登录
            // 计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            // ajax 请求服务器时间，第二个参数为请求参数
            $.get(seckill.URL.now(), {}, function (result) {
                if (result && result['success']) {
                    var nowTime = result['data'];
                    // 时间判断，计时交互
                    seckill.countDown(seckillId, nowTime, startTime, endTime);
                }
                else {
                    console.log('result' + result);
                    alert('result' + result);
                }
            });
        }
    }
}
