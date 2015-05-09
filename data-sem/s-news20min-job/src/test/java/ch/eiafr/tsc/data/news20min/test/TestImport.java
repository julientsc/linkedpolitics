package ch.eiafr.tsc.data.news20min.test;

import info.aduna.iteration.Iterations;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import org.openrdf.model.Model;
import org.openrdf.model.Statement;
import org.openrdf.model.impl.LinkedHashModel;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.RepositoryResult;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.manager.RemoteRepositoryManager;
import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFHandlerException;
import org.openrdf.rio.RDFParseException;
import org.openrdf.rio.RDFParser;
import org.openrdf.rio.Rio;

public class TestImport {

	public static void main(String[] args) throws RepositoryException, RepositoryConfigException, IOException, RDFParseException {
		
		RDFParser parser;
		parser = Rio.createParser(RDFFormat.RDFXML);
		
		try {
//			try {
//				parser.parse(new FileReader("C:\\Users\\jacky\\Documents\\Ontology\\data\\news20min-db-RDFXML"), "http://test.com");
//			} catch (RDFParseException e) {
//				System.out.println("erreur");
//			}
//			
//			StatementCollector handler = new StatementCollector();
//			parser.setRDFHandler(handler);
//	
//			Collection<Statement> col = handler.getStatements();
//
//			model.addAll(col);
			
			String serverUrl = "http://semantic.ilab-research.ch:8080/openrdf-sesame/";
			RemoteRepositoryManager manager = new RemoteRepositoryManager(serverUrl);
			manager.initialize();
			Repository repo = manager.getRepository("20min");
			
			RepositoryResult<Statement> v = repo.getConnection().getStatements(null, null, null, true);
			Model model = Iterations.addAll(v, new LinkedHashModel()); 
			
			
			OutputStream os = new FileOutputStream("C:\\Users\\jacky\\Documents\\Ontology\\data\\news20min-db-RDFXML.out");
			Rio.write(model, os, RDFFormat.RDFXML);
			os.close();
			
			System.out.println("Write : ok");
			
			
			parser.parse(new FileReader("C:\\Users\\jacky\\Documents\\Ontology\\data\\news20min-db-RDFXML.out"), "<http://test.com>");

			System.out.println("Read : ok");

		} catch (RDFHandlerException e) {
			e.printStackTrace();
		}
	}
}
