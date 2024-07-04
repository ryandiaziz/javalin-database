package id.dojo.models;

import id.dojo.PgConnection;
import id.dojo.helper.DatabaseUtils;
import org.sql2o.Sql2o;

import java.util.List;

public class FilmActor {
    private Integer film_id;
    private Integer actor_in_film;
    private Film film;
    private List<Actor> actors;

    public Integer getFilm_id() {
        return film_id;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    static public <T> Response<List<FilmActor>> listFilmActor(){
        return DatabaseUtils.listJoinType(
                """
                    SELECT fa.film_id, COUNT(a.actor_id) AS actor_in_film
                    FROM film_actor fa
                    JOIN actor a ON fa.actor_id = a.actor_id
                    GROUP BY fa.film_id
                    ORDER BY fa.film_id ASC;
                """,
                FilmActor.class
        );
    }

    public static Response<List<Actor>> getActorById(Integer film_id){
        return DatabaseUtils.listJoinType(
                """
                        SELECT a.actor_id, a.first_name, a.last_name, a.last_update\s
                        FROM film_actor fa
                        JOIN actor a ON fa.actor_id = a.actor_id
                        where fa.film_id = :p1
                        ORDER BY a.actor_id ASC;
                        """,
                film_id,
                Actor.class
        );
    }
}
