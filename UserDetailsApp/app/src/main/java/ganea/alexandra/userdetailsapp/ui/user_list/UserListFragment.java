package ganea.alexandra.userdetailsapp.ui.user_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import ganea.alexandra.userdetailsapp.R;
import ganea.alexandra.userdetailsapp.api_calls.ApiCalls;
import ganea.alexandra.userdetailsapp.api_calls.OnCallbackEventListener;
import ganea.alexandra.userdetailsapp.api_calls.calls_entities.GetPaginatedUsers;
import ganea.alexandra.userdetailsapp.api_calls.response_entities.ApiGetPaginatedUsers;
import ganea.alexandra.userdetailsapp.ui.MainActivity;

/**
 * A fragment representing a list of Items.
 * <p/>
 */
public class UserListFragment extends Fragment {

    private EndlessRecyclerViewScrollListener scrollListener;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UserListFragment() {
    }

    @SuppressWarnings("unused")
    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_user_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(linearLayoutManager);

//            recyclerView.setAdapter(new UserListRecyclerViewAdapter(DummyContent.ITEMS, getContext()));
            recyclerView.setAdapter(new UserListRecyclerViewAdapter(MainActivity.USER_ITEMS, getContext()));
            scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    // Triggered only when new data needs to be appended to the list
                    // Add whatever code is needed to append new items to the bottom of the list
                    if (page < 3) {
                        loadNextDataFromApi(page);
                    }
                }
            };
            // Adds the scroll listener to RecyclerView
            recyclerView.addOnScrollListener(scrollListener);


            // Append the next page of data into the adapter
            // This method probably sends out a network request and appends new data items to your adapter.

            /*GetPaginatedUsers paginatedUsers = new GetPaginatedUsers();
            paginatedUsers.setPage(((MainActivity)getActivity()).getWhichIsNextPage());
            paginatedUsers.setResults(100);
            paginatedUsers.setSeed("abc");
            ApiCalls.getInstance(getContext().getApplicationContext()).getPaginatedUsersCall(paginatedUsers, mGetUsersListener);*/
            loadNextDataFromApi(((MainActivity) getActivity()).getWhichIsNextPage());
        }
        return view;
    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        ((MainActivity) getActivity()).showHideProgressBar(true);
        GetPaginatedUsers paginatedUsers = new GetPaginatedUsers();
        paginatedUsers.setPage(offset);
        paginatedUsers.setResults(100);
        paginatedUsers.setSeed("abc");
        ApiCalls.getInstance(getContext().getApplicationContext()).getPaginatedUsersCall(paginatedUsers, mGetUsersListener);
    }


    OnCallbackEventListener mGetUsersListener = new OnCallbackEventListener() {
        @Override
        public void onSuccessfulResponse(JSONObject response, String url) {
            Gson gson = new Gson();
            ApiGetPaginatedUsers getUsers = gson.fromJson(response.toString(), new TypeToken<ApiGetPaginatedUsers>() {
            }.getType());
            int posStart = MainActivity.USER_ITEMS.size();
            MainActivity.USER_ITEMS.addAll(getUsers.getUsers());
            recyclerView.getAdapter().notifyItemRangeInserted(posStart, MainActivity.USER_ITEMS.size());

            ((MainActivity) getActivity()).increaseNextPage();
            ((MainActivity) getActivity()).showHideProgressBar(false);

            /*Intent intent = new Intent(MainActivity.this, ManageData.class);
            intent.setAction(ManageData.ACTION_SAVE_CONDIMENTS);
            intent.putExtra(ManageData.EXTRA_PARAM1, "condiments");
            startService(intent);*/
        }

        @Override
        public void onFailResponse(JSONObject response, String url) {
            Log.e(this.getClass().getSimpleName(), "FAIL " + response.toString());
            ((MainActivity) getActivity()).showHideProgressBar(false);
        }

        @Override
        public void onErrorEvent(String error, String url) {
            Log.e(this.getClass().getSimpleName(), "ERROR " + error);
            ((MainActivity) getActivity()).showHideProgressBar(false);
        }
    };
}
