package com.example.weatherapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.*;

public class Controller {

    @FXML
    private TextField city;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Button getData;


    @FXML
    void initialize() {

        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=ea346e94899e2a34b86489fd7b775f62&units=metric");
                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp_info.setText("temperature: " + obj.getJSONObject("main").getDouble("temp"));
                    temp_max.setText("temperature minimum: " + obj.getJSONObject("main").getDouble("temp_min"));
                    temp_min.setText("temperature maximum: " + obj.getJSONObject("main").getDouble("temp_max"));

                }
            }
        });
    }
    private  String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
            city.setText("Such a city was not found!");
        }
        return content.toString();
    }

}
