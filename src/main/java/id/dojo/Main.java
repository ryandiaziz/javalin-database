package id.dojo;

import com.google.gson.Gson;
import id.dojo.controllers.ActorController;
import io.javalin.Javalin;

import static id.dojo.models.Actor.listActors;
import static id.dojo.models.Payment.listPayment;

public class Main {
    static Gson gson = new Gson();

    public static void main(String[] args) {

        var app = Javalin.create(/*config*/)
                .start(7070);

        app
            .get("/", ActorController.listActorApi)
            .get("/payment", ctx -> ctx.result(gson.toJson(listPayment())));
    }
}