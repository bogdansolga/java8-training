package net.safedata.java8.training.jool;

public class CleanCodingTryCatchHandling {

    public static void main(String[] args) {
        // the recommended way to use try / catch / finally blocks, from a clean coding perspective
        try {
            exceptionCausingCode();
        } catch (final Exception ex) {
            handleException(ex);
        } finally {
            finalOperations();
        }
    }

    private static void exceptionCausingCode() {
        // perform the processing, throwing runtime exceptions if / when needed
    }

    private static void handleException(Exception e) {
        // perform the handling of the caught exceptions; can re-throw runtime exceptions from it
    }

    private static void finalOperations() {
        // perform finally operations, if / when needed
    }
}
