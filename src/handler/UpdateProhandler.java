package handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.BoardDao;
import model.BoardDataBean;

public class UpdateProhandler implements CommandHandler
{

    @Override
    public String process(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
       
        BoardDataBean article = new BoardDataBean();
        article.setNum(Integer.parseInt(request.getParameter("num")));
        article.setWriter(request.getParameter("writer"));
        article.setContent(request.getParameter("content"));
        article.setPasswd(request.getParameter("passwd"));
        article.setSubject(request.getParameter("subject"));
        article.setEmail(request.getParameter("email"));
        

        BoardDao service = BoardDao.getInstance();
        int check = service.updateArticle(article);
        System.out.println(check);
        request.setAttribute("check", check);
        request.setAttribute("article", article);
      
        return "/view/board/updatePro.jsp";
    }
    
}
