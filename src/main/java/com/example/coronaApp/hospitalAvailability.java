package com.example.coronaApp;


import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class hospitalAvailability {

    String totalHospitals;
    String totalBeds;
    String urbanHospitals;
    String ruralHospitals;

    public String getTotalHospitals() {
        return totalHospitals;
    }

    public void setTotalHospitals(String totalHospitals) {
        this.totalHospitals = totalHospitals;
    }

    public String getTotalBeds() {
        return totalBeds;
    }

    public void setTotalBeds(String totalBeds) {
        this.totalBeds = totalBeds;
    }

    public String getUrbanHospitals() {
        return urbanHospitals;
    }

    public void setUrbanHospitals(String urbanHospitals) {
        this.urbanHospitals = urbanHospitals;
    }

    public String getRuralHospitals() {
        return ruralHospitals;
    }

    public void setRuralHospitals(String ruralHospitals) {
        this.ruralHospitals = ruralHospitals;
    }



    public hospitalAvailability setHospitalAvailablityDetails(String state){
        RestTemplate restTemplate = new RestTemplate();
        hospitalAvailability hospitalAvailability = new hospitalAvailability();
        String response = restTemplate.getForObject("https://api.rootnet.in/covid19-in/hospitals/beds", String.class);
        String jsonExp = String.format("$.data.regional[?(@.state=='%s')]", state);
        JSONArray tmpArray = JsonPath.parse(response).read(jsonExp);
        org.json.JSONArray obj = new org.json.JSONArray(tmpArray.toJSONString());
        JSONObject finalObj = obj.getJSONObject(0);
        hospitalAvailability.setTotalHospitals(finalObj.get("totalHospitals").toString());
        hospitalAvailability.setTotalBeds(finalObj.get("totalBeds").toString());
        hospitalAvailability.setUrbanHospitals(finalObj.get("urbanHospitals").toString());
        hospitalAvailability.setRuralHospitals(finalObj.get("ruralHospitals").toString());
        return hospitalAvailability;
    }

}
