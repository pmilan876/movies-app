var onLoginSuccess=function(loginResponse){
    console.log(loginResponse);
    localStorage.setItem("X-USER",loginResponse["userName"]);
    localStorage.setItem("X-TOKEN",loginResponse["token"]);
    localStorage.setItem("X-ROLE",loginResponse["role"]);
    appInit();
}

$( document ).on( "click", "#loginFormLoginBtn",function() {
    var userName=$("#loginFormUserName").val();
    var password=$("#loginFormPassword").val();
    var loginRequest={
        "userName":userName,
        "password":password
    }
    doPost(url+"authenticate",loginRequest,onLoginSuccess,failureHandler);
       $("#loginFormPassword").val("");
});

var onLogoutSuccess=function(logoutResponse){
    localStorage.removeItem("X-USER")//,loginResponse["userName"]);
    localStorage.removeItem("X-TOKEN")//,loginResponse["token"]);
  //  localStorage.removeItem("X-ROLE",loginResponse["role"]);
    appInit();
}

$( document ).on( "click", "#logoutFormBtn",function() {

    doGet(url+"logout",onLogoutSuccess,failureHandler);
});
