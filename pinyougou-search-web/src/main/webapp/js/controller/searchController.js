app.controller('searchController', function ($scope, $location, searchService) {

  //定义搜索对象的结构  category:商品分类
  $scope.searchMap = {
    'keywords': '',
    'category': '',
    'brand': '',
    'spec': {},
    'price': '',
    'pageNo': 1,
    'pageSize': 40,
    'sort': '',
    'sortField': ''
  };

  //搜索
  $scope.search = function () {
    //转换为数字
    $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo);
    searchService.search($scope.searchMap).success(
      function (response) {
        $scope.resultMap = response;

        //构建分页栏
        buildPageLabel();
      }
    );
  };

  //构建分页栏
  var buildPageLabel = function () {
    //构建分页栏
    $scope.pageLabel = [];
    //开始页码
    var firstPage = 1;
    //截止页码
    var lastPage = $scope.resultMap.totalPages;
    //前面有点
    $scope.firstDot = true;
    //后边有点
    $scope.lastDot = true;
    //如果页码数量大于5
    if ($scope.resultMap.totalPages > 5) {
      //如果当前页码小于等于3 ，显示前5页
      if ($scope.searchMap.pageNo <= 3) {
        lastPage = 5;
        //前面没点
        $scope.firstDot = false;
      } else if ($scope.searchMap.pageNo >= $scope.resultMap.totalPages - 2) {
        //显示后5页
        firstPage = $scope.resultMap.totalPages - 4;
        //后边没点
        $scope.lastDot = false;
      } else {
        //显示以当前页为中心的5页
        firstPage = $scope.searchMap.pageNo - 2;
        lastPage = $scope.searchMap.pageNo + 2;
      }
    } else {
      //前面无点
      $scope.firstDot = false;
      //后边无点
      $scope.lastDot = false;
    }

    //构建页码
    for (var i = firstPage; i <= lastPage; i++) {
      $scope.pageLabel.push(i);
    }
  };

  //添加搜索项  改变searchMap的值
  $scope.addSearchItem = function (key, value) {
    //如果用户点击的是分类或品牌
    if (key === 'category' || key === 'brand' || key === 'price') {
      $scope.searchMap[key] = value;
    } else {
      //用户点击的是规格
      $scope.searchMap.spec[key] = value;
    }
    //查询
    $scope.search();
  };

  //撤销搜索项
  $scope.removeSearchItem = function (key) {
    //如果用户点击的是分类或品牌
    if (key === 'category' || key === 'brand' || key === 'price') {
      $scope.searchMap[key] = "";
    } else {
      //用户点击的是规格
      delete $scope.searchMap.spec[key];
    }
    //查询
    $scope.search();
  };

  //分页查询
  $scope.queryByPage = function (pageNo) {
    if (pageNo < 1 || pageNo > $scope.resultMap.totalPages) {
      return;
    }
    $scope.searchMap.pageNo = pageNo;
    //查询
    $scope.search();
  };

  //判断当前页是否为第一页
  $scope.isTopPage = function () {
    return $scope.searchMap.pageNo === 1;
  };

  //判断当前页是否为最后一页
  $scope.isEndPage = function () {
    return $scope.searchMap.pageNo === $scope.resultMap.totalPages;
  };

  //排序查询
  $scope.sortSearch = function (sortField, sort) {
    $scope.searchMap.sortField = sortField;
    $scope.searchMap.sort = sort;

    //查询
    $scope.search();
  };

  //判断关键字是否是品牌
  $scope.keywordsIsBrand = function () {
    for (var i = 0; i < $scope.resultMap.brandList.length; i++) {
      if ($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text) >= 0) {
        return true;
      }
    }
    return false;
  };

  //加载关键字
  $scope.loadKeyWords = function () {
    $scope.searchMap.keywords = $location.search()['keywords'];
    //查询
    $scope.search();
  };

});