package ru.otus.sua.L12.appSecure.hashing;

public interface HashGenerator {

    public String getHashedText(final String text);

    public boolean isHashedTextMatch(final String text, final String hashedText);
}
