package app.techsol.falconnotify;

class FeedbackModel {
    String username, feedback;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public FeedbackModel() {
    }

    public FeedbackModel(String username, String feedback) {
        this.username = username;
        this.feedback = feedback;
    }
}
