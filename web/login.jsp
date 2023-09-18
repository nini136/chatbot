

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="CSS/login.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <title>Login</title>
          <script>
            function refresh()
                {
                window.location='index.jsp';
                }
        </script>
    </head>
    <body>
            <%
           StringBuffer sb=new StringBuffer();  
                    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                              + "0123456789"
                                              + "abcdefghijklmnopqrstuvxyz";
                        for(int i=1;i<=5;i++)  
                        {  
                            int index = (int)(AlphaNumericString.length()* Math.random());

                        // add Character one by one in end of sb
                            sb.append(AlphaNumericString.charAt(index));
            //                sb.append((char)(int)(Math.random()*79+23+7));  
                        }  
                    String cap=new String(sb);  
                    
                      session.setAttribute("cap",cap);
        
        %>
  
        <div class="login">
            <div class="container-fluid" id="login-container">
                <div class="row main-content bg-success text-center">
                    <div class="col-md-5 text-center company__info">
                        <img src="CSS/images/iocl.jpg" class="image"/>
                        <h4 class="company_title">Punjab State Office, IOCL(Marketing Division)</h4>
                    </div>
                    <div class="col-md-7 col-xs-12 col-sm-12 login_form ">
                        <div class="login-container-fluid">
                            <div class="row">
                                <h2>Log In</h2>
                            </div>
                            <div class="row">
                                <form control="" class="form-group" action="Login" method="POST">
                                    <div class="row">
                                        <input type="text" name="userid" id="userid" class="form__input" placeholder="Userid">
                                    </div>
                                    <div class="row">
                                      <!-- <span class="fa fa-lock"></span> -->
                                        <input type="password" name="password" id="password" class="form__input" placeholder="Password">
                                    </div>
<!--                                    <div class='recaptcha'>
                                    <table border="0">  
                                        <tbody>  
                                           <tr>  
                                            <td>  
                                              <div style="background-color: #000066;color:#fff"><h2><s><i><font face="casteller"><%=cap%></font></i></s></h2></div>  
                                            </td>  
                                            <td><input type="text" name="cap1" value="" /></td>  
                                            <td><input type="hidden" name="cap2" value='<%=cap%>' readonly="readonly" </td>  
                                            <td><a class="button button-small edit" title="Edit"><i class="fa fa-refresh" aria-hidden="true"></i></a></td>
                                            <td><input type="reset" name="Refresh" value="Refresh" /></td>
                                          </tr>  
                                       </tbody>  
                                    </table> 
                          
                                     </div>-->
                                    <div class='recaptcha'>
                                        <div class="g-recaptcha" data-sitekey="6LeX5x8aAAAAAIgX0DO1HIqUt-XZ04N2aZ_2uFSo" name="recaptcha"></div>
                                    </div>
                                    <div class="row"><center><p style="color: red;">${LoginError}</p></center></div>
                                    <div class="row">
                                        <button class="btn" id="fetch" style="background-color: #ee7600;  color:#fff;">Log in</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
             </div>
        </div>
    </body>
</html>
