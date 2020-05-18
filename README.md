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

* A number of epoch days.
* A number of epoch millis (it assumes that any date-thing above 30000 is a millis value).
* A date or date/time string in [ISO8601](https://en.wikipedia.org/wiki/ISO_8601) form.
