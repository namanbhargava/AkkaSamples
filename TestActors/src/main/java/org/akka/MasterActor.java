package org.akka;

import akka.actor.AbstractActor;

public class MasterActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(String.class, msg ->{
            System.out.println(msg);
        }).build();
    }
}
