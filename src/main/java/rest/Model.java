package rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {

	private Map<Disease, List<Symptom>> data;
	
	public Model() {
		this(new HashMap<>());
	}
	
	public Model(Map<Disease, List<Symptom>> data) {
		setData(data);
	}
	
	
	public Map<Disease, List<Symptom>> getData() {
		return this.data;
	}
	
	public void setData(Map<Disease, List<Symptom>> data) {
		this.data = data;
	}
	
}
