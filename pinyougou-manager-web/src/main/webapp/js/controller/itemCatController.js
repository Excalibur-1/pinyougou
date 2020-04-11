//控制层
app.controller('itemCatController', function ($scope, $controller, itemCatService, typeTemplateService) {

  $controller('baseController', {$scope: $scope});//继承

  //读取列表数据绑定到表单中
  $scope.findAll = function () {
    itemCatService.findAll().success(
      function (response) {
        $scope.list = response;
      }
    );
  };

  //分页
  $scope.findPage = function (page, rows) {
    itemCatService.findPage(page, rows).success(
      function (response) {
        $scope.list = response.rows;
        $scope.paginationConf.totalItems = response.total;//更新总记录数
      }
    );
  };

  //查询实体
  $scope.findOne = function (id) {
    itemCatService.findOne(id).success(
      function (response) {
        $scope.entity = response;
        $scope.entity.typeTemplate = {'id': $scope.entity.typeId, 'text': $scope.entity.typeTemplateName};
      }
    );
  };

  $scope.parentId = 0;

  //保存
  $scope.save = function () {
    var serviceObject;//服务层对象
    $scope.entity.typeId = $scope.entity.typeTemplate.id;
    $scope.entity.parentId = $scope.parentId;
    if ($scope.entity.id != null) {//如果有ID
      serviceObject = itemCatService.update($scope.entity); //修改
    } else {
      serviceObject = itemCatService.add($scope.entity);//增加
    }
    serviceObject.success(
      function (response) {
        if (response.success) {
          //重新查询
          $scope.findByParentId($scope.parentId);//重新加载
        } else {
          alert(response.message);
        }
      }
    );
  };

  //批量删除
  $scope.dele = function () {
    if ($scope.selectIds.length === 0) {
      alert("请勾选需要删除的数据！");
      return ;
    }
    //获取选中的复选框
    itemCatService.dele($scope.selectIds).success(
      function (response) {
        if (response.success) {
          $scope.findByParentId($scope.parentId);//刷新列表
          $scope.selectIds = [];
        } else {
          alert(response.message);
        }
      }
    );
  };

  $scope.searchEntity = {};//定义搜索对象

  //搜索
  $scope.search = function (page, rows) {
    itemCatService.search(page, rows, $scope.searchEntity).success(
      function (response) {
        $scope.list = response.rows;
        $scope.paginationConf.totalItems = response.total;//更新总记录数
      }
    );
  };

  // 根据父ID查询分类
  $scope.findByParentId = function (parentId) {
    itemCatService.findByParentId(parentId).success(function (response) {
      $scope.list = response;
      $scope.parentId = parentId;
      $scope.select_all = false;//切换分页时取消全选按钮的选中状态并清空被选列表
      $scope.selectIds = [];
    });
  };

  // 定义一个变量记录当前是第几级分类
  $scope.grade = 1;

  //设置当前分类等级
  $scope.setGrade = function (value) {
    $scope.grade = value;
  };

  //根据分类级别查询分类
  $scope.selectList = function (p_entity) {
    if ($scope.grade === 1) {
      $scope.entity_1 = null;
      $scope.entity_2 = null;
    }
    if ($scope.grade === 2) {
      $scope.entity_1 = p_entity;
      $scope.entity_2 = null;
    }
    if ($scope.grade === 3) {
      $scope.entity_2 = p_entity;
    }
    $scope.findByParentId(p_entity.id);
  };

  $scope.typeTemplateList = {data: []};//规格列表

  $scope.init = function () {
    $scope.entity = {typeTemplateList: {}};
  };

  //读取规格列表
  $scope.findTypeTemplateList = function () {
    typeTemplateService.selectOptionList().success(function (response) {
        $scope.typeTemplateList = {data: response};
      }
    );
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
