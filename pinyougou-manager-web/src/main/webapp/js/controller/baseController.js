app.controller("baseController", function ($scope) {

  //分页控件配置 currentPage:当前页,totalItems:总记录数,itemsPerPage:每页记录数,perPageOptions:分页选项,onChange:当页码变更后自动触发的方法
  $scope.paginationConf = {
    currentPage: 1,
    totalItems: 10,
    itemsPerPage: 5,
    perPageOptions: [10, 20, 30, 40, 50],
    onChange: function () {
      $scope.reloadList();//重新加载
    }
  };

  //刷新列表
  $scope.reloadList = function () {
    $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
  };

  //用户勾选的id集合
  $scope.selectIds = [];

  //根据用户勾选复选框操作类型，更新id集合元素
  $scope.updateSelection = function ($event, id) {
    if ($event.target.checked) {//判断为勾选
      $scope.selectIds.push(id);//向集合添加元素
      if ($scope.selectIds.length === $scope.list.length) {
        $scope.select_all = true;
      }
    } else {//取消勾选
      var index = $scope.selectIds.indexOf(id);//查找id在集合中的位置
      $scope.selectIds.splice(index, 1);//index为参数的位置，1表示移除的个数
      if ($scope.selectIds.length < $scope.list.length) {
        $scope.select_all = false;
      }
    }
  };

  //提取json字符串数据中某个属性，返回拼接字符串 逗号分隔
  $scope.jsonToString = function (jsonString, key) {
    var json = JSON.parse(jsonString);//将json字符串转换为json对象
    var value = "";
    for (var i = 0; i < json.length; i++) {
      if (i > 0) {
        value += "，"
      }
      value += json[i][key];
    }
    return value;
  };

  // 从集合中查询某个名称的值是否存在
  $scope.searchObjectByKey = function (list, keyName, keyValue) {
    for (var i = 0; i < list.length; i++) {
      if (list[i][keyName] === keyValue) {
        return list[i];
      }
    }
    return null;
  }

});