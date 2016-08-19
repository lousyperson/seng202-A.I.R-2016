package seng202.group4.data.parser;

import java.io.BufferedReader;

/**
 * Created by jjg64 on 15/08/16.
 */
public class AirportParser {
    private BufferedReader file;

    AirportParser(BufferedReader file) {
        this.file = file;
    }
}

class AirportFileValidator {
    private BufferedReader file;

    AirportFileValidator(BufferedReader file) {
        this.file = file;
    }
}
