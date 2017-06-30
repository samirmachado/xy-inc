package br.com.samir.baas.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;

import br.com.samir.baas.database.Database;
import br.com.samir.baas.exception.NonUniqueResultException;

@Repository
public class BaasRepository {
	
	@Autowired 
	private Database database;
	
	public String findById(String tableName, Long id){
		MongoCursor<Document> iterator = database.getDatabase().getCollection(tableName)
			.find(Filters.eq("id", id)).iterator();
		return createSingleJsonElement(iterator);
	}
	
	private List<String> createListJsonElements(MongoCursor<Document> iterator){
		List<String> jsonElements = new ArrayList<>();
		while (iterator.hasNext()) {
			jsonElements.add(iterator.next().toJson());
		}
		return jsonElements;
	}
	
	private String createSingleJsonElement(MongoCursor<Document> iterator){
		List<String> jsonElements = createListJsonElements(iterator);
		if(jsonElements.size() > 1) {
			throw new NonUniqueResultException();
		}
		return jsonElements.isEmpty() ? null : jsonElements.get(0);
	}
}
