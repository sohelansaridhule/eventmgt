package test.admin.eventmanagement;

public class Event {

    private int id;
    private String EventName;
    private String EventDate;
    private String EventImg;
    private String EventDetail;
    private String EventCaption;
    private String ColgName;

    public String getColgName() {
        return ColgName;
    }

    public void setColgName(String colgName) {
        ColgName = colgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
