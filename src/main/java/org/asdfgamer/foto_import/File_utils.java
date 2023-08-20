package org.asdfgamer.foto_import;

import java.nio.file.Files;
import java.nio.file.Path;

import org.asdfgamer.foto_import.hilfreich.Log;

/**
 * Diese Klasse soll viele verschiedene Funktionen haben, welche häufig bei Dateien/Ordnern gebraucht werden.
 * @author wildh
 * @version 0.1
 */
public abstract class File_utils {
	
	/**
	 * Der Log für die anzeige von Nachrichten.
	 * @since 0.1
	 */
	private Log log = new Log(super.getClass().getName());
	
	/**
	 * Diese Funktion überprüft, ob es sich bei der Datei auf die der Pfad zeigt um ein Duplikat von einer schon kopierten Datei handelt.
	 * @param pfad Der Pfad der Datei
	 * @return true, falls es ein Duplikat ist und false, falls es kein Duplikat ist.
	 * @since 0.1
	 */
	public boolean check_duplicat(Path pfad){
		//pfad.has
		return true;
	}
	
	/**
	 * Dies überprüft, ob ein Pfad existiert und erstellt ihn, falls er noch nicht existiert.
	 * @since 0.1
	 */
	public void check_folder_exist(Path pfad){
		if (!Files.isDirectory(pfad)){
			if(!pfad.toFile().mkdirs()){
				log.write("Es konnte nicht die gesamte Ordnerstruktur von " + pfad.toUri() + " erstellt werden.",1); //TODO? Ordner trotzdem mit "name"2 erstellen und dann den Pfad ändern.
			};
		}
		
	}
}
