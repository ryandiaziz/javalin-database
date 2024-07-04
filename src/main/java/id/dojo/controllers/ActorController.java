package id.dojo.controllers;

import com.google.gson.Gson;
import id.dojo.dto.ActorDto;
import id.dojo.models.Actor;

import io.javalin.http.Handler;

public class ActorController {
    static public Gson gson = new Gson();

    public static Handler listActorApi = ctx -> ctx.json(Actor.listActors());

    public static Handler detailActorApi = ctx -> {
        String actor_id = ctx.pathParam("actor_id");
        ctx.json(Actor.detailActor(actor_id));
    };

    public static Handler deleteActorApi = ctx -> {
        String actor_id = ctx.pathParam("actor_id");
        ctx.json(Actor.deleteActor(actor_id));
    };

    public static Handler addActorAPI = context -> {
        ActorDto actor = gson.fromJson(context.body(), ActorDto.class);
        context.result(Actor.addActor(actor));
    };

    public static Handler updateActorAPI = context -> {
        ActorDto actor = gson.fromJson(context.body(), ActorDto.class);
        context.result(Actor.updateActor(actor));
    };
}
