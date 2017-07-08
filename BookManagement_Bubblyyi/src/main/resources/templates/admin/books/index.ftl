<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>图书查询</title>
    <meta name="keywords" content="">
    <meta name="description" content="">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${ctx!}/assets/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${ctx!}/assets/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="${ctx!}/assets/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/animate.css" rel="stylesheet">
    <link href="${ctx!}/assets/css/style.css?v=4.1.0" rel="stylesheet">

</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">

        <div class="col-sm-12">
            <div class="ibox float-e-margins">
                <div class="ibox-content">

                    <form class="form-inline">
                        <label class="control-label" for="inputBookName">书名：</label>
                        <input class="form-control" type="text" id="inputBookName"/>
                        <label class="control-label" for="inputPublication">出版社：</label>
                        <input class="form-control" type="text" id="inputPublication"/>
                        <label class="control-label" for="inputAuthor">作者：</label>
                        <input class="form-control" type="text" id="inputAuthor"/>
                        <!--<button type="button" id="search" class="btn" onclick="loadData()">Search</button>-->
                        <button  type="submit" id="search" class="btn btn-warning"><span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询</button>
                    </form>

                </div>

                <div class="ibox-content">
                    <div class="row row-lg">
                        <div class="col-sm-12">
                            <!-- Example Card View -->
                            <div class="example-wrap">
                                <!--提交按钮-->
                                <div class="example">
                                    <button id = "addNewBook" name="addNewBook" type="button" class="btn btn-sm btn-success" onclick="add();">
                                        <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> 添加图书
                                    </button>

                                    <button id = "returnBooks" name="returnBooks" type="button" class="btn btn-sm btn-info" data-toggle="modal" data-target="#returnBookModal">
                                        <span class="glyphicon glyphicon-retweet" aria-hidden="true"></span> 还书
                                    </button>

                                </div>
                                <div class="example">
                                    <table id="table_list"></table>
                                </div>
                                <!--提交按钮-->
                                <div class="example">
                                    <button id = "submit" name = "submit" type="submit" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                                        确认
                                    </button>
                                </div>
                            </div>
                            <!-- End Example Card View -->
                        </div>
                    </div>

                </div>
            </div>
        </div>

    </div>

</div>
<!--还书-->
<div class="modal fade" id="returnBookModal" name = "returnBookModal" style="display: none;" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel" name = "myModalLabel">
                    输入用户ID并进行还书操作
                </h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <form class="form-inline">
                        <label class="control-label" for="inputUserId" name = "inputUserIdLabel">用户id：</label>
                        <input type="text" id="inputUserId" name = "inputUserId"/>
                        <button type="button" id="searchUser" name = "searchUser" class="btn btn-sm btn-warning">
                            <span class="glyphicon glyphicon-search" aria-hidden="true"></span> 查询
                        </button>
                    </form>
                </div>

                <table id = "ReturnBookTable" name = "ReturnBookTable" class="table table-bordered">
                    <th>用户id</th>
                    <th>书名</th>
                    <th>出版社</th>
                    <th>作者</th>
                    <th>借出日期</th>
                    <th>是否超期</th>
                    <th>操作</th>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>

<!-- 全局js -->
<script src="${ctx!}/assets/js/jquery.min.js?v=2.1.4"></script>
<script src="${ctx!}/assets/js/bootstrap.min.js?v=3.3.6"></script>

<!-- Bootstrap table -->
<script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${ctx!}/assets/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${ctx!}/assets/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

<!-- Peity -->
<script src="${ctx!}/assets/js/plugins/peity/jquery.peity.min.js"></script>

<script src="${ctx!}/assets/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="${ctx!}/assets/js/content.js?v=1.0.0"></script>

