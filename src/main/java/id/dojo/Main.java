package id.dojo;

import com.google.gson.Gson;
import id.dojo.controllers.*;
import io.javalin.Javalin;

public class Main {
    static Gson gson = new Gson();

    public static void main(String[] args) {

        Javalin app = Javalin.create(/*config*/)
                .start(7070);

        app
                // ACTOR
                .get("/", IndexController.index)
                .get("/actor", ActorController.listActorApi)
                .get("/actor/{actor_id}", ActorController.detailActorApi)
                .put("/actor", ActorController.updateActorAPI)
                .post("/actor", ActorController.addActorAPI)
                .delete("/actor/{actor_id}", ActorController.deleteActorApi)
                // FILM-CATEGORY
                .get("/film-category", FilmCategoryController.listFilmCategory)
                // INVENTORY
                .get("/inventory", InventoryController.listInventory)
                // FILM-ACTOR
                .get("/film-actor", FilmActorController.listFilmActor);
    }
}