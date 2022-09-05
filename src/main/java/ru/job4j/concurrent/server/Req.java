package ru.job4j.concurrent.server;

public class Req {

    private final String httpRequestType;
    private final String poohMode;
    private final String sourceName;
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }
    public static Req of(String content) {
        String tmpDir = content.substring(content.indexOf("/") + 1, content.indexOf(System.lineSeparator()));
        String pooh = tmpDir.substring(0, tmpDir.indexOf("/"));
        String name = tmpDir.substring(tmpDir.indexOf("/") + 1, tmpDir.indexOf(" "));
        String parameter = "";
        if (name.contains("/")) {
            parameter = name.substring(name.indexOf("/") + 1);
            name = name.substring(0, name.indexOf("/"));
        } else if (content.contains("Content-Length")) {
            String[] arr = content.split(System.lineSeparator());
            parameter = arr[arr.length - 1];
        }
        return new Req(content.substring(0, content.indexOf(" ")), pooh, name, parameter);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}