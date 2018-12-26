package ru.otus.sua.L12.appSecure.hashing;

public enum AlgorithmSHA {
    SHA256("SHA-256"),
    SHA512("SHA-512");

    private final String name;

    AlgorithmSHA(String name) {
        this.name = name;
    }

    public String getAlgorithmName() {
        return this.name;
    }
}
