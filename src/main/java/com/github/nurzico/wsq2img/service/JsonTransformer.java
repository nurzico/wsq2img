package com.github.nurzico.wsq2img.service;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * @author Nur Alam Zico
 */
public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();
    @Override
    public String render(Object o) throws Exception {
        return gson.toJson(o);
    }
}