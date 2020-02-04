package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteFormhandler implements CommandHandler
{

    @Override
    public String process(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {

        request.setAttribute("num", request.getParameter("num"));
        
        return "/view/board/deleteForm.jsp";
    }
    
}
