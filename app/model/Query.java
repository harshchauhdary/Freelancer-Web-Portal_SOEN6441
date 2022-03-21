package model;

/**
 * used for storing and passing the query using form factory
 * @author Sahil_40192697
 */
public class Query {
    /**
     * Query to be searched
     */
    private String query;

    /**
     * Getter Method, used to obtain the query of the Query object
     * @return Query to be searched
     */
    public String getQuery() {
        return query;
    }

    /**
     * setter Method, used to set the query of the Query object
     * @param query query to be searched
     */
    public void setQuery(String query) {
        this.query = query;
    }
}
