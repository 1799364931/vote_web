package com.example.database_system.pojo.dto;

import java.util.UUID;

public class OptionResourceDto {

    private UUID optionId;
    private String resourceUrl;

    public OptionResourceDto(UUID optionId, String resourceUrl) {
        this.optionId = optionId;
        this.resourceUrl = resourceUrl;
    }

    public UUID getOptionId() {
        return optionId;
    }

    public void setOptionId(UUID optionId) {
        this.optionId = optionId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
