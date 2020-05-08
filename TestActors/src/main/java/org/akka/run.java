package org.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class run {
    public static void main(String args[]){
        ActorSystem actorSystem = ActorSystem.create("BasicActorSystem");
        ActorRef masterActor = actorSystem.actorOf(Props.create(MasterActor.class));
        masterActor.tell("Hi There", ActorRef.noSender());
        actorSystem.terminate();
    }
}
