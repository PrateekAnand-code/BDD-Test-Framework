package Utilities;

import java.util.LinkedHashMap;

public class GlabalDataParams {
    static LinkedHashMap<String, LinkedHashMap> PAGE_OBJECT = new LinkedHashMap<>();
    static LinkedHashMap<String, LinkedHashMap> DATA_OBJECT = new LinkedHashMap<>();


    public LinkedHashMap<String, LinkedHashMap> getPAGE_OBJECT() {
        return PAGE_OBJECT;
    }

    public void setPAGE_OBJECT(LinkedHashMap<String, LinkedHashMap> PAGE_OBJECT) {
        this.PAGE_OBJECT = PAGE_OBJECT;
    }

    public LinkedHashMap<String, LinkedHashMap> getDATA_OBJECT() {
        return DATA_OBJECT;
    }

    public void setDATA_OBJECT(LinkedHashMap<String, LinkedHashMap> DATA_OBJECT) {
        this.DATA_OBJECT = DATA_OBJECT;
    }


}
