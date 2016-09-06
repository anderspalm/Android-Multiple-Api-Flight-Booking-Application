package com.example.ander.travelapp;

import java.util.ArrayList;

/**
 * Created by ander on 9/2/2016.
 */
public class AgentItemsObjGson {

    public ArrayList<AgentsObjClass> Agents;

    public ArrayList<AgentsObjClass> getAgents() {
        return Agents;
    }

    public class AgentsObjClass {
        public String Name;
        public String ImageUrl;
        public String getName() {
            return Name;
        }

        public String getImageUrl() {
            return ImageUrl;
        }
    }
}
