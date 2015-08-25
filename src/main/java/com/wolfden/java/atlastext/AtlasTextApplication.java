package com.wolfden.java.atlastext;

import com.wolfden.java.atlastext.preferences.PreferenceManager;
import com.wolfden.java.atlastext.swt.SWTResourceManager;
import com.wolfden.java.atlastext.syntax.Indentable;
import com.wolfden.java.atlastext.syntax.StyledTokenReader;
import com.wolfden.java.atlastext.syntax.SyntaxManager;
import com.wolfden.java.atlastext.syntax.java.JavaIndentationListener;
import com.wolfden.java.atlastext.syntax.java.JavaLexer;
import com.wolfden.java.atlastext.syntax.java.JavaTokenReader;
import com.wolfden.java.atlastext.syntax.plaintext.PlainTextTokenReader;
import com.wolfden.java.atlastext.utils.ErrorUtils;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.IOException;

public class AtlasTextApplication {
	private static final String AppName = "Atlas";

	protected Shell shlAtlas;
	private StyledText styledText;
	int numDigits = 3;
	int curActiveLine = 0;
	private Label lblLineCount;
	private Composite composite;
	private int numBlockComments = -1;
	private StyledTokenReader styledTokenReader;
	private PreferenceManager preferenceManager;
	private Label lblPlainText;
	private Indentable listener;
	private boolean unsavedChanges = false;
	private int lnWidth = 5;

	private FileManager fileManager;

	public AtlasTextApplication() {
		preferenceManager = PreferenceManager.getInstance();
		preferenceManager.init();

		fileManager = FileManager.getInstance();
	}

	/**
	 * Launch the application.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AtlasTextApplication window = new AtlasTextApplication();
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
			preferrences.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					showAboutDialog();
				}
			});
			about.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					showAboutDialog();
				}
			});
		}
		createContents();

		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shlAtlas.getBounds();

		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;

		shlAtlas.setLocation(x, y);

		shlAtlas.open();
		shlAtlas.layout();

		//handle closing if there is unsaved changes
		shlAtlas.addListener(SWT.Close, new Listener() {
			public void handleEvent(Event event) {
				if(unsavedChanges)
				{
					MessageBox messageBox = new MessageBox(shlAtlas, SWT.ICON_QUESTION | SWT.YES | SWT.NO | SWT.CANCEL);
					messageBox.setMessage("Would you like to save your changes?");
					messageBox.setText("Unsaved Changes");

					int res = messageBox.open();
					if (res == SWT.CANCEL)
						event.doit = false;
					else if(res == SWT.YES)
						saveAs();
				}
			}
		});

		while (!shlAtlas.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}

		}
	}

	/**
	 * Show
	 */
	protected void showAboutDialog() {
		MessageBox about = new MessageBox(shlAtlas);
		about.setText("About " + AppName);
		about.setMessage("Copyright Nish Tahir 2015. \n Version: "
				+ preferenceManager
				.getPreference(PreferenceManager.KEY_VERSION)
				+ " alpha");
		about.open();
	}

	private static MenuItem getSystemItem(Menu menu, int id) {
		for (MenuItem item : menu.getItems()) {
			if (item.getID() == id)
				return item;
		}
		return null;
	}

