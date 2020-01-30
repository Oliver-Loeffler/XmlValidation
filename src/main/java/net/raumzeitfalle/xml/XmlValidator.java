package net.raumzeitfalle.xml;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XmlValidator {

	private final File schema;

	public XmlValidator(File xsdFile) {
		this.schema = xsdFile;
	}

	public void validate(File xmlFile) throws Exception {

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setAttribute(XMLConstants.FEATURE_SECURE_PROCESSING, true);

		DocumentBuilder parser = documentBuilderFactory.newDocumentBuilder();
		Document document = parser.parse(xmlFile);

		// create a SchemaFactory capable of understanding WXS schemas
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		// load a WXS schema, represented by a Schema instance
		Source schemaFile = new StreamSource(schema);
		Schema schema = factory.newSchema(schemaFile);

		// create a Validator instance, which can be used to validate an instance
		// document
		Validator validator = schema.newValidator();

		// validate the DOM tree
		try {
			validator.validate(new DOMSource(document));
		} catch (SAXException e) {
			// instance document is invalid!
		}

	}
}
