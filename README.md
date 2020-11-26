ep
==

Simple date converter. Prints out the "date thing" converted to:

* An instant
* Epoch days
* Epoch millis

Usage
-----

* From source:

```
clj -Arun <date-thing>
```

Or from an uberjar

* Build from source: `clj -Auberjar`
* Run `java -jar target/ep-1.0.0-standalone.jar <date-thing>`

The "date thing" can be:

* A number of epoch days (any number less than 100000 is assumed to be epoch days). 
* A number of epoch minutes (any number in the range 100000..10000000000 inclusive is assumed to be epoch minutes)
* A number of epoch millis (any number above 10000000000 is assumed to be epoch millis).
* A date or date/time string in [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) form.

Epoch days
----------
As they're referred to a lot less frequently than epoch millis, it's worth clarifying what is meant by "epoch days".

* The epoch days is the number of days since the Epoch start (i.e. 01/01/1970).
* So epoch days 0 = 01/01/1970, 1 = 02/01/1970 and so on.
* This means that when converting from a date thing representing a date/**time**, the epoch day is considered to be inclusive of all times on that day. So, for example, `1970-01-01T05:00:00Z` and `1970-01-01T23:59:59Z` are both epoch day 0 but `1970-01-02T00:00:00Z` is epoch day 1.
