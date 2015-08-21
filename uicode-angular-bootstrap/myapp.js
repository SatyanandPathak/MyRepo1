
var myapp = angular.module('MyApplication', []);

// Shared service
myapp.factory('myService', function ($http) {
  
  var factory = {            
           query: function () {                
               // Need to call amethod to generate token to share and validate
            },
            getToken: function () {                
               return token;
            }
       }       
        return factory; 
  });



myapp.controller("LoginController", function($scope, $http){
$scope.showLogin = true;
$scope.homepageDisplay = false;
$scope.ajaxResponse = "";
$scope.errorMessage = "";
$scope.displayUserName = $scope.userName;

$scope.submitForm = function(){

	
	// Prepare JSON request for POST
	var requestJSON = {"userName":$scope.userName, "password":$scope.password};	
	console.log(JSON.stringify(requestJSON));	
	
	$http({
        url: 'http://localhost:8080/MyWebApplication/auth/service/authenticateUser',
         method: "POST",
         data: JSON.stringify(requestJSON),
         headers: {'Content-Type': 'application/json'}
    })
    .then(function(response, data) {
            // success			
		if(response.data.status == 'SUCCESS' && response.data.message == 'Credentials matched'){
			$scope.showLogin = false;
			$scope.homepageDisplay = true;				
		} else {
			$scope.errorMessage = response.data.message;
		}				
		$scope.ajaxResponse = response.data;
    }, 
    function(response) { // optional
      // failed
	  $scope.errorMessage = "Services not responding. Please try after sometime";
    });	
}

$scope.registerForm = function(){

	document.getElementById('RegisterDiv').style.display = 'block';
	document.getElementById('LoginDiv').style.display = 'none';

}

$scope.logout = function(){
	document.getElementById('LoginDiv').style.display = 'block';
	$scope.showLogin = true;
	$scope.homepageDisplay = false;
	$scope.userName = "";
	$scope.password = "";

}

}
);

myapp.controller("RegisterController", function($scope, $http){

$scope.showRegisterForm = true;
$scope.showSuccessRegistration = false;
$scope.errorMessage = "";
$scope.ajaxResponse = "";
$scope.passwordMismatch = false;

$scope.submitRegistration = function(){

	
	console.log("submit registration")
	if($scope.newpassword != $scope.newrepassword) {
		$scope.errorMessage = "Passwords do not match. Please check.";	
	} else {
	
	
	var registerJSON = {"newUserName":$scope.newuserName, "newPassword":$scope.newpassword, "newrePassword":$scope.newrepassword, "emailId":$scope.emailId, "firstName":$scope.firstName, "firstName":$scope.lastName};
	
	
	console.log(JSON.stringify(registerJSON));
	$scope.response = "";
	/*$http({
            url: 'http://localhost:8080/MyWebApplication/auth/service/registerUser',
            method: "POST",
            data: JSON.stringify(registerJSON),
            headers: {'Content-Type': 'application/json'}
        }).success(function (data) {                
				$scope.response = data;
            }).error(function (data, status, headers, config) {
               console.log("failure data::"+data.status)
			   $scope.response = data;
    });*/
	
	$http({
        url: 'http://localhost:8080/MyWebApplication/auth/service/registerUser',
         method: "POST",
         data: JSON.stringify(registerJSON),
         headers: {'Content-Type': 'application/json'}
    })
    .then(function(response, data) {
            // success			
			console.log("success status::"+response.status)
			console.log("success message::"+response.data.message)
		if(response.data.status == 'SUCCESS' && response.data.message == 'Registered successfully'){
		console.log("success registration condition")
			$scope.showRegisterForm = false;
			$scope.showSuccessRegistration = true;
			//document.getElementById("SuccesRegistration").style.display = 'block';
			
		} else {
			$scope.errorMessage = response.data.message;
		}				
		$scope.ajaxResponse = response.data;
    }, 
    function(response) { // optional
      // failed
	  $scope.errorMessage = "Services not responding. Please try after sometime";
    });	
	
	}
	
}

$scope.showLoginPage = function(){

document.getElementById('LoginDiv').style.display = 'block';
//document.getElementById('RegisterMain ').style.display = 'none';
$scope.showRegisterForm = false;
$scope.showSuccessRegistration = false;

}

});

