package temple.edu;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class BookViewPagerFragment extends Fragment {
    View mainView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_book_view_pager, container,false);
        return mainView;

    }
}
