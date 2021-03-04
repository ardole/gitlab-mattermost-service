package fr.ardole.mm.gitlab.api;

public class MMQuery {

    private String text;
    private String channel_id;
    private String channel_name;
    private String command;
    private String response_url;
    private String team_domain;
    private String team_id;
    private String trigger_id;
    private String user_id;
    private String user_name;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTrigger_id() {
        return trigger_id;
    }

    public void setTrigger_id(String trigger_id) {
        this.trigger_id = trigger_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getResponse_url() {
        return response_url;
    }

    public void setResponse_url(String response_url) {
        this.response_url = response_url;
    }

    public String getTeam_domain() {
        return team_domain;
    }

    public void setTeam_domain(String team_domain) {
        this.team_domain = team_domain;
    }

    public String getTeam_id() {
        return team_id;
    }

    public void setTeam_id(String team_id) {
        this.team_id = team_id;
    }

    @Override
    public String toString() {
        return "Query{" +
                "text='" + text + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", channel_name='" + channel_name + '\'' +
                ", command='" + command + '\'' +
                ", response_url='" + response_url + '\'' +
                ", team_domain='" + team_domain + '\'' +
                ", team_id='" + team_id + '\'' +
                ", trigger_id='" + trigger_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
