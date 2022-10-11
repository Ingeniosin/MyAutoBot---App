package me.juan.bot.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import lombok.Data;
import me.juan.bot.configuration.Configuration;

@Data
public class Connection<T> {

    private final String api;
    private final String values;
    private final Class<T> type;

    public Connection(String api, String values, Class<T> type) {
        this.api = api;
        this.values = values;
        this.type = type;
    }

    public ServiceResponse<T> getResponse() {
        try {
            URL url = new URL(Configuration.BASE_URL + "/api/"+api);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
            writer.write("values="+values.replace("'", "\""));
            writer.flush();
            writer.close();
            httpConn.getOutputStream().close();
            boolean isSuccess = httpConn.getResponseCode() / 100 == 2;
            if (!isSuccess) {
                String errorMessage = new Scanner(httpConn.getErrorStream()).useDelimiter("\\A").next();
                throw new IOException(errorMessage);
            }
            Scanner s = new Scanner(httpConn.getInputStream()).useDelimiter("\\A");
            String response = s.hasNext() ? s.next() : "";
            ObjectMapper mapper = new ObjectMapper();
            return new ServiceResponse<>(mapper.readValue(response, type));
        } catch (Exception e) {
            e.printStackTrace();
            return new ServiceResponse<>(e.getMessage());
        }

    }

}
