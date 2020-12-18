package com.example.traveldiary;

public interface Urls {
    String API = "api";
    String API_VERSION = "v1";
    String ROOT = "/" + API + "/" + API_VERSION;

    interface Auth {
        String PART = "auth";
        String FULL = ROOT + "/" + PART;
    }

    interface ExpenseTypes {
        String PART = "expensetypes";
        String FULL = ROOT + "/" + PART;
    }

    interface Play {
        String PART = "play";
        String FULL = ROOT + "/" + PART;

        interface Now {
            String PART = "now";
            String FULL = Play.FULL + "/" + PART;
        }

        interface Greeting {
            String PART = "greeting";
            String FULL = Play.FULL + "/" + PART;
        }
    }

    interface Travels {
        String PART = "travels";
        String FULL = ROOT + "/" + PART;
    }

    interface Users {
        String PART = "users";
        String FULL = ROOT + "/" + PART;

        interface Password {
            String PART = "password";
        }
    }
}
