(ns time.core
  (:import (java.time Instant ZoneId ZoneOffset ZonedDateTime LocalDate)
           (java.time.format DateTimeFormatter)
           (java.util Locale Date)))


(defn of [v]
  (let [[year month day hour minute second nano-second timezone] v]
    (ZonedDateTime/of (or year 1970) (or month 1) (or day 1) (or hour 0) (or minute 0) (or second 0) (or nano-second 0) (ZoneId/of (or timezone "UTC")))))


(defn ms [val]
  (cond
    (string? val) (-> (ZonedDateTime/parse val) .toInstant .toEpochMilli)
    (vector? val) (-> (of val) .toInstant .toEpochMilli)
    :else nil))


(defn zoned
  ([ms]
   (zoned ms "UTC"))
  ([ms timezone]
   (let [zone-id (ZoneId/of (or timezone "UTC"))]
     (-> (Instant/ofEpochMilli ms)
         (ZonedDateTime/ofInstant zone-id)))))


(defn fmt
  ([date-time]
   (fmt date-time "MM/dd/YYYY HH:mm:ss"))
  ([date-time pattern]
   (let [formatter (DateTimeFormatter/ofPattern pattern Locale/ENGLISH)]
     (.format date-time formatter))))



(defn date [ms]
  (-> (Instant/ofEpochMilli ms)
      (Date/from)))


(defn now []
  (-> (Instant/now) .toEpochMilli))


(def options {:seconds 1 :second 1
              :minutes 60 :minute 60
              :hour 3600 :hours 3600
              :day 86400 :days 86400
              :week 604800 :weeks 604800
              :year 31557600 :years 31557600})


(defn at
  ([t num k]
   (when (every? some? [t num k])
     (let [val (get options k)]
       (+ t (* val num)))))
  ([num k]
   (at (now) num k)))


(defn forward
  ([t num k]
   (at num k))
  ([num k]
   (forward (now) num k)))


(defn backward
  ([t num k]
   (at (* -1 num) k))
  ([num k]
   (backward (now) num k)))


(defn in-time-zone [t tz]
  (let [hours (-> (zoned t tz)
                  (.getOffset)
                  (.getTotalSeconds)
                  (quot 3600))]
    (at t hours :hours)))


(defn start-of
  ([t k]
   (- t (mod t (get options k))))
  ([k]
   (start-of (now) k)))


(defn end-of
  ([t k]
   (+ (start-of t k) (- (get options k) 1)))
  ([k]
   (end-of (now) k)))


(defn pluralize [s num]
  (if (= num 1)
    (str s)
    (str s "s")))


(defn ago [t]
  (let [d (- (now) t)]
    (cond
      (< d 60) (str d " " (pluralize "second" d) " ago")
      (< d 3600) (let [minutes (quot d 60)]
                   (str minutes " " (pluralize "minute" minutes) " ago"))
      (< d 86400) (let [hours (quot d 3600)]
                    (str hours " " (pluralize "hour" hours) " ago"))
      :else (let [days (quot d 86400)]
              (str days " " (pluralize "day" days) " ago")))))
