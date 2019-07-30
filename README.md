# time
Clojure time helpers

## Installation

Add it to your `deps.edn` file

```clojure
{:deps {coast-framework/time {:git/url "https://github.com/coast-framework/time"
                              :sha "089743d3dac38385bc1e189e394cd171be8a6832"}}}
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

There are more helper functions, but the docs are thin, you can always use the source, Luke.

## License

MIT

## Contributing

Do it. I dare you.
