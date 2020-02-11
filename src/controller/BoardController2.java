package controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import dao.BoardDao;
import model.BoardDataBean;
import temp.RequestMapping;
import temp.ActionAnnotation;
import temp.RequestMapping.RequestMethod;;

public class BoardController2 extends ActionAnnotation
{
    @Override
    public void initProcess(HttpServletRequest request,
            HttpServletResponse arg1)
    
    {
        // setRequset
        HttpSession session = request.getSession();
        try
        {
            request.setCharacterEncoding("UTF-8");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (request.getParameter("boardid") != null
                && !request.getParameter("boardid").equals(""))
        {
            session.setAttribute("boardid", request.getParameter("boardid"));
            session.setAttribute("pageNum", 1);
        }
        String boardid = (String) session.getAttribute("boardid");
        if (boardid == null || boardid.equals(""))
        {
            boardid = "1";
            session.setAttribute("boardid", "1");
        }
        
    }
    @RequestMapping(value="list",method=RequestMethod.GET)
    public String board_list(HttpServletRequest request,
            HttpServletResponse res) throws Exception
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
        int startPage = 1 + (currentPage - 1) / bottomLine * bottomLine;
        int endPage = startPage + bottomLine - 1;
        if (endPage > pageCount) endPage = pageCount;
        
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
    @RequestMapping(value="writeForm",method=RequestMethod.GET)
    public String board_writeForm(HttpServletRequest request,
            HttpServletResponse res) throws Exception
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
        
        return "/view/board/"
                + "writeUploadForm.jsp";
        
    }
    @RequestMapping(value="content",method=RequestMethod.GET)
    public String board_content(HttpServletRequest request,
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
    @RequestMapping(value="deleteForm",method=RequestMethod.GET)
    public String board_deleteForm(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        
        request.setAttribute("num", request.getParameter("num"));
        
        return "/view/board/deleteForm.jsp";
    }
    @RequestMapping(value="deletePro",method=RequestMethod.POST)
    public String board_deletePro(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        
        int num = Integer.parseInt(request.getParameter("num"));
        String passwd = request.getParameter("passwd");
        
        BoardDao service = BoardDao.getInstance();
        int check = service.deleteArticle(num, passwd);
        
        request.setAttribute("check", check);
        
        return "/view/board/updatePro.jsp";
    }
    @RequestMapping(value="updateForm",method=RequestMethod.GET)
    public String board_updateForm(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("UTF-8");
        int num = Integer.parseInt(request.getParameter("num"));
        
        BoardDao dbPro = BoardDao.getInstance();
        BoardDataBean article = dbPro.getUpdateArticle(num);
        
        request.setAttribute("num", num);
        request.setAttribute("article", article);
        
        return "/view/board/updateForm.jsp";
    }
    @RequestMapping(value="updatePro",method=RequestMethod.POST)
    public String board_updatePro(HttpServletRequest request,
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
    @RequestMapping(value="writePro",method=RequestMethod.POST)
    public String board_writePro(HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        
        String realFolder = "";
        String saveFolder = "uploadFile";
        String encType = "UTF-8";
        int maxSize = 10 * 1024 * 1024;
        ServletContext context = request.getServletContext();
        realFolder = context.getRealPath(saveFolder);
        
        try
        {
            
            MultipartRequest multi = new MultipartRequest(request, realFolder,
                    maxSize, encType, new DefaultFileRenamePolicy());
            BoardDataBean article = new BoardDataBean();
            Enumeration files = multi.getFileNames();
            if (files.hasMoreElements())
            {
                String name = (String) files.nextElement();
                File file = multi.getFile(name);
                if (file != null)
                {
                    article.setFilename(file.getName());
                    article.setFilesize((int) file.length());
                }
                else
                {
                    article.setFilename("");
                    article.setFilesize(0);
                }
                
            }
            
            article.setNum(Integer.parseInt(multi.getParameter("num")));
            article.setRef(Integer.parseInt(multi.getParameter("ref")));
            article.setRe_step(Integer.parseInt(multi.getParameter("re_step")));
            article.setRe_level(Integer
                    .parseInt(multi.getParameter("re_level")));
            
            article.setWriter(multi.getParameter("writer"));
            article.setContent(multi.getParameter("content"));
            article.setPasswd(multi.getParameter("passwd"));
            article.setSubject(multi.getParameter("subject"));
            article.setEmail(multi.getParameter("email"));
            article.setBoardid((String) request.getSession()
                    .getAttribute("boardid"));
            article.setIp(request.getRemoteAddr());
            
            BoardDao service = BoardDao.getInstance();
            service.insertArticle(article);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        // response.sendRedirect(request.getContextPath() + "/Board/list.jsp");
        
        // return "/board/list";
        return "redirect:/board/list";
    }
    
}