package rest;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
public class RESTController {
	
	private Model model;
	
	public RESTController() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		model = context.getBean("model", Model.class);
    }

    @RequestMapping(value="/aae",method=RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE } )
    public ResponseEntity<Disease[]> aae(@RequestParam(value="data", required=true) String symptomsJSON) {
    	Gson gson = new Gson();
    	Symptom[] symptoms = gson.fromJson(symptomsJSON, Symptom[].class);
    	return new ResponseEntity<Disease[]>(compute(symptoms), HttpStatus.OK);
    }
    
    private Disease[] compute(Symptom[] symptoms) {
    	List<Disease> diseases = new LinkedList<>();
    	
    	boolean containsOneSymptom;
    	int index;
    	
    	for (Entry<Disease, List<Symptom>> entry : model.getData().entrySet()) {
    		
    		System.out.println(entry.getKey());
    		System.out.println(entry.getValue());
    		containsOneSymptom = false;
    		index = 0;
    		
    		while (index < symptoms.length && !containsOneSymptom) {
    			System.out.println(symptoms[index]);
    			
    			for (Symptom s : entry.getValue()) {
    				if (	s.getUniqueName().equals(symptoms[index].getUniqueName()) && !symptoms[index].isValueInRange()	)
    					containsOneSymptom = true;
    			}
    			index++;
    		}
    		
    		if (containsOneSymptom)
				diseases.add(entry.getKey());
    	}
    	
    	Disease[] diseasesArray = new Disease[diseases.size()];
    	diseases.toArray(diseasesArray);
    	
    	return diseasesArray;
    }
    
}