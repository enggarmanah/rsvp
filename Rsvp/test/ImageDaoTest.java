import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.infoklinik.rsvp.server.dao.ImageDAO;
import com.infoklinik.rsvp.shared.ImageBean;


public class ImageDaoTest {
	
	public static void main(String[] args) {
		
		String path = "C:/Workspace/gwt_clinic/Rsvp/war/images/galeri-small.jpg";
		path = "C:/Workspace/gwt-google/Rsvp/war/images/promo-platelet.jpg";
		
		File file = new File(path);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		
		byte[] imageBytes = null;
		
		try {
			fis = new FileInputStream(file);
 
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			
			byte[] buffer = new byte[512];
			ByteBuffer b = ByteBuffer.allocate(1000000);
			
			while (dis.available() != 0) {
				int length = dis.read(buffer);
				b.put(buffer, 0,length);
			}
			
			imageBytes = b.array();
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				bis.close();
				dis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		ImageBean imageBean = new ImageBean();
		imageBean.setBytes(imageBytes);
		
		ImageDAO imageDao = new ImageDAO();
		imageDao.addImage(imageBean);
	}
}
