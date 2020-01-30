package net.raumzeitfalle.xml;

import java.io.File;
import java.util.logging.Logger;

public class ValidationExample {

    public static void main(String... args) {
        Logger logger=Logger.getLogger(ValidationExample.class.getName());
        logger.info("This is a module-using example for XML validation!");
        
        XmlValidator validator = new XmlValidator(new File("mySchema.xsd"));
        
        try {
			validator.validate(new File("instance.xml"));
			logger.info("XML seems to be valid.");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        
    }
}
