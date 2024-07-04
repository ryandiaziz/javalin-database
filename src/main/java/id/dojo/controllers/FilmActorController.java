package id.dojo.controllers;

import com.google.gson.Gson;
import id.dojo.models.*;
import io.javalin.http.Handler;

import java.util.List;

public class FilmActorController {
    static Gson gson = new Gson();

    static public Handler listFilmActor = context -> {
        Response<List<FilmActor>> list = FilmActor.listFilmActor();
        List<FilmActor> listFilmActor = list.data;

        for (FilmActor filmActor : listFilmActor){
            List<Actor> actors = FilmActor.getActorById(filmActor.getFilm_id()).data;
            Film film = Film.getFilmById(filmActor.getFilm_id()).data;
            filmActor.setActors(actors);
            filmActor.setFilm(film);
        }

        context.json(
                gson.toJson(list)
        );
    };
}
