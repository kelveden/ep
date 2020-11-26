(ns ep.core
  (:gen-class)
  (:require [java-time :as jt]
            [puget.printer :as puget])
  (:import (java.time ZoneOffset)))

(def ^:private millis-per-day (* 60 60 24 1000))

(defn to-instant
  "Attempts to convert the given x to a date/time. If only a date is specified then 00:00 on that date is
  implied as the time."
  [x]
  (cond
    (jt/instant? x)
    x

    (nil? x)
    nil

    (jt/local-date? x)
    (-> (.atStartOfDay x)
        (jt/instant ZoneOffset/UTC))

    (string? x)
    (try
      (-> x jt/zoned-date-time jt/instant)
      (catch Exception e
        (if (clojure.string/ends-with? x "T00:00Z")
          (throw e)
          (to-instant (str x "T00:00Z")))))

    ; Epoch days
    (< x 100000)
    (jt/instant (* x millis-per-day))

    ; Epoch minutes
    (<= 100000 x 10000000000)
    (jt/instant (* 1000 x))

    :else
    ; Epoch millis
    (jt/instant x)))

(defn to-date-time-string
  [x]
  (some-> x to-instant str))

(defn to-epoch-millis
  "Attempts to convert the given x to a date/time and coerce to millis since epoch. If only a date is specified then
  00:00 on that date is implied as the time."
  [x]
  (some-> x to-instant jt/to-millis-from-epoch))

(defn to-epoch-days
  "Attempts to convert the given x to a date/time and coerce to days since epoch. If only a date is specified then
  00:00 on that date is implied as the time."
  [x]
  (some-> x to-epoch-millis (quot millis-per-day)))

(defn ep
  "Attempts to convert the given x to a date/time and returns a summary. If only a date is specified then
  00:00 on that date is implied as the time."
  ([x]
   {:date-time    (to-date-time-string x)
    :epoch-millis (to-epoch-millis x)
    :epoch-days   (to-epoch-days x)}))

(defn -main [& [x]]
  (let [date-thing (if (clojure.string/blank? x)
                     (jt/instant (System/currentTimeMillis))
                     (try (Long/parseLong x)
                          (catch Exception _ x)))]
    (puget/cprint (ep date-thing))))
