package com.example.database_system.pojo.dto;

import java.util.UUID;

public class OptionResourceDto {


    private String resourceUrl;

    public OptionResourceDto(UUID optionId, String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }
}
