package com.example.coronaApp;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class appController {

    @Autowired
    casesCount casesCount;

    @Autowired
    hospitalAvailability hospitalAvailability;

    public static String state;
    public static String city;
    public static String url;

    @RequestMapping("/home")
    public ModelAndView get_Coordinates(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("coordinatesPage.html");
        return modelAndView;
    }

    @GetMapping("/location")
    public ModelAndView getLocation(@RequestParam("lat") String lat,@RequestParam("lng") String lng){
        url = "https://locationiq.org/v1/reverse.php?key=d246984396eb01&lat="+lat+"&lon="+lng+"&format=json";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        JSONObject response = new JSONObject(result).getJSONObject("address");
        ModelAndView modelAndView = new ModelAndView();
        if(response.has("city"))
            city = response.getString("city");
        else
            city = response.getString("neighbourhood");
        state = response.getString("state");
        modelAndView.addObject("cityName", city);
        modelAndView.addObject("stateName", state);
        modelAndView.setViewName("serviceSelection.html");
        return modelAndView;
    }

    @GetMapping("/casesCount")
    public ModelAndView getCasesCount(){
        ModelAndView modelAndView = new ModelAndView();
        casesCount = casesCount.setCasesCountDetails(this.state);
        modelAndView.addObject("casesCountObject", casesCount);
        modelAndView.addObject("mainPage", this.url);
        modelAndView.setViewName("casesCountPage.html");
        return modelAndView;
    }

    @GetMapping("/hospitalAndBedEnquiry")
    public ModelAndView getHospitalEnquiry(){
        ModelAndView modelAndView = new ModelAndView();
        hospitalAvailability = hospitalAvailability.setHospitalAvailablityDetails(this.state);
        modelAndView.addObject("hospitalDetails", hospitalAvailability);
        modelAndView.addObject("mainPage", this.url);
        modelAndView.setViewName("hospitalAvailabilityPage.html");
        return modelAndView;
    }
}
