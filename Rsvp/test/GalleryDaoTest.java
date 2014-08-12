import com.infoklinik.rsvp.server.dao.GalleryDAO;
import com.infoklinik.rsvp.shared.GalleryBean;
import com.infoklinik.rsvp.shared.InstitutionBean;

public class GalleryDaoTest {
	
	public static void main(String[] args) {
		
		InstitutionBean institution = new InstitutionBean();
		institution.setId(Long.valueOf(1));
		
		GalleryBean galleryBean = new GalleryBean();
		galleryBean.setInstitution(institution);
		galleryBean.setImage_id(Long.valueOf(12));
		galleryBean.setDescription("Lobby");
		
		GalleryDAO galleryDao = new GalleryDAO();
		galleryDao.addGallery(galleryBean);
	}
}
