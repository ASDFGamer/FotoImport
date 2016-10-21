package main;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.widgets.ProgressBar;

import hilfreich.Log;

/**
 * Diese Klasse soll für das kopieren von Dateien und Ordnern zuständig sein.
 * @author wildh
 * @version 0.5
 */
public class Copy_File extends File_utils{
	//Variablen
	
	/**
	 * Der Pfad der Quelldatei.
	 * @since 0.1
	 */
	private Path source;
	
	/**
	 * Der Zielpfad der Dateien
	 * @since 0.1
	 */
	private Path destination;
	
	/**
	 * Bestimmt, ob die Dateien nach dem Ordnernamen umbenannt wrden nach dem Muster "Ordnername""alter Name"
	 * @since 0.5
	 */
	private boolean rename = false;
	
	/**
	 * Zeigt an wie viele Dateien schon kopiert wurden
	 * @since 0.5
	 */
	private int files_copyed = 0;
	
	/**
	 * Der Log für die anzeige von Nachrichten.
	 * @since 0.1
	 */
	private Log log = new Log(super.getClass().getName());
	
	//Konstruktoren
	
	/**
	 * Dies ist der Standardkonstruktor
	 * @since 0.1
	 */
	public Copy_File(){
		init();
	}
	
	//Setter
	
	/**
	 * Dies wird verwendet, um den Pfad der Quelldatei zu setzen.
	 * @param quelle Der Pfad zu der Quelldatei
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean set_source(Path quelle){
		source=quelle;
		return true;
	}
	
	/**
	 * Dies wird verwendet, um den Pfad der Zieldatei zu setzen.
	 * @param quelle Der Pfad zu der Zieldatei
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean set_destination(Path ziel){
		destination=ziel;
		return true;
	}
	
	/**
	 * Dies wird verwendet, um den Pfad der Quelldatei zu setzen.
	 * @param quelle Der Pfad zu der Quelldatei
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean set_source(String quelle){
		source = Paths.get(quelle);
		return true;
	}
	
	/**
	 * Dies wird verwendet, um den Pfad der Zieldatei zu setzen.
	 * @param quelle Der Pfad zu der Zieldatei
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean set_destination(String ziel){
		destination = Paths.get(ziel);
		return true;
	}
	
	/**
	 * Dies wird verwendet, um den festzulegen, ob der Dateiname verändert werden soll.
	 * @param change gibt an ob der Name verändert wird.
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.5
	 */
	public boolean set_rename(boolean change){
		rename = change;
		return true;
	}
	
	//Getter
	
	/**
	 * Dies wird verwendet, um den Pfad der Quelldatei abzufragen
	 * @return der Pfad der Quelldatei
	 * @since 0.1
	 */
	public Path get_source(){
		return source;
	}
	
	/**
	 * Dies wird verwendet, um den Pfad der Zieldatei abzufragen
	 * @return der Pfad der Zieldatei
	 * @since 0.1
	 */
	public Path get_destination(){
		return destination;
	}
	
	/**
	 * Dies wird verwendet um abzufragen, ob der Dateiname geändert wird.
	 * @return ob der Dateiname geändert wird.
	 * @since 0.5
	 */
	public boolean get_rename(){
		return rename;
	}
	
	/**
	 * Dies wird verwendet um abzzufragen, wie viele Dateien schon kopiert wurden.
	 * @return die Anzahl der kopierten Dateien
	 * @since 0.5
	 */
	public int get_files_copyed(){
		return files_copyed;
	}
	
	//andere Funktionen
	
	/**
	 * Diese Funktion wird bei der Initialisierung vom Konstruktor aufgerufen.
	 * @since 0.1
	 */
	private void init(){
		log.write("Copy_File wurde erstellt");
	}
	
	/**
	 * Dies kopiert alles von source nach destination.
	 * Falls source ein Ordner ist, dann wird der gesamte Ordner kopiert und sonst nur eine Datei.
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean copy(){
		return copy(source,destination);
	}
	
	/**
	 * Dies kopiert alles von quelle nach ziel.
	 * Falls quelle ein Ordner ist, dann wird der gesamte Ordner kopiert und sonst nur eine Datei.
	 * @param quelle Der jetzige Ort
	 * @param ziel Das Ziel
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean copy(Path quelle, Path ziel){
		boolean fehler =false;
		if (Files.isDirectory(quelle)){
			log.write(quelle.toUri()+" ist ein Ordner",-1);
			fehler=!copyfolder(quelle,ziel);
		} else if (Files.exists(quelle)){
			log.write(quelle.toUri()+" ist eine Datei",-1);
			fehler=!copyfile(quelle,ziel);
		} else {
			log.write("Der Pfad " + source.toUri() + " führt ins nichts!",1);
			fehler=true;
		}
		
		if (fehler){
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * Diese Funktion kopiert eine Datei von Source zu destination.
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean copyfile(){
		return copyfile(source,destination);
	}
	
	
	/**
	 * Diese Funktion kopiert quelle nach ziel. 
	 * Eigentlich sollte für die meisten Fälle {@link Copy_File#copyfile()} benutzt werden.
	 * @param quelle Der jetzige Ort der Datei
	 * @param ziel Der neue ort der Datei
	 * @return true, falls alles gut ging, sonst false.
	 * @since 0.1
	 */
 	public boolean copyfile(Path quelle, Path ziel){
		try {
			log.write(ziel.toUri()+" das Ziel",-1);
			if (rename){
				String URI=ziel.getRoot().toString()+File.separator;
				for (int i=0 ; i<ziel.getNameCount();i++ ){
					if (i<ziel.getNameCount()-1){
						URI=URI+ziel.getName(i)+File.separator;
					}else{
						URI=URI+ziel.getName(i-1)+ziel.getName(i);
					}
				}
				log.write(URI + " die URI",-1);
				ziel=Paths.get(URI);
			}

			Files.copy(quelle, ziel);
		} catch (IOException e) {
			log.write("Das kopieren der Datei " + quelle.getFileName()+ " nach " + ziel.toUri()+" hat nicht geklappt.",2);
			return false;
		}
		log.write("Es wurde die Datei " + quelle.getFileName().toString() + " nach " + ziel.getFileName().toString() + " kopiert.");
		files_copyed=files_copyed+1;
		return true;
	}
	
	/**
	 * Kopiert alle Dateien aus einem Ordner in einen anderen Ordner.
	 * @return true, wenn alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean copyfolder(){
		return copyfolder(source,destination);
	}
	
	/**
	 * Kopiert alle Dateien aus einem Ordner in einen anderen Ordner.
	 * @param quelle der jetzige Ordner
	 * @param ziel der Zielordner
	 * @return true, wenn alles gut ging, sonst false.
	 * @since 0.1
	 */
	public boolean copyfolder(Path quelle, Path ziel){
		check_folder_exist(ziel);
		boolean fehler=false;
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(quelle)) {
		    for (Path file: stream) {
		        log.write("im Ordner " + quelle.getFileName().toString() + " ist die Datei " +file.getFileName());
		        if(!copy(Paths.get(quelle.toString()+File.separator+file.getFileName()),Paths.get(ziel.toString()+File.separator+file.getFileName()))){
		        	fehler=true;
		        };
		    }
		} catch (IOException | DirectoryIteratorException e) {
			log.write("Das kopieren des Ordners " + quelle.getFileName()+" hat nicht geklappt.",2);
			return false;
		}
		if (fehler){
			return false;
		} else {
			return true;
		}
	}
	
}
