package com.example;

import io.reactivex.Flowable;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.TimeUnit;


@Path("/ParamChanged")
public class ParameterEventHandler {

    private SseBroadcaster broadcaster;
    public static List<ParameterEventListener> listeners = new ArrayList<>();


    public void addListener(String name, String id) {
        listeners.add(new ParameterEventListener( name, id ) );
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public void HandleEvents(@Context Sse sse, @Context SseEventSink sink) {

        String sseMessage= "nme";
        broadcaster = sse.newBroadcaster();
        broadcaster.register(sink);
        String str="";
        ParameterEventListener temp;

        ListIterator<ParameterEventListener> iterator = listeners.listIterator();
         while ( iterator.hasNext() ) {


//           String toAdd="Parameter "+temp.name+", of type "+temp.type+" has changed\n";
//
//           sseMessage = sseMessage + toAdd;

             temp = iterator.next();

             OutboundSseEvent event = sse.newEventBuilder().
                     name("Parameter Changed").
                     data("Parameter "+ temp.name+", of type "+temp.type+" has changed").
                     build();
             sink.send(event);
             //broadcaster.broadcast(event);
             listeners.remove(temp);

         }

//            event = sse.newEventBuilder().
//                    name("Parameters Changed: ").
//                    data(sseMessage.toString()).
//                    build();



    }

}

class ParameterEventListener implements EventListener {

    String name, type;

    public ParameterEventListener(String name, String type) {

        this.name = name;
        this.type = type;

    }

}
