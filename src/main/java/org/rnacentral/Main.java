package org.rnacentral;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import java.util.concurrent.TimeUnit;

import static org.rnacentral.Producer.*;
import static org.rnacentral.Expression.*;
import static akka.pattern.Patterns.ask;
import static akka.japi.Util.classTag;

public class Main {
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("calculator-system");
        ActorRef producerService =
                system.actorOf(Props.create(Producer.class), "producer");

        // (3 + 5) / (2 * (1 + 1))
        Expression task = new Divide(
                new Add(new Const(3), new Const(5)),
                new Multiply(
                        new Const(2),
                        new Add(new Const(1), new Const(1))
                )
        );

        FiniteDuration duration = Duration.create(1, TimeUnit.SECONDS);
        Integer result = Await.result(ask(producerService, task, new Timeout(duration)).mapTo(classTag(Integer.class)), duration);
        System.out.println("Got result: " + result);

        Await.ready(system.terminate(), Duration.Inf());
    }
}
