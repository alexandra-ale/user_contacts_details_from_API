package ganea.alexandra.userdetailsapp.api_calls;

import org.json.JSONObject;

public interface OnCallbackEventListener {
    void onSuccessfulResponse(JSONObject response, String url);
    void onFailResponse (JSONObject response, String url);
    void onErrorEvent(String error, String url);
}
