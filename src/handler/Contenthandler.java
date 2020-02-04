package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import model.BoardDataBean;

public class Contenthandler implements CommandHandler
{

    @Override
    public String process(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("UTF-8");
        
        int num = Integer.parseInt(request.getParameter("num"));

        BoardDao dbPro = BoardDao.getInstance();
        
        BoardDataBean article = dbPro.getArticle(num);
        
        request.setAttribute("num", num);
        request.setAttribute("article", article);
        
        return "/view/board/content.jsp";
    }
    
}
