package ru.otus.sua.L12.appSecure.hashing;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Stateless
@HashServiceType(HashType.SHA)
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorSHA implements HashGenerator {

    public String algorithmName;

    @Override
    public String getHashedText(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(this.algorithmName);
            byte[] hash = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean isHashedTextMatch(String text, String hashedText) {
        String tempHashedText = getHashedText(text);
        return tempHashedText.equals(hashedText);
    }
}
