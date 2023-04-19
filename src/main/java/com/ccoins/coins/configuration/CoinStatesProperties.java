package com.ccoins.coins.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(value = "coin-states")
public class CoinStatesProperties {

    private String inDemand;
    private String expired;
    private String delivered;
    private String cancelled;

    public List<String> getStaticList(){
        List<String> list = new ArrayList<>();
        list.add(this.inDemand);
        list.add(this.expired);
        return list;
    }

    public List<String> getEnabledStateList(){
        List<String> list = new ArrayList<>();
        list.add(this.delivered);
        list.add(this.cancelled);
        return list;
    }

    public  List<String> getAllStateList(){
        List<String> list = this.getEnabledStateList();
        list.addAll(this.getStaticList());
        return list;
    }
}