	/**
	 * Create contents of the window.
	 *
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		int width = Integer.valueOf(preferenceManager
				.getPreference(PreferenceManager.KEY_WINDOW_WIDTH));
		int height = Integer.valueOf(preferenceManager
				.getPreference(PreferenceManager.KEY_WINDOW_HEIGHT));
		shlAtlas = new Shell();
		shlAtlas.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				int newWidth = shlAtlas.getSize().x;
				int newHeight = shlAtlas.getSize().y;
				preferenceManager.setPreference(
						PreferenceManager.KEY_WINDOW_WIDTH, newWidth);
				preferenceManager.setPreference(
						PreferenceManager.KEY_WINDOW_HEIGHT, newHeight);
				preferenceManager.commit();
			}
		});
		shlAtlas.setSize(width, height);
		shlAtlas.setText("Atlas");
		GridLayout gl_shlAtlas = new GridLayout(1, false);
		gl_shlAtlas.horizontalSpacing = 0;
		gl_shlAtlas.verticalSpacing = 0;
		gl_shlAtlas.marginWidth = 0;
		gl_shlAtlas.marginHeight = 0;
		shlAtlas.setLayout(gl_shlAtlas);

		Menu menu = new Menu(shlAtlas, SWT.BAR);
		shlAtlas.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_2 = new Menu(mntmFile);
		mntmFile.setMenu(menu_2);

		MenuItem mntmNew = new MenuItem(menu_2, SWT.NONE);
		mntmNew.setText("New");
		mntmNew.setAccelerator(AcceleratorHelper.ATLAS_NEW);

		MenuItem mntmOpen = new MenuItem(menu_2, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openFile();
			}
		});
		mntmOpen.setText("Open");
		mntmOpen.setAccelerator(AcceleratorHelper.ATLAS_OPEN);

		MenuItem mntmSave = new MenuItem(menu_2, SWT.NONE);
		mntmSave.setText("Save");
		mntmSave.setAccelerator(AcceleratorHelper.ATLAS_SAVE);
		;

		MenuItem mntmSaveAs = new MenuItem(menu_2, SWT.NONE);
		mntmSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveAs();
			}
		});
		mntmSaveAs.setText("Save As...");
		mntmSaveAs.setAccelerator(AcceleratorHelper.ATLAS_SAVE_AS);
		;

		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");

		Menu menu_3 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_3);

		MenuItem mntmUndo = new MenuItem(menu_3, SWT.NONE);
		mntmUndo.setText("Undo");
		mntmUndo.setAccelerator(AcceleratorHelper.ATLAS_UNDO);
		;

		MenuItem mntmRedo = new MenuItem(menu_3, SWT.NONE);
		mntmRedo.setText("Redo");
		mntmRedo.setAccelerator(AcceleratorHelper.ATLAS_REDO);

		new MenuItem(menu_3, SWT.SEPARATOR);

		MenuItem mntmCut = new MenuItem(menu_3, SWT.NONE);
		mntmCut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cut();
			}
		});
		mntmCut.setText("Cut");
		mntmCut.setAccelerator(AcceleratorHelper.ATLAS_CUT);

		MenuItem mntmCopy = new MenuItem(menu_3, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				copy();
			}
		});
		mntmCopy.setText("Copy");
		mntmCopy.setAccelerator(AcceleratorHelper.ATLAS_COPY);

		MenuItem mntmPaste = new MenuItem(menu_3, SWT.NONE);
		mntmPaste.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paste();
			}
		});
		mntmPaste.setText("Paste");
		mntmPaste.setAccelerator(AcceleratorHelper.ATLAS_PASTE);
		;

		MenuItem mntmView = new MenuItem(menu, SWT.CASCADE);
		mntmView.setText("View");

		Menu menu_6 = new Menu(mntmView);
		mntmView.setMenu(menu_6);

		final MenuItem mntmWordWrap = new MenuItem(menu_6, SWT.CHECK);
		mntmWordWrap.setText("Word wrap");
		mntmWordWrap.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				styledText.setWordWrap(mntmWordWrap.getSelection());
			}
		});

		MenuItem mntmSyntax = new MenuItem(menu_6, SWT.CASCADE);
		mntmSyntax.setText("Syntax");

		Menu menu_7 = createSyntaxMenu(mntmSyntax);
		mntmSyntax.setMenu(menu_7);

		MenuItem mntmSelection = new MenuItem(menu, SWT.CASCADE);
		mntmSelection.setText("Selection");

		Menu menu_4 = new Menu(mntmSelection);
		mntmSelection.setMenu(menu_4);

		MenuItem mntmSelectAll_1 = new MenuItem(menu_4, SWT.NONE);
		mntmSelectAll_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectAll();
			}
		});
		mntmSelectAll_1.setText("Select All");
		mntmSelectAll_1.setAccelerator(AcceleratorHelper.ATLAS_SELECT_ALL);
		;

		styledText = new StyledText(shlAtlas, SWT.V_SCROLL | SWT.H_SCROLL);
		styledText.addLineBackgroundListener(new LineBackgroundListener() {
			public void lineGetBackground(LineBackgroundEvent event) {
				if (styledText.getLineAtOffset(event.lineOffset) == getCurrentLine()){
					// If the cursor is on this line, set this line's background color to the
					// line highlighting color.
					event.lineBackground = SWTResourceManager.getColor(52, 54, 46);
				}else{
					// Otherwise do nothing at all.
					event.lineBackground = null;
				}
			}
		});
		styledText
				.setFont(SWTResourceManager.getFont("Monaco", 12, SWT.NORMAL));
		styledText.setAlwaysShowScrollBars(false);
		styledText.setForeground(SWTResourceManager.getColor(245, 245, 245));
		styledText.setBackground(SWTResourceManager.getColor(39, 40, 34));
		//styledText.setWordWrap(true);
		styledText.addCaretListener(new CaretListener() {
			public void caretMoved(CaretEvent event) {
				// TODO - Implement Line numbering later
				// int activeLine =
				// styledText.getLineAtOffset(event.caretOffset);
				// if (curActiveLine != activeLine) {
				// int digits = 3;
				// if (styledText.getLineCount() > 999)
				// digits = (int) (Math.floor(Math.log10(styledText
				// .getLineCount())) + 1);
				// int width = digits * 12;
				// styledText.redraw(0, styledText.getLinePixel(activeLine),
				// width, styledText.getLineHeight(), true);
				// styledText.redraw(0,
				// styledText.getLinePixel(curActiveLine), width,
				// styledText.getLineHeight(), true);
				// curActiveLine = activeLine;
				// }
			}
		});
		styledText.addExtendedModifyListener(new ExtendedModifyListener() {
			public void modifyText(ExtendedModifyEvent event) {
				lblLineCount.setText("Lines: " + styledText.getLineCount()
						+ ", Characters: " + styledText.getCharCount());
				composite.layout();
				unsavedChanges = true;
				// TODO - Implement Line numbering later
				// int digits = 3;
				// int lineCount = styledText.getLineCount();
				// if (lineCount > 999)
				// digits = (int) (Math.floor(Math.log10(styledText
				// .getLineCount())) + 1);
				// if (numDigits != digits) {
				// numDigits = digits;
				// styledText.redraw();
				// return;
				// }
				// int startLine = styledText.getLineAtOffset(event.start);
				// int endLine = styledText.getLineAtOffset(event.start
				// + event.length);
				// if (startLine != endLine
				// || event.replacedText.contains(System.lineSeparator())) {
				// styledText.redraw(0, styledText.getLinePixel(startLine),
				// digits * 12, styledText.getLineHeight()
				// * (lineCount - startLine), true);
				// }

			}
		});

		styledText.setWrapIndent(lnWidth*10);
		styledText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent e) {

				parseSyntax(e);

				// TODO - Implement Line numbering later
				// int activeLine = styledText.getLineAtOffset(styledText.getCaretOffset());
				// int currentLine = styledText.getLineAtOffset(event.lineOffset);
				// event.bulletIndex = currentLine;
				// int width = 36;
				//
				// if (styledText.getLineCount() > 999)
				// width = (int) ((Math.floor(Math.log10(styledText.getLineCount())) + 1) * 12);
				// // Set the style, 12 pixles wide for each digit
				// StyleRange style = new StyleRange();
				// style.metrics = new GlyphMetrics(0, 0, width);
				// if (activeLine == currentLine) {
				// // style.background = Theme.highlightedLineColor;
				// }
				// event.bullet = new Bullet(ST.BULLET_NUMBER, style);
				e.bulletIndex = styledText.getLineAtOffset(e.lineOffset);

				int oldWidth = lnWidth;
				if(styledText.getLineCount() > 99) {
					lnWidth = (int) ((Math.floor(Math.log10(styledText.getLineCount())) + 1) * 12);
				}
				if(oldWidth > lnWidth)
				{
					styledText.setWrapIndent(lnWidth*10);
					styledText.redraw();
				}

				StyleRange style = new StyleRange();
				style.metrics = new GlyphMetrics(0, 0, 50);
				style.foreground = new org.eclipse.swt.graphics.Color(null, 52, 186, 207);

				e.bullet = new Bullet(ST.BULLET_NUMBER,style);

			}
		});
		styledText.setBottomMargin(8);
		styledText.setTopMargin(8);
		styledText.setRightMargin(8);
		styledText.setLeftMargin(8);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1));

		Menu menu_1 = new Menu(styledText);
		styledText.setMenu(menu_1);

		MenuItem mntmCut_1 = new MenuItem(menu_1, SWT.NONE);
		mntmCut_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cut();
			}
		});
		mntmCut_1.setText("Cut");

		MenuItem mntmCopy_1 = new MenuItem(menu_1, SWT.NONE);
		mntmCopy_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				copy();
			}
		});
		mntmCopy_1.setText("Copy");

		MenuItem mntmPaste_1 = new MenuItem(menu_1, SWT.NONE);
		mntmPaste_1.setText("Paste");
		mntmPaste_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paste();
			}
		});

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmSelectAll = new MenuItem(menu_1, SWT.NONE);
		mntmSelectAll.setText("Select All");
		mntmSelectAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectAll();
			}
		});

		composite = new Composite(shlAtlas, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(105, 105, 105));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		GridLayout gl_composite = new GridLayout(2, false);
		gl_composite.marginRight = 4;
		gl_composite.marginLeft = 4;
		gl_composite.marginBottom = 2;
		gl_composite.marginHeight = 2;
		composite.setLayout(gl_composite);

		lblLineCount = new Label(composite, SWT.SHADOW_IN);
		lblLineCount.setBackground(SWTResourceManager.getColor(105, 105, 105));
		lblLineCount.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		lblLineCount.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblLineCount.setText("Lines: 0, Characters: 0");

		lblPlainText = new Label(composite, SWT.NONE);
		lblPlainText.setForeground(SWTResourceManager.getColor(245, 245, 245));
		lblPlainText.setText(preferenceManager.getPreference("language"));
		lblPlainText.setBackground(SWTResourceManager.getColor(105, 105, 105));

		Menu menu_5 = createSyntaxMenu(lblPlainText);
		lblPlainText.setMenu(menu_5);

		MenuItem mntmText = new MenuItem(menu_6, SWT.CASCADE);
		mntmText.setText("Text size");

		Menu menu_9 = new Menu(mntmText);
		mntmText.setMenu(menu_9);

		MenuItem mntmIncreaseFontSize = new MenuItem(menu_9, SWT.NONE);
		mntmIncreaseFontSize.setText("Increase");
		mntmIncreaseFontSize.setAccelerator(AcceleratorHelper.ATLAS_INCREASE_FONT);

		MenuItem mntmDecreaseFontSize = new MenuItem(menu_9, SWT.NONE);
		mntmDecreaseFontSize.setText("Decrease");
		mntmDecreaseFontSize.setAccelerator(AcceleratorHelper.ATLAS_DECREASE_FONT);

		new MenuItem(menu_9, SWT.SEPARATOR);

		MenuItem mntmReset = new MenuItem(menu_9, SWT.NONE);
		mntmReset.setText("Reset");
		mntmReset.setAccelerator(AcceleratorHelper.ATLAS_RESET_FONT);

		MenuItem mntmSource = new MenuItem(menu, SWT.CASCADE);
		mntmSource.setText("Source");

		Menu menu_8 = new Menu(mntmSource);
		mntmSource.setMenu(menu_8);

		MenuItem mntmFormat = new MenuItem(menu_8, SWT.NONE);
		mntmFormat.setText("Format");
		mntmFormat.setAccelerator(AcceleratorHelper.ATLAS_FORMAT_CODE);
		mntmFormat.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				super.widgetSelected(e);
				formatCode(styledText);
			}
		});
		// mntmPlainText.addSelectionListener(new SelectionAdapter() {
		// @Override
		// public void widgetSelected(SelectionEvent e) {
		// preferenceManager.setPreference("language", "text");
		// lblPlainText.setText("Plain text");
		// styledText.redraw();
		// }
		// });
	}

	private Menu createSyntaxMenu(MenuItem menuItem) {
		return populateSyntaxMenu(new Menu(menuItem));
	}

	/**
	 *
	 * @param parent
	 * @return
	 */
	private Menu createSyntaxMenu(Control parent) {
		return populateSyntaxMenu(new Menu(parent));
	}

