import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class TopGitHubRepoFinder
{
    private String keyword;
    private int count;

    public TopGitHubRepoFinder(String keyword, int count) {
        this.keyword = keyword;
        this.count = count;
    }

    public void execute() throws IOException {
        String reposJson = getRepos();
        printTopRepos(reposJson);
    }

    private String getRepos() throws IOException {
        StringBuilder content = new StringBuilder();
        URL url = new URL("https://api.github.com/search/repositories?q=" + this.keyword +
                "&sort=stars&order=desc");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            content.append(line + "\n");
        }
        bufferedReader.close();
        urlConnection.disconnect();
        return content.toString();
    }

    private void printTopRepos(String json){
        JSONObject o = new JSONObject(json);
        JSONArray items = o.getJSONArray("items");
        for (int i = 0; i < Math.min(count, items.length()); i++) {
            JSONObject currentObject = items.getJSONObject(i);
            System.out.println("REPO NAME: " + currentObject.getString("full_name") + " \nSTARS: " +
                    currentObject.get("stargazers_count"));
        }
    }

    public static void main(String[] args) {
        TopGitHubRepoFinder repoFinder = new TopGitHubRepoFinder("json", 10);
        try {
            repoFinder.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
