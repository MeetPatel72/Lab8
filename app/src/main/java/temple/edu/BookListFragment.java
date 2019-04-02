package temple.edu;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookListFragment extends Fragment {

    //declaring varables
    private ArrayAdapter<Item> itemsAdapter;
    private SimpleAdapter adapter;
    List<Map<String, String>> data = new ArrayList<Map<String, String>>();
    private ListView listItems;
    private OnItemSelectedListener listener;
    ArrayList<Item> items;
    public interface OnItemSelectedListener {
        public void onItemSelected(Item i);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) activity;
        } else {
            throw new ClassCastException(activity.toString()
                    + " must implement ItemsListFragment.OnItemSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInStanceState){
        super.onCreate(savedInStanceState);
        //creating arraylist fro item fixtures
        items= Item.getItems();
//        itemsAdapter = new ArrayAdapter<Item>(getActivity(),
//                    android.R.layout.simple_list_item_activated_1, items);
        for(int i=0; i<items.size(); i++)
        {
            Map<String, String> datum = new HashMap<String, String>(2);
            datum.put("First Line", items.get(i).getBookTitle());
            datum.put("Second Line",items.get(i).getAuthor());
            data.add(datum);
        }
         adapter = new SimpleAdapter(getActivity(), data,
                R.layout.simplerow,
                new String[] {"First Line", "Second Line" },
                new int[] {R.id.header, R.id.subheader});
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.fragment_book_list, container,
                false);
        // Bind adapter to ListView
        listItems = (ListView) view.findViewById(R.id.justItems);
        listItems.setAdapter(adapter);
        listItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View item, int position,
                                    long rowId) {
                // Retrieve item based on position
                Item i = items.get(position);
                // Fire selected event for item
                listener.onItemSelected(i);
            }
        });
        return view;
    }
    /*
    this will trun on activied-on-click mode. when this mode is on
    list items will be given the activited states when it touched
     When setting CHOICE_MODE_SINGLE, ListView will automatically
      give items the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        listItems.setChoiceMode(
                activateOnItemClick ? ListView.CHOICE_MODE_SINGLE
                        : ListView.CHOICE_MODE_NONE);
    }
}


