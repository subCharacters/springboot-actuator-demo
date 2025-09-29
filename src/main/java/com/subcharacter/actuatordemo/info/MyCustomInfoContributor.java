package com.subcharacter.actuatordemo.info;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class MyCustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("myKey", "myValue");
        stringStringHashMap.put("myKey2", "myValue2");

        builder.withDetail("myCustomInfo", stringStringHashMap);
    }
}
