                    <%-- 
    Document   : bot
    Created on : 22 Feb, 2021, 11:42:20 AM
    Author     : T_IS_JUHI
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://psosamarth.ds.indianoil.in:8443/assets/modules/channel-web/inject.js"></script>
        <link src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/css/bootstrap.min.css"/>
        <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.3.1/js/bootstrap.bundle.min.js"></script>
        <link src="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"/>
        <link rel="stylesheet" type="text/css" href="CSS/bot.css">

        <title>ChatBot</title>
    </head>
    <style>
          .image{
      background-image: url("CSS/images/NumeroUno-02.11.14.jpg");

      /* Full height */
      height: 40%; 
      -webkit-filter: blur(0px); /* Safari 6.0 - 9.0 */
      filter: blur(0px);
      /* Center and scale the image nicely */
      background-position: center;
      background-repeat: no-repeat;
      background-size: cover;
    }
    
    </style>
    <body>
        <%
            String userid = (String)session.getAttribute("id");
         if(session.getAttribute("id").equals(userid)){
      %>
        <div class ="image">
           <img class="iocl-logo" src="CSS/images/iocl.jpg">
        </div>
         <footer class="ct-footer">
      <div class="container">
            <div class="ct-footer-pre text-center-lg">
             
                <div class="col-md-3">
                    <img class="logo" src="CSS/images/image.png">
                    <h1 class="navbar-title">SAMARTH</h1>
                    <h5 class="subtitle">Solutions to IS related queries</h5>
                    <hr style="margin:0;padding-left:3%;height:1px;border-width:0;background-color:#000066;">
                          
                </div>
                <div class="col-md-5" >
                    <ul class="ct-footer-list text-center-sm">
                        <li>
                            <h2 class="ct-footer-list-header">
                                Contact
                            </h2>
                            <ul>
                                <li>
                                   Email Id     :- ishdpbso@indianoil.in
                                </li>
                                <li>
                                    Contact      :- 0172-2540291
                                </li>
                                <li>
                                    Extensions   :- 1206, 1207
                                </li>
                                
                            </ul>
                        </li>
                        <li>
                            <h2 class="ct-footer-list-header">
                                Version
                            </h2>
                            <ul>
                                <li>
                                   Version 1.0
                                </li>
                                <li>
                                    Release: Testing
                                </li>
                                <li>
                                   Developed By: IS Department, PBSO
                                </li>
                                
                            </ul>
                        </li>
                        
                    </ul>
                </div>
            </div>
           <div class="col-md-4">
               <a href="LogoutServlet" class="logout">Logout</a>            
            </div>
         
      
  </footer>
        <%}%>
    </body>

     <script>
$(document).ready(function(){
            window.botpressWebChat.init({ 
                host: 'https://psosamarth.ds.indianoil.in:8443',
                botId: 'indie' ,
                botConvoDescription: 'Chat to solve your IS related Queries',
                enableTranscriptDownload: false, 
                enableReset:false,
                showConversationsButton: false,
                extraStylesheet: '/assets/modules/channel-web/custom.css',
                hideWidget: true,
//              enableVoiceComposer: true,
//                 greetingScreen: {
//                    type: 'blank_chat', // If you want to see "Get started" button you need to change it to 'get_started'
//                    options: {
//                      buttonTitle: 'Get Started',
//                      message: ''
//                    }
//                  },
                filterQuickReplies:true

            });
         
            window.setTimeout(function() {
              
                window.addEventListener('message', function(event) {
                     if (event.data.name === 'webchatLoaded') {
                       
                        window.botpressWebChat.mergeConfig({
                            containerWidth: '75%',
                            layoutWidth: '75%',;

                        }) ;
                         window.botpressWebChat.sendEvent({ type: 'show' });
                      }
                    if (event.data.name === 'webchatReady') {
                      window.botpressWebChat.sendEvent({
                        type: 'proactive-trigger',
                        platform: 'web',
                        channel: 'web',
                        payload: { text: 'Hello <%= session.getAttribute("name")%> \nKindly type "Hi" or "Hello" to start conversation',
                                   userid:'<%= session.getAttribute("id")%>',
                                   email:'<%= session.getAttribute("email") %>',;

                                }
                        })
                    }
                }) 
//                window.botpressWebChat.mergeConfig({
//                    containerWidth: '75%',
//                    layoutWidth: '75%',
//                    
//                })   
                window.botpressWebChat.sendEvent({ type: 'show' })
            }, 500)
            });
    </script>
    
</html>
