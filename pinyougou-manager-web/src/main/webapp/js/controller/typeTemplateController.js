//控制层
app.controller('typeTemplateController', function ($scope, $controller, typeTemplateService, brandService, specificationService) {

  $controller('baseController', {$scope: $scope});//继承

  //读取列表数据绑定到表单中
  $scope.findAll = function () {
    typeTemplateService.findAll().success(
      function (response) {
        $scope.list = response;
      }
    );
  };

  //分页
  $scope.findPage = function (page, rows) {
    typeTemplateService.findPage(page, rows).success(
      function (response) {
        $scope.list = response.rows;
        $scope.paginationConf.totalItems = response.total;//更新总记录数
      }
    );
  };

  //查询实体
  $scope.findOne = function (id) {
    typeTemplateService.findOne(id).success(
      function (response) {
        $scope.entity = response;

        //转换字符串为json对象（集合）
        $scope.entity.brandIds = JSON.parse($scope.entity.brandIds);
        $scope.entity.specIds = JSON.parse($scope.entity.specIds);
        $scope.entity.customAttributeItems = JSON.parse($scope.entity.customAttributeItems);
      }
    );
  };

  //保存
  $scope.save = function () {
    var serviceObject;//服务层对象
    if ($scope.entity.id != null) {//如果有ID
      serviceObject = typeTemplateService.update($scope.entity); //修改
    } else {
      serviceObject = typeTemplateService.add($scope.entity);//增加
    }
    serviceObject.success(
      function (response) {
        if (response.success) {
          //重新查询
          $scope.reloadList();//重新加载
        } else {
          alert(response.message);
        }
      }
    );
  };


  //批量删除
  $scope.delete = function () {
    if ($scope.selectIds.length === 0) {
      alert("请勾选需要删除的数据！");
      return;
    }
    //获取选中的复选框
    typeTemplateService.delete($scope.selectIds).success(
      function (response) {
        if (response.success) {
          $scope.reloadList();//刷新列表
        } else {
          alert(response.message);
        }
      }
    );
  };

  $scope.searchEntity = {};//定义搜索对象

  //搜索
  $scope.search = function (page, rows) {
    typeTemplateService.search(page, rows, $scope.searchEntity).success(
      function (response) {
        $scope.list = response.rows;
        $scope.paginationConf.totalItems = response.total;//更新总记录数
        $scope.select_all = false;//切换分页时取消全选按钮的选中状态并清空被选列表
        $scope.selectIds = [];
      }
    );
  };

  $scope.brandList = {data: []};//品牌列表

  //读取品牌列表
  $scope.findBrandList = function () {
    brandService.selectOptionList().success(function (response) {
        $scope.brandList = {data: response};
      }
    );
  };

  $scope.specList = {data: []};//规格列表

  //读取规格列表
  $scope.findSpecList = function () {
    specificationService.selectOptionList().success(function (response) {
        $scope.specList = {data: response};
      }
    );
  };

  $scope.init = function () {
    $scope.entity = {brandList: {}, specList: {}, customAttributeItems: []};
  };

  //增加扩展属性行
  $scope.addTableRow = function () {
    $scope.entity.customAttributeItems.push({});
  };

  //删除扩展属性行
  $scope.deleteTableRow = function (index) {
    $scope.entity.customAttributeItems.splice(index, 1);
  };

  //全选
  $scope.selectAll = function () {
    if (!$scope.select_all) {
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
