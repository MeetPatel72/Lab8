package temple.edu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;

public class BookDetailActivity extends FragmentActivity {   //this first book detail  calss
    //private  Item item;
    BookDetailFragment fragmentBookDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Item item = (Item) getIntent().getSerializableExtra("item");
        if(savedInstanceState == null){
            fragmentBookDetail = BookDetailFragment.newInstance(item);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.flDetailContainer, fragmentBookDetail);
            ft.commit();

        }
    }

}
