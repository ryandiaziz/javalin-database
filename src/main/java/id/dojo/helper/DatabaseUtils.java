package id.dojo.helper;

import com.google.gson.Gson;
import id.dojo.models.CheckData;
import id.dojo.models.Response;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

import static id.dojo.PgConnection.getSql2o;

public class DatabaseUtils {
    static Gson gson = new Gson();
    static Sql2o sql2o = getSql2o();

    static public <T> String get(String query, String type, Class<T> object){
        try (Connection con = sql2o.open()){
            List<T> results = con.createQuery(query).executeAndFetch(object);

            if (type.equalsIgnoreCase("detail"))
                if (results.isEmpty()) throw new Sql2oException("data tidak ditemukan");

            return gson.toJson(
                    new Response<>(200,"Berhasil mendapatkan data", results)
            );
        } catch (Sql2oException sql2oException){
            return gson.toJson(
                    new Response<>(500, sql2oException.toString(), null)
            );
        }
    }

    static public String addAndUpdate(String query){
        try (Connection con = sql2o.open()){
            con.createQuery(query)
                    .withParams()
                    .executeUpdate();

            return gson.toJson(
                    new Response<>(200,"Berhasil mengubah data", true)
            );
        } catch (Sql2oException sql2oException){
            return gson.toJson(
                    new Response<>(500, sql2oException.toString(), null)
            );
        }
    }

    static public <T> String delete(String query, String table_name, String id_nme, int id, Class<T> object){
        CheckData checkData = isExist(table_name, id_nme, id, object);

        if (checkData.isError)
            return gson.toJson(new Response<>(500, checkData.message, null));

        if(!checkData.isExist)
            return gson.toJson(new Response<>(500, "Data tidak ditemukan", null));

        try (Connection con = sql2o.open()){
            con.createQuery(query).executeUpdate();

            return gson.toJson(
                    new Response<>(200,"Berhasil menghapus data", true)
            );
        } catch (Sql2oException sql2oException){
            return gson.toJson(
                    new Response<>(500, sql2oException.toString(), null)
            );
        }
    }

    static public <T> CheckData isExist(String table_name, String id_name, int id, Class<T> object){
        CheckData checkData = new CheckData();

        try (Connection connection = sql2o.open()){
            List<T> results = connection.createQuery(String.format("SELECT * FROM %s WHERE %s = %s", table_name, id_name, id))
                    .executeAndFetch(object);

            checkData.isExist = !results.isEmpty();
            checkData.isError = false;
        }catch (Sql2oException sql2oException){
            checkData.isError = true;
            checkData.message = sql2oException.toString();
        }

        return checkData;
    }

    static public <T> String listJoin(String query, Class<T> mapper){
        try (Connection connection = sql2o.open()){
            List<T> list = connection.createQuery(query).executeAndFetch(mapper);

            return gson.toJson(new Response<>(200,"Berhasil mendapatkan data", list)) ;
        } catch (Exception e){
            e.printStackTrace();
            return gson.toJson(new Response<>(500, e.toString(), null));
        }
    }

    static public <T> Response<List<T>> listJoinType(String query, Class<T> mapper){
        try (Connection connection = sql2o.open()){
            List<T> list = connection.createQuery(query).executeAndFetch(mapper);

            return new Response<List<T>>(200,"Berhasil mendapatkan data", list);
        } catch (Exception e){
            e.printStackTrace();
            return new Response<List<T>>(500, e.toString(), null);
        }
    }

    static public <T> Response<T> get2(String query,Object param, Class<T> mapper) {
        try (Connection con = sql2o.open()) {
            T data = con.createQuery(query)
                    .withParams(param)
                    .executeAndFetchFirst(mapper);

            return new Response<T>(200,"Berhasil Mendapatkan data", data);
        } catch (Exception e){
            return new Response<>(500,e.toString(), null);
        }
    }

    static public <T> Response<List<T>> listJoinType(String query,Object param, Class<T> mapper){
        try (Connection connection = sql2o.open()){
            List<T> list = connection.createQuery(query)
                    .withParams(param)
                    .executeAndFetch(mapper);

            return new Response<List<T>>(200,"Berhasil mendapatkan data", list);
        }catch (Sql2oException sql2oException){
            sql2oException.printStackTrace();
            return new Response<List<T>>(500,sql2oException.toString(),null);
        }catch (Exception e){
            e.printStackTrace();
            return new Response<List<T>>(500, e.toString(), null);
        }
    }
}
