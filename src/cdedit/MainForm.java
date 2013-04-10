/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cdedit;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import org.fife.ui.rsyntaxtextarea.ActiveLineRangeEvent;
import org.fife.ui.rsyntaxtextarea.ActiveLineRangeListener;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import static java.awt.event.InputEvent.CTRL_DOWN_MASK;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author Lachlan
 */
public class MainForm extends JFrame
{

    private JTabbedPane mainTabs;
    private HashMap<String, RSyntaxTextArea> tabs;
    private HashMap<Integer, String> tabPages;
    private HashMap<String, Boolean> changedTabs;
    private HashMap<String, String> tabFiles;
    private HashMap<String, String> tabLangs;
    private HashMap<String, String> langExt;
    private HashMap<String, JButton> tabCBtn;

    public MainForm()
    {
	initGUI();

	newTab();

	setVisible(true);
    }

    private void newTab()
    {
	String newTab = "New";

	addTab(newTab);
    }

    private void initGUI()
    {


	mainTabs = new JTabbedPane();
	tabs = new HashMap<String, RSyntaxTextArea>();
	tabPages = new HashMap<Integer, String>();
	changedTabs = new HashMap<String, Boolean>();
	tabFiles = new HashMap<String, String>();
	tabLangs = new HashMap<String, String>();
	langExt = new HashMap<String, String>();
	tabCBtn = new HashMap<String, JButton>();

	JMenuBar menubar = setupMenu();
	JPanel topBar = new JPanel();
	JButton closeBtn = new JButton("(x)");
	//closeBtn.setFont(Font.);
	topBar.add(closeBtn, BorderLayout.EAST);
	closeBtn.addMouseListener(new MouseListener()
	{
	    @Override
	    public void mouseClicked(MouseEvent me)
	    {
		//throw new UnsupportedOperationException("Not supported yet.");
		closeCurrentTab();
	    }

	    @Override
	    public void mousePressed(MouseEvent me)
	    {
		//throw new UnsupportedOperationException("Not supported yet.");
	    }

	    @Override
	    public void mouseReleased(MouseEvent me)
	    {
		//throw new UnsupportedOperationException("Not supported yet.");
	    }

	    @Override
	    public void mouseEntered(MouseEvent me)
	    {
		//throw new UnsupportedOperationException("Not supported yet.");
	    }

	    @Override
	    public void mouseExited(MouseEvent me)
	    {
		//throw new UnsupportedOperationException("Not supported yet.");
	    }
	});
	getContentPane().add(topBar, BorderLayout.NORTH);
	getContentPane().add(mainTabs, BorderLayout.CENTER);

	setJMenuBar(menubar);
	setTitle("Syntax Editor");
	setSize(1024, 700);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    private HashMap<String, String> langs;

    private JMenuBar setupMenu()
    {
	//String tst = SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86;
	langs = new HashMap<String, String>();
	setupLangs();
	//String[] langs = new String[]{"SYNTAX_STYLE_ACTIONSCRIPT","SYNTAX_STYLE_ASSEMBLER_X86"};

	JMenuBar menubar = new JMenuBar();

	JMenu file_menu = new JMenu("File");
	file_menu.setMnemonic(KeyEvent.VK_F);
	JMenuItem menu_file_new = new JMenuItem("New");
	menu_file_new.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	menu_file_new.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent event)
	    {
		newTab();
	    }
	});
	file_menu.add(menu_file_new);
	JMenuItem menu_file_open = new JMenuItem("Open");
	menu_file_open.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent event)
	    {
		final JFileChooser fc = new JFileChooser();
		fc.setDragEnabled(true);
		int returnVal = fc.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
		    File op = fc.getSelectedFile();
		    if (op.exists())
		    {
			addTab(op);
		    }
		}
	    }
	});
	menu_file_open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	file_menu.add(menu_file_open);
	JMenuItem menu_file_save = new JMenuItem("Save");
	menu_file_save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	menu_file_save.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent event)
	    {
		int ctab = mainTabs.getSelectedIndex();
		String tabName = mainTabs.getTitleAt(ctab);
		File f = new File(tabFiles.get(tabName));
		if (f.exists())
		{
		    PrintWriter write = null;
		    try
		    {
			write = new PrintWriter(f);
			write.write(tabs.get(tabName).getDocument().getText(0, tabs.get(tabName).getDocument().getLength()));
			write.flush();
			write.close();
		    }
		    catch (BadLocationException ex)
		    {
			Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    catch (FileNotFoundException ex)
		    {
			Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
		    }
		    finally
		    {
			write.close();
		    }
		}
		else
		{
		    String langName = "Document";
		    Iterator it = langs.entrySet().iterator();
		    //System.out.println(tabs.get(tabName).getSyntaxEditingStyle());
		    while (it.hasNext())
		    {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getValue().equals(tabs.get(tabName).getSyntaxEditingStyle()))
			{
			    langName = (String) pairs.getKey();
			    break;
			}
		    }
		    //System.out.println(langExt.get(tabs.get(tabName).getSyntaxEditingStyle()));
		    FileFilter ft = new FileNameExtensionFilter(langName, langExt.get(tabs.get(tabName).getSyntaxEditingStyle()));

		    final JFileChooser fc = new JFileChooser();
		    JCheckBox cb = new JCheckBox("Add Extendsion");
		    cb.setSelected(true);
		    fc.setAccessory(cb);
		    fc.addChoosableFileFilter(ft);

		    int returnVal = fc.showSaveDialog(null);
		    if (returnVal == JFileChooser.APPROVE_OPTION)
		    {
			try
			{
			    File file = fc.getSelectedFile();
			    if (cb.isSelected())
			    {
				String nn = file.getAbsolutePath() + "." + langExt.get(tabs.get(tabName).getSyntaxEditingStyle());
				file = new File(nn);
			    }

			    if (file.exists())
			    {
				file.delete();
			    }
			    file.createNewFile();
			    String ntn = file.getName();

			    PrintWriter write = new PrintWriter(file);
			    write.write(tabs.get(tabName).getDocument().getText(0, tabs.get(tabName).getDocument().getLength()));
			    write.flush();
			    write.close();

			    tabFiles.put(ntn, file.getCanonicalPath());
			    tabFiles.remove(tabName);
			    tabPages.put(ctab, ntn);
			    tabs.put(ntn, tabs.get(tabName));
			    tabs.remove(tabName);
			    changedTabs.put(ntn, changedTabs.get(tabName));
			    changedTabs.remove(ntn);
			    mainTabs.setTitleAt(ctab, ntn);
			}
			catch (BadLocationException ex)
			{
			    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (IOException ex)
			{
			    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			}
		    }
		}
	    }
	});
	file_menu.add(menu_file_save);
	JMenuItem menu_file_close = new JMenuItem("Close");
	//menu_file_close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent., ActionEvent.CTRL_MASK));
	menu_file_close.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent event)
	    {
		closeCurrentTab();
	    }
	});
	file_menu.add(menu_file_close);
	JMenuItem menu_file_quit = new JMenuItem("Exit");
	menu_file_quit.setMnemonic(KeyEvent.VK_Q);
	menu_file_quit.setToolTipText("Exit application");
	menu_file_quit.addActionListener(new ActionListener()
	{
	    @Override
	    public void actionPerformed(ActionEvent event)
	    {
		System.exit(0);
	    }
	});
	file_menu.add(menu_file_quit);
	menubar.add(file_menu);

	JMenu lang_menu = new JMenu("Language");
	Iterator it = langs.entrySet().iterator();
	while (it.hasNext())
	{
	    Map.Entry pairs = (Map.Entry) it.next();
	    JMenuItem menu_itm_lng = new JMenuItem((String) pairs.getKey());
	    menu_itm_lng.addActionListener(langMenuEventHandler);
	    lang_menu.add(menu_itm_lng);
	}
	menubar.add(lang_menu);

	return menubar;
    }
    ActionListener langMenuEventHandler = new ActionListener()
    {
	@Override
	public void actionPerformed(ActionEvent ae)
	{
	    //System.out.println(ae.getActionCommand());
	    Iterator it = langs.entrySet().iterator();
	    while (it.hasNext())
	    {
		try
		{
		    Map.Entry pairs = (Map.Entry) it.next();
		    String key = (String) pairs.getKey();
		    if (key.equalsIgnoreCase(ae.getActionCommand()))
		    {
			int ctab = mainTabs.getSelectedIndex();
			String tabName = mainTabs.getTitleAt(ctab);
			tabs.get(tabName).setSyntaxEditingStyle((String) pairs.getValue());

			break;
		    }
		}
		catch (Exception er)
		{
		}
	    }
	}
    };

    private void closeCurrentTab()
    {
	int ctab = mainTabs.getSelectedIndex();
	String tabName = mainTabs.getTitleAt(ctab);
	tabPages.remove(ctab);
	tabs.remove(tabName);
	changedTabs.remove(tabName);
	tabFiles.remove(tabName);
	mainTabs.remove(ctab);

	if (mainTabs.getTabCount() == 0)
	{
	    newTab();
	}
    }

    private void setupLangs()
    {
	langs.put("Actionscript", "text/actionscript");
	langExt.put("text/actionscript", "as");

	langs.put("Asm", "text/asm");
	langExt.put("text/asm", "asm");

	langs.put("BBCode", "text/bbcode");
	langExt.put("text/bbcode", "txt");

	langs.put("C", "text/c");
	langExt.put("text/c", "c");

	langs.put("Clojure", "text/clojure");
	langExt.put("text/clojure", "cloj");

	langs.put("CPP", "text/cpp");
	langExt.put("text/cpp", "cpp");

	langs.put("CS", "text/cs");
	langExt.put("text/cs", "cs");

	langs.put("CSS", "text/css");
	langExt.put("text/css", "css");

	langs.put("Delphi", "text/delphi");
	langExt.put("text/delphi", "delphi");

	langs.put("Dtd", "text/dtd");
	langExt.put("text/dtd", "dtd");

	langs.put("Fortran", "text/fortran");
	langExt.put("text/fortran", "fortran");

	langs.put("Groovy", "text/groovy");
	langExt.put("text/groovy", "groove");

	langs.put("HTML", "text/html");
	langExt.put("text/html", "html");

	langs.put("Java", "text/java");
	langExt.put("text/java", "java");

	langs.put("Javascript", "text/javascript");
	langExt.put("text/javascript", "js");

	langs.put("JSP", "text/jsp");
	langExt.put("text/jsp", "jsp");

	langs.put("Latex", "text/latex");
	langExt.put("text/latex", "latex");

	langs.put("Lisp", "text/lisp");
	langExt.put("text/lisp", "lisp");

	langs.put("Lua", "text/lua");
	langExt.put("text/lua", "lua");

	langs.put("Makefile", "text/makefile");
	langExt.put("text/makefile", "");

	langs.put("MXML", "text/mxml");
	langExt.put("text/mxml", "mxml");

	langs.put("Plain", "text/plain");
	langExt.put("text/plain", "txt");

	langs.put("Perl", "text/perl");
	langExt.put("text/perl", "pl");

	langs.put("PHP", "text/php");
	langExt.put("text/php", "php");

	langs.put("Properties", "text/properties");
	langExt.put("text/properties", "properties");

	langs.put("Python", "text/python");
	langExt.put("text/python", "py");

	langs.put("Ruby", "text/ruby");
	langExt.put("text/ruby", "ruby");

	langs.put("SAS", "text/sas");
	langExt.put("text/sas", "sass");

	langs.put("Scala", "text/scala");
	langExt.put("text/scala", "scala");

	langs.put("SQL", "text/sql");
	langExt.put("text/sql", "sql");

	langs.put("TCL", "text/tcl");
	langExt.put("text/tcl", "tcl");

	langs.put("Unix", "text/unix");
	langExt.put("text/unix", "sh");

	langs.put("Bat", "text/bat");
	langExt.put("text/bat", "bat");

	langs.put("XML", "text/xml");
	langExt.put("text/xml", "xml");

    }

    private void addTab(String title)
    {
	addTab(title, "", null);
    }

    private void addTab(final String title, final String lang, final File path)
    {
	SwingUtilities.invokeLater(new Runnable()
	{
	    @Override
	    public void run()
	    {
		if (tabPages.containsValue(title) && !title.equals("New"))
		{
		    Iterator it = tabPages.entrySet().iterator();
		    while (it.hasNext())
		    {
			Map.Entry pairs = (Map.Entry) it.next();
			String val = (String) pairs.getValue();
			if (val.equalsIgnoreCase(title))
			{

			    mainTabs.setSelectedIndex(Integer.parseInt(String.valueOf(pairs.getKey())));
			    break;
			}
		    }
		}
		else
		{
		    int i = 1;
		    String newTitle = title;
		    if (tabs.containsKey(newTitle) && newTitle.startsWith("New"))
		    {
			//System.out.println("tst");
			while (true)
			{
			    if (tabs.containsKey(newTitle))
			    {
				//System.out.println("tst");
				newTitle = "New(" + String.valueOf(i) + ")";
				i++;
			    }
			    else
			    {
				break;
			    }
			}
		    }
		    //System.out.println(newTitle);
		    RSyntaxTextArea newText = new RSyntaxTextArea();
		    if (!lang.isEmpty())
		    {
			newText.setSyntaxEditingStyle(lang);
			newText.setCodeFoldingEnabled(true);
			newText.setAntiAliasingEnabled(true);
		    }
		    tabPages.put(mainTabs.getTabCount() - 1, newTitle);
		    tabs.put(newTitle, newText);
		    changedTabs.put(newTitle, false);
		    if (path != null)
		    {
			if (path.exists())
			{
			    BufferedReader rdr = null;
			    try
			    {
				tabFiles.put(newTitle, path.getAbsolutePath());
				rdr = new BufferedReader(new FileReader(path));
				String curLine;

				while ((curLine = rdr.readLine()) != null)
				{
				    newText.getDocument().insertString(newText.getDocument().getLength(), curLine + "\n", null);
				}
			    }
			    catch (BadLocationException ex)
			    {
				Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			    }
			    catch (IOException ex)
			    {
				Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
			    }
			    finally
			    {
				try
				{
				    rdr.close();
				}
				catch (IOException ex)
				{
				    Logger.getLogger(MainForm.class.getName()).log(Level.SEVERE, null, ex);
				}
			    }

			}
			else
			{
			    tabFiles.put(newTitle, "");
			}
		    }
		    else
		    {
			tabFiles.put(newTitle, "");
		    }
		    
		    mainTabs.addTab(newTitle, new RTextScrollPane(newText));
		    mainTabs.setSelectedIndex(mainTabs.getTabCount()-1);

		}
		//mainTabs.addTab(title, this);
	    }
	});
    }
    
    private void addTab(final File file)
    {
	SwingUtilities.invokeLater(new Runnable()
	{
	    @Override
	    public void run()
	    {
		if (file.exists())
		{
		    String fileName = file.getName();
		    int mid = fileName.lastIndexOf(".");
		    String fname = fileName.substring(0, mid);
		    String ext = fileName.substring(mid + 1, fileName.length());
		    Iterator it = langs.entrySet().iterator();
		    //System.out.println(tabs.get(tabName).getSyntaxEditingStyle());
		    String lang = "text/plain";
		    while (it.hasNext())
		    {
			Map.Entry pairs = (Map.Entry) it.next();
			String val = (String)pairs.getKey();
			//System.out.println(val);
			if (val.equalsIgnoreCase(ext))
			{
			    lang = (String)pairs.getValue();
			    break;
			}
		    }
		    addTab(fileName, (String) lang, file);
		}
	    }
	});
    }
}
