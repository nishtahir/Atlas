package com.wolfden.java.notetitan;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.LineBackgroundEvent;
import org.eclipse.swt.custom.LineBackgroundListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;

public class NoteTitan implements ShellListener, VerifyKeyListener,
		LineStyleListener, LineBackgroundListener, ModifyListener {
	private static String AppName = "Note Titan";

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
		shlNoteTitan.setSize(450, 300);
		shlNoteTitan.setText("Note Titan - Untitled");
		shlNoteTitan.setLayout(new GridLayout(2, false));

		Menu menu = new Menu(shlNoteTitan, SWT.BAR);
		shlNoteTitan.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmNew = new MenuItem(menu_1, SWT.NONE);
		mntmNew.setText("New");

		MenuItem mntmOpen = new MenuItem(menu_1, SWT.NONE);
		mntmOpen.setText("Open...");

		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save");

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

			}
		});
		mntmQuit.setText("Quit");

		MenuItem mntmEdit = new MenuItem(menu, SWT.NONE);
		mntmEdit.setText("Edit");

		MenuItem mntmView = new MenuItem(menu, SWT.NONE);
		mntmView.setText("View");

		MenuItem mntmAbout = new MenuItem(menu, SWT.NONE);
		mntmAbout.setText("About");

		final StyledText styledText = new StyledText(shlNoteTitan, SWT.BORDER
				| SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
			}
		});

		// Java SWT show Line numbers for StyledText
		// http://stackoverflow.com/questions/11057442/java-swt-show-line-numbers-for-styledtext

		styledText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent event) {

				Device device = Display.getCurrent();
				final RGB LINE_NUMBER_BG = new RGB(160, 80, 0); // brown
				final RGB LINE_NUMBER_FG = new RGB(255, 255, 255); // white
				event.bulletIndex = styledText
						.getLineAtOffset(event.lineOffset);

				// Set the style, 12 pixels wide for each digit
				StyleRange style = new StyleRange();
				style.metrics = new GlyphMetrics(0, 0, Integer.toString(
						styledText.getLineCount() + 2).length() * 12);

				style.background = new Color(device, LINE_NUMBER_BG);
				style.foreground = new Color(device, LINE_NUMBER_FG);
				// Create and set the bullet
				event.bullet = new Bullet(ST.BULLET_NUMBER, style);
			}
		});
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				2, 1));

		final Label lblLine = new Label(shlNoteTitan, SWT.NONE);
		lblLine.setText("Line: 1234");

		final Label lblColumn = new Label(shlNoteTitan, SWT.NONE);
		lblColumn.setText(", Characters: 1234");

		styledText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				lblLine.setText("Line: " + styledText.getLineCount());
				lblColumn.setText(", Characters: " + styledText.getCharCount());
			}
		});
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
}
