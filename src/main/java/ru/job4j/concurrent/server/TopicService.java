package ru.job4j.concurrent.server;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import static ru.job4j.concurrent.server.QueueService.POST;

public class TopicService implements Service {
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics
            = new ConcurrentHashMap<>();
    @Override
    public synchronized Resp process(Req req) {
        return POST.equals(req.httpRequestType()) ? put(req) : get(req);
    }

    private Resp put(Req req) {
        ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> topic = topics.get(req.getSourceName());
        for (var clq : topic.values()) {
            clq.add(req.getParam());
        }
        return new Resp("", "200");
    }

    private Resp get(Req req) {
        topics.putIfAbsent(req.getSourceName(),
                new ConcurrentHashMap<>());
        topics.get(req.getSourceName()).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
        String txt = topics.get(req.getSourceName()).get(req.getParam()).poll();
        return txt == null ? new Resp("", "200") : new Resp(txt, "200");
    }
}
