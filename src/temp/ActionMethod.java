package temp;

import java.io.IOException;
import java.lang.reflect.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public abstract class ActionMethod extends HttpServlet
{
    public void init() throws ServletException{}
    public abstract void initProcess(HttpServletRequest request,
            HttpServletResponse response);
    
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        process(request, response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        // TODO Auto-generated method stub
        process(request, response);
    }
    
    private void process(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        initProcess(request, response);
        String command = request.getRequestURI();
        if (command.indexOf(request.getContextPath()) == 0)
        {
            command = command.substring(request.getContextPath().length() + 1);
        }
        System.out.println(command);
        String pathMethod = command.replace("/", "_");
        Method[] methods = this.getClass().getMethods();
        String viewPage = null;
        try
        {
            for (int i = 0; i < methods.length; i++)
            {
                if (methods[i].getName().equals(pathMethod))
                    viewPage = (String) methods[i].invoke(this, request, response);
                
            }
            
        }
        catch (Throwable e)
        {
            throw new ServletException(e);
            // TODO: handle exception
        }
        
        if (viewPage != null)
        {
            if (viewPage.startsWith("redirect:"))
                response.sendRedirect(request.getContextPath() + "/board/list");
            else
            {
                RequestDispatcher dispatcher = request
                        .getRequestDispatcher(viewPage);
                dispatcher.forward(request, response);
            }
            
        }
        else
        {
            try
            {
                throw new UrlNotMatch(
                        pathMethod + "  not exists in " + getClass().getName());
            }
            catch (UrlNotMatch e)
            {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }
    }
}
    
    class UrlNotMatch extends Exception
    {
        UrlNotMatch(String name)
        {
            super(name);
        }
    }
