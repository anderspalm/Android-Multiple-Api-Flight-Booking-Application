package com.andersgpalm.travelapp;

/**
 * Created by ander on 8/31/2016.
 */
public class AgentNNObj {

    public String agentname;
    public String agentnum;

    public AgentNNObj(String agentName, String agentNum) {
        this.agentname = agentName;
        this.agentnum = agentNum;
    }

    public String getAgentName() {
        return agentname;
    }

    public void setFlightName(String agentName) {
        this.agentname = agentName;
    }

    public String getAgentNum() {
        return agentnum;
    }

    public void setAgentNum(String agentNum) {
        this.agentnum = agentNum;
    }
}
