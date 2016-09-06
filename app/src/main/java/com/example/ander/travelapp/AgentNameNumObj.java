package com.example.ander.travelapp;

/**
 * Created by ander on 8/31/2016.
 */
public class AgentNameNumObj {

    public String agentname;
    public String agentnum;

    public AgentNameNumObj(String agentName, String agentNum) {
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
