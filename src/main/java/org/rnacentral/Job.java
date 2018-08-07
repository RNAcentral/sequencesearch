package org.rnacentral;

import java.util.ArrayList;

import org.rnacentral.Mapper.Topic;

public class Job {
    String id;
    ArrayList<Topic> databases;

    public Job(String id, ArrayList<Topic> databases) {
        this.id = id;
        this.databases = databases;
    }
}
