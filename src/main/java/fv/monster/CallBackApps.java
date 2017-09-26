package fv.monster;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author davidtobing -- fv.davidtobing@gmail.com
 */
public class CallBackApps {

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event, String replyToken) {

        System.out.println("event: " + event);
        List<Message> reply = new ArrayList<>();
        String[] args = event.getMessage().getText().split(" ", 2);

        // melihat waktu & daftar zona waktu yg tersedia
        if ("@time".equals(args[0])) {
            reply.add(new TextMessage("Melihat waktu!"));
            try {
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of(args[1]));
                reply.add(new TextMessage(now.format(DateTimeFormatter.ofPattern("MM/dd HH:mm"))));

            } catch (ArrayIndexOutOfBoundsException | DateTimeException e) {
                String sb = "daftar zona waktu yang tersedia!"
                        + System.getProperty("line.separtor") + "https://git.io/vyqDP";

                reply.add(new TextMessage(sb));
            }

        } 
        // perhitungan matematika, cuaca, negara, dll
        // ex: 10*(2+9) ->>> di ubah ke url encode (agar bisa dibaca melalui url)
        else if ("@wol".equals(args[0])) {
            reply.add(new TextMessage("silahkan tunggu!"));

            try {
                String url = URLEncoder.encode(args[1], "UTF-8");
                reply.add(new TextMessage("http://www.wolframalpha.com/input/?i=" + url));

            } catch (ArrayIndexOutOfBoundsException | UnsupportedEncodingException e) {

            }
        } else {
            reply.add(new TextMessage("terima kasih telah mencoba!"));
        }
    }

    @EventMapping
    public TextMessage handleDefaultMessageEvent(Event ev) {
        System.out.println("event: " + ev);
        return new TextMessage("text tidak tersedia");
    }

    @EventMapping
    public TextMessage handleGambar(MessageEvent<ImageMessageContent> event) {
        System.out.println("event: " + event);
        return new TextMessage("maaf type gambar belum tersedia!");
    }

    @EventMapping
    public TextMessage handleStiker(MessageEvent<StickerMessageContent> event) {
        System.out.println("event: " + event);
        return new TextMessage("epss.. belum tersedia!!");
    }

}
