package id.dojo;

public class Utils {
    static public String showString(String... datas){
        StringBuilder tmp = new StringBuilder();

        for (String data : datas){
            tmp.append(data);
        }
        tmp.append("\n");

        return tmp.toString();
    }
}
