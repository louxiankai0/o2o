/**
 * 1.后台获取到教学楼分类以及区域等信息将它填充到前台html
 * 2.前台表单数据获取转发到后台去注册教学楼
 */
$(function () {
    //初始化url
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    //注册店铺
    var registerShopUrl = '/o2o/shopadmin/registershop';
    // alert(initUrl);
    //js被加载就调用
    getShopInitInfo();
    //获取店铺基本信息
    function getShopInitInfo() {
        $.getJSON(initUrl,function (data) {
            if (data.success){
                var tempHtml = '';
                var tempAreaHtml = '';
                data.shopCategoryList.map(function (item,index) {
                    tempHtml += '<option data-id="'+ item.shopCategoryId + '">'
                    +item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item,index) {
                    tempAreaHtml += '<option data-id= "'+ item.areaId +'">'
                    +item.areaName +'</option>';
                });
                $('#shop-category').html(tempHtml);
                $('#area').html(tempAreaHtml);
            }
        });
        //点击提交的时候获取表单的内容
        $('#submit').click(function () {
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId:$('#shop-category').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId:$('#area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            };
            var shopImg = $('#shop-img')[0].files[0];
            var formData = new FormData();
            formData.append('shopImg',shopImg);
            formData.append('shopStr',JSON.stringify(shop));
            //传验证码
            var verifyCodeActual = $('#j_captcha').val();
            if(!verifyCodeActual){
                $.toast('请输入验证码');
                return;
            }
            formData.append("verifyCodeActual", verifyCodeActual);

            $.ajax({
                url:registerShopUrl,
                type:'POST',
                data:formData,
                contentType:false,
                processData:false,
                cache:false,
                success:function (data) {
                    if (data.success){
                        $.toast('提交成功！');
                    } else {
                        $.toast('提交失败！'+data.errMsg);
                    }
                     $('#captcha_img').click();
                }
            })
        })
    }

})