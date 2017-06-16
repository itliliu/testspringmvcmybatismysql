package com.we.enums;

	/**
 	* 0-UNSUBMIT
	*1-SUBMIT
	*2-received
	*3-inprogress
	*4-completed
	*/
public enum BriefStatusEnum {
	
	
    UNSUBMIT(0, "UNSUBMIT"),
    SUBMIT(1, "SUBMIT");

    private Integer key;

    private String value;

    private BriefStatusEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(String key) {
        for (BriefStatusEnum type : values()) {
            if (type.getKey().equals(key)) {
                return type.getValue();
            }
        }
        return null;
    }

}