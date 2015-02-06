package com.wolfden.java.notetitan;

import java.io.IOException;
import java.util.ResourceBundle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class NoteTitan implements ShellListener, VerifyKeyListener,
		LineStyleListener, LineBackgroundListener, ModifyListener {
	private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("com.wolfden.java.notetitan.messages"); //$NON-NLS-1$
	private static String AppName = BUNDLE.getString("NoteTitan.appName");
	private static NoteTitan applicationInstance;

	private static final int DEFAULT_WINDOW_WIDTH = 640;
	private static final int DEFAULT_WINDOW_HEIGHT = 480;

	protected static Shell shlNoteTitan;
	private StyledText styledText;

	public NoteTitan() {
		
	}

	public static NoteTitan getInstance() {
		if (applicationInstance == null) {
			applicationInstance = new NoteTitan();
		}
		return applicationInstance;
	}
	
	protected StyledText getStyledText(){
		return styledText;
	}
	
	protected Shell getShell(){
		return shlNoteTitan;
	}

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NoteTitan window = getInstance();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display.setAppName(AppName);
		Display display = Display.getDefault();

		Menu systemMenu = Display.getDefault().getSystemMenu();
		if (systemMenu != null) {
			MenuItem preferrences = getSystemItem(systemMenu,
					SWT.ID_PREFERENCES);
			MenuItem about = getSystemItem(systemMenu, SWT.ID_ABOUT);
			preferrences
					.addSelectionListener(new SelectionHelper.Preferences());
			about.addSelectionListener(new SelectionHelper.About(shlNoteTitan));
		}

		createContents();
		shlNoteTitan.open();
		shlNoteTitan.layout();
		while (!shlNoteTitan.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	static MenuItem getSystemItem(Menu menu, int id) {
		for (MenuItem item : menu.getItems()) {
			if (item.getID() == id)
				return item;
		}
		return null;
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlNoteTitan = new Shell();
		shlNoteTitan.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		shlNoteTitan.setText("Untitled");
		GridLayout gl_shlNoteTitan = new GridLayout(1, false);
		gl_shlNoteTitan.horizontalSpacing = 0;
		gl_shlNoteTitan.verticalSpacing = 2;
		gl_shlNoteTitan.marginBottom = 2;
		gl_shlNoteTitan.marginWidth = 0;
		gl_shlNoteTitan.marginHeight = 0;
		shlNoteTitan.setLayout(gl_shlNoteTitan);

		Menu menuBar = new Menu(shlNoteTitan, SWT.BAR);
		shlNoteTitan.setMenuBar(menuBar);

		MenuItem mntmFile = new MenuItem(menuBar, SWT.CASCADE);
		mntmFile.setText(BUNDLE.getString("NoteTitan.mntmFile.text")); //$NON-NLS-1$

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		// Menu Item - New
		MenuItem mntmNew = new MenuItem(menu_1, SWT.NONE);
		mntmNew.setText(BUNDLE.getString("NoteTitan.mntmNew.text")); //$NON-NLS-1$
		mntmNew.setAccelerator(SWT.COMMAND + 'N');

		// Menu Item - Open
		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionHelper.Open());
		mntmOpen.setAccelerator(SWT.COMMAND + 'O');
		mntmOpen.setText(BUNDLE.getString("NoteTitan.mntmOpen.text")); //$NON-NLS-1$

		// Menu Item - Save
		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setAccelerator(SWT.COMMAND + 'S');
		mntmSave.setText(BUNDLE.getString("NoteTitan.mntmSave.text")); //$NON-NLS-1$

		// Menu Item - Quit
		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionHelper.Quit());
		mntmQuit.setAccelerator(SWT.COMMAND + 'Q');
		mntmQuit.setText(BUNDLE.getString("NoteTitan.mntmQuit.text")); //$NON-NLS-1$

		MenuItem mntmEdit = new MenuItem(menuBar, SWT.CASCADE);
		mntmEdit.setText(BUNDLE.getString("NoteTitan.mntmEdit.text")); //$NON-NLS-1$

		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);

		MenuItem mntmUndo = new MenuItem(menu_2, SWT.NONE);
		mntmUndo.setAccelerator(SWT.COMMAND + 'Z');
		mntmUndo.setText(BUNDLE.getString("NoteTitan.mntmUndo.text")); //$NON-NLS-1$

		MenuItem mntmRedo = new MenuItem(menu_2, SWT.NONE);
		mntmRedo.setAccelerator(SWT.COMMAND + 'Y');
		mntmRedo.setText(BUNDLE.getString("NoteTitan.mntmRedo.text")); //$NON-NLS-1$

		MenuItem menuItem = new MenuItem(menu_2, SWT.SEPARATOR);
		menuItem.setText(BUNDLE.getString("NoteTitan.menuItem.text")); //$NON-NLS-1$

		MenuItem mntmCut = new MenuItem(menu_2, SWT.NONE);
		mntmCut.setAccelerator(SWT.COMMAND + 'X');
		mntmCut.addSelectionListener(new SelectionHelper.Cut());
		mntmCut.setText(BUNDLE.getString("NoteTitan.mntmCut.text")); //$NON-NLS-1$

		MenuItem mntmCopy = new MenuItem(menu_2, SWT.NONE);
		mntmCopy.setAccelerator(SWT.COMMAND + 'C');
		mntmCopy.addSelectionListener(new SelectionHelper.Copy());
		mntmCopy.setText(BUNDLE.getString("NoteTitan.mntmCopy.text")); //$NON-NLS-1$

		MenuItem mntmPaste = new MenuItem(menu_2, SWT.NONE);
		mntmPaste.setAccelerator(SWT.COMMAND + 'V');
		mntmPaste.addSelectionListener(new SelectionHelper.Paste());
		mntmPaste.setText(BUNDLE.getString("NoteTitan.mntmPaste.text")); //$NON-NLS-1$

		MenuItem mntmView = new MenuItem(menuBar, SWT.CASCADE);
		mntmView.setText(BUNDLE.getString("NoteTitan.mntmView.text")); //$NON-NLS-1$

		Menu menu = new Menu(mntmView);
		mntmView.setMenu(menu);

		MenuItem mntmWordWrap = new MenuItem(menu, SWT.CHECK);
		mntmWordWrap.setText(BUNDLE.getString("NoteTitan.mntmWordWrap.text")); //$NON-NLS-1$

		MenuItem mntmSelection = new MenuItem(menuBar, SWT.CASCADE);
		mntmSelection.setText(BUNDLE.getString("NoteTitan.mntmSelection.text")); //$NON-NLS-1$

		Menu menu_3 = new Menu(mntmSelection);
		mntmSelection.setMenu(menu_3);

		MenuItem mntmSelectAll = new MenuItem(menu_3, SWT.NONE);
		mntmSelectAll.setAccelerator(AcceleratorUtils.SWT_SELECT_ALL);
		mntmSelectAll.addSelectionListener(new SelectionHelper.SelectAll());
		mntmSelectAll.setText(BUNDLE.getString("NoteTitan.mntmSelectAll.text")); //$NON-NLS-1$

		styledText = new StyledText(shlNoteTitan, SWT.NONE);
		styledText.setTopMargin(4);
		styledText.setRightMargin(4);
		styledText.setLeftMargin(4);
		styledText.setBottomMargin(4);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		Menu contextMenu = new Menu(styledText);
		styledText.setMenu(contextMenu);

		MenuItem mntmCut_1 = new MenuItem(contextMenu, SWT.NONE);
		mntmCut_1.setAccelerator(AcceleratorUtils.SWT_CUT);
		mntmCut_1.setText(BUNDLE.getString("NoteTitan.mntmCut_1.text")); //$NON-NLS-1$

		MenuItem mntmCopy_1 = new MenuItem(contextMenu, SWT.NONE);
		mntmCopy_1.setAccelerator(AcceleratorUtils.SWT_COPY);
		mntmCopy_1.setText(BUNDLE.getString("NoteTitan.mntmCopy_1.text")); //$NON-NLS-1$

		MenuItem mntmPaste_1 = new MenuItem(contextMenu, SWT.NONE);
		mntmPaste_1.setAccelerator(AcceleratorUtils.SWT_PASTE);
		mntmPaste_1.addSelectionListener(new SelectionHelper.Paste());
		mntmPaste_1.setText(BUNDLE.getString("NoteTitan.mntmPaste_1.text")); //$NON-NLS-1$

		MenuItem menuItem_1 = new MenuItem(contextMenu, SWT.SEPARATOR);
		menuItem_1.setText(BUNDLE.getString("NoteTitan.menuItem_1.text")); //$NON-NLS-1$

		MenuItem mntmSelectAll_1 = new MenuItem(contextMenu, SWT.NONE);
		mntmSelectAll_1.setAccelerator(AcceleratorUtils.SWT_SELECT_ALL);
		mntmSelectAll_1.addSelectionListener(new SelectionHelper.SelectAll());
		mntmSelectAll_1.setText(BUNDLE
				.getString("NoteTitan.mntmSelectAll_1.text")); //$NON-NLS-1$

		Label lblNewLabel = new Label(shlNoteTitan, SWT.NONE);
		GridData gd_lblNewLabel = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_lblNewLabel.horizontalIndent = 5;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText(BUNDLE.getString("NoteTitan.lblNewLabel.text")); //$NON-NLS-1$
	}

	@Override
	public void verifyKey(VerifyEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellActivated(ShellEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellClosed(ShellEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellDeactivated(ShellEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellDeiconified(ShellEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void shellIconified(ShellEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lineGetBackground(LineBackgroundEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lineGetStyle(LineStyleEvent event) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyText(ModifyEvent e) {
		
	}

	public void loadFileFromPath(String filePath) {
		try {
			FileUtils.loadFileIntoEditor(filePath, styledText);
		} catch (IOException ioe) {
			ioe.printStackTrace();
			ErrorUtils.showErrorMessageBox(ioe, shlNoteTitan);
		}
	}
}
