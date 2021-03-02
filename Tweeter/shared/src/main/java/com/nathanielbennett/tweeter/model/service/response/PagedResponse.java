package com.nathanielbennett.tweeter.model.service.response;

/**
 * A response that can indicate whether there is more data available from the server.
 */
public class PagedResponse extends TweeterAPIResponse {

    private final boolean hasMorePages;

    /**
     * creates a response indicating that the page request was successful.
     * @param hasMorePages indicator of whether pages are available or not.
     */
    PagedResponse(boolean hasMorePages) {
        super();
        this.hasMorePages = hasMorePages;
    }

    /**
     * creates a response indicating that the paged request failed.
     * @param message an error message indicating why the page request was unsuccessful.
     */
    PagedResponse(String message) {
        super(message);
        hasMorePages = false;
    }

    /**
     * An indicator of whether more data is available from the server. A value of true indicates
     * that the result was limited by a maximum value in the request and an additional request
     * would return additional data.
     *
     * @return true if more data is available; otherwise, false.
     */
    public boolean getHasMorePages() {
        return hasMorePages;
    }
}
