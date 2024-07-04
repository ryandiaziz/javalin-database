package id.dojo.models;

import id.dojo.Utils;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.sql.Timestamp;
import java.util.List;

import static id.dojo.PgConnection.getSql2o;

public class Payment {
    private Long payment_id;
    private String customer_id;
    private String staff_id;
    private String rental_id;
    private String amount;
    private Timestamp payment_date;

    static Sql2o sql2o = getSql2o();

    @Override
    public String toString() {
        return Utils.showString(payment_id.toString(), customer_id);
    }

    public static Response<List<Payment>> listPayment(){
        try (Connection con = sql2o.open()){
            String query = "SELECT * FROM payment";
            List<Payment> payments = con.createQuery(query).executeAndFetch(Payment.class);
            return new Response<>(200,"Berhasil mandapatkan data payment", payments);
        }catch (Sql2oException sql2oException){
            System.out.println(sql2oException.toString());
            return null;
        }
    }
}
