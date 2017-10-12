package ganea.alexandra.userdetailsapp.api_calls;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ganea.alexandra.userdetailsapp.api_calls.calls_entities.GetPaginatedUsers;

public class ApiCalls {


    private static ApiCalls instance;
    private RequestQueue requestQueue;

    private ApiCalls(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized ApiCalls getInstance(Context context) {
        if (instance == null) {
            instance = new ApiCalls(context);
        }
        return instance;
    }


    private void getRequest(final String url_ending, final OnCallbackEventListener onCallbackEventListener) {

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url_ending,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(this.getClass().getSimpleName(), "getRequest");

                        try {
                            JSONArray hasError = response.getJSONArray("results");

                            if (hasError != null) {
                                onCallbackEventListener.onSuccessfulResponse(response, url_ending);
                            } else {
                                onCallbackEventListener.onFailResponse(response.getJSONObject("errors"), url_ending);
                            }

                        } catch (JSONException e) {
                            onCallbackEventListener.onErrorEvent(e.toString(), url_ending);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onCallbackEventListener.onErrorEvent(error.toString(), url_ending);
                    }
                }
        );
        getRequest.setTag("GET");
        // add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    static final String GET_USERS_URL = "https://randomuser.me/api/";
    static final String PARAM_PAGE = "?page=";
    static final String PARAM_RESULTS = "&results=";
    static final String PARAM_SEED = "&seed=";


    public void getPaginatedUsersCall(GetPaginatedUsers getUsers, OnCallbackEventListener onCallbackEventListener) {
        String finalUrl = GET_USERS_URL + PARAM_PAGE + getUsers.getPage()
                + PARAM_RESULTS + getUsers.getResults()
                + PARAM_SEED + getUsers.getSeed();
        getRequest(finalUrl, onCallbackEventListener);
    }


}
