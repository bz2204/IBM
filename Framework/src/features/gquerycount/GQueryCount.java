package features.gquerycount;

// standard io
import java.util.*;
import java.io.*;
// for internet access
import java.net.*;
// google query return json format object
import org.json.*;
// for matching keywords in the search result page
import java.util.regex.Pattern;
import java.util.regex.Matcher;
// for the variable size array
// import java.util.List

class GQueryCount{
    String query;
    String theKeywords[];
    String queryPagesContent[];
    String resultTitle[];
    String resultURLString[];
    int[] count;
    int estimatedResultCount;
    // The request also includes the userip parameter which provides the end
    // user's IP address. Doing so will help distinguish this legitimate
    // server-side traffic from traffic which doesn't come from an end-user.
    String userIP = "127.0.0.1";
    // According to the documentation here:
    // https://developers.google.com/web-search/docs/reference#_intro_fonje
    // Free user can only have at most 8 returning results in each request,
    // and at most 100 requests per day.
    int maxNumberResultsReturn = 8;
    // However, a search request can return the result from a given index.
    // So if we make the request start from 8 instead 0,
    // then we can get the result from 9-16, so on and so for.
    int searchStartIndexShift = 0;
    
    public GQueryCount (String question, String keys) throws Exception {
        query = URLEncoder.encode(question, "UTF-8");
        theKeywords = keys.split(" ");
        count = new int[theKeywords.length];
    }
    
    public void setStart(int i) {
        searchStartIndexShift = 8 * i;
    }
    
    public void print() {
        System.out.println("Query: "+ query);
        System.out.println("Keywords to be counted: ");
        for (String k : theKeywords) {
            System.out.println(k);
        }
    }
    
    // return some private variables;
    public String[] keys() {
        return theKeywords;
    }
    
    public String keys(int i) {
        return theKeywords[i];
    }
    
    public int[] indCount() {
        return count;
    }
    
    public int indCount(int i) throws Exception {
        return count[i];
    }
    
    public int totalCount() throws Exception {
        int totalCount=0;
        for (int eachCount: count) {
            totalCount += eachCount;
        }
        return totalCount;
    }
    
    public int pageCount() throws Exception {
        return estimatedResultCount;
    }
    
    public String[] url() throws Exception {
        return resultURLString;
    }
    
    public String url(int i) throws Exception {
        return resultURLString[i];
    }
    
    public String[] title() throws Exception {
        return resultTitle;
    }
    
    public String title(int i) throws Exception {
        return resultTitle[i];
    }
    
    public String[] content() throws Exception {
        return queryPagesContent;
    }
    
    public String content(int i) throws Exception {
        return queryPagesContent[i];
    }

    public void query() throws Exception {
        URL url = new URL(
            "https://ajax.googleapis.com/ajax/services/search/web?v=1.0"
            + "&q="+ query + "&userip=" + userIP + "&rsz="+ maxNumberResultsReturn 
            + "&start=" + searchStartIndexShift);
        URLConnection connection = url.openConnection();
        // userIP: the IP of the endUser, to avoid blocking due to automatic access
        connection.addRequestProperty("Referer", userIP);

        String line;
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();
        
        // json: The whole google search query result
        JSONObject json = new JSONObject(builder.toString());
        JSONObject responseData = json.getJSONObject("responseData");
        // count: estimate number of pages matched
        JSONObject responseCursor = responseData.getJSONObject("cursor");
        estimatedResultCount = Integer.parseInt(responseCursor.get("estimatedResultCount").toString());
        // resultList: list of search results available
        JSONArray resultList = responseData.getJSONArray("results");
        JSONObject result;
        // get the search result information: title & URL
        resultTitle = new String[resultList.length()];
        resultURLString = new String[resultList.length()];
        queryPagesContent = new String[resultList.length()];
        count = new int[theKeywords.length];
        
        // now dig into each result page
        for(int i = 0; i < resultList.length(); i++) {
            result = resultList.getJSONObject(i);
            resultTitle[i] = result.get("titleNoFormatting").toString();
            resultURLString[i] = result.get("unescapedUrl").toString();
            // Connect to each result URL, and get the content
            URL resultURL = new URL(resultURLString[i]);
            URLConnection pageConnection = resultURL.openConnection();
            pageConnection.addRequestProperty("Referer", userIP);
            // reuse the google-query accessing objects to access the page
            builder = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(pageConnection.getInputStream()));
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String resultPageString = builder.toString();
            queryPagesContent[i] = resultPageString;
            // Count the keyword occurence in the given page content
            for (int k = 0; k < theKeywords.length; k++) {
                Pattern p = Pattern.compile(theKeywords[k]);
                Matcher m = p.matcher(resultPageString);
                int matchCount = 0;
                while(m.find()) {
                    matchCount++;
                }
                count[k] = count[k] + matchCount;
            }
        }        
    }
}
