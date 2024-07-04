package id.dojo.models;

import id.dojo.helper.DatabaseUtils;

public class Film {
    private Integer film_id;
    private String title;
    private String description;

    public static Response<Film> getFilmById(Integer film_id){
        return DatabaseUtils.get2(
                """
                        SELECT film_id, title, description FROM film
                        WHERE film_id = :p1;
                        """,
                film_id,
                Film.class
        );
    }
}
