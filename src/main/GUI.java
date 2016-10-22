package main;



import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import hilfreich.Log;

/**
 * Diese Klasse ist für die Darstellung des GUIs zuständig mithilfe von SWT
 * @author wildh
 * @version 0.5
 */
public class GUI {
	
	//Variablen
	/**
	 * Das Objekt, dass das GUI aufruft
	 * @since 0.1
	 */
	Main obj;
	
	/**
	 * Das Standarddisplay
	 * @since 0.1
	 */
	Display display;
	
	/**
	 * Die Standardshell
	 * @since 0.1
	 */
	Shell shell;
	
	/**
	 * Die Standardschriftart, hier Arial, 14
	 * @since 0.1
	 */
	Font default_font;
	
	/**
	 * Der Log
	 * @since 0.1
	 */
	private Log log = new Log(super.getClass().getName());
	
	//Konstruktoren
	
	/**
	 * Der Standardkonstruktor, erwartet Main als Argument 
	 * @param objekt
	 * * @since 0.1
	 */
	public GUI(Main objekt){
		obj=objekt;
		init();
	}
	
	//Setter
	
	//Getter
	
	//andere Funktionen
	
	/**
	 * Dies wird vom Konstruktor aufgerufen, wenn das Objekt erstellt wird
	 * @since 0.1
	 */
	private void init(){
		display = new Display();
	    shell = new Shell(display);
	    default_font = new Font(display,"Arial", 14, SWT.BOLD );
	    shell.setText("Foto Import");
	    // erstelle ein Layout
	    GridLayout layout = new GridLayout(3, false);
	    shell.setSize(300, 210);
	    // füge das Layout der shell hinzu.
	    shell.setLayout(layout);
	    
	    // Erstelle eine Überschrift
	    Label headline = new Label(shell, SWT.NONE);
	    headline.setText("Bilder import");
	    headline.setFont( default_font );
	    GridData data_headline = new GridData(SWT.CENTER, SWT.CENTER, true, false, 3, 1);
	    headline.setLayoutData(data_headline);
	    
	    
	    // Erstelle ein neues Label, das als Separator benutzt wird.
	    Label seperator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
	    seperator1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	    
	    // erstelle ein Textfeld für den Ordnernamen
	    Text ordner = new Text(shell, SWT.NONE);
	    ordner.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3,1));
	    ordner.setFont(default_font);
	    ordner.setText("Name");
	    ordner.setToolTipText("Hier musst du den Ordnernamen den du haben möchtest eingeben.");
	    ordner.addModifyListener(new ModifyListener(){
			@Override
			public void modifyText(ModifyEvent event) {
				Text name = (Text) event.widget;
				obj.setordnername(name.getText());
			}
	    	
	    });
	    
	    
	    // erstelle einen Button, der alle Bilder importiert.
	    Button bilder_import = new Button(shell, SWT.PUSH);
	    bilder_import.setText("Alle Bilder importieren");
	    bilder_import.setFont( default_font );
	    bilder_import.setToolTipText("Mit diesem Knopf kopiert du alle Bilder in den Ordner den du oben angegeben hast.");
	    bilder_import.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 3,1));

	    
	    // erstelle einen Ladebalken, der den jetzigen Zustand des Kopiervorganges anzeigt.
	    ProgressBar copy_progress = new ProgressBar(shell, SWT.SMOOTH);
	    copy_progress.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 3,1));
	    //setzt den Fortschritt auf Null.
	    copy_progress.setSelection(0);
	    //erstellt ein 'Tag' mit dem Namen.
	    copy_progress.setData("Name", "copy_progress");
	    obj.setcopybar(copy_progress);
	    
	    //Fügt einen Event Listener zu dem Button hinzu.
	    bilder_import.addSelectionListener(new SelectionAdapter() {
	        @Override
	        public void widgetSelected(SelectionEvent e) {
	            log.write("Alle Bilder sollen importiert werden.");
	            copy_progress.setSelection(0);
	            if (obj.import_all()){
	            	log.write("Alle Bilder wurden importiert.");
	            	bilder_import.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
	            }else{
	            	log.write("Es wurden nicht alle Bilder importiert!",2);
	            	bilder_import.setBackground(display.getSystemColor(SWT.COLOR_RED));
	            }
	        }
	    });	
	    
	    // Erstelle ein neues Label, das als Separator benutzt wird.
	    Label seperator2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
	    seperator2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
	    
	    // Ein Hilfe Knopf
	    Button hilfe = new Button(shell, SWT.PUSH);
	    hilfe.setText("Hilfe");
	    hilfe.setFont(default_font);
	    hilfe.setToolTipText("Mit diesem Knopf rufst du die Hilfe auf.");
	    hilfe.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false, 3,1));
	    hilfe.addSelectionListener(new SelectionAdapter(){
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		log.write("Die Hilfe wird geöfnet");
	    		openhelp();
	    	}
	    });
	}
	
	/**
	 * Hiermit wird das GUI angezeigt
	 * @since 0.1
	 */
	public void open(){
		shell.open();
		while(!shell.isDisposed()){
			if (!display.readAndDispatch ()) 
				display.sleep ();
		}
		display.dispose();
		
	}
	
	/**
	 * Dies erzeugt ein neues Label
	 * @param text Dies ist der Text des Labels
	 * @return Das neu erzeugte Label
	 * @since 0.1
	 */
	protected Label addLabel(String text, Shell shell){
		Label label = new Label(shell, SWT.NONE);
		label.setText(text);
		label.setFont(new Font(display,"Arial", 14, SWT.NONE));
		return label;
	}
	
	/**
	 * Dies erzeugt ein neues Textfeld
	 * @param inhalt Dies ist der Text des Textfelds
	 * @return Das neu erzeugte Textfeld
	 * @since 0.1
	 */
	protected Text addText(String inhalt, Shell shell){
		Text text = new Text(shell, SWT.NONE);
		text.setText(inhalt);
		text.setFont(new Font(display,"Arial", 14, SWT.NONE));
		return text;
	};
	
	/**
	 * Dies öffnet ein Fenter als Hilfe.
	 * @since 0.1
	 */
	@SuppressWarnings("unused")
	private void openhelp(){
		//Shell
		Shell shell_help = new Shell(display);
		shell_help.setFont(default_font);
		shell_help.setText("Hilfe zu Foto Import");
		shell_help.setSize(1000,300);
		
		//Layout
		RowLayout layout_help = new RowLayout();
		layout_help.pack=false;
		shell_help.setLayout(layout_help);
		
		//Inhalt
		Label text1 = addLabel("Dies ist eine Hilfe zu dem Foto Import Programm. ",shell_help);
			text1.setFont(default_font);
		Label text2 = addLabel("",shell_help);
		Label text3 = addLabel("In dem Feld in dem am Anfang \" Name \" steht musst du den Ordnernamen eingeben.",shell_help);
		Label text4 = addLabel("Als nächstes musst du einfach auf \" Alle Bilder importieren \" drücken. ",shell_help);
		Label text5 = addLabel("Wenn alles gut ging, dann färbt sich der Button grün, sonst rot.",shell_help);
		Label text6 = addLabel("Wenn der Button Rot ist, kannst du versuchen das Problem zu beheben, indem du nochmal auf den Button drückst.",shell_help);
		Label text7 = addLabel("Ansonsten frag einfach nach.",shell_help);
		Label text9 = addLabel("",shell_help);
		Label text10 = addLabel("Info, es existiert eine Logdatei und eine Einstellungsdatei im Ordner des Programms.",shell_help);
		Label text11 = addLabel("von Christoph, Version 0.5",shell_help);
		
		//Öffnet das Fenter
		shell_help.open();
		
		//wie bei  open()(sorgt für das schließen)
		while(!shell_help.isDisposed()){
			if (!display.readAndDispatch ()) 
				display.sleep ();
		}
		shell_help.dispose();
		
	}
}
