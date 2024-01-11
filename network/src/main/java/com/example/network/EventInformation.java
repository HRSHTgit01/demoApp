package com.example.network;

import java.io.Serializable;

public class EventInformation implements Serializable {

    public String baseEventId;

    public String baseEventTs;

    public String baseEventType;

    public String sourceEventId;

    public String getBaseEventId() {
        return baseEventId;
    }

    public void setBaseEventId(String baseEventId) {
        this.baseEventId = baseEventId;
    }

    public String getBaseEventTs() {
        return baseEventTs;
    }

    public void setBaseEventTs(String baseEventTs) {
        this.baseEventTs = baseEventTs;
    }

    public String getBaseEventType() {
        return baseEventType;
    }

    public void setBaseEventType(String baseEventType) {
        this.baseEventType = baseEventType;
    }

    public String getSourceEventId() {
        return sourceEventId;
    }

    public void setSourceEventId(String sourceEventId) {
        this.sourceEventId = sourceEventId;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "";
        //GBUtils.getJSONString(this);
    }
}
