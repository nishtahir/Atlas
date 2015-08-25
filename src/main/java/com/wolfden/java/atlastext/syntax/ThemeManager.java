package com.wolfden.java.atlastext.syntax;

import com.wolfden.java.atlastext.exception.UnsupportedXmlException;
import com.wolfden.java.atlastext.utils.ColorUtils;
import com.wolfden.java.atlastext.utils.FileUtils;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 *
 */
public class ThemeManager {

	private static ThemeManager instance;
	private static List<Theme> themes = new ArrayList<>();
	
	public static ThemeManager getInstance() {
		if (instance == null) {
			instance = new ThemeManager();
		}
		return instance;
	}

	protected ThemeManager() {
		try {
			parseXmlFile(new File(FileUtils.getSystemCompatiblePath("res/asset/java.xml")));
		} catch (ParserConfigurationException | SAXException | IOException
				| UnsupportedXmlException e) {
			e.printStackTrace();
		}
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
				table.put(e.getAttribute("name"), ColorUtils.getColorFromHex(Display.getCurrent(), e.getAttribute("color")));
			}
		}

		return new Theme(rootElement.getAttribute("name"), table);
	}

    public List<Theme> getThemes() {
        return themes;
    }
}
