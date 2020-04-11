//品牌控制器
app.controller('brandController', function ($scope, $controller, brandService) {

  $controller('baseController', {$scope:$scope});

  //查询所有数据
  $scope.findAll = function () {
    brandService.findAll().success(function (response) {
        $scope.list = response;
      }
    );
  };

  //分页查询
  $scope.findPage = function (page, size) {
    brandService.findPage(page, size).success(function (response) {
      $scope.list = response.rows;//显示当前页的数据
      $scope.paginationConf.totalItems = response.total;//更新总记录数
    });
  };

  //查询单个实体
  $scope.findOne = function (id) {
    brandService.findOne(id).success(function (response) {
      $scope.entity = response;
    });
  };

  //删除
  $scope.delete = function () {
    if ($scope.selectIds.length === 0) {
      alert("请勾选需要删除的数据！");
      return ;
    }
    brandService.delete($scope.selectIds).success(function (response) {
      if (response.success) {
        $scope.reloadList();//删除成功，刷新列表
      } else {
        alert(response.message);
      }
    });
  };

  //初始化搜索条件
  $scope.searchEntity = {};

  //条件查询
  $scope.search = function (page, size) {
    brandService.search(page, size, $scope.searchEntity).success(function (response) {
      $scope.list = response.rows;//显示当前页的数据
      $scope.paginationConf.totalItems = response.total;//更新总记录数
      $scope.select_all = false;//切换分页时取消全选按钮的选中状态并清空被选列表
      $scope.selectIds = [];
    });
  };

  //保存（新增和修改）
  $scope.save = function () {
    var object = null;//定义方法对象
    if ($scope.entity.id != null) {
      object = brandService.update($scope.entity);
    } else {
      object = brandService.add($scope.entity);
    }
    object.success(function (response) {
      if (response.success) {
        $scope.reloadList();//新增成功，刷新列表
      } else {
        alert(response.message);
      }
    });
  };

  //全选
  $scope.selectAll = function() {
    if(!$scope.select_all) {
      $scope.selectIds = [];
      angular.forEach($scope.list, function (i) {
        i.checked = true;
        $scope.selectIds.push(i.id);
      });
    } else {
      angular.forEach($scope.list, function (i) {
        i.checked = false;
        $scope.selectIds = [];
      })
    }
  };

  //点击行选中
  $scope.rowClick = function ($event, id) {
    if ($event.target.type !== "checkbox" && $event.target.type !== "button") {
      angular.forEach($scope.list, function (i) {
        if (i.id === id) {
          if (i.checked === true) {
            i.checked = false;
            var index = $scope.selectIds.indexOf(id);//查找id在集合中的位置
            $scope.selectIds.splice(index, 1);//index为参数的位置，1表示移除的个数
            if ($scope.selectIds.length < $scope.list.length) {
              $scope.select_all = false;
            }
          } else {
            i.checked = true;
            $scope.selectIds.push(id);//向集合添加元素
            if ($scope.selectIds.length === $scope.list.length) {
              $scope.select_all = true;
            }
          }
        }
      });
    }
  };

});