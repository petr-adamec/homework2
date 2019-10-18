package homework.homework;

import homework.homework.entitymanager.H2EntityManager;
import homework.homework.gui.Frame;

/**
 * Hello world!
 *
 */

public class Application 
{
	public Application() {
	}
	
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        //SpringApplication.run(Application.class, args);
        //new H2EntityManager().init();
        new Frame().init(); 
        
        
    }
}
