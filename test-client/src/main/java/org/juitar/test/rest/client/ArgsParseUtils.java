package org.juitar.test.rest.client;

import javax.ws.rs.HttpMethod;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sha1n
 * Date: 1/30/13
 */
class ArgsParseUtils {

    static Args parse(String... args) {
        Args arguments = new Args();

        List<String> errorMessages = new ArrayList<>();

        try {
            arguments.setUri(extractUri(args, errorMessages));
            arguments.setMethod(extractMethod(args, errorMessages));
            arguments.setThreads(extractThreads(args, errorMessages));
            arguments.setRequests(extractRequests(args, errorMessages));
        } catch (RuntimeException e) {
            errorMessages.add("Exception: " + e.getMessage());
        }
        if (!errorMessages.isEmpty()) {
            printErrors(errorMessages, System.err);
            printUsage(System.err);
            throw new IllegalArgumentException();
        }

        return arguments;
    }

    private static void printErrors(List<String> errorMessages, PrintStream printStream) {
        printStream.println("Failed to parse program arguments:");
        for (String errorMessage : errorMessages) {
            printStream.print("\t");
            printStream.println(errorMessage);
        }

        printStream.println();
    }

    private static int extractRequests(String[] args, List<String> errorMessages) {
        return extractOptionalPositiveInt(args, "r", 1, errorMessages);
    }

    private static int extractThreads(String[] args, List<String> errorMessages) {
        return extractOptionalPositiveInt(args, "t", 1, errorMessages);
    }

    private static int extractOptionalPositiveInt(final String[] args, final String argName, final int defaultValue, final List<String> errorMessages) {
        int threads = defaultValue;
        String errorMessage = "%s - Illegal value for argument [%s]: %s";

        for (int i = 0; i < args.length; i++) {
            if (argName.equals(args[i])) {

                try {
                    threads = Integer.parseInt(args[i + 1]);
                    if (threads <= 0) {
                        errorMessages.add(String.format(errorMessage, argName, "value has to be positive."));
                    }
                    break;
                } catch (NumberFormatException e) {
                    errorMessages.add(String.format(errorMessage, argName, argName, e.getMessage()));
                    break;
                }
            }
        }

        return threads;
    }

    private static String extractMethod(String[] args, List<String> errorMessages) {
        String method = null;
        for (int i = 0; i < args.length; i++) {
            if ("m".equals(args[i])) {
                int valueIndex = i + 1;
                method = args[valueIndex];
                switch (args[valueIndex].toUpperCase()) {
                    case HttpMethod.GET:
                    case HttpMethod.PUT:
                        method = args[valueIndex];
                        break;
                    default:
                        errorMessages.add(String.format("%s - Method name: '%s' is not supported.", "m", args[valueIndex]));
                        break;
                }
            }
        }

        return method;

    }

    private static URI extractUri(String[] args, List<String> errorMessages) {
        URI uri = null;
        for (int i = 0; i < args.length; i++) {
            if ("u".equals(args[i])) {
                try {
                    uri = new URL(args[i + 1]).toURI();
                    break;
                } catch (URISyntaxException | MalformedURLException e) {
                    errorMessages.add(String.format("%s - invalid URL: %s", "u", e.getMessage()));
                    break;
                }
            }
        }

        return uri;
    }

    private static void printUsage(PrintStream printStream) {
        printStream.println("USAGE: u <service URL> m <GET | PUT | POST | DELETE> [t <threads>] [r <requests-per-thread>]");
        printStream.println();
        printStream.println("\tu \t- the URL of the REST service to load.");
        printStream.println("\tm \t- the HTTP method to call.");
        printStream.println("\tt \t- the number of threads to issue requests. This argument is optional and defaults to 1.");
        printStream.println("\tr \t- the number of requests per thread. This argument is optional and defaults to 1.");
        printStream.println();
        printStream.println("  \t*** Currently only GET is implemented with URL and query parameters only ***");
        printStream.println();
    }
}
