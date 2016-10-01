package io.roberthernandez.code2040_fellows_2017_app;

/**
 * Created by robert on 10/1/16.
 */

/*
 * Copyright 1999,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


public class ISO8601DateFactory {

    //TODO create the rest of the supporting functions to actually construct an ISO8601 compliant datetime
    private int determineMonth(String month) {
        int val = 0;
        switch (month) {
            case("Jan"):
                val = 1;
                break;
            case("Feb"):
                val = 2;
                break;
            case("Mar"):
                val = 3;
                break;
            case("Apr"):
                val = 4;
                break;
            case("May"):
                val = 5;
                break;
            case("Jun"):
                val = 6;
                break;
            case("Jul"):
                val = 7;
                break;
            case("Aug"):
                val = 8;
                break;
            case("Sep"):
                val = 9;
                break;
            case("Oct"):
                val = 10;
                break;
            case("Nov"):
                val = 11;
                break;
            case("Dec"):
                val = 12;
                break;
        }
       return val;
    }

}

