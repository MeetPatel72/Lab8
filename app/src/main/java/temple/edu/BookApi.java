package temple.edu;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/*
    Name:- Meet A. Patel
    Class:- CIS-3515
    Date:- 03/29/2019
    Lab:- Lab8
 */

public class BookApi {

    //the site was given by the professor
    private static String dataUrl="https://kamorris.com/lab/audlib/booksearch.php";
    public  static String searchUrl="https://kamorris.com/lab/audlib/booksearch.php?search=";
    public static ArrayList<Item> books=new ArrayList<>();   //book
    public static ArrayList<Item> search=new ArrayList<>();
    public static void fetchBooks(Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://www.google.com";

    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dataUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("Response",response);
                        books.clear();
                        try {
                            JSONArray result=new JSONArray(response);
                            for(int i=0;i<result.length();i++)
                            {
                                JSONObject object=result.getJSONObject(i);
                                books.add(new Item(object.getInt("book_id"),object.getString("title"),object.getString("author"),object.getInt("published"),object.getString("cover_url")));
                            }
                            MainActivity.viewPagerAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//textView.setText("if that dont work");
            }
        });

    // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public static void searchBook(Context context,String searchword)
    {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url ="http://www.google.com";

    // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, searchUrl+searchword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
// textView.setText("if that dont work");
            }
        });

    // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
