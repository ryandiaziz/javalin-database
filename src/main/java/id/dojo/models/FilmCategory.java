package id.dojo.models;

import id.dojo.PgConnection;
import id.dojo.helper.DatabaseUtils;
import org.sql2o.Sql2o;

import java.util.List;

public class FilmCategory {
    private Integer film_id;
    private Integer category_id;
    private java.sql.Timestamp last_update;
    private Film film;

    public void setFilm(Film film) {
        this.film = film;
    }

    public Integer getFilm_id() {
        return film_id;
    }

    static public <T> Response<List<FilmCategory>> listFilmCategory(){
        return DatabaseUtils.listJoinType(
                """
                    SELECT fc.film_id, fc.category_id, fc.last_update FROM film_category fc
                    JOIN film f ON f.film_id = fc.film_id;
                """,
                FilmCategory.class
        );
    }
}
