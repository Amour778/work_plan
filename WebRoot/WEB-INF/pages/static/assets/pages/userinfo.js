
layui.use(['layer', 'form', 'table', 'upload','admin'],
function() {
	layer = layui.layer,
	table = layui.table,
	form = layui.form,
	upload = layui.upload,
	admin = layui.admin,
	$ = layui.jquery;
	//鼠标悬停提示
	$("#upload_file").hover(function() {
		layer.tips('导入的文件需要为.xls格式', '#upload_file',{tips:1});
	}, function() {
		layer.closeAll();
	});
	//执行渲染
	table.render({
		id: 'userinfo_table',
		elem: '#table_userinfo',
		loading: true,
		url: 'get_user',
		where: {"user_id": ""},
		page: true,
		loading: true,
		cols: [[{field: null,type: 'checkbox',sort: true},
		{field: 'user_id',title: '用户工号'},
		{field: 'user_name',title: '用户名称'},
		{field: 'user_tel',title: '联系方式'},
		{field: 'user_area_wechat_edianzu',title: '易点租-所属地区'},
		{field: 'user_wechat',title: '易点租-是否绑定微信', templet: '#sexTpl'},
		{field: 'wcp_area',title: '弱电-所属大区'},
		{field: 'wcp_head',title: '弱电-是否大区负责人'},
		{fixed: 'right',title: '操作',align: 'center',toolbar: '#userinfo_bar',fixed: 'right',width:200}]],
		done: function(res, page, count) {
			if(res.code=="1")
				layer.msg(res.msg);
			curr = page; 
		}
	});
	
	

	table.on('tool(table_userinfo_lay-filter)',function(obj) { //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
		var objdata = obj.data; //获得当前行数据
		var layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
		var tr = obj.tr; //获得当前行 tr 的DOM对象
		if (layEvent === 'del_user') { //删除
			layer.confirm('真的删除此用户么',function(index) {
				$.ajax({
					type: "POST",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url:  "del_user",
					data: {
						userid: objdata.user_id
					},
					beforeSend: function() {
						layer.load(0);
					},
					success: function(data) {
						layer.closeAll();
						var datas = JSON.parse(data);
						if (datas.code == 0) {
							layer.msg("删除成功!", {
								icon: 1
							});
							obj.del();
							layer.close(index);
						} else {
							layer.alert(datas.info, {
								icon: 5
							});
						}
					},
					complete: function() {
						layer.closeAll('loading');
					}
				});
			});
		} else if (layEvent === 'edit_phone') {
			layer.prompt({
				formType: 0,
				title: '请输入新的手机号',
			},
			function(value, index, elem) {
				if(value.length!=11){
				  layer.tips('手机号长度需要11位', ".layui-layer-input");
				  return;
				}
				$.ajax({
					type: "POST",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url: admin.base_server + "edit_phone",
					async: false,
					data: {
						user_id: objdata.user_id,
						user_tel: value
					},
					beforeSend: function() {
						layer.load(0);
					},
					success: function(data) {
						layer.closeAll();
						var data = JSON.parse(data);
						if (data.code == 0) {
							layer.close(index);
							layer.msg(data.info, {
								icon: 1
							});
							 table.reload('userinfo_table', {
								  url: '/get_user'
								  ,method:'post'
								  , where: {
								  user_id: $("#input_value").val()
								  }
								});
						} else {

							layer.msg(data.info, {
								icon: 5
							});
						}
					},
					complete: function() {
						layer.closeAll('loading');
					}
				});
			})
		} else if (layEvent === 'reset_password') {
			layer.confirm('确定重置登录账号[  '+objdata.user_id+'  ]的密码吗',function(index) {
				$.ajax({
					type: "POST",
					contentType: "application/x-www-form-urlencoded; charset=utf-8",
					url: "generatePassword",
					data: {
						user_id:objdata.user_id
					},
					beforeSend: function() {
						layer.load(0);
					},
					success: function(data) {
						layer.closeAll();
						var obj = JSON.parse(data);
						if (obj.code == 0) {
							layer.alert(obj.info, {
								icon: 1
							});
							layer.close(index);
						} else {
							layer.alert(obj.info, {
								icon: 5
							});
						}
					},
					complete: function() {
						layer.closeAll('loading');
					}
				});
			});
		}else if (layEvent === 'edit_user_role'){
			admin.setTableBtnInfo(objdata.user_id);
			admin.popupCenter({
			    title: '修改用户角色',
			    path: 'tpl/edit_user_role',
			    finish: function () {
				table.reload('userinfo_table', {
					  url: '/get_user'
					  ,method:'post'
					  , where: {
					  user_id: $("#input_value").val()
					  }
					});

			    }
			});
			
		}
	});

	
	
	
	$('.layui-btn').on('click',
	function() {
		var othis = $(this),
		method = othis.data('method');
		active[method] ? active[method].call(this, othis) : '';
	});
	var active = {
		get_user: function() {
			var input_value = $("#input_value").val();
			  table.reload('userinfo_table', {
				  url: 'get_user'
				  ,method:'post'
				  , where: {
				  user_id: input_value
				  }
				});
		},
		add_user: function() {
			admin.popupCenter({
				title: '添加用户',
			    path: 'tpl/add_edit_user',
				 end:function () {
						table.reload('userinfo_table', {
							  url: 'get_user'
							  ,method:'post'
							  , where: {
							  user_id: $("#input_value").val()
							  }
							});

					    }
			});
		},
		batch_del_user: function() {
			var checkStatus = table.checkStatus('userinfo_table');
			if (checkStatus.data != '') {
				var data_idList = "";
				for (var a = 0; a < checkStatus.data.length; a++) {
					data_idList += checkStatus.data[a].user_id + ";"
				}
				layer.confirm('确定删除吗？', {
					btn: ['确定', '取消']
				},
				function(index, layero) {
					$.ajax({
						type: "POST",
						contentType: "application/x-www-form-urlencoded; charset=utf-8",
						url:  "batch_del_user",
						data: {
							user_id: data_idList
						},
						beforeSend: function() {
							layer.load(0);
						},
						success: function(data) {
							layer.closeAll();
							var obj = JSON.parse(data);
							if (obj.code == 0) {
								layer.msg(obj.info, {
									icon: 1
								});
								
							} else {
								layer.alert(obj.info, {
									icon: 5
								});
							}
							table.reload('userinfo_table', {
								where: {
									"input_value": $("#input_value").val()
								},
								page: {
									curr: 1
								}
							});
						},
						complete: function() {
							layer.closeAll('loading');
						}
					});
				});
			} else {
				layer.tips('请选择需要删除的行', $('#batch_del_user'), {
					tips: [3, '#5fb878']
				})
			}
		}
	}
    var xhrOnProgress = function (fun) {
        xhrOnProgress.onprogress = fun; //绑定监听
        //使用闭包实现监听绑
        return function () {
            //通过$.ajaxSettings.xhr();获得XMLHttpRequest对象
            var xhr = $.ajaxSettings.xhr();
            //判断监听函数是否为函数
            if (typeof xhrOnProgress.onprogress !== 'function')
                return xhr;
            //如果有监听函数并且xhr对象支持绑定时就把监听函数绑定上去
            if (xhrOnProgress.onprogress && xhr.upload) {
                xhr.upload.onprogress = xhrOnProgress.onprogress;
            }
            return xhr;
        }
    }
    
	/**
	 * 上传按钮
	 */
	upload.render({
		elem: '#batch_add_user',
		url: 'doUploadUserinfo',
		accept: 'file',
		exts: 'xls|xlsx',
		before: function(obj) {
			layer.load(); //上传loading
		},
		done: function(res, index, upload) {
			layer.closeAll('loading'); //关闭loading
			if (res.code == 0) {
				layer.msg(res.info, {
					icon: 1
				});
				  table.reload('userinfo_table', {
					  url: 'get_user'
					  ,method:'post'
					  , where: {
					  user_id: ""
					  }
					});
			} else{
				layer.alert("存在错误，错误如下:<br/>" + res.info);
				}
		},
		error: function(index, upload) {
			layer.closeAll('loading'); //关闭loading
		}
	});
});