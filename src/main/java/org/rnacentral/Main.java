package org.rnacentral;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;

import static akka.pattern.Patterns.ask;
import static akka.japi.Util.classTag;

public class Main {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("calculator-system");
        ActorRef producerService = system.actorOf(Props.create(Mapper.class), "producer");

        Job job = new Job("12345", (ArrayList<String>) Arrays.asList("miRBase", "tRNA"));

        FiniteDuration duration = Duration.create(5, TimeUnit.SECONDS);
        Integer result = Await.result(ask(producerService, job, new Timeout(duration)).mapTo(classTag(Integer.class)), duration);
        System.out.println("Got result: " + result);

        Await.ready(system.terminate(), Duration.Inf());
    }
}
