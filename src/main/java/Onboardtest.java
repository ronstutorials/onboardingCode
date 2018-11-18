
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Onboardtest
{
    public static void main(String[] args) {
        String output  = getUrlContents("https://api.github.com/search/repositories?q=google&sort=stars&order=desc");
        parseThisJson(output);

    }

    private static void parseThisJson  (String input){
        JSONObject o = new JSONObject(input);
        JSONObject currentobject;
        JSONArray items = o.getJSONArray("items");
        for(int i = 0; i < 10; i++) {
            currentobject = items.getJSONObject(i);
            System.out.println("REPO NAME: " + currentobject.getString("full_name") + " \nSTARS: " + currentobject.get("stargazers_count"));
        }

    }
    private static String getUrlContents(String theUrl)
    {
        StringBuilder content = new StringBuilder();
        try
        {
            URL url = new URL(theUrl);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}