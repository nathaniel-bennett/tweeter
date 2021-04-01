package com.nathanielbennett.tweeter.server.model;

import com.nathanielbennett.tweeter.model.domain.Status;

import java.util.List;

public class PostUpdateBatch {
    private List<String> aliases;
    private Status status;

    public PostUpdateBatch(Status status, List<String> aliases) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
