import com.infoklinik.rsvp.server.dao.CommentDAO;

public class ScheduleDaoTest {
	
	public static void main(String[] args) {
							
		long instId = 8;
		CommentDAO commentDao = new CommentDAO();
		
		System.out.println("Inst ID : " + 8 + ", comment count : " + commentDao.getInstCommentsCount(instId));
	}
}
