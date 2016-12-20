package nl.pdok.catalog.workbench;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Workbench implements Serializable {

    private static final long serialVersionUID = 89987294789L;
    
    private String name;
    private File target;
    
    private List<WorkbenchParameter> parameters = new ArrayList<WorkbenchParameter>();
    private List<WorkbenchResource> resources = new ArrayList<WorkbenchResource>();
    
    public Workbench(String name, File target) {
        this.name = name;
        this.target = target;
    }
    
    public String getName() {
        return name;
    }

    public List<WorkbenchParameter> getParameters() {
        return parameters;
    }
    
    public void addParameter(WorkbenchParameter parameter) {
        parameters.add(parameter);
    }
    
    public void addParameters(Collection<WorkbenchParameter> parameters) {
        this.parameters.addAll(parameters);
    }
    
    public List<WorkbenchResource> getResources() {
        return resources;
    }
    
    public WorkbenchResource getResource(String resourcename) {
    	
    	for (WorkbenchResource resource : resources) 
    		if (resource.getName().equals(resourcename))
    			return resource;
		
    	return null;
    }
    
    public void addResource(WorkbenchResource resource) {
        resources.add(resource);
    }
    
    public InputStream getStream() throws IOException {
        return new FileInputStream(target);
    }
    
    public long getStreamLength() {
        return target.length();
    }

    public Workbench clone() {
        Workbench clone = new Workbench(name, null);
        for (WorkbenchParameter param : this.parameters) {
            clone.parameters.add(param.clone());
        }
        
        return clone;
    }

}
