package id.dojo.models;

import com.google.gson.Gson;
import id.dojo.Utils;
import id.dojo.dto.ActorDto;
import id.dojo.helper.DatabaseUtils;
import org.sql2o.Sql2o;

import java.sql.Timestamp;

import static id.dojo.PgConnection.getSql2o;

public class Actor {
    private Long actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

    static Gson gson = new Gson();
    static Sql2o sql2o = getSql2o();

    final static String TABLE_NAME = "actor";
    final static String ID_NAME = "actor_id";

    @Override
    public String toString() {
        return Utils.showString(actor_id.toString(), first_name, last_name, last_update.toString());
    }

    public static String listActors(){
        return DatabaseUtils.get(
                "SELECT actor_id, first_name, last_name, last_update FROM actor",
                "list",
                Actor.class
        );
    }

    public static String detailActor(String id){
        return DatabaseUtils.get(
                String.format("SELECT * FROM actor WHERE actor_id = %s", id),
                "detail",
                Actor.class
        );
    }

    public static String deleteActor(String actor_id){
        int id = Integer.parseInt(actor_id);

        return DatabaseUtils.delete(
                String.format("DELETE FROM actor WHERE actor_id = %s", id),
                TABLE_NAME,
                ID_NAME,
                id,
                Actor.class
        );
    }

    public static String addActor(ActorDto actorDto){
        String query = String.format(
                "INSERT INTO actor(first_name, last_name) VALUES('%s', '%s')",
                actorDto.first_name,
                actorDto.last_name
        );

        return DatabaseUtils.addAndUpdate(query);
    }

    public static String updateActor(ActorDto actorDto){
        String query = String.format(
                "UPDATE actor SET first_name = '%s', last_name = '%s' WHERE actor_id = %s",
                actorDto.first_name,
                actorDto.last_name,
                actorDto.actor_id
        );

        return DatabaseUtils.addAndUpdate(query);
    }

    public static Response<Actor> getActorById(Integer actor_id){
        return DatabaseUtils.get2(
                """
                        SELECT * FROM actor
                        WHERE actor_id = :p1;
                        """,
                actor_id,
                Actor.class
        );
    }
}
