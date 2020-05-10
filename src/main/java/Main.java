import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import service.DataInitializer;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Service
public class Main {

    public static void main(String args[]) {
        DataInitializer.run();
    }
}
