package com.kyub.idea.plugin.mantis.config;

import org.jdom.Element;

/**
 * Date: 19-set-2006
 */
public final class SerializableConfigData extends ConfigData {
    public SerializableConfigData(ConfigData data) {
        this.setEnabled(data.isEnabled());
        this.setEndpointUrl(data.getEndpointUrl());
        this.setUsername(data.getUsername());
        this.setPassword(data.getPassword());
        this.setProjectId(data.getProjectId());
    }

    public final void save(Element element) {
        writeElement(element, "enabled", Boolean.toString(this.isEnabled()));
        writeElement(element, "EndpointUrl", this.getEndpointUrl());
        writeElement(element, "Username", this.getUsername());
        writeElement(element, "Password", new String(this.getPassword()));
        writeElement(element, "ProjectId", this.getProjectId().toString());
    }

    public SerializableConfigData() {
        super();
    }

    public SerializableConfigData(Element element) {
        String enab = readElement(element, "enabled");
        if (enab != null)
            this.setEnabled(Boolean.valueOf(enab));
        this.setEndpointUrl(readElement(element, "EndpointUrl"));
        this.setUsername(readElement(element, "Username"));
        this.setPassword(readElement(element, "Password").toCharArray());
        String pid = readElement(element, "ProjectId");
        if (pid != null)
            this.setProjectId(Long.decode(pid));
        System.out.println("pid = " + pid);
    }

    private void writeElement(Element element, String name, String value) {
        Element entryElement = new Element(name);
        // Set the boolean value as an attribute in the enable element.
        entryElement = entryElement.setAttribute("value", value);
        // Add our new element to the IDEA element that was passed into this method
        element.addContent(entryElement);
    }

    private String readElement(Element element, String name) {
        Element entry = element.getChild(name);
        // Restore the previously stored value
        return entry == null ? null : entry.getAttributeValue("value");
    }
}
