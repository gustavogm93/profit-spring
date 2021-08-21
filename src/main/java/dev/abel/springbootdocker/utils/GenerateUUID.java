package dev.abel.springbootdocker.utils;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.security.SecureRandom;
import java.util.UUID;

public class GenerateUUID {

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final ObjectIdGenerators.UUIDGenerator generator = new ObjectIdGenerators.UUIDGenerator();

    public GenerateUUID() {
    }

    public synchronized static String generateUniqueId() {
        UUID uuid = generator.generateId(secureRandom);

        return uuid.toString().replaceAll("-", "").toUpperCase();
    }
}
