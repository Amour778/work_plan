<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<style>
.title {
	color: #333;
	background-color: #f2f2f2;
	font-size: 14px;
}
</style>
<shiro:hasAnyRoles name="super_admin,admin.default_role,per_user">
<div class="layui-card">
  <div class="layui-card-header">工作全景</div>
  <div class="layui-card-body layui-colla-item">
<div class="layui-row layui-col-space15">
    <div class="layui-col-xs6 layui-col-md3">
        <div class="layui-card">
            <div class="layui-card-header">
                <span class="layui-badge layui-bg-gray">未开始</span>
            </div>
            <div class="layui-card-body">
                <p class="lay-big-font" id="no-1">0</p>
            </div>
        </div>
    </div>
    <div class="layui-col-xs6 layui-col-md3">
        <div class="layui-card">
            <div class="layui-card-header">
                <span class="layui-badge layui-bg-cyan">进行中</span>
            </div>
            <div class="layui-card-body">
                <p class="lay-big-font" id="no0">0</p>
            </div>
        </div>
    </div>
    <div class="layui-col-xs6 layui-col-md3">
        <div class="layui-card">
            <div class="layui-card-header">
                 <span class="layui-badge layui-bg-green pull-right">已完成</span>
            </div>
            <div class="layui-card-body">
                <p class="lay-big-font" id="no1">0</p>
            </div>
        </div>
    </div>
    <div class="layui-col-xs6 layui-col-md3">
        <div class="layui-card">
            <div class="layui-card-header">
                <span class="layui-badge layui-bg-orange pull-right">总数</span>
            </div>
            <div class="layui-card-body">
                <p class="lay-big-font" id="no2">0</p>
            </div>
        </div>
    </div>
</div>
  </div>
</div>
</shiro:hasAnyRoles>



<shiro:hasAnyRoles name="wck_per_user,ruoh,ruoa,ruof,ruod,ruob">
<div class="layui-card">
  <div class="layui-card-header"><b><span class="layui-badge layui-bg-orange">即将到期</span></b></div>
  <div class="layui-card-body layui-colla-item">
	<table id="timeout" lay-filter="wcp_actual_expenditure_table_tool"></table>
	</div>
</div>
<div class="layui-card">
  <div class="layui-card-header"><b><span class="layui-badge">已到期</span></b></div>
  <div class="layui-card-body layui-colla-item">
<table id="already_timeout" lay-filter="wcp_actual_expenditure_table_tool"></table>
	</div>
</div>
</shiro:hasAnyRoles>
<script>
    layui.use(['jquery','carousel', 'element','admin','table'], function () {
        var carousel = layui.carousel;
        var element = layui.element;
        var device = layui.device;
        var admin = layui.admin;
        var $ = layui.jquery;
        var table = layui.table;
        layui.link('assets/css/console.css');
        <shiro:hasRole name="super_admin"> 
			$.post("queryWorkContentToMain", {},function(data) {
				var len=data.info.length;
				for(var a=0;a<len;a++){
					if(data.info[a].state==-1)
						$("#no-1").html(data.info[a].num);
					else if(data.info[a].state==0)
						$("#no0").html(data.info[a].num);
					else if(data.info[a].state==1)
						$("#no1").html(data.info[a].num);
					else if(data.info[a].state==99)
						$("#no2").html(data.info[a].num);
				}
		  	},'json');
  		</shiro:hasRole>
        <shiro:hasAnyRoles name="wck_per_user,ruoh,ruoa,ruof,ruod,ruob">
        $.post("getWCPDReturnedIsComing", {},function(data) {
	        if(data.code==1){
	        	layer.alert(data.info);
	        	return;
	        }
		  table.render({
			    elem: '#timeout'
			    ,height: 312
			    ,data:data.infoOne
			    ,cols: [[
			      {field: 'project_code', title: '项目编码'}
			      ,{field: 'project_name', title: '项目名称'}
			      ,{field: 'project_leader', title: '责任人'}
			      ,{field: 'returned_money_date', title: '应回款日期'} 
			      ,{field: 'returned_money_point', title: '质保金点数'}
			      ,{field: 'project_area_department', title: '地区'}
			    ]]
			  });
		   table.render({
			    elem: '#already_timeout'
			    ,height: 312
			    ,data:data.infoTwo
			    ,cols: [[
			      {field: 'project_code', title: '项目编码'}
			      ,{field: 'project_name', title: '项目名称'}
			      ,{field: 'project_leader', title: '责任人'}
			      ,{field: 'returned_money_date', title: '应回款日期'} 
			      ,{field: 'returned_money_point', title: '质保金点数'}
			      ,{field: 'project_area_department', title: '地区'}
			    ]]
			  });
		  	},'json');
		</shiro:hasAnyRoles>
	    });
</script>
