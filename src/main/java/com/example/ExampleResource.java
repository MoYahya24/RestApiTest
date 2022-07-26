package com.example;

import io.reactivex.Flowable;
import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Path("/hello")
public class ExampleResource {

    ParameterEventHandler handler = new ParameterEventHandler();

    public static List<String> names = new ArrayList<>(List.of("yahya"));

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("text/plain")
    public Publisher<String> getNames() {

        return Flowable.timer(2, TimeUnit.MILLISECONDS).map(ticker -> names.toString());
        //return Flowable.interval(1, TimeUnit.SECONDS).map(ticker -> names.toString());
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void addName(String newName) {

        ParameterEventListener listener = new ParameterEventListener("", "");
        names.add(newName);
        handler.addListener(newName, "bruh");

    }

//    @PUT
//    @Path("{toUpdate}")
//    @Produces(MediaType.SERVER_SENT_EVENTS)
//    @SseElementType("text/plain")
//    @Consumes(MediaType.TEXT_PLAIN)
//    public Publisher<String> updateName(
//            @PathParam("toUpdate") String toUpdate,
//            @QueryParam("name") String newName) {
//
//        names = names.stream().map(name-> {
//
//            if(name.equals(toUpdate)) {
//                return newName;
//            }
//            return name;
//                }
//
//        ).collect(Collectors.toList());
//
//        return Flowable.interval(1, TimeUnit.SECONDS).map(ticker-> names.toString());
//    }

}

