(ns time.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [time.core :as time]))


(deftest ms
  (testing "with a string"
    (is (= 1546300800000 (time/ms "2019-01-01T00:00:00+00"))))

  (testing "with a vector"
    (is (= 1546300800000 (time/ms [2019]))))

  (testing "with empty vector"
    (is (= 0 (time/ms []))))

  (testing "with nil"
    (is (= nil (time/ms nil)))))

(deftest at
  (testing "at with a valid value"
    (is (= 1546300886400 (time/at (time/ms [2019]) 1 :day))))

  (testing "at without a start time"
    (is (= "30 seconds ago" (time/ago (time/at -30 :seconds)))))

  (testing "at with nil"
    (is (= nil (time/at nil nil))))

  (testing "at with one second"
    (is (= 1546300800001 (time/at (time/ms [2019]) 1 :second))))

  (testing "one second from given time"
    (is (= 1546300799999 (time/at (time/ms [2019]) -1 :second)))))

(deftest ago
  (testing "one second ago"
    (is (= "1 second ago" (time/ago (time/at -1 :second)))))

  (testing "ten seconds ago"
    (is (= "10 seconds ago" (time/ago (time/backward 10 :seconds))))))
