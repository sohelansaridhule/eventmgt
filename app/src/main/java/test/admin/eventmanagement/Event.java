package test.admin.eventmanagement;

public class Event {

    private String EventName;
    private String EventDate;
    private String EventImg;
    private String EventDetail;
    private String EventCaption;


    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        this.EventName = eventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        this.EventDate = eventDate;
    }

    public String getEventImg() {
        return EventImg;
    }

    public void setEventImg(String eventImg) {
        this.EventImg = eventImg;
    }

    public String getEventDetail() {
        return EventDetail;
    }

    public void setEventDetail(String eventDetail) {
        this.EventDetail = eventDetail;
    }

    public String getEventCaption() {
        return EventCaption;
    }

    public void setEventCaption(String eventCaption) {
        this.EventCaption = eventCaption;
    }
}
