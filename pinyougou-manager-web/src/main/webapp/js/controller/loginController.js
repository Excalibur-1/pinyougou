app.controller('loginController', function ($scope, $interval, loginService) {

  //显示当前用户名
  $scope.showLoginName = function () {
    loginService.loginName().success(function (response) {
      $scope.loginName = response.loginName;
    });
  };

  //获取系统当前时间，自动计时
  $interval(function () {
    $scope.date = new Date().toLocaleTimeString();
  }, 1000, 100);

});