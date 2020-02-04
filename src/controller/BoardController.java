package controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDao;
import model.BoardDataBean;
import mskim.ActionMethod;

public class BoardController extends ActionMethod
{
   @Override
protected void service(HttpServletRequest request, HttpServletResponse arg1)
        throws ServletException, IOException
{
       super.service(request, arg1);
       //setRequset
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
    
    public String board_list(HttpServletRequest request, HttpServletResponse res)
            throws Exception
    {
        HttpSession session = request.getSession();
        
        request.setCharacterEncoding("UTF-8");
        
        int pageSize = 3;
        int currentPage = 0;
        try
        {
            // 유효하지않으면 입셉션처리
            currentPage = Integer.parseInt(request.getParameter("pageNum"));
            session.setAttribute("pageNum", currentPage);
        }
        catch (Exception e)
        {
            if (session.getAttribute("pageNum") == null)
            {
                session.setAttribute("pageNum", 1);
            }
        }
        
        currentPage = (int) session.getAttribute("pageNum");
        String boardid = (String) session.getAttribute("boardid");
        BoardDao service = BoardDao.getInstance();
        int count = service.getArticleCount(boardid);
        
        int temp = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        if (currentPage > temp) currentPage = temp;
        
        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = startRow + pageSize - 1;
        List<BoardDataBean> li = service.getArticle(startRow, endRow, boardid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int number = count - (currentPage - 1) * pageSize;
        
        
        int bottomLine = 3;
        int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
        int startPage = 1 + (currentPage - 1)  / bottomLine * bottomLine;
        int endPage = startPage + bottomLine - 1;
        if(endPage > pageCount ) 
            endPage = pageCount;
        
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("startRow", startRow);
        request.setAttribute("endRow", endRow);
        request.setAttribute("count", count);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("number", number);
        request.setAttribute("li", li);
        
        request.setAttribute("startPage", startPage);
        request.setAttribute("endPage", endPage);
        request.setAttribute("pageCount", pageCount);
        request.setAttribute("bottomLine", bottomLine);
        
        return "/view/board/list.jsp";
    }
    
   
    public String  board_writeForm   (HttpServletRequest request, HttpServletResponse res)
            throws Exception
    {

        int num = 0, ref = 1, re_step = 0, re_level = 0;
        if (request.getParameter("num") != null)
        {
            num = Integer.parseInt(request.getParameter("num"));
            ref = Integer.parseInt(request.getParameter("ref"));
            re_step = Integer.parseInt(request.getParameter("re_step"));
            re_level = Integer.parseInt(request.getParameter("re_level"));
        }
        
        request.setAttribute("num", num);
        request.setAttribute("ref", ref);
        request.setAttribute("re_step", re_step);
        request.setAttribute("re_level", re_level);
        
       return "/view/board/writeUploadForm.jsp";
       
    }
}