	private Menu populateSyntaxMenu(Menu syntaxMenu) {
		for (final String language : SyntaxManager.SUPPORTED_LANGUAGES) {
			MenuItem item = new MenuItem(syntaxMenu, SWT.NONE);
			item.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					super.widgetSelected(e);
					preferenceManager.setPreference(
							PreferenceManager.KEY_LANGUAGE, language);
					lblPlainText.setText(language);
					composite.layout();
					styledText.redraw();
				}
			});
			item.setText(language);
		}
		return syntaxMenu;
	}

	/**
	 *
	 * @param event
	 */
	protected void parseSyntax(LineStyleEvent event) {
		String language = preferenceManager.getPreference("language");
		styledTokenReader = getTokenReader(language);
		ANTLRInputStream input = new ANTLRInputStream(styledText.getText());
		JavaLexer lexer = new JavaLexer(input);
		lexer.removeErrorListeners();

		CommonTokenStream tokens = new CommonTokenStream(lexer);
		event.styles = styledTokenReader.getStyles(tokens, event.styles);

	}

	public StyledTokenReader getTokenReader(String language) {
		switch (language) {
			case "Java":
				return new JavaTokenReader();
			case "C":
//				return new CTokenReader();
			default:
				return new PlainTextTokenReader();
		}
	}

	protected void openFile() {
		FileDialog fileDialog = new FileDialog(shlAtlas, SWT.OPEN);
		fileDialog.setText("Open...");
		String filePath = fileDialog.open();
		if (filePath != null) {
			// FIXME - Not the best design because of the tight coupling
			// but it's 3AM... I'm tired :p
			try {
				styledText.setText(fileManager.openFile(filePath));
			} catch (IOException e) {
				ErrorUtils.showErrorMessageBox(e, shlAtlas);
				e.printStackTrace();
			}
		}
	}

	public void formatCode(StyledText text) {
		String language = preferenceManager.getPreference("language");
		if (language.equals("Java")) {
			listener = new JavaIndentationListener();
			listener.updateIndentation(text);
		}
	}

	/**
	 * Save as command
	 */
	public void saveAs() {
		FileDialog fileDialog = new FileDialog(shlAtlas, SWT.SAVE);
		String fileName = fileManager.getActiveFileName();

		if (fileName != null) {
			fileDialog.setFileName(fileName);
		}

		String temp = fileDialog.open();
		if (temp != null) {
			fileName = temp;
			try {
				fileManager.saveFileAs(fileName, styledText.getText());
				unsavedChanges = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// updateWindowTitle();
	}

	public int getCurrentLine(){
		int pos = styledText.getCaretOffset();
		// For readability, get the line number into a temp variable
		int line = styledText.getLineAtOffset(pos);
		return line;
	}

	/**
	 * Copy command
	 */
	public void copy() {
		styledText.copy();
	}

	/**
	 * Cut command
	 */
	public void cut() {
		styledText.cut();
	}

	/**
	 * Paste command
	 */
	public void paste() {
		styledText.paste();
	}

	/**
	 * Select all text command
	 */
	public void selectAll() {
		styledText.selectAll();
	}


}
