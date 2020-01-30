package net.raumzeitfalle.xml;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class XmlValidatorTest {

	@Test
	void valid() throws Exception {
		
		File schema = new File("mySchema.xsd");
		File xml    = new File("instance.xml");
		
		XmlValidator validator = new XmlValidator(schema);
		
		assertDoesNotThrow(()->validator.validate(xml));
	}
	
	@Test
	void invalid() throws Exception {
		
		File schema = new File("mySchema.xsd");
		File xml    = new File("invalid-instance.xml");
		
		XmlValidator validator = new XmlValidator(schema);
		
		assertThrows(SAXException.class, ()->validator.validate(xml));
	}

}
