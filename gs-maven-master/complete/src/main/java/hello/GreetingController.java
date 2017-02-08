package hello;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GreetingController{

	public static List<String> dBChanges = new ArrayList<>();
	
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
    	String content = readPropertiesFile();
    	List<String> serviceList = Arrays.asList(content.split(","));
    	Greeting greeting = new Greeting("");
    	greeting.setServiceList(serviceList);
        return greeting;
    }
    
    @MessageMapping("/getDBChanges")
    @SendTo("/topic/dbList")
    public Greeting dbList(HelloMessage message) throws Exception {
//  		List<String> dbChangeList = new ArrayList<String>();
//    	dBChanges.add("A");
    	Greeting greeting = new Greeting("");
    	greeting.setDbChangeList(dBChanges);
        return greeting;
    }
    
    String readPropertiesFile() throws IOException{
    	Properties prop = new Properties();
    	InputStream is = null;
    	try {
			is = new FileInputStream("data.properties");
			prop.load(is);
		} catch (IOException  e) {
			e.printStackTrace();
		}finally {
			if(is != null){
				is.close();
			}
		}
    	return prop.getProperty("data");
    }

}
