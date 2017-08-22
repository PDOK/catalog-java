package nl.pdok.catalog.jobentry;

import java.io.Serializable;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.annotate.JsonProperty;

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
    private JsonNode dataIn;

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

    public JsonNode getDataIn() {
        return dataIn;
    }

    public void setDataIn(JsonNode dataIn) {
        this.dataIn = dataIn;
    }

    public String toString() {
        return ("{\"job_name\":\"" + this.jobName + "\",")
                + "\"execution_order\":" + this.executionOrder + ","
                + "\"name\":\"" + this.name + "\","
                + "\"data_in_type\":\"" + this.dataInType + "\","
                + "\"active\":" + this.active + ","
                + "\"data_in\":" + this.dataIn.toString() + "}";
    }
}
