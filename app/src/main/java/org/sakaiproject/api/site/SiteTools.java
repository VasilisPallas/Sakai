package org.sakaiproject.api.site;

/**
 * Created by vasilis on 1/15/16.
 * a JavaBeans convention class for the site tools data
 */
public class SiteTools {

    private String toolId;
    private int pageOrder;
    private String placementId;
    private String description;
    private String title;
    // it's always null, so the type is unknown
    // private ?? home;


    public String getToolId() {
        return toolId;
    }

    public void setToolId(String toolId) {
        this.toolId = toolId;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }

    public String getPlacementId() {
        return placementId;
    }

    public void setPlacementId(String placementId) {
        this.placementId = placementId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
