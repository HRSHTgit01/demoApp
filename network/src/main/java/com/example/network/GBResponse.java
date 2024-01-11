package com.example.network;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class GBResponse implements Serializable {
    @SerializedName("status")
    private Status mStatus;
    @SerializedName("eventInformation")
    private EventInformation mEventInformation;
    @SerializedName("eventInfo")
    private EventInformation mEventInfo;

    public GBResponse() {
    }

    public Status getStatus() {
        return this.mStatus;
    }

    public EventInformation getEventInformation() {
        return this.mEventInformation == null ? this.mEventInfo : this.mEventInformation;
    }

    public String toString() {
        return "";
    }
}
