package com.wolfden.java.notetitan.syntax;

import java.util.Hashtable;

import org.eclipse.swt.graphics.Color;

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
		super();
		this.setName(name);
		this.hashTable = hashTable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Hashtable<String, Color> getHashTable() {
		return hashTable;
	}
}
