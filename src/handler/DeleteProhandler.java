package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;

public class DeleteProhandler implements CommandHandler
{
    @Override
    public String process(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        
        int num = Integer.parseInt(request.getParameter("num"));
        String passwd = request.getParameter("passwd");
        
       
        
        BoardDao service = BoardDao.getInstance();
        int check = service.deleteArticle(num,passwd);
            
        request.setAttribute("check", check);

            
        return "/view/board/updatePro.jsp";
    }
    
}
