package temple.edu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class BookDetailFragment extends Fragment {
    private Item item;
    private boolean searchs;
    TextView bdTitle,bdBody;
    ImageView cover;
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        item = (Item) getArguments().getSerializable("item");
        searchs=(boolean) getArguments().getBoolean("onClick");

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_book_detail, container, false);
        bdTitle = (TextView) view.findViewById(R.id.BDtitle);
         bdBody = (TextView) view.findViewById(R.id.BDbody);
         cover = (ImageView) view.findViewById(R.id.cover);
        bdTitle.setText(item.getTitle());
        bdBody.setText(item.getAuthor());
        Glide.with(this).load(item.getCoverURL()).into(cover);
        if(searchs!=true)
        {
            if(view.findViewById(R.id.btsearch2)!=null)
            view.findViewById(R.id.btsearch2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View views) {
                    EditText search=(EditText) view.findViewById(R.id.search2);
                    searchBook(search.getText().toString());
                }
            });
        }


        return view;
    }
    public  void searchBook(String searchword)
    {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, BookApi.searchUrl+searchword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(response!=null && !response.equals(""))
                        {
                            checkItem(response);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
// textView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    private void checkItem(String response)
    {
        try {
            JSONObject jsonObject=(new JSONArray(response)).getJSONObject(0);
            for(int i = 0; i< BookApi.books.size(); i++)
            {
                Item b= BookApi.books.get(i);
                if(jsonObject.getInt("book_id")==b.getId())
                {
//                    getActivity().onItemSelected();
                    bdTitle.setText(b.getTitle());
                    bdBody.setText(b.getAuthor());
                    Glide.with(this).load(b.getCoverURL()).into(cover);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(),"No Result Found",Toast.LENGTH_LONG).show();
        }
//        return viewPager.getCurrentItem();
    }
    // ItemDetailFragment.newInstance(item)
    public static BookDetailFragment newInstance(Item item) {
        BookDetailFragment frag = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        frag.setArguments(args);
        return frag;
    }
}
