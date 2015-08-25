package com.wolfden.java.atlastext.syntax;

import org.eclipse.swt.graphics.Color;

import java.util.Hashtable;

/**
 * This class describes a theme that can be applied in the 
 * editor
 * 
 * @author Nish
 *
 */
public class Theme {
	
	private String name;
	
	/**
	 * Table of values the correspond to keys listed in the language lexer
	 */
	private Hashtable<String, Color> hashTable;
	
	public Theme(String name, Hashtable<String, Color> hashTable) {
		this.setName(name);
		this.hashTable = hashTable;
	}

	/**
	 *
	 * @return name of theme
	 */
	public String getName() {
		return name;
	}

	/**
	 *
	 * @param name sets name of theme
	 */
	public void setName(String name) {
		this.name = name;
	}

    /**
     *
     * @return
     */
	public Hashtable<String, Color> getHashTable() {
		return hashTable;
	}
}
