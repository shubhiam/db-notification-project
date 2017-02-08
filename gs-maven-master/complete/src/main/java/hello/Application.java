package hello;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        
        DBChangeNotify dcn = new DBChangeNotify();
        try {
          dcn.prop = new Properties();
          dcn.run();
        }
        catch(Exception e) {
          e.printStackTrace();
        }
    }
}
