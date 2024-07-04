package id.dojo.controllers;

import com.google.gson.Gson;

import id.dojo.models.Film;
import id.dojo.models.FilmCategory;
import id.dojo.models.Response;
import io.javalin.http.Handler;

import java.util.List;

public class FilmCategoryController {
    static Gson gson = new Gson();

    static public Handler listFilmCategory = context -> {
        Response<List<FilmCategory>> list = FilmCategory.listFilmCategory();
        List<FilmCategory> listFilmCategory = list.data;

        for (FilmCategory filmCategory : listFilmCategory){
            Film film = Film.getFilmById(filmCategory.getFilm_id()).data;
            filmCategory.setFilm(film);
        }

        context.json(
                gson.toJson(list)
        );
    };
}
