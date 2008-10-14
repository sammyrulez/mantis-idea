package com.kyub.idea.plugin.mantis.config;

import org.jdom.Element;

/**
 * Date: 19-set-2006
 */
public final class SerializableConfigData extends ConfigData {
    public SerializableConfigData(ConfigData data) {
        setEnabled(data.isEnabled());
        setEndpointUrl(data.getEndpointUrl());
        setUsername(data.getUsername());
        setPassword(data.getPassword());
        setProjectId(data.getProjectId());
    }

    public final void save(Element element) {
        writeElement(element, "enabled", Boolean.toString(isEnabled()));
        writeElement(element, "EndpointUrl", getEndpointUrl());
        writeElement(element, "Username", getUsername());
        writeElement(element, "Password", new String(getPassword()));
        if (getProjectId() != null)
            writeElement(element, "ProjectId", getProjectId().toString());
    }

    public SerializableConfigData() {
        super();
    }

    public SerializableConfigData(Element element) {
        String enab = readElement(element, "enabled");
        if (enab != null)
            setEnabled(Boolean.valueOf(enab));
        setEndpointUrl(readElement(element, "EndpointUrl"));
        setUsername(readElement(element, "Username"));
        setPassword(readElement(element, "Password").toCharArray());
        String pid = readElement(element, "ProjectId");
        if (!isEmpty(pid))
            setProjectId(Long.decode(pid));
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

    private static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }
}
