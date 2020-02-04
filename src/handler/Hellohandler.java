package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import handler.CommandHandler;

public class Hellohandler implements CommandHandler
{

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res)
            throws Exception
    {
       req.setAttribute("hello", "안녕하세요!");
        return "/view/hello.jsp";
    }
    
}
