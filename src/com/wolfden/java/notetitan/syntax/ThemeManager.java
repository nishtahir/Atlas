package com.wolfden.java.notetitan.syntax;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.swt.graphics.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.wolfden.java.notetitan.exception.UnsupportedXmlException;
import com.wolfden.java.notetitan.util.ColorUtils;

public class ThemeManager {

	private static ThemeManager instance;
	private static List<Theme> themes = new ArrayList<Theme>();
	
	public static ThemeManager getInstance() {
		if (instance == null) {
			instance = new ThemeManager();
		}
		return instance;
	}

	/**
	 * Protected constructor to prevent instantiation
	 */
	protected ThemeManager() {
		try {
			parseXmlFile(new File("assets/Java.xml"));
		} catch (ParserConfigurationException | SAXException | IOException
				| UnsupportedXmlException e) {
			e.printStackTrace();
		}
	}
	
	public List<Theme> getThemes() {
		return themes;
	}


	public static void parseXmlFile(File xml)
			throws ParserConfigurationException, SAXException, IOException,
			UnsupportedXmlException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document document = dBuilder.parse(xml);
		document.getDocumentElement().normalize();

		themes.add(getThemeFromDocument(document));
	}

	private static Theme getThemeFromDocument(Document document)
			throws UnsupportedXmlException {
		Node root = document.getDocumentElement();

		if (!root.getNodeName().equals("theme")) {
			throw new UnsupportedXmlException("Root element must be 'Theme'");
		}
		
		Element rootElement = (Element) root;
		switch (rootElement.getAttribute("language").toLowerCase()) {
		case "java":
			break;
		default:
			throw new UnsupportedXmlException("Unsupported Language");
		}


		Hashtable<String, Color> table = new Hashtable<>();
		NodeList nodeList = document.getElementsByTagName("style");
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) node;
				table.put(e.getAttribute("name"), ColorUtils.getColorFromHex(e.getAttribute("color")));
			}
		}
		
		Theme theme = new Theme(rootElement.getAttribute("name"), table);

		
//		LANGUAGE lang = null;
//		Element rootElement = (Element) root;
//		switch (rootElement.getAttribute("language").toLowerCase()) {
//		case "java":
//			lang = LANGUAGE.JAVA;
//			break;
//		default:
//			throw new UnsupportedXmlException("Unsupported Language");
//		}
//
//		Theme theme = new Theme(rootElement.getAttribute("name"), lang);
//
//		NodeList nodeList = document.getElementsByTagName("style");
//		for (int i = 0; i < nodeList.getLength(); i++) {
//			Node node = nodeList.item(i);
//			if (node.getNodeType() == Node.ELEMENT_NODE) {
//				theme.addStyle(getStyleFromElement((Element) node));
//			}
//		}

		return theme;
	}
}
