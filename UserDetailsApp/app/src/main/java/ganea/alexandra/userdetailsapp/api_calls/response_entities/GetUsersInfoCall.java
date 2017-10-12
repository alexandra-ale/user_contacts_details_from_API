package ganea.alexandra.userdetailsapp.api_calls.response_entities;

public class GetUsersInfoCall {
    private Long page;
    private Long results;
    private String seed;
    private String version;

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getResults() {
        return results;
    }

    public void setResults(Long results) {
        this.results = results;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
