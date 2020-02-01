package net.raumzeitfalle.xml;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XmlValidator {

	public void validate(InputStream xmlStream, InputStream xsdStream) throws SAXException, IOException {
		Schema schema = createSchema(xsdStream);
		Source source = createSource(xmlStream);
		Validator validator = createValidator(schema);
		validator.validate(source);
	}

	private Source createSource(InputStream xmlStream) {
		return new StreamSource(xmlStream);
	}

	private Validator createValidator(Schema schema) throws SAXException {
		Validator validator = schema.newValidator();
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		return validator;
	}

	private Schema createSchema(InputStream schema) throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		Source schemaSource = new StreamSource(schema);
		return factory.newSchema(schemaSource);
	}

}
