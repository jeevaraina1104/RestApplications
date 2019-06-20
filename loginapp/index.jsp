<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login Page</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" id="loginForm" >
            <table>
                <tr bgcolor="black">                
                    <th colspan="3"><font color="white">Enter login details</th>

                </tr>
                <tr height="20"></tr>

                <tr>
                    <td>User Name</td>
                    <td>:</td>
                    <td>
                        <input type="text" id="username" name="username" required/>
                    </td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td>:</td>
                    <td>
                        <input type="password" id="password" name="password" required/>
                    </td>
                </tr>
				<tr>
                    <td>
                       <a href="signup.html">Create a New Account</a>
                    </td>
                </tr>

                <tr height="10"></tr>
                <tr>
                    <td></td>
                    <td></td>
                    <td align="center">
					 <input type="button" value="Submit" onclick="LoginForm()" />
                </tr>
            </table>
        </form>
         <script lang="javascript">
            function LoginForm()
            {
                //Collect input from html page
                var username = document.getElementById("username").value;
                var password = document.getElementById("password").value;
                 
                //Call the REST APIs with directly method names
                var message = LoginAppService.loginUser({username:username,password:password});
                 
                //Use the REST API response
                document.getElementById("error").innerHTML = "<h2><span style='color:red'>" + message + " !!</span></h2>";
            }
        </script>

</body>
</html>