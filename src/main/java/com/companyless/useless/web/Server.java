package com.companyless.useless.web;

import com.companyless.useless.service.JsonTransformer;
import com.companyless.useless.service.WsqDecoder;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.staticFileLocation;

/**
 * @author Nur Alam Zico
 */
public class Server {
    public WsqDecoder decoder;

    public static void main(String[] args) {
        Server server = new Server();
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
