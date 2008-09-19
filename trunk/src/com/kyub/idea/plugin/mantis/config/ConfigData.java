package com.kyub.idea.plugin.mantis.config;

public class ConfigData {
    private boolean enabled;
    private String endpointUrl;
    private String username;
    private char[] password;
    private Long projectId;

    public ConfigData() {
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public final String getEndpointUrl() {
        return endpointUrl;
    }

    public final void setEndpointUrl(final String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final char[] getPassword() {
        return password;
    }

    public final void setPassword(final char[] password) {
        this.password = password;
    }

    public final Long getProjectId() {
        return projectId;
    }

    public final void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}