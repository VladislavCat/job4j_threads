package ru.job4j.concurrent.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueService implements Service {
    private final ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
    public static final String POST = "POST";

    @Override
    public Resp process(Req req) {
        return POST.equals(req.httpRequestType()) ? put(req) : get(req);
    }

    private Resp put(Req req) {
        queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
        boolean a = queue.get(req.getSourceName()).add(req.getParam());
        return new Resp("", a ? "200" : "204");
    }

    private Resp get(Req req) {
        var que = queue.get(req.getSourceName());
        return que != null ? new Resp(que.poll(), "200") : new Resp("", "204");
    }
}
