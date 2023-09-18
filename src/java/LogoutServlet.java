
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;


@WebServlet(urlPatterns = {"/LogoutServlet"})
public class LogoutServlet extends HttpServlet {

   
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           RequestDispatcher rd= request.getRequestDispatcher("login.jsp");
           rd.include(request, response);
           HttpSession session = request.getSession();
           session.invalidate();
//           out.println("Successfully logged out!");
           out.close();
        }
    }

    

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
