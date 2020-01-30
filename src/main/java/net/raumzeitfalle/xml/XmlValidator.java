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
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XmlValidator {

	private final File schemaFile;

	public XmlValidator(File xsdFile) {
		this.schemaFile = xsdFile;
	}

	public void validate(File xmlFile) throws Exception {

		DOMSource domSource = createDOMSource(xmlFile);

		Schema schema = createSchema();

		Validator validator = createValidator(schema);

		// validate the DOM tree
		try {
			validator.validate(domSource);
		} catch (SAXException e) {
			// instance document is invalid!
		}

	}

	private Validator createValidator(Schema schema) throws SAXNotRecognizedException, SAXNotSupportedException {
		// create a Validator instance, which can be used to validate an instance
		// document
		Validator validator = schema.newValidator();
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		return validator;
	}

	private Schema createSchema() throws SAXNotRecognizedException, SAXNotSupportedException, SAXException {
		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		
		// load a WXS schema, represented by a Schema instance
		Source schemaSource = new StreamSource(this.schemaFile);
		Schema schema = factory.newSchema(schemaSource);
		return schema;
	}

	private DOMSource createDOMSource(File xmlFile) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();	
		DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
		documentBuilderFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
		Document document = builder.parse(new InputSource(xmlFile.getAbsolutePath()));
		DOMSource domSource = new DOMSource(document);
		return domSource;
	}
}