<script type="text/javascript">

    /*
    * BootstrapTable的初始化，动态从服务器加载数据
    * */
    $(document).ready(function () {

        $("#table_list").bootstrapTable({
            //使用get请求到服务器获取数据
            method: "POST",
            //必须设置，不然request.getParameter获取不到请求参数
            contentType: "application/x-www-form-urlencoded",
            //获取数据的Servlet地址
            url: "${ctx!}/web/books/list",
            //表格显示条纹
            striped: true,
            //启动分页
            pagination: true,
            //每页显示的记录数
            pageSize: 10,
            //当前第几页
            pageNumber: 1,
            //记录数可选列表
            pageList: [5, 10, 15, 20, 25],
            //是否启用查询
            search: false,
            //是否启用详细信息视图
            detailView:true,
            detailFormatter:detailFormatter,
            //表示服务端请求
            sidePagination: "server",
            //设置为undefined可以获取pageNumber，pageSize，searchText，sortName，sortOrder
            //设置为limit可以获取limit, offset, search, sort, order
            queryParamsType: "undefined",
            //json数据解析
            responseHandler: function(res) {
                return {
                    "rows": res.content,
                    "total": res.totalElements
                };
            },
            //数据列
            columns: [{
                title: "书名",
                field: "bookName"
            },{
                title: "作者",
                field: "bookAuthor"
            },{
                title: "出版社",
                field: "bookPress"
            },{
                title: "现有库存",
                field: "currentInventory"

            },{
                title: "操作",
                field: "mOperate",
                formatter: function (value, row, index) {
                    var operateHtml = '<button class = "btn btn-xs btn-primary" onclick="edit(\''+row.bookId+'\')"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 修改</button>';
                    operateHtml = operateHtml +' <button class = "btn btn-xs btn-danger" id = "delete" name = "delete" onclick="deletBook(\''+row.bookId+'\')"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 删除</button>';
                    return operateHtml;
                }
            }]
        });
    });

    /*
    * 添加图书
    * */
    function add(){
        layer.open({
            type: 2,
            title: '图书添加',
            shadeClose: true,
            shade: false,
            area: ['893px', '600px'],
            content: '${ctx!}/web/books/addBook',
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }

    /*
    * 修改图书
    * */
    function edit(id){
        layer.open({
            type: 2,
            title: '图书信息修改',
            shadeClose: true,
            shade: false,
            area: ['893px', '600px'],
            content: '${ctx!}/web/books/edit/' + id,
            end: function(index){
                $('#table_list').bootstrapTable("refresh");
            }
        });
    }
    /*
     * 删除图书
     * */
    function deletBook(id){
        layer.confirm('确定删除吗?', {icon: 3, title:'提示'}, function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx!}/web/books/delete/" + id,
                success: function(msg){
                    layer.msg(msg.message, {time: 2000},function(){
                        $('#table_list').bootstrapTable("refresh");
                        layer.close(index);
                    });
                }
            });
        });
    }

    /*
     * 归还图书时查询借书者id并显示该借书者所借阅的图书
     * */
    $("#searchUser").click(function(){
        //判断页面上是否输入id
        var id=$("#inputUserId").val();
        if(id!=null&&id!="")
        {


            $.post("${ctx!}/web/books/returnBookList/"+id,
                    function(data){
                        //保留表格第一项其他项清除
                        $("#ReturnBookTable  tr:not(:first)").html("");
                        var preData=JSON.parse(data);
                        var obj=JSON.parse(preData);
                        for(var i=0;i<obj.books.length;i++)
                        {
                            //注意月份从0开始
                            var date=new Date();
                            var days=GetDateDiff(obj.borrowBooks[i].updateTime,date.toLocaleDateString());
                            //规定书籍只能借阅30天，要判断一下是否超期
                            var isDelay="否";
                            if(days>30)
                            {

                                isDelay="是";
                            }
                            //动态插入数据
                            var id="#"+obj.books[i].bookId;
                            //每一行的id与图书id绑定这样方便操作
                            var newLine ="<tr id="+obj.books[i].bookId+">";
                            newLine += "<td>"+obj.borrowBooks[i].userId+"</td>";
                            newLine += "<td>"+obj.books[i].bookName+"</td>";
                            newLine += "<td>"+obj.books[i].bookAuthor+"</td>";
                            newLine += "<td>"+obj.books[i].bookPress+"</td>";
                            newLine += "<td>"+obj.borrowBooks[i].updateTime+"</td>";
                            newLine += "<td>"+isDelay+"</td>";
                            newLine += '<td><button name="returnBook" class="btn btn-xs btn-info" onclick="returnBook(\''+obj.borrowBooks[i].userId+'\',\''+obj.borrowBooks[i].bookId+'\')"> <span class="glyphicon glyphicon-retweet" aria-hidden="true"></span> 还书</button></td>';
                            newLine += "</tr>";
                            $("#ReturnBookTable").append(newLine);
                        }
                    });
        }else
        {
            alert("请输入查找id！");

        }
    });

    /*
     * 归还图书
     * */
    function returnBook(userId,bookId) {

        //判断是否传入有效参数
        if(userId!=null&&userId!=""&&bookId!=null&&bookId!="")
        {

            var booklist=new Array();

            booklist.push(bookId);

            //新建borrowlist对象
            var borrowlist= {
                id:userId,
                booklist:booklist
            };

            //转换为json格式
            var mborrowlist=JSON.stringify(borrowlist);

            layer.confirm('确定归还这本图书吗?', {icon: 3, title:'提示'}, function(index){
                $.ajax({
                    type: "POST",
                    dataType: "json",
                    url: "${ctx!}/web/books/returnBook/" + mborrowlist,
                    success: function(msg){
                        //每还一本书就动态将该行删除
                        var id="#"+bookId;
                        $(id).remove();
                        layer.msg(msg.message, {time: 2000},function(){
                            $('#table_list').bootstrapTable("refresh");
                            layer.close(index);
                        });
                    }
                });
            });
        }

    }

    /*
    * 计算两个日期之间所差天数
    * */

    function GetDateDiff(startDate,endDate)
    {
        var startTime = new Date(Date.parse(startDate.replace(/-/g,"/"))).getTime();
        var endTime = new Date(Date.parse(endDate.replace(/-/g,"/"))).getTime();
        var dates = Math.abs((startTime - endTime))/(1000*60*60*24);
        return  dates;
    }

    /*
     * 详细内容格式转换器
     * */
    function detailFormatter(index, row) {
        var html = [];
        html.push('<p><b>描述:</b> ' + row.description + '</p>');
        return html.join('');
    }

</script>

</body>

</html>
