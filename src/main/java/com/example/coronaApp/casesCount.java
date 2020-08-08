package com.example.coronaApp;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class casesCount {

    String totalConfirmed;
    String discharged;
    String deaths;
    String mortalityRate;

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public String getDischarged() {
        return discharged;
    }

    public void setDischarged(String discharged) {
        this.discharged = discharged;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getMortalityRate() {
        Double tmp = (Double.parseDouble(this.deaths)/Double.parseDouble(this.totalConfirmed))*100;
        return String.valueOf(tmp).substring(0,4)+"%";
    }

    public void setMortalityRate(String mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public casesCount setCasesCountDetails(String state){
        RestTemplate restTemplate = new RestTemplate();
        casesCount casesCount = new casesCount();
        String response = restTemplate.getForObject("https://api.rootnet.in/covid19-in/stats/latest", String.class);
        String jsonExp = String.format("$.data.regional[?(@.loc=='%s')]", state);
        JSONArray tmpArray = JsonPath.parse(response).read(jsonExp);
        org.json.JSONArray obj = new org.json.JSONArray(tmpArray.toJSONString());
        JSONObject finalObj = obj.getJSONObject(0);
        casesCount.setTotalConfirmed(finalObj.get("totalConfirmed").toString());
        casesCount.setDeaths(finalObj.get("deaths").toString());
        casesCount.setDischarged(finalObj.get("discharged").toString());
        casesCount.setMortalityRate(casesCount.getMortalityRate());
        return casesCount;
    }

}
