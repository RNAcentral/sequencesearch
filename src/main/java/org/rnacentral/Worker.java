package org.rnacentral;

import akka.actor.AbstractActor;

import org.rnacentral.Job

public class Worker extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(String.class, s -> {
                    log.info("Received String message: {}", s);
                })
                .match(Job.class, s -> {
                    log.info("Received a Job: {}", s);
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
