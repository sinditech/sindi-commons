/**
 * 
 */
package za.co.sindi.commons.utils;

import java.io.IOException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Buhake Sindi
 * @since 14 January 2024
 */
public class XMLUtils {

	private XMLUtils() {
		//TODO: Absolutely nothing...
	}
	
	public static Document createDocument(final URL resource) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document xmlDocument = builder.parse(resource.openStream());
		return xmlDocument;
	}
	
	public static final XPath newXPath() {
		return XPathFactory.newInstance().newXPath();
	}
	
	public static Boolean evaluateAsBoolean(final Document xmlDocument, final String xPathExpression) throws XPathExpressionException {
		return evaluateAsBoolean(xmlDocument, newXPath(), xPathExpression);
	}
	
	public static Node evaluateAsNode(final Document xmlDocument, final String xPathExpression) throws XPathExpressionException {
		return evaluateAsNode(xmlDocument, newXPath(), xPathExpression);
	}
	
	public static NodeList evaluateAsNodeList(final Document xmlDocument, final String xPathExpression) throws XPathExpressionException {
		return evaluateAsNodeList(xmlDocument, newXPath(), xPathExpression);
	}
	
	public static Double evaluateAsNumber(final Document xmlDocument, final String xPathExpression) throws XPathExpressionException {
		return evaluateAsNumber(xmlDocument, newXPath(), xPathExpression);
	}
	
	public static String evaluateAsString(final Document xmlDocument, final String xPathExpression) throws XPathExpressionException {
		return evaluateAsString(xmlDocument, newXPath(), xPathExpression);
	}
	
	public static Boolean evaluateAsBoolean(final Document xmlDocument, final XPath xPath, final String xPathExpression) throws XPathExpressionException {
		return (Boolean) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.BOOLEAN);
	}
	
	public static Node evaluateAsNode(final Document xmlDocument, final XPath xPath, final String xPathExpression) throws XPathExpressionException {
		return (Node) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NODE);
	}
	
	public static NodeList evaluateAsNodeList(final Document xmlDocument, final XPath xPath, final String xPathExpression) throws XPathExpressionException {
		return (NodeList) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NODESET);
	}
	
	public static Double evaluateAsNumber(final Document xmlDocument, final XPath xPath, final String xPathExpression) throws XPathExpressionException {
		return (Double) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.NUMBER);
	}
	
	public static String evaluateAsString(final Document xmlDocument, final XPath xPath, final String xPathExpression) throws XPathExpressionException {
		return (String) xPath.compile(xPathExpression).evaluate(xmlDocument, XPathConstants.STRING);
	}
}
