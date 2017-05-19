package nl.pdok.catalog.workbench;

import java.util.List;

public class FmeWorkbenchEnvConfig {

    private List<WorkbenchParameter> parameters;

    public FmeWorkbenchEnvConfig(List<WorkbenchParameter> parameters) {
        this.setParameters(parameters);
    }

    public List<WorkbenchParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<WorkbenchParameter> parameters) {
        this.parameters = parameters;
    }
}
