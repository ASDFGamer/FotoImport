package org.asdfgamer.foto_import;

import java.io.File;

import org.asdfgamer.foto_import.hilfreich.Log;

/**
 * Diese Klasse soll einen Ordner nach bestimmten Eigenschaften durchsuchen können.
 * @author wildh
 * @version 0.1
 */
public class Scan_Folder extends File_utils{
	
	/**
	 * Dies ist der Ordner der gescannt werden soll.
	 * Damit dieser nicht geändert werden kann ist er konstant.
	 * @since 0.1
	 */
	private final String folder;
	
	/**
	 * Der Log für die anzeige von Nachrichten.
	 * @since 0.1
	 */
	private Log log = new Log(super.getClass().getName());
	
	//Konstruktoren
	/**
	 * Der Standardkonstruktor
	 * @since 0.0
	 */
	public Scan_Folder(String folder){
		log.write("Es wurde ein neuer Ordnerscanner erstellt.");
		this.folder=folder;
		log.write("Der dazugehörige Ordner ist " + folder);
	}
	
	//Setter
	
	
	//Getter
	
	public String get_folder(){
		return folder;
	}
	
	//andere Methoden
	/**
	 * Diese Methode wird zum Initialisieren benutzt.
	 * @return true falls alles hingehauen hat, sonst false
	 * @since 0.1
	 */
	public boolean init(){
		return true;
	}
	
	/**
	 * Diese Methode gibt die anzahl der Dateien in einem Ordner und seinen Unterordnern zurück
	 */
	public int Filecount() {
	    int anzahl=0;
		File f = new File(folder);
	    File[] files = f.listFiles();

	    if (files != null)
	    for (int i = 0; i < files.length; i++) {
	    	anzahl++;
	        File file = files[i];

	        if (file.isDirectory()) {   
	        	Filecount_r(file.getAbsolutePath(),anzahl); 
	        }
	    }
	    return anzahl;
	}
	
	private void Filecount_r(String path,int anzahl) {
	    File f = new File(path);
	    File[] files = f.listFiles();

	    if (files != null)
	    for (int i = 0; i < files.length; i++) {
	    	anzahl++;
	        File file = files[i];

	        if (file.isDirectory()) {   
	        	Filecount_r(file.getAbsolutePath(),anzahl); 
	        }
	    }
	}
}
