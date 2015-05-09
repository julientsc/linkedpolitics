package ch.eiafr.tsc.data.global.job.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.log4j.Logger;

public class SerializeObject<T> {

	static Logger log = Logger.getLogger(SerializeObject.class);
	
	public void write(T obj, File file) {
		try {
			FileOutputStream fichier = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			log.info("Write texte file ("+file.getAbsolutePath()+") success");
		} catch (FileNotFoundException e) {
			log.error("Cannot write file at " + file.getAbsolutePath() + " (FileNotFoundException)");
		} catch (IOException e) {
			log.error("Cannot write file at " + file.getAbsolutePath() + " (IOException)");
		}
	}

	public T read(File f) {

		try {
			final FileInputStream fichier = new FileInputStream(f);
			ObjectInputStream ois = new ObjectInputStream(fichier);
			
			@SuppressWarnings("unchecked")
			T obj = (T) ois.readObject();
			ois.close();
			log.info("Read texte file ("+f.getAbsolutePath()+") success");
			
			return obj;
		} catch (final java.io.IOException e) {
			log.error("Cannot read file at " + f.getAbsolutePath());
		} catch (final ClassNotFoundException e) {
			log.error("Cannot found class to serialize file (" + f.getAbsolutePath()+")");
		}
		
		return null;
	}

}