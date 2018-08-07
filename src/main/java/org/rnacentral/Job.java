package org.rnacentral;

import java.util.ArrayList;

public class Job {
    String id;
    ArrayList<String> databases;

    public Job(String id, ArrayList<String> databases) {
        this.id = id;
        this.databases = databases;
    }
}
