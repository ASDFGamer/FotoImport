/**
 * 
 */
package org.asdfgamer.foto_import;



import org.eclipse.swt.widgets.ProgressBar;

import org.asdfgamer.foto_import.hilfreich.*;

/**
 * Dies ist ein Programm, mit dem Fotos importiert werden können.
 * @author wildh
 * @version 0.5
 */
public class Main {
	// Variablen
	
	/**
	 * Dies ist der Pfad an den die Ordner mit den Bildern kopiert werden (z.B. Bilderbibliothek)
	 * @since 0.1
	 */
	private String path_save;
	
	/**
	 * Dies ist der Pfad an dem die Bilder z.Z. liegen.
	 * @since 0.1
	 */
	private String path_load;
	
	/**
	 * Dies ist der Name des Ordners, der erstellt werden soll.
	 * @since 0.1
	 */
	private String ordnername = "kein_name";
	
	/**Gibt an, ob die Dateien umbenannt werden sollen.
	 * @since 0.5
	 */
	private boolean rename = true;
	
	/**
	 * Wird benutzt, um die Einstellungen zu laden und zu speichern.
	 * @since 0.1
	 */
	private Load_Save_txt load_save;
	
	/**
	 * Der Log, für Ausgaben.
	 * @since 0.1
	 */
	private Log log = new Log(super.getClass().getName());
	
	/**
	 * Das GUI der Anwendung
	 * @since 0.1
	 */
	private GUI gui;	
	
	/**
	 * Zum Kopieren der Dateien
	 * @since 0.1
	 */
	private Copy_File copy_file;
	
	/**
	 * Dies ist die Fortschrittsanzeige aus der GUI
	 * @since 0.5
	 */
	private ProgressBar copybar;
	
	/**
	 * Gibt die anzahl der zu kopierenen Dateien an.
	 * @since 0.5
	 */
	private int files;
	
	/**
	 * Ein Object vom Type Scan_Folder.
	 * @since 0.5
	 */
	private Scan_Folder scanfolder;
	
	// Funktionen
	/**
	 * Standardprozedur beim Start
	 * @param args
	 * @since 0.1
	 */
	public static void main(String[] args) {
		Main app = new Main();
		Log.std_setlevel(-1);
		app.init();
		// TODO Auto-generated method stub

	}
	// Setter
	
	/**
	 * Dies setzt den Speicherpfad
	 * @param path Der Pfad
	 * @return true, falls alles hingehauen hat, sonst false
	 * @since 0.1
	 */
	public boolean setpath_save(String path){
		path_save=path;
		return true;
	}
	
	/**
	 * Dies setzt den Pfad von dem die Bilder geladen werden.
	 * @param path Der Pfad
	 * @return true, falls alles hingehauen hat, sonst false
	 * @since 0.1
	 */
	public boolean setpath_load(String path){
		path_load=path;
		return true;
	}
	
	/**
	 * Dies setzt den Ordnername an dem die Bilder gespeichert werden.
	 * @param path Der Ordnername
	 * @return true, falls alles hingehauen hat, sonst false
	 * @since 0.1
	 */
	public boolean setordnername(String path){
		ordnername=path;
		return true;
	}
	
	/**
	 * Dies setzt die Fortschritsanzeige
 	 * @param copyprogress Eine Forschrittsanzeige
	 * @return true, falls alles hingehauen hat, sonst false
	 * @since 0.5
	 */
	public boolean setcopybar(ProgressBar copyprogress){
		copybar=copyprogress;
		return true;
		
	}
	
	// Getter
	
	/**
	 * Gibt den Speicherpfad zurück.
	 * @return Der Speicherpfad
	 * @since 0.1
	 */
	public String getpath_save(){
		return path_save;
	}
	
	/**
	 * Gibt den Ordnernamen zurück.
	 * @return Der Ordnername
	 * @since 0.1
	 */
	public String getordnername(){
		return ordnername;
	}
	
	/**
	 * Gibt den Pfad von dem die Bilder geladen werden zurück.
	 * @return Der Pfad von dem die Bilder geladen werden
	 * @since 0.1
	 */
	public String getpath_load(){
		return path_load;
	}
	
	/**
	 * Dies gibt die zugewiesene Fortschrittsanzeige zurück.
	 * @return  Die zugewiesene Fortschrittsanzeige
	 * @since 0.5
	 */
	public ProgressBar getcopybar(){
		return copybar;
	}
	
	// andere Funktionen
	/**
	 * Dies ist die Funktion die zum Initialisieren benutzt wird.
	 * @since 0.1
	 */
	public void init(){
		log.write("Start");
		load_save = new Load_Save_txt("Einstellungen.txt");
		path_load = load_save.load.load_str("path_load");
		path_save = load_save.load.load_str("path_save");
		rename = load_save.load.load_bool("rename");
		scanfolder = new Scan_Folder(path_load);
		gui = new GUI(this);
		gui.open();
		log.write("Close");
	}
	
	/**
	 * Dies speichert alle Variablen in die Textfile ab.
	 * @return true, falls alles gut ging, sonst false
	 */
	public boolean save(){
		load_save.save.save("path_load", path_load);
		load_save.save.save("path_save", path_save);
		load_save.save.save("rename", rename);
		log.write("Alle Variablen aus 'Main' wurden gespeichert.");
		return true;
	}
	
	/**
	 * Dies Funktion kopiert ohne weitere Überprüfung alle Dateien von path_load nach path_save.
	 * @return true, falls alles gut ging, sonstz false.
	 * @since 0.1
	 */
	public boolean import_all(){
		files = scanfolder.Filecount();
		copy_file = new Copy_File();
		copy_file.set_source(path_load);
		copy_file.set_rename(rename);
		copy_file.set_destination(path_save+"\\"+ordnername);//TODO Win-specific
		if (copy_file.copyfolder()){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean copyprogress(int already_copyed){
		copybar.setSelection((int)(already_copyed/files));
		return true;
	}
	
}
