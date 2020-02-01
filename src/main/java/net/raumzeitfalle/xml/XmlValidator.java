package net.raumzeitfalle.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlValidator {

	private final File schemaFile;

	public XmlValidator(File xsdFile) {
		this.schemaFile = xsdFile;
	}

	public void validate(File xmlFile) throws ParserConfigurationException, SAXException, IOException {

		DOMSource domSource = createDOMSource(xmlFile);
		Schema schema = createSchema();
		Validator validator = createValidator(schema);
		validator.validate(domSource);
		
	}
	
	private Validator createValidator(Schema schema) throws SAXException {
		Validator validator = schema.newValidator();
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		return validator;
	}

	private Schema createSchema() throws SAXException {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		Source schemaSource = new StreamSource(this.schemaFile);
		return factory.newSchema(schemaSource);
	}

	private DOMSource createDOMSource(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();	
		
		documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "false");
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "false");
		
		DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
		
		InputSource source = new InputSource(xmlFile.getAbsolutePath());
		Document document = builder.parse(source);
		return new DOMSource(document);
	}
}
