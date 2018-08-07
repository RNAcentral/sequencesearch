package org.rnacentral;

import java.util.Map;
import java.util.HashMap;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import org.rnacentral.Job;

public class Mapper extends AbstractActor {
    // List of all topics
    public enum Topic {
        tRNA,
        rRNA,
        miRBase
    }

    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // Map of workers to the original actors requesting the calculation
    private Map<Topic, ActorRef> workers = new HashMap<>();

    public Mapper() {
        for (Topic topic : Topic.values()) {
            ActorRef worker = getContext().getSystem().actorOf(Props.create(Worker.class), topic.toString());
            this.workers.put(topic, worker);
        }
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Job.class, job -> {
                    log.info("Received Job: {}", job);

                    for (Topic topic : job.databases) {
                        ActorRef worker = workers.get(topic);
                    }

                    // We delegate the job of running NHMMER to a worker, passing the
                    // job as a constructor argument to the actor.
                    ActorRef worker = getContext().actorOf(Props.create(Worker.class));
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
