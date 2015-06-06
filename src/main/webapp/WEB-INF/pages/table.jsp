<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Basic CRUD Application - jQuery EasyUI CRUD Demo</title>
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.com/easyui/themes/mobile.css">
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.min.js"></script>
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.mobile.js"></script>
</head>
<body>
         <div class="easyui-navpanel">
        <header>
            <div class="m-toolbar">
                <span class="m-title">Login to System</span>
            </div>
        </header>
        <div style="margin:20px auto;width:100px;height:100px;border-radius:100px;overflow:hidden">
            <img src="images/Koala.jpg" width="180px" style="margin:0">
        </div>
        <div style="padding:0 20px">
            <div style="margin-bottom:10px">
                <input class="easyui-textbox" data-options="prompt:'Type username',iconCls:'icon-man'" style="width:100%;height:38px">
            </div>
            <div>
                <input class="easyui-textbox" type="password" data-options="prompt:'Type password',iconCls:'icon-lock'" style="width:100%;height:38px">
            </div>
            <div style="text-align:center;margin-top:30px">
                <a href="#" class="easyui-linkbutton" style="width:100%;height:40px"><span style="font-size:16px">Login</span></a>
            </div>
            <div style="text-align:center;margin-top:30px">
                <a href="#" class="easyui-linkbutton" plain="true" outline="true" style="width:100px;height:35px"><span style="font-size:16px">Register</span></a> 
            </div>
        </div>
    </div>
</body>
</html>