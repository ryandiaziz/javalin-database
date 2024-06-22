package id.dojo.models;

import com.google.gson.Gson;
import id.dojo.Utils;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

import static id.dojo.PgConnection.getSql2o;

public class Actor {
    static Gson gson = new Gson();
    private Long actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;
    static Sql2o sql2o = getSql2o();

    public Long getActor_id() {
        return actor_id;
    }

    public void setActor_id(Long actor_id) {
        this.actor_id = actor_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public String toString() {
        // return "( " + actor_id + ", " + first_name + ", " + last_name + ", " + last_update + " )";
        return Utils.showString(actor_id.toString(), first_name, last_name, last_update.toString());
    }

    public static String listActors(){
        try (Connection con = sql2o.open()){
            String query = "SELECT actor_id, first_name, last_name, last_update FROM actor";
            List<Actor> actors = con.createQuery(query).executeAndFetch(Actor.class);
            Response<List<Actor>> results = new Response<>(200,"Berhasil mandapatkan data actor", actors);
            return gson.toJson(results);
        }catch (Sql2oException sql2oException){
            System.out.println(sql2oException.toString());
            return null;
        }
    }
}
