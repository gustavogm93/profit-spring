package dev.abel.springbootdocker.scraping.country.domain;

import dev.abel.springbootdocker.utils.GenerateUUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class EncodedShare {

    @Id
    String id;

    @Field("code")
    String code;

    @Field("title")
    String title;

    @Field("fullTitle")
    String fullTitle;

    @Field("url")
    String url;


    public EncodedShare(String code, String title, String fullTitle, String url) {
        this.id = GenerateUUID.generateUniqueId();
        this.code = code;
        this.title = title;
        this.fullTitle = fullTitle;
        this.url = url;
    }

    public String verifyValidEncodedShare() {
        try {
            Integer.parseInt(this.code);
        } catch (Exception e) {
            return  String.format("share invalid code, id: %s",id);
        }

        if (fullTitle.isEmpty()) {
            return  String.format("share empty fullTitle, id: %s",id);
        }

        if (title.isEmpty()) {
            return  String.format("share empty title, id: %s",id);
        }
        return "true";

    }


}
