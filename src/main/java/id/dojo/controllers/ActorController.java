package id.dojo.controllers;

import id.dojo.models.Actor;

import io.javalin.http.Handler;

public class ActorController {
    public static Handler listActorApi = ctx -> ctx.json(Actor.listActors());
}
