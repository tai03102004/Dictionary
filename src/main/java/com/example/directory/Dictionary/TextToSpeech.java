import javazoom.jl.player.Player;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class TextToSpeech {
    private static final String us_accent = "en-us";
    private static final String uk_accent = "en-gb";
    public static String language = "en";
    public static void VoiceAudio(String text) {
        try {
            String api = "https://translate.google.com/translate_tts?ie=UTF-8&tl=" + language
                         + "&client=tw-ob&q=" + URLEncoder.encode(text, StandardCharsets.UTF_8);
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            InputStream audio = con.getInputStream();
            new Player(audio).play();
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        VoiceAudio("I hate programming");
    }
}
