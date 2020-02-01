package net.raumzeitfalle.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

public class ValidationExample {

	public static void main(String... args) {
		Logger logger = Logger.getLogger(ValidationExample.class.getName());
		logger.info("This is an example for XML validation!");

		XmlValidator validator = new XmlValidator();

		runValidation(logger, validator, "mySchema.xsd", "instance.xml");
		runValidation(logger, validator, "mySchema.xsd", "invalid-instance.xml");
	}

	private static void runValidation(Logger logger, XmlValidator validator, String schema, String xmlname) {

		try (FileInputStream xml = new FileInputStream(xmlname);
			 FileInputStream xsd = new FileInputStream(schema)){
			
			validator.validate(xml, xsd);
			logger.log(Level.INFO, "XML is valid.");

		} catch (SAXException s) {

			logger.log(Level.WARNING, "Invalid XML: {0}", s.getMessage());

		} catch (IOException e) {

			logger.log(Level.WARNING, "IO-Error: ", e);
		}
	}
}
