package com.nathanielbennett.tweeter.server.model;

import com.nathanielbennett.tweeter.model.domain.Status;

import java.util.List;

public class PostUpdateBatch {
    private List<String> aliases;
    private StoredStatus status;

    public PostUpdateBatch(StoredStatus status, List<String> aliases) {
        this.status = status;
        this.aliases = aliases;
    }

    public PostUpdateBatch() { }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public StoredStatus getStatus() {
        return status;
    }

    public void setStatus(StoredStatus status) {
        this.status = status;
    }
}
