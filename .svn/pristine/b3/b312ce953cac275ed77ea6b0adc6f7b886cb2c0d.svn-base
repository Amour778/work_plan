<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<div class="layui-card">
	    <div class="layui-card-header">
	        <h2 class="header-title">用户管理</h2>
	        <span class="layui-breadcrumb pull-right">
	          <a href="#!home_console">首页</a>
	          <a><cite>用户管理</cite></a>
	        </span>
	    </div>
	    <div class="layui-card-body">
	        <div class="layui-form toolbar">
	        
	    <shiro:hasPermission name="get_user">
	    <div class="layui-form layui-form-pane">
<div class="layui-inline">
			<label class="layui-form-label">
				用户工号
			</label>
			<div class="layui-input-inline" style="width: 200px;">
				<input type="text" name="item_name" autocomplete="off"
					id="input_value" class="layui-input" placeholder="">
			</div>
			
	</shiro:hasPermission>
	<div class="layui-btn-group">
		<shiro:hasPermission name="get_user">  
		<button class="layui-btn layui-btn-normal" data-method="get_user"><i class="layui-icon layui-icon-search"></i>搜索</button>
		</shiro:hasPermission>
		<shiro:hasPermission name="add_user">  
		<button class="layui-btn layui-btn-normal" data-method="add_user" id="addBtn"><i class="layui-icon layui-icon-add-1"></i>添加</button>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="batch_add_user">  
		<button class="layui-btn layui-btn-normal" lay-tips="导入的文件需要为.xls|.xlsx格式" data-method="batch_add_user" id="batch_add_user"><i class="layui-icon layui-icon-upload-drag"></i>导入数据</button>
		</shiro:hasPermission>
		
		<shiro:hasPermission name="batch_del_user">  
		<button class="layui-btn layui-btn-danger" data-method="batch_del_user" id="batch_del_user"><i class="layui-icon layui-icon-delete"></i>批量删除</button>
		</shiro:hasPermission>
	</div>
		</div>
	</div>
		</div>
<table id="table_userinfo" lay-filter="table_userinfo_lay-filter" cellspacing="0" cellpadding="0" border="0" class="layui-table"></table>
</div>
</div>
<script type="text/html" id="sexTpl">
  {{#  if(d.user_wechat === ''){ }}
    <span style="color: #F581B1;">未绑定</span>
  {{#  } else { }}
     <span style="color: green;" >已绑定</span>
  {{#  } }}
</script>
<!-- 表格 -->
<script type="text/javascript" language="javascript" src="assets/pages/userinfo.js"></script>
<script type="text/javascript" language="javascript" src="module/pace/pace.min.js"></script>
<script type="text/html" id="userinfo_bar">
		<shiro:hasPermission name="edit_phone">  
  <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="edit_phone" lay-tips="修改手机号"><i class="layui-icon layui-icon-cellphone"></i></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="reset_password">  
  <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="reset_password" lay-tips="重置密码"><i class="layui-icon layui-icon-refresh"></i></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="tpl/edit_user_role">  
  <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="edit_user_role" lay-tips="用户角色"><i class="layui-icon layui-icon-group"></i></a>
		</shiro:hasPermission>
		<shiro:hasPermission name="del_user">  
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del_user" lay-tips="删除"><i class="layui-icon layui-icon-delete"></i></a>
		</shiro:hasPermission>

</script>