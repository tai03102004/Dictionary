import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;


public class TranslateAPI {
    public static String Translate(String sourceLang, String targetLang, String text) throws IOException {
        String urlString = "https://script.google.com/macros/s/AKfycby3AOWmhe32TgV9nW-Q0TyGOEyCHQeFiIn7hRgy5m8jHPaXDl2GdToyW_3Ys5MTbK6wjg/exec"
                   + "?q=" + URLEncoder.encode(text, StandardCharsets.UTF_8)
                   + "&target=" + targetLang
                   + "&source=" + sourceLang;
        //tạo ra URL dẫn tới API translate
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); //Dữ liệu đợc nhập vào sẽ đi theo đường dẫn "con"
        String translated = in.lines().collect(Collectors.joining("\n"));
        in.close();
        return translated;
    }

    public static void main(String[] args) throws IOException {
        String text = "yesterday my mother brought home a gorgeous girl who I had loved at first sight" ;
        String text1 = "I hate programming";
        String text2 = "Привет как ты?";
        System.out.println(Translate("", "vi", text));
        System.out.println(Translate("", "vi", text1));
        System.out.println(Translate("", "vi", text2));
    }
}
