package hello;

import java.util.ArrayList;
import java.util.List;

public class Greeting {

    private String content;
    private List<String> serviceList;
    private List<String> dbChangeList;

	public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public List<String> getServiceList() {
    	if(serviceList == null)
    		serviceList = new ArrayList<>();
		return serviceList;
	}

	public void setServiceList(List<String> serviceList) {
		this.serviceList = serviceList;
	}
    
	public List<String> getDbChangeList() {
		if(dbChangeList == null)
			dbChangeList = new ArrayList<>();
		return dbChangeList;
	}

	public void setDbChangeList(List<String> dbChangeList) {
		this.dbChangeList = dbChangeList;
	}

}
