package com.github.nurzico.wsq2img.web;

import com.github.nurzico.wsq2img.service.JsonTransformer;
import com.github.nurzico.wsq2img.service.WsqDecoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;
import static spark.SparkBase.staticFileLocation;

/**
 * @author Nur Alam Zico
 */
public class Server {
    public WsqDecoder decoder;

    public static void main(String[] args) {
        Server server = new Server();
        int port;
        if (null != System.getProperty("server.port"))
            port = Integer.parseInt(System.getProperty("server.port"));
        else port = 8090;
        port(port);
        staticFileLocation("/static");


        get("/", (req, res) -> {
            res.redirect("/index.html");
            return "Hello!";
        });

        post("/process", (req, res) -> {
            String wsqString = req.queryParams("wsq");
            byte[] data = new BASE64Decoder().decodeBuffer(wsqString);
            byte[] result = server.processWSQ(data);
            res.status(200);
            if (result != null) {
                String jpg = new BASE64Encoder().encode(result);
                return jpg;
            }
            return "ERROR";
        }, new JsonTransformer());
    }

    public byte[] processWSQ(byte[] data) {
        try {
            return decoder.decode(data);
        } catch (RuntimeException e) {
            return null;
        }
    }
}
