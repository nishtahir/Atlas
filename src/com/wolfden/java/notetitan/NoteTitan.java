package com.wolfden.java.notetitan;

import java.io.File;
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
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class NoteTitan implements ShellListener, VerifyKeyListener,
		LineStyleListener, LineBackgroundListener, ModifyListener {
	private static final ResourceBundle BUNDLE = ResourceBundle
			.getBundle("com.wolfden.java.notetitan.messages"); //$NON-NLS-1$
	private static String AppName = BUNDLE.getString("NoteTitan.appName");
	private static final int DEFAULT_WINDOW_WIDTH = 640;
	private static final int DEFAULT_WINDOW_HEIGHT = 480;

	private String filePath;
	private StyledText styledText;
	protected Shell shlNoteTitan;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			NoteTitan window = new NoteTitan();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlNoteTitan.open();
		shlNoteTitan.layout();
		while (!shlNoteTitan.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlNoteTitan = new Shell();
		shlNoteTitan.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		shlNoteTitan.setText(AppName + " - Untitled");
		GridLayout gl_shlNoteTitan = new GridLayout(1, false);
		gl_shlNoteTitan.horizontalSpacing = 0;
		gl_shlNoteTitan.verticalSpacing = 2;
		gl_shlNoteTitan.marginBottom = 2;
		gl_shlNoteTitan.marginWidth = 0;
		gl_shlNoteTitan.marginHeight = 0;
		shlNoteTitan.setLayout(gl_shlNoteTitan);

		Menu menu = new Menu(shlNoteTitan, SWT.BAR);
		shlNoteTitan.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText(BUNDLE.getString("NoteTitan.mntmFile.text")); //$NON-NLS-1$

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNew = new MenuItem(menu_1, SWT.NONE);
		mntmNew.setText(BUNDLE.getString("NoteTitan.mntmNew.text")); //$NON-NLS-1$

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.addSelectionListener(new Open());
		mntmOpen.setText(BUNDLE.getString("NoteTitan.mntmOpen.text")); //$NON-NLS-1$

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText(BUNDLE.getString("NoteTitan.mntmSave.text")); //$NON-NLS-1$

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		mntmQuit.setText(BUNDLE.getString("NoteTitan.mntmQuit.text")); //$NON-NLS-1$

		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText(BUNDLE.getString("NoteTitan.mntmEdit.text")); //$NON-NLS-1$

		Menu menu_2 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_2);

		MenuItem mntmCut = new MenuItem(menu_2, SWT.NONE);
		mntmCut.setText(BUNDLE.getString("NoteTitan.mntmCut.text")); //$NON-NLS-1$

		MenuItem mntmCopy = new MenuItem(menu_2, SWT.NONE);
		mntmCopy.setText(BUNDLE.getString("NoteTitan.mntmCopy.text")); //$NON-NLS-1$

		MenuItem mntmPaste = new MenuItem(menu_2, SWT.NONE);
		mntmPaste.setText(BUNDLE.getString("NoteTitan.mntmPaste.text")); //$NON-NLS-1$

		MenuItem mntmView = new MenuItem(menu, SWT.NONE);
		mntmView.setText(BUNDLE.getString("NoteTitan.mntmView.text")); //$NON-NLS-1$

		styledText = new StyledText(shlNoteTitan, SWT.NONE);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

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
		// TODO Text has been modified. Do not quit without saving

	}

	class Open implements SelectionListener {
		@Override
		public void widgetSelected(SelectionEvent e) {
			FileDialog fileDialog = new FileDialog(shlNoteTitan, SWT.OPEN);
			fileDialog.setText("Open...");
			filePath = fileDialog.open();

			if(filePath != null){
				//File file = FileUtils.getFile(filePath);
				try {
					FileUtils.loadFileIntoEditor(filePath, styledText);
					ErrorUtils.showErrorMessageBox(new IOException(), shlNoteTitan);
				} catch (IOException ioe) {
					ioe.printStackTrace();
					ErrorUtils.showErrorMessageBox(ioe, shlNoteTitan);
				}
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

	class New implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class Save implements SelectionListener {

		@Override
		public void widgetSelected(SelectionEvent e) {

		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {

		}

	}

}
