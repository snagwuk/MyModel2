package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BoardDataBean;

public class BoardDao
{
    private static BoardDao instance = new BoardDao();
    
    public static BoardDao getInstance()
    {
        return instance;
    }
    
    private BoardDao()
    {
    }
    
    @SuppressWarnings("unused")
    private Connection getConnection() throws Exception
    {
        Connection conn = null;
        try
        {
            String jdbcUrl = "jdbc:mysql://localhost:3306/jspdb";
            String dbId = "scott";
            String dbpass = "1111";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(jdbcUrl, dbId, dbpass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return conn;
    }
    
    public void insertArticle(BoardDataBean article)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        int num = article.getNum();
        int ref = article.getRef();
        int re_step = article.getRe_step();
        int re_level = article.getRe_level();
        int number = 0;
      
     
        
        try
        {
            conn = getConnection();
            pstmt = conn.prepareStatement("select max(num)+1 from board");
            rs = pstmt.executeQuery();
            if(rs.next())
                number = rs.getInt(1);
            
            if(num != 0)
            {
                
                pstmt = conn.prepareStatement("update board set re_step= re_step+1 where ref = ? and re_step > ? ");
                pstmt.setInt(1, ref);
                pstmt.setInt(2, re_step);
                pstmt.executeUpdate();
                re_step++;
                re_level++;   
            }
            else
                ref=number;
            

            pstmt = conn.prepareStatement("insert into board values (?,?,?,?,?,?,now(),0,?,?,?,?,?,?,?)");
            pstmt.setInt(1, number);
            pstmt.setString(2, article.getBoardid());
            pstmt.setString(3, article.getWriter());
            pstmt.setString(4, article.getEmail());
            pstmt.setString(5, article.getSubject());
            pstmt.setString(6, article.getPasswd());
            
            pstmt.setInt(7, ref);
            pstmt.setInt(8, re_step);
            pstmt.setInt(9, re_level);
            
            pstmt.setString(10, article.getContent());
            pstmt.setString(11, article.getIp());
            
            pstmt.setString(12, article.getFilename());
            pstmt.setInt(13, article.getFilesize());
            
            
            pstmt.executeUpdate();
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        
    }
    
    public int getArticleCount(String boardid)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        ResultSet rs = null;
        int count = 0;
        try
        {
            conn = getConnection();
            pstmt = conn
                    .prepareStatement("select ifnull(count(*),0) from board where boardid = ?");
            pstmt.setString(1, boardid);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                count = rs.getInt(1);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        return count;
        
    }
    
    public List<BoardDataBean> getArticle(int startRow, int endRow,
            String boardid)
    {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<BoardDataBean> articleList = null;
        
        
        startRow = startRow -1 ;
        endRow = endRow - startRow;
        System.out.println("dao==="+startRow +":" + endRow + ":" + boardid);
        
        try
        {
            conn = getConnection();
            
            // pstmt = conn.prepareStatement("select * from board where boardid
            // = ?");
           /* String sql = " select * from( " + " select rownum rnum, a.* from( "
                    + " select * from board where boardid=? order by ref desc , re_step) a) "
                    + " where rnum between ? and ? ";*/
            String sql = " select * from board where boardid= ? order by ref desc , re_step limit ? , ? " ;
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, boardid);
            pstmt.setInt(2, startRow);
            pstmt.setInt(3, endRow);
            rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                articleList = new ArrayList<BoardDataBean>();
                do
                {
                    BoardDataBean temp = new BoardDataBean();
                    temp.setNum(rs.getInt("num"));
                    temp.setBoardid(rs.getString("boardid"));
                    temp.setWriter(rs.getString("writer"));
                    temp.setEmail(rs.getString("email"));
                    temp.setSubject(rs.getString("subject"));
                    temp.setPasswd(rs.getString("passwd"));
                    temp.setReg_date(rs.getTimestamp("reg_date"));
                    temp.setReadcount(rs.getInt("readcount"));
                    temp.setRef(rs.getInt("ref"));
                    temp.setRe_step(rs.getInt("re_step"));
                    temp.setRe_level(rs.getInt("re_level"));
                    temp.setContent(rs.getString("content"));
                    temp.setIp(rs.getString("ip"));
                    temp.setFilename(rs.getString("filename"));
                    temp.setFilesize(rs.getInt("filesize"));
                    
                    articleList.add(temp);
                    
                } while (rs.next());
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        
        return articleList;
    }
    
    public BoardDataBean getArticle(int num)
    {
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDataBean temp = null;
        try
        {
            conn = getConnection();
            
            // 조회수 카운트 증가
            pstmt = conn
                    .prepareStatement("update board set readcount = readcount + 1 where num = ?");
            pstmt.setInt(1, num);
            pstmt.executeUpdate();
            
            pstmt = conn.prepareStatement("select * from board where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                temp = new BoardDataBean();
                temp.setNum(rs.getInt("num"));
                temp.setBoardid(rs.getString("boardid"));
                temp.setWriter(rs.getString("writer"));
                temp.setEmail(rs.getString("email"));
                temp.setSubject(rs.getString("subject"));
                temp.setPasswd(rs.getString("passwd"));
                temp.setReg_date(rs.getTimestamp("reg_date"));
                temp.setReadcount(rs.getInt("readcount"));
                temp.setRef(rs.getInt("ref"));
                temp.setRe_step(rs.getInt("re_step"));
                temp.setRe_level(rs.getInt("re_level"));
                temp.setContent(rs.getString("content"));
                temp.setIp(rs.getString("ip"));
                temp.setFilename(rs.getString("filename"));
                temp.setFilesize(rs.getInt("filesize"));
                
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        
        return temp;
    }
    
    public BoardDataBean getUpdateArticle(int num)
    {
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        BoardDataBean temp = null;
        try
        {
            conn = getConnection();
            
            pstmt = conn.prepareStatement("select * from board where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                temp = new BoardDataBean();
                temp.setNum(rs.getInt("num"));
                temp.setBoardid(rs.getString("boardid"));
                temp.setWriter(rs.getString("writer"));
                temp.setEmail(rs.getString("email"));
                temp.setSubject(rs.getString("subject"));
                temp.setPasswd(rs.getString("passwd"));
                temp.setReg_date(rs.getTimestamp("reg_date"));
                temp.setReadcount(rs.getInt("readcount"));
                temp.setRef(rs.getInt("ref"));
                temp.setRe_step(rs.getInt("re_step"));
                temp.setRe_level(rs.getInt("re_level"));
                temp.setContent(rs.getString("content"));
                temp.setIp(rs.getString("ip"));
                temp.setFilename(rs.getString("filename"));
                temp.setFilesize(rs.getInt("filesize"));
                
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        
        return temp;
    }
    
    @SuppressWarnings("resource")
    public int updateArticle(BoardDataBean article)
    {
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbpasswd = "";
        int x = -1;
        try
        {
            conn = getConnection();
            
            pstmt = conn
                    .prepareStatement("select passwd from board where num = ?");
            pstmt.setInt(1, article.getNum());
            rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                dbpasswd = rs.getString(1);
                
                if (article.getPasswd().equals(dbpasswd))
                {
                    
                    pstmt = conn
                            .prepareStatement("update board set writer = ? , subject = ? , email = ? , content = ? where num = ?");
                    pstmt.setString(1, article.getWriter());
                    pstmt.setString(2, article.getSubject());
                    pstmt.setString(3, article.getEmail());
                    
                    pstmt.setString(4, article.getContent());
                    pstmt.setInt(5, article.getNum());
                    
                    pstmt.executeUpdate();
                    
                    x = 1;
                    
                }
                else
                    x = 0;
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        System.out.println("x = " + x);
        return x;
        
    }
    
    @SuppressWarnings("resource")
    public int deleteArticle(int num, String passwd)
    {
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String dbpasswd = "";
        int x = -1;
        try
        {
            conn = getConnection();
            
            pstmt = conn
                    .prepareStatement("select passwd from board where num = ?");
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            
            if (rs.next())
            {
                dbpasswd = rs.getString(1);
                
                if (passwd.equals(dbpasswd))
                {
                    
                    pstmt = conn
                            .prepareStatement("delete from board where num = ? ");
                    pstmt.setInt(1, num);
                    pstmt.executeUpdate();
                    
                    x = 1;
                    
                }
                else
                    x = 0;
            }
            
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (rs != null) try
            {
                rs.close();
            }
            catch (SQLException ex)
            {
            }
            if (pstmt != null) try
            {
                pstmt.close();
            }
            catch (SQLException ex)
            {
            }
            if (conn != null) try
            {
                conn.close();
            }
            catch (SQLException ex)
            {
            }
        }
        System.out.println("x = " + x);
        return x;
        
    }
    
}
