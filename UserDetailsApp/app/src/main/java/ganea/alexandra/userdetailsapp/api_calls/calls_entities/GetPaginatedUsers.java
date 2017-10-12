package ganea.alexandra.userdetailsapp.api_calls.calls_entities;

/**
 * Created by AlexandraAle on 7/20/2017.
 */

public class GetPaginatedUsers {
    private long page;
    private int results;
    private String seed;

    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public int getResults() {
        return results;
    }

    public void setResults(int results) {
        this.results = results;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }
}

