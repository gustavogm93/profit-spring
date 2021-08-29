package dev.abel.springbootdocker.scraping.country.domain;


import dev.abel.springbootdocker.utils.GenerateUUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class EncodedMarketIndex {

    @Id
    String id;

    @Field("title")
    String title;

    @Field("code")
    String code;

    @Field("shares")
    List<EncodedShare> shares;

    public EncodedMarketIndex(String title, String code, List<EncodedShare> shares) {
        this.id = GenerateUUID.generateUniqueId();
        this.title = title;
        this.code = code;
        this.shares = shares;
    }

    public String verifyValidEncoded(){
        StringBuffer errors = new StringBuffer();

        try {
            Integer.parseInt(this.code);
        } catch (Exception e) {
            if(!this.code.equalsIgnoreCase("all"))
            errors.append(String.format("marketIndex invalid code, id: %s",this.id));
        }

        if(this.title.isEmpty()){
            errors.append(String.format("marketIndex title is empty ,id: %s", this.id));
        }

        if(shares.size() <= 1){
            errors.append(String.format("marketIndex shares list is empty ,id: %s", this.id));
            return errors.toString();
        }

        for(EncodedShare share: shares){
             String error = share.verifyValidEncodedShare();

             if(!error.equalsIgnoreCase("true")) {
                 errors.append(error.concat("; "));
             }
        }

        if(errors.length() > 0){
            return errors.toString();
        }

        return "true";
    }

}
