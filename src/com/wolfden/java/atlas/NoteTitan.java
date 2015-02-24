package com.wolfden.java.atlas;

import java.io.IOException;
import java.util.List;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CaretEvent;
import org.eclipse.swt.custom.CaretListener;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import com.wolfden.java.atlas.syntax.java.JavaLexer;
import com.wolfden.java.atlas.syntax.java.JavaTokenReader;

public class NoteTitan {

	protected Shell shlNoteTitan;
	private StyledText styledText;
	int numDigits = 3;
	int curActiveLine = 0;
	private Label lblLineCount;
	private Composite composite;
	private int numBlockComments = -1;
	private JavaTokenReader listener;

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
		Display.setAppName("Note Titan");
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

			createContents();
			shlNoteTitan.open();
			shlNoteTitan.layout();
			while (!shlNoteTitan.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		}
	}

	protected void showAboutDialog() {
		MessageBox about = new MessageBox(shlNoteTitan);
		about.setText("About Note Titan");
		about.setMessage("Copyright Nish Tahir 2015. \n Version 0.1 alpha");
		about.open();
	}

	/**
	 * Create contents of the window.
	 * 
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shlNoteTitan = new Shell();
		shlNoteTitan.setSize(640, 480);
		shlNoteTitan.setText("Note Titan");
		GridLayout gl_shlNoteTitan = new GridLayout(1, false);
		gl_shlNoteTitan.horizontalSpacing = 0;
		gl_shlNoteTitan.verticalSpacing = 0;
		gl_shlNoteTitan.marginWidth = 0;
		gl_shlNoteTitan.marginHeight = 0;
		shlNoteTitan.setLayout(gl_shlNoteTitan);

		Menu menu = new Menu(shlNoteTitan, SWT.BAR);
		shlNoteTitan.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_2 = new Menu(mntmFile);
		mntmFile.setMenu(menu_2);

		MenuItem mntmNew = new MenuItem(menu_2, SWT.NONE);
		mntmNew.setText("New");
		mntmNew.setAccelerator(SelectionHelper.SWT_NEW);

		MenuItem mntmOpen = new MenuItem(menu_2, SWT.NONE);
		mntmOpen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openFile();
			}
		});
		mntmOpen.setText("Open");
		mntmOpen.setAccelerator(SelectionHelper.SWT_OPEN);

		MenuItem mntmSave = new MenuItem(menu_2, SWT.NONE);
		mntmSave.setText("Save");
		mntmSave.setAccelerator(SelectionHelper.SWT_SAVE);
		;

		MenuItem mntmSaveAs = new MenuItem(menu_2, SWT.NONE);
		mntmSaveAs.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveAs();
			}
		});
		mntmSaveAs.setText("Save As...");
		mntmSaveAs.setAccelerator(SelectionHelper.SWT_SAVE_AS);
		;

		MenuItem mntmEdit = new MenuItem(menu, SWT.CASCADE);
		mntmEdit.setText("Edit");

		Menu menu_3 = new Menu(mntmEdit);
		mntmEdit.setMenu(menu_3);

		MenuItem mntmUndo = new MenuItem(menu_3, SWT.NONE);
		mntmUndo.setText("Undo");
		mntmUndo.setAccelerator(SelectionHelper.SWT_UNDO);
		;

		MenuItem mntmRedo = new MenuItem(menu_3, SWT.NONE);
		mntmRedo.setText("Redo");
		mntmRedo.setAccelerator(SelectionHelper.SWT_REDO);
		;

		new MenuItem(menu_3, SWT.SEPARATOR);

		MenuItem mntmCut = new MenuItem(menu_3, SWT.NONE);
		mntmCut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cut();
			}
		});
		mntmCut.setText("Cut");
		mntmCut.setAccelerator(SelectionHelper.SWT_CUT);

		MenuItem mntmCopy = new MenuItem(menu_3, SWT.NONE);
		mntmCopy.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				copy();
			}
		});
		mntmCopy.setText("Copy");
		mntmCopy.setAccelerator(SelectionHelper.SWT_COPY);

		MenuItem mntmPaste = new MenuItem(menu_3, SWT.NONE);
		mntmPaste.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				paste();
			}
		});
		mntmPaste.setText("Paste");
		mntmPaste.setAccelerator(SelectionHelper.SWT_PASTE);
		;

		MenuItem mntmView = new MenuItem(menu, SWT.NONE);
		mntmView.setText("View");

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
		mntmSelectAll_1.setAccelerator(SelectionHelper.SWT_SELECT_ALL);
		;

		styledText = new StyledText(shlNoteTitan, SWT.V_SCROLL | SWT.H_SCROLL);
		styledText
				.setFont(SWTResourceManager.getFont("Monaco", 12, SWT.NORMAL));
		styledText.setAlwaysShowScrollBars(false);
		styledText.setForeground(SWTResourceManager.getColor(245, 245, 245));
		styledText.setBackground(SWTResourceManager.getColor(39, 40, 34));
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
		styledText.addLineStyleListener(new LineStyleListener() {
			public void lineGetStyle(LineStyleEvent event) {

				parseSyntax(event);

				// TODO - Implement Line numbering later
				// int activeLine = styledText.getLineAtOffset(styledText
				// .getCaretOffset());
				// int currentLine =
				// styledText.getLineAtOffset(event.lineOffset);
				// event.bulletIndex = currentLine;
				// int width = 36;
				//
				// if (styledText.getLineCount() > 999)
				// width = (int) ((Math.floor(Math.log10(styledText
				// .getLineCount())) + 1) * 12);
				// // Set the style, 12 pixles wide for each digit
				// StyleRange style = new StyleRange();
				// style.metrics = new GlyphMetrics(0, 0, width);
				// if (activeLine == currentLine) {
				// // style.background = Theme.highlightedLineColor;
				// }
				// event.bullet = new Bullet(ST.BULLET_NUMBER, style);
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

		composite = new Composite(shlNoteTitan, SWT.NONE);
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
		lblLineCount.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1));
		lblLineCount.setForeground(SWTResourceManager.getColor(245, 245, 245));
		lblLineCount.setText("Lines: 0, Characters: 0");
		
		Label lblPlainText = new Label(composite, SWT.NONE);
		lblPlainText.setForeground(SWTResourceManager.getColor(245, 245, 245));
		lblPlainText.setText("Plain Text");
		
		Menu menu_5 = new Menu(lblPlainText);
		lblPlainText.setMenu(menu_5);
		
		MenuItem mntmJava = new MenuItem(menu_5, SWT.NONE);
		mntmJava.setText("Java");
		
		MenuItem mntmPlainText = new MenuItem(menu_5, SWT.NONE);
		mntmPlainText.setText("Plain Text");

	}

	protected void parseSyntax(LineStyleEvent event) {
		listener = new JavaTokenReader();
		ANTLRInputStream input = new ANTLRInputStream(styledText.getText());
		JavaLexer lexer = new JavaLexer(input);
		lexer.removeErrorListeners();

		CommonTokenStream tokens = new CommonTokenStream(lexer);

		event.styles = listener.getStyles(tokens, event.styles);

		List<Token> blockComments = tokens.getTokens(0, tokens.size() - 1,
				JavaLexer.COMMENT);
		if ((blockComments != null && blockComments.size() != numBlockComments)
				|| (blockComments == null && numBlockComments != 0)) {
			numBlockComments = blockComments == null ? 0 : blockComments.size();
			styledText.redraw();
		}
	}

	protected void openFile() {
		FileDialog fileDialog = new FileDialog(shlNoteTitan, SWT.OPEN);
		fileDialog.setText("Open...");
		String filePath = fileDialog.open();
		if (filePath != null) {
			// FIXME - Not the best design because of the tight coupling
			// but it's 3AM... I'm tired :p
			try {
				styledText.setText(FileManager.openFile(filePath));
			} catch (IOException e) {
				ErrorUtils.showErrorMessageBox(e, shlNoteTitan);
				e.printStackTrace();
			}
		}
	}

	public void saveAs() {
		FileDialog dlg = new FileDialog(shlNoteTitan, SWT.SAVE);
		String fileName = FileManager.getFileName();

		if (fileName != null) {
			dlg.setFileName(fileName);
		}

		String temp = dlg.open();
		if (temp != null) {
			fileName = temp;
			try {
				FileManager.saveFileAs(fileName, styledText.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// updateWindowTitle();
	}

	public void copy() {
		styledText.copy();
	}

	public void cut() {
		styledText.cut();
	}

	public void paste() {
		styledText.paste();
	}

	public void selectAll() {
		styledText.selectAll();
	}

	static MenuItem getSystemItem(Menu menu, int id) {
		for (MenuItem item : menu.getItems()) {
			if (item.getID() == id)
				return item;
		}
		return null;
	}
}
