package servlets;
import model.Point;
import model.PointContainer;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
public class AreaCheckServlet extends HttpServlet {
    private final int MAX_LENGTH = 6;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        double x,y,r;
        boolean isInArea;
        if(isCorrectData(request.getParameter("x"), request.getParameter("y"), request.getParameter("r"))){
            x = Double.parseDouble(request.getParameter("x"));
            y = Double.parseDouble(request.getParameter("y"));
            r = Double.parseDouble(request.getParameter("r"));
            isInArea = checkArea(x,y,r);
            PointContainer container;
            PrintWriter printWriter = response.getWriter();
            if(getServletContext().getAttribute(request.getSession().getId()) != null){
                container = (PointContainer) getServletContext().getAttribute(request.getSession().getId());
            }else {
                container = new PointContainer();
            }
            container.addPoint(new Point(x,y,r,isInArea));
            getServletContext().setAttribute(request.getSession().getId(), container);
            for(Point p:container){
                printWriter.println("<tr>");
                printWriter.println("<td>"+p.getX()+"</td>");
                printWriter.println("<td>"+p.getY()+"</td>");
                printWriter.println("<td>"+p.getR()+"</td>");
                printWriter.println("<td>"+p.isInArea()+"</td>");
                printWriter.println("</tr>");
            }
            printWriter.close();
        }else{
            response.setStatus(444);
            request.setAttribute("errorMsg", "Invalid Data");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    private boolean isCorrectData(String x, String y, String r){
        if((x == null || x.equals("")) || (y == null || y.equals("")) || (r == null || r.equals("")) ||
                (x.length() > MAX_LENGTH) || (y.length() > MAX_LENGTH) || (r.length()) > MAX_LENGTH){
            return false;
        }
        try {
            double xValue = Double.parseDouble(x);
            double yValue = Double.parseDouble(y);
            double rValue = Double.parseDouble(r);
            return (!(xValue < -3)) && (!(xValue > 5)) && (!(yValue < -5)) && (!(yValue > 3)) && (!(rValue < 1)) && (!(rValue > 4));
        }catch (Exception e){
            return false;
        }
    }

    private boolean checkArea(double x, double y, double r){
        return ((x * x + y * y <= (r * r / 4)) && (x <= 0 && y <= 0)) ||
                ((x >= 0 && y >= 0) && (y <= r && x <= r / 2)) || ((x <= 0 && y >= 0) && (x >= -r && y <= r / 2));
    }
}
