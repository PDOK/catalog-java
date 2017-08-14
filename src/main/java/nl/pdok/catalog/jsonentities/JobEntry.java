package nl.pdok.catalog.jsonentities;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.json.simple.JSONObject;

public class JobEntry implements Serializable {

    private static final long serialVersionUID = 567355463389L;

    @JsonProperty(value = "job_name")
    private String jobName;

    @JsonProperty(value = "execution_order")
    private Long executionOrder;

    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "data_in_type")
    private String dataInType;

    @JsonProperty(value = "active")
    private Boolean active;
    
    @JsonProperty(value = "data_in")
    private JSONObject dataIn;

    public JobEntry() {
    }

    public String getJobName() {
        return jobName;
    }

    public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setExecutionOrder(Long executionOrder) {
		this.executionOrder = executionOrder;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDataInType(String dataInType) {
		this.dataInType = dataInType;
	}

	public Long getExecutionOrder() {
        return executionOrder;
    }

    public String getName() {
        return name;
    }

    public String getDataInType() {
        return dataInType;
    }

    public Boolean isActive() {
        return active;
    }

	public JSONObject getDataIn() {
		return dataIn;
	}

	public void setDataIn(JSONObject dataIn) {
		this.dataIn = dataIn;
	}
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{\"job_name\":\"" + this.jobName + "\",");
		builder.append("\"execution_order\":" + this.executionOrder + ",");
		builder.append("\"name\":\"" + this.name + "\",");
		builder.append("\"data_in_type\":\"" + this.dataInType + "\",");
		builder.append("\"active\":" + this.active + ",");
		builder.append("\"data_in\":" + this.dataIn + "}");
		return builder.toString();
	}
}
