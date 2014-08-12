import com.infoklinik.rsvp.shared.GisLatLng;
import com.infoklinik.rsvp.shared.SharedUtil;


public class SharedUtilTest {
	
	public static void main(String args[]) {
		
		//LatLng p1 = new LatLng(-6.196747035266851, 106.80981159210205);
		//LatLng p2 = new LatLng(-6.196747035266851, 106.82787895202637);
		//1999 : 15
		
		GisLatLng p1 = new GisLatLng(-6.00001, 106.00001);
		GisLatLng p2 = new GisLatLng(-6.00002, 106.00001);
		//1007 : 16
		
		
		System.out.println("Distance : " + SharedUtil.getDistance(p1, p2));
	}
}
