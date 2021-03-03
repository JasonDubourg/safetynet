package com.safetynet.api.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.exceptions.DataRepositoryException;
import com.safetynet.api.model.Database;

@Repository
public class DataRepository {

	// Permet de mapper un Json en objet Java.
	private static final ObjectMapper objectMapper = new ObjectMapper();

	private static final Logger logger = LogManager.getLogger(DataRepository.class);
	public static Database database;

	String jsonFile = "data.json";
	private boolean commit = true;

	// On va lire le fichier Json
	public DataRepository() throws IOException {
		this.init();
	}

	public void init() {
		try (InputStream ips = ClassLoader.getSystemResourceAsStream(jsonFile)) {
			System.out.println(ClassLoader.getSystemResource(jsonFile));
			database = objectMapper.readerFor(Database.class).readValue(ips);
			logger.info("OK - FILE_OPEN : " + jsonFile);
		} catch (FileNotFoundException e) {
			logger.info("KO - FILE_NOT_FOUND INIT:" + jsonFile);
			throw new DataRepositoryException("KO - FILE_NOT_FOUND", e);
		} catch (IOException e) {
			logger.info("KO - I/O ERROR :" + jsonFile);
			throw new DataRepositoryException("KO - I/O ERROR", e);
		}
	}

	public void commit() {
		// Ecriture sur fichier json
		if (commit) {
			// Récupérer le path du JSON
			System.out.println(ClassLoader.getSystemResource(jsonFile));
			URL url = ClassLoader.getSystemResource(jsonFile);
			// On ouvre un flux d'écriture vers le fichier JSON
			try (OutputStream ops = new FileOutputStream(Paths.get(url.toURI()).toFile())) {
				// Ecriture du fichier JSON avec formatage
				// (WithDefaultPrettyPrinter)
				objectMapper.writerWithDefaultPrettyPrinter().writeValue(ops, database);
				logger.info("OK - fichier JSON  mis à jour " + jsonFile);
			} catch (FileNotFoundException e) {
				logger.info("KO - FILE_NOT_FOUND" + jsonFile);
				throw new DataRepositoryException("KO - FILE_NOT_FOUND", e);
			} catch (IOException e) {
				logger.info("KO - I/O ERROR" + jsonFile);
				throw new DataRepositoryException("KO - I/O ERROR", e);
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	// Autorisation de modifier la velur de commit
	public void setCommit(boolean commit) {
		this.commit = commit;
	}

}
