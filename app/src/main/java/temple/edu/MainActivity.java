package temple.edu;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
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

import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragment.OnItemSelectedListener {
    private boolean isTwoPane = false;
    public static MyViewPagerAdapter viewPagerAdapter;

    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BookApi.fetchBooks(this);
         viewPager = findViewById(R.id.viewPager);  //get the id of the vivepager
        if(viewPager != null){ //check
            viewPagerAdapter=new MyViewPagerAdapter(MainActivity.this, Item.getItems());
            viewPager.setAdapter(viewPagerAdapter);
            findViewById(R.id.btsearch).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    searchBook(((EditText)findViewById(R.id.search)).getText().toString());
                }
            });
        }else{
            determinePaneLayout();
        }

    }
    public  void searchBook(String searchword)
    {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BookApi.searchUrl+searchword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(response!=null && !response.equals(""))
                            viewPager.setCurrentItem(checkItem(response));

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
    private int checkItem(String response)
    {
        try {
            JSONObject jsonObject=(new JSONArray(response)).getJSONObject(0);
            for(int i = 0; i< BookApi.books.size(); i++)
            {
                Item b= BookApi.books.get(i);
                if(jsonObject.getInt("book_id")==b.getId())
                {
                    return i;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"No Result Found",Toast.LENGTH_LONG).show();
        }
        return viewPager.getCurrentItem();
    }
    private void determinePaneLayout(){
        FrameLayout fragmentItemDetail = (FrameLayout) findViewById(R.id.flDetailContainer);
        if(fragmentItemDetail != null){
            isTwoPane = true; //set to true
            BookListFragment fragmentBookList =
                    (BookListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentItemsList);
            fragmentBookList.setActivateOnItemClick(true);
        }
        BookDetailFragment fragmentItem = BookDetailFragment.newInstance(BookApi.books.get(0));
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flDetailContainer, fragmentItem);
        ft.commit();
    }

    @Override
    public void onItemSelected(Item item){
        if(isTwoPane){ //single activity with kist detail
            //replace fram layout with correct detail fragment
            BookDetailFragment fragmentItem = BookDetailFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentItem);
            ft.commit();
        }else{
            Intent i = new Intent(this, BookDetailActivity.class);
            i.putExtra("item", item);
            i.putExtra("onClick", true);
            startActivity(i);
        }
    }
    protected class MyViewPagerAdapter extends PagerAdapter{
        private List<Item> books;
        private Context context;

        MyViewPagerAdapter(Context context, List<Item> books){
            this.context = context;
            this.books = books;
        }
        @Override
        public int getCount(){

            return books.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object){
            return view == object;

        }
        @Override
        public Object instantiateItem(ViewGroup container, int position){
            LayoutInflater inflater = LayoutInflater.from(context);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_book_detail,container,false);
            ImageView cover = layout.findViewById(R.id.cover);
            TextView title = layout.findViewById(R.id.BDtitle);
            TextView body = layout.findViewById(R.id.BDbody);
            title.setText(books.get(position).getTitle());
            body.setText(books.get(position).getAuthor());
            Glide.with(MainActivity.this).load(books.get(position).getCoverURL()).into(cover);
            container.addView(layout);
            return layout;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object){
            container.removeView((View) object);

        }
        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }
    }

}
