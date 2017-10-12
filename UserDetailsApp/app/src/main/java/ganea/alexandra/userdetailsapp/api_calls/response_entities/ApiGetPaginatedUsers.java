package ganea.alexandra.userdetailsapp.api_calls.response_entities;

import java.util.ArrayList;

import ganea.alexandra.userdetailsapp.entities.ItemUser;

public class ApiGetPaginatedUsers {
    private ArrayList<ItemUser> results;
    private GetUsersInfoCall info;

    public ArrayList<ItemUser> getUsers() {
        return results;
    }

    public void setUsers(ArrayList<ItemUser> users) {
        this.results = users;
    }

    public GetUsersInfoCall getInfo() {
        return info;
    }

    public void setInfo(GetUsersInfoCall info) {
        this.info = info;
    }
}
