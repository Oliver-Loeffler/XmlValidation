package net.raumzeitfalle.xml;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.File;

import org.junit.jupiter.api.Test;

class XmlValidatorTest {

	@Test
	void test() throws Exception {
		
		File schema = new File("mySchema.xsd");
		File xml    = new File("instance.xml");
		
		XmlValidator validator = new XmlValidator(schema);
		
		assertDoesNotThrow(()->validator.validate(xml));
	}

}
