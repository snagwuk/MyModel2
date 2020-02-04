package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface CommandHandler
{
    public String process(HttpServletRequest request,HttpServletResponse response)
    throws Exception;
    
    public default void setRequest(HttpServletRequest request) throws Exception
    {
        HttpSession session = request.getSession();
         request.setCharacterEncoding("UTF-8");

        if (request.getParameter("boardid") != null && !request.getParameter("boardid").equals("")){
                session.setAttribute("boardid", request.getParameter("boardid"));
                session.setAttribute("pageNum", 1);
        }
        String boardid = (String) session.getAttribute("boardid");
        if (boardid == null || boardid.equals("")) {
                boardid = "1";
                session.setAttribute("boardid", "1");
        }
    }
}
