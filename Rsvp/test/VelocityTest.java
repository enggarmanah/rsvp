import java.io.StringWriter;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

public class VelocityTest {
	
	public static void main( String args[] )
    {
        /* first, we init the runtime engine.  Defaults are fine. */

		/* first, we init the runtime engine.  Defaults are fine. */
		
		VelocityEngine ve = new VelocityEngine();
		
        try
        {
        	ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        	ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        	ve.init();
        	
        	VelocityContext context = new VelocityContext();

            context.put("name", "Velocity");
            context.put("project", "Jakarta");

            StringWriter w = new StringWriter();
            
            Template t = ve.getTemplate( "example.vm" );
        	t.merge(context, w);
        	
        	System.out.println(" template : " + w );
        }
        catch(Exception e)
        {
            System.out.println("Problem initializing Velocity : " + e );
            return;
        }
    }
}
