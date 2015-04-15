package com.wolfden.java.atlas;

import org.eclipse.swt.SWT;

/**
 * Helper class to help manage Menu Accelerators
 * 
 * @author Nish
 *
 */

public class AcceleratorHelper {

	public static final int ATLAS_CUT = SWT.MOD1 + 'X';
	public static final int ATLAS_COPY = SWT.MOD1 + 'C';
	public static final int ATLAS_PASTE = SWT.MOD1 + 'V';

	public static final int ATLAS_SELECT_ALL = SWT.MOD1 + 'A';

	public static final int ATLAS_OPEN = SWT.MOD1 + 'O';
	public static final int ATLAS_NEW = SWT.MOD1 + 'N';
	public static final int ATLAS_SAVE = SWT.MOD1 + 'S';
	public static final int ATLAS_SAVE_AS = SWT.MOD1 + SWT.MOD2 + 'S';
	public static final int ATLAS_QUIT = SWT.MOD1 + 'Q';

	public static final int ATLAS_UNDO = SWT.MOD1 + 'Z';
	public static final int ATLAS_REDO = SWT.MOD1 + SWT.MOD2 + 'Z';

	public static final int ATLAS_FORMAT_CODE = SWT.MOD1 + SWT.MOD2 + 'F';
	
	public static final int ATLAS_INCREASE_FONT = SWT.MOD1 + '+';
	public static final int ATLAS_DECREASE_FONT = SWT.MOD1 + '-';
	public static final int ATLAS_RESET_FONT = SWT.MOD1 + '0';

}
