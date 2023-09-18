

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;






@WebServlet(urlPatterns = {"/Login"})
public class Login extends HttpServlet {

  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession();
//            session.setMaxInactiveInterval(2*60);
            String username = request.getParameter("userid");
            session.setAttribute("id",username);
            System.out.println(username);
            String pwd = request.getParameter("password");
            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            String empCode="";
            String empName="";
            String locName="";
            String designation="";
            String mobileNo="";
            String emailId="";
            Statement stmt = null;
            
            try{
//                URL obj = new URL("https://xsparsh.indianoil.in/EmpLogin/login?username="+username+"&password="+pwd+"");
//                HttpURLConnection con =(HttpURLConnection) obj.openConnection();
//                con.setRequestMethod("GET");
//                con.setRequestProperty("Accept", "application/json");
//                int responseCode;
//                responseCode = con.getResponseCode();
//                if(responseCode == HttpURLConnection.HTTP_OK){
//                    BufferedReader in = new BufferedReader(new InputStreamReader(
//                                                con.getInputStream()));
//                    
//                    String inputLine;
//                    StringBuffer res = new StringBuffer();
//                    
//                    while((inputLine = in.readLine()) != null){
//                        res.append(inputLine);
//                    }
//                    in.close();
                    if(username.equals("") || pwd.equals("")|| gRecaptchaResponse.equals("")){
                        request.setAttribute("LoginError"," Please fill the fields!!!");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.include(request, response);
                    }
                    else if(username != null || pwd != null){
//                        if(res.toString().equals("true")  ){
                        if(empLogin(username,pwd)){
                            String inline = "";
                            
                            try{
                                URL url = new URL("https://xsparsh.indianoil.in/soa-infra/resources/default/MPower/RSEmpData?emp_code="+username+"");

                                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("GET");
                                conn.setRequestProperty("Accept", "application/json");
                                conn.connect();
                               
                                int responseCode1 = conn.getResponseCode();
//                                System.out.println(responseCode1);
                                if(responseCode1 !=200){
                                    throw new RuntimeException("HttpResponseCode:" +responseCode1);
                                }else{
                                    Scanner sc= new Scanner(url.openStream());
                                    while(sc.hasNext()){
                                        inline+=sc.nextLine();
                                    }
                                    sc.close();
                                }
//                                  
                                    JSONParser parse = new JSONParser();
                                    JSONObject jobj = (JSONObject)parse.parse(inline);
                                    JSONArray jsonarr_1 = (JSONArray) jobj.get("MstEmployee");
                                    
                                  
                                    try{
                                        Driver d = (Driver)Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
                                        String dbURL = "jdbc:sqlserver://PBSOSERVERFILE;dbName=Report;";
                                        String user = "sa";
                                        String pass = "iocl@123";
//                                        System.out.println(pass);
                                        try(  Connection connection = DriverManager.getConnection(dbURL, user, pass);){
                                            if (connection != null) {
                                                DatabaseMetaData dm = (DatabaseMetaData) connection.getMetaData();
                                                System.out.println("successfully connected ");
                                                stmt = connection.createStatement();
                                                String sql;
                                                sql= "select * from [Report].[dbo].[user_details] where user_id = "+username+"";
                                                ResultSet rs = stmt.executeQuery(sql);
                                             
                                                String updateQuery ="update [Report].[dbo].[user_details] set user_id=?, user_name=?,designation=?,location=?,phone_no=?,email_id=? where user_id=?";
                                                       
                                                for(int i=0;i<jsonarr_1.size();i++)
                                                {
                                                    JSONObject jsonobj_1 = (JSONObject)jsonarr_1.get(i);
                                                    empCode = jsonobj_1.get("empCode").toString();
//                                                    System.out.println(empCode);
                                                    session.setAttribute("id",empCode);
                                                    empName =jsonobj_1.get("empName").toString();
                                                    session.setAttribute("name",empName);
                                                    designation =jsonobj_1.get("designation").toString();
                                                    locName=jsonobj_1.get("locName").toString();
                                                    mobileNo = jsonobj_1.get("mobileNo").toString();
                                                    emailId=jsonobj_1.get("emailId").toString();
                                                    session.setAttribute("email",emailId);
                                                  
                                                    if(!rs.next()){
                                                        System.out.println("hello");
                                                        PreparedStatement ps = connection.prepareStatement("insert into [Report].[dbo].[user_details](user_id,user_name,role,designation,location,phone_no,email_id)values(?,?,'User',?,?,?,?)");
                                                        System.out.println(ps);
                                                        ps.setString(1,empCode);
                                                        ps.setString(2,empName);
                                                        ps.setString(3,designation);
                                                        ps.setString(4,locName);
                                                        ps.setString(5,mobileNo);
                                                        ps.setString(6,emailId);
                                                        ps.executeUpdate();
                                                        
//                                                      
                                                    }else{
                                                        PreparedStatement ps1= connection.prepareStatement(updateQuery);
                                                        ps1.setString(1,empCode);
                                                        ps1.setString(2,empName);
                                                        ps1.setString(3,designation);
                                                        ps1.setString(4, locName);
                                                        ps1.setString(5,mobileNo);
                                                        ps1.setString(6,emailId);
                                                        ps1.setString(7,empCode);
                                                        ps1.executeUpdate();   
                                                        
                                                    }
                                                        rs.close();
                                                        stmt.close();
                                                        connection.close();
                                                }                                                            
                                            }   
                                        }   
                                    }catch(Exception e){}
                                }catch(Exception e){}
                           response.sendRedirect("bot.jsp");

                    
                        }else{

                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                            if (verify) {
                                request.setAttribute("LoginError"," Invalid userid and password !!");
                                } else {
                                request.setAttribute("LoginError"," Invalid userid or password !!!");
                                }
                         rd.include(request, response);
                        }
                    }else{
                         out.println("Get request not worked");
                    }
//                }
                
            }catch(Exception e){
                    out.println("Exception:" +e);
            }
        
        }
    }

  private boolean empLogin(String uid, String pass) {
            boolean b = false;
            String principalName = uid + "@ds.indianoil.in";
            Hashtable env = new Hashtable(11);
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "LDAP://10.52.25.27:389");
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, principalName);
            env.put(Context.SECURITY_CREDENTIALS, pass);

            try {

                DirContext ctx = new InitialDirContext(env);
                byte[] key = {(byte) 0x61, (byte) 0x62, (byte) 0x63, (byte) 0x64, (byte) 0x65, (byte) 0x66, (byte) 0x67};

                SearchControls ctls = new SearchControls();

                System.out.println("m here");
                ctls.setReturningAttributes(new String[0]);       // Return no attrs
                ctls.setSearchScope(SearchControls.SUBTREE_SCOPE); // Search object only

                NamingEnumeration answer = ctx.search("dc=ds,dc=indianoil,dc=in", "(&(objectClass=user)(sAMAccountName=" + uid + "))", ctls);

                //b = printSearchEnumeration(answer);
                b = true;
                ctx.close();
            } catch (Exception e) {
                //System.out.println(e);
                e.printStackTrace();
                return false;
            }
            return b;

    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
