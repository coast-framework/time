# time
Clojure time helpers

## Installation

Add it to your [deps.edn](https://clojure.org/guides/deps_and_cli)

```clojure
coast-framework/time {:mvn/version "1.0.0"}
```

## Usage

Everything is centered around epoch/milliseconds and the java.time apis

```clojure
(ns your-project
  (:require [time.core :as time]))

(time/now) ; => prints the current # of seconds since epoch

(time/ms [2019 01 01 0 0 0]) ; => milliseconds from date/time

; here's a more complete example of going from (now) to zoned date time to a formatted string

(-> (time/now)
    (time/zoned)
    (time/fmt)) ; => "01/01/2019 00:00:00"
```

There are more helper functions, but the docs are thin, __use the source, Luke__.

## License

MIT

## Contributing

Do it. I dare you.
