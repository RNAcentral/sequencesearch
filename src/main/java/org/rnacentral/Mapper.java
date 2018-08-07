package org.rnacentral;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import org.rnacentral.Job;

public class Mapper extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);

    // Map of workers to the original actors requesting the calculation
    private Map<String, ActorRef> pendingWorkers = new HashMap<>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Job.class, s -> {
                    log.info("Received Job: {}", s);

                    for (String topic : job.databases) {
                        topic
                    }

                    // We delegate the job of running NHMMER to a worker, passing the
                    // job as a constructor argument to the actor.
                    ActorRef worker = getContext().actorOf(Props.create(Worker.class));
                    pendingWorkers.put(, worker);
                })
                .matchAny(o -> log.info("received unknown message"))
                .build();
    }
}
