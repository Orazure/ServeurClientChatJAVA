import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;


import java.util.Date;
import java.sql.Timestamp;

public class Weather {
    private HttpResponse<JsonNode> response;
    private JSONObject main,wind,sys;

    public void fetchData(){
        response =  Unirest.get("http://api.openweathermap.org/data/2.5/weather?q=Amiens&appid=c4e0cdb5b1efe60c192917ea9467abab&units=metric").asJson();

        if(response.isSuccess())
        {
            JSONObject responsejson = response.getBody().getObject();
            // JSONArray r = responsejson.getJSONArray("weather");
            main =  responsejson.getJSONObject("main");
            wind = responsejson.getJSONObject("wind");
            sys = responsejson.getJSONObject("sys");
        }
    }

    public double getTemperature()
    {
        return (Double) main.get("temp");
    }

    public double getFeelLike()
    {
        return (Double) main.get("feels_like");
    }

    public double getSpeedOfWind()
    {
        return (Double) wind.get("speed");
    }

    public Date getSunrise()
    {
        return (Timestamp) sys.get("sunrise");
    }

    public Date getSunSet()
    {
        String time = ""+sys.get("sunset");
        Timestamp stamp = new Timestamp(Long.parseLong(time));
        System.out.println(Long.parseLong(time));
        Date date = new Date(Long.parseLong(time));
        System.out.println(date);
        return date;
    }

    public String printTemp()
    {
        fetchData();
        return "Aujourd'hui il fait : "+getTemperature() + " °C " + "avec un ressenti de : " + getFeelLike() + " °C ";
    }

    public String printWind()
    {
        return "Un vent de "+getSpeedOfWind() +" km/h";
    }
}
