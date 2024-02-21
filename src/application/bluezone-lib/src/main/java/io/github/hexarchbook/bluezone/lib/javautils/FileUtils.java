package io.github.hexarchbook.bluezone.lib.javautils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class 	FileUtils {

	private FileUtils() { }

	public static Properties propertiesFromFile ( Path propertiesFile ) {
		InputStream inputStream = null;
		try {
			inputStream = Files.newInputStream ( propertiesFile.toRealPath() );
			Properties properties = new Properties();
			properties.load(inputStream);
			return properties;
		} catch ( Exception e ) {
			throw new RuntimeException ( "Error loading properties from file '"+propertiesFile+"'", e);			
		} finally {
			if ( inputStream != null ) {
				try {
					inputStream.close();
				} catch (IOException ioe) {
					throw new RuntimeException ( "Error closing the file '"+propertiesFile+"'", ioe);			
				}
			}
		}
	}

	public static Optional<String> valueOfKeyFromPropertiesFile ( Path aPropertiesFile, String aKey ) {
		String value = propertiesFromFile(aPropertiesFile).getProperty(aKey);
		return Optional.ofNullable(value);
	}

	public static void writeToStandardOutput ( Path file ) {
		try
		( Stream<String> lines = Files.lines(file) )
		{
            String content = lines.collect(Collectors.joining(System.lineSeparator()));
            System.out.println(content);
        } catch (IOException ioe) {
			throw new RuntimeException ( "Error writing the file '"+file+"' to the standard output", ioe);
        }
	}

	public static List<String[]> readColumnsIgnoringHeader(Path file ) {
		try (Scanner scanner = new Scanner(file)) {
			List<String[]> lines = new ArrayList<String[]>();
			if ( scanner.hasNextLine() ) {
				scanner.nextLine();
			}
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] columns = line.split("\\s+");
				lines.add(columns);
			}
			return lines;
		} catch (FileNotFoundException fnfe) {
			throw new RuntimeException("File "+file+" not found",fnfe);
		} catch (IOException ioe) {
			throw new RuntimeException("Error reading the file "+file,ioe);
		}
	}

}
