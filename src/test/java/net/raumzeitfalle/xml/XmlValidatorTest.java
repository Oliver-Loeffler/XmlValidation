package net.raumzeitfalle.xml;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class XmlValidatorTest {
	
	@BeforeAll
	public static void prepare() {
		Locale.setDefault(Locale.ENGLISH);
	}
	
	@Test
	void positiveValidation_noFileIO() throws Exception {
		
		// GIVEN
		String schema = createXsdSchema();
		String xml = new StringBuilder()
						.append("<INSTANCE>")
						.append("<NAME>Hello XML Validation</NAME>")
						.append("</INSTANCE>")
						.toString();
		
		InputStream xmlInput = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		InputStream xsdSchema = new ByteArrayInputStream(schema.getBytes(StandardCharsets.UTF_8));
		XmlValidator validator = new XmlValidator();
		
		// WHEN THEN
		assertDoesNotThrow(()->validator.validate(xmlInput,xsdSchema));
		
	}
	
	@Test
	void negativeValidation_noFileIO() throws Exception {
		
		// GIVEN
		String schema = createXsdSchema();	
		String invalidXml = new StringBuilder()
						.append("<INSTANCE>")
						.append("<OTHER_NAME>Hello XML Validation</OTHER_NAME>")
						.append("</INSTANCE>")
						.toString();
		
		InputStream xmlInput = new ByteArrayInputStream(invalidXml.getBytes(StandardCharsets.UTF_8));
		InputStream xsdSchema = new ByteArrayInputStream(schema.getBytes(StandardCharsets.UTF_8));
		
		XmlValidator validator = new XmlValidator();

		// WHEN THEN
		Throwable t = assertThrows(SAXException.class,
				()->validator.validate(xmlInput,xsdSchema));
		
		assertEquals("cvc-complex-type.2.4.a: Invalid content was found starting with element 'OTHER_NAME'. One of '{NAME}' is expected.",
				t.getMessage());
		
	}
	
	@Test
	void negativeValidation_withFileIO() throws Exception {
		
		// GIVEN
		File schema = new File("mySchema.xsd");
		File xml    = new File("invalid-instance.xml");
		
		XmlValidator validator = new XmlValidator();
		
		// WHEN THEN
		Throwable t = assertThrows(SAXException.class,
				()->validator.validate(new FileInputStream(xml),new FileInputStream(schema)));
		
		assertEquals("cvc-complex-type.2.4.a: Invalid content was found starting with element 'OTHER_NAME'. One of '{NAME}' is expected.",
				t.getMessage());
	}
	
	private String createXsdSchema() {
		return new StringBuilder()
						.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
						.append("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">")
						.append("<xs:complexType name=\"INSTANCE\">")
						.append("<xs:sequence>")
						.append("<xs:element name=\"NAME\" type=\"xs:string\"/>")
						.append("</xs:sequence>")
						.append("</xs:complexType>")
						.append("<xs:element name=\"INSTANCE\" type=\"INSTANCE\"/>")
						.append("</xs:schema>")
						.toString();
	}

}
