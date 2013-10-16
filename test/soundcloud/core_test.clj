(ns soundcloud.core-test
  (:require [clojure.test :refer :all]
            [cheshire.core :refer :all]
            [soundcloud.core :refer :all]))

(def settings (parse-string (slurp "resources/settings.json") true))

(deftest user-id-from-username
  (testing "Get userid from username"
    (is (= 2569442 (get-user-id settings "L1fescape")))))

(deftest get-user-info
  (testing "Get user info"
    (is (= "Andrew Kennedy" (:full_name (users settings 2569442))))))
