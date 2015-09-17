package com.companyless.useless.service;

import org.jnbis.api.Jnbis;

/**
 * @author Nur Alam Zico
 */
public class WsqDecoder {
    public static byte[] decode(byte[] wsq) throws RuntimeException {
        return Jnbis.wsq().decode(wsq).toJpg().asByteArray();
    }
}