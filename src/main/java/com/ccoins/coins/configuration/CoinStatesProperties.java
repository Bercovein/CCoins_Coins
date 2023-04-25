package com.ccoins.coins.configuration;

import com.ccoins.coins.dto.StateDTO;
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

    private StateDTO inDemand;
    private StateDTO expired;
    private StateDTO adjustment;
    private StateDTO delivered;
    private StateDTO cancelled;

    public List<StateDTO> getStaticList(){
        List<StateDTO> list = new ArrayList<>();
        list.add(this.inDemand);
        list.add(this.expired);
        list.add(this.adjustment);
        return list;
    }

    public List<StateDTO> getEnabledStateList(){
        List<StateDTO> list = new ArrayList<>();
        list.add(this.delivered);
        list.add(this.cancelled);
        return list;
    }

    public List<String> getNotDemandList(){
        List<String> list = new ArrayList<>();
        list.add(this.expired.getName());
        list.add(this.adjustment.getName());
        list.add(this.delivered.getName());
        list.add(this.cancelled.getName());
        return list;
    }

    public List<String> getDemandList(){
        List<String> list = new ArrayList<>();
        list.add(this.inDemand.getName());
        return list;
    }

    public  List<StateDTO> getAllStateList(){
        List<StateDTO> list = this.getEnabledStateList();
        list.addAll(this.getStaticList());
        return list;
    }
}
