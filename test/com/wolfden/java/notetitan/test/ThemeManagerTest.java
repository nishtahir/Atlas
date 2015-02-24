package com.wolfden.java.notetitan.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

import com.wolfden.java.atlas.exception.UnsupportedXmlException;
import com.wolfden.java.atlas.syntax.ThemeManager;

public class ThemeManagerTest {

	@Test
	public void test_ReadXML() throws ParserConfigurationException, SAXException, IOException, UnsupportedXmlException {
		ThemeManager.parseXmlFile(new File("assets/Java.xml"));
	}

}
