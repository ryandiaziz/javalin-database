package id.dojo.controllers;

import com.google.gson.Gson;
import id.dojo.models.Film;
import id.dojo.models.Inventory;
import id.dojo.models.Response;
import io.javalin.http.Handler;

import java.util.List;

public class InventoryController {
    static Gson gson = new Gson();

    static public Handler listInventory = context -> {
        Response<List<Inventory>> list = Inventory.listInventory();
        List<Inventory> listInventory = list.data;

        for (Inventory inventory : listInventory){
            Film film = Film.getFilmById(inventory.getFilm_id()).data;
            inventory.setFilm(film);
        }

        context.json(
                gson.toJson(list)
        );
    };
}
