(ns soundcloud.core
  (:require [clj-http.client :as client]
            [cheshire.core :refer :all]
            [clojure.string :as str]
            [clojure.string :refer [join]])
  (:import [com.soundcloud.api ApiWrapper Env Request Endpoints Params$Track Http]))

;; Helper function for getting an oauth token
(defn get-auth-token [settings]
  (let [parse-token (fn [token] 
                      (str/replace (str/replace token #"(^[^\']*)+." "") #"'.*" ""))]
    (assoc 
      settings
      :token
      (parse-token (.login 
                     (ApiWrapper. (:client-id settings) (:client-secret settings) nil nil) 
                     (:username settings) (:password settings) (into-array String []))))))


(defn soundcloud [route params]
  (->
    (client/get (str "https://api.soundcloud.com" route ".json") {:query-params params})
    (:body)
    (parse-string true)))
      
(defn tracks 
  ([settings]
   (soundcloud "/tracks" {"client_id" (:client-id settings)} ))
  ([settings & args] 
   (soundcloud (str "/tracks/" (join "/" args) ) {"client_id" (:client-id settings)} )))

(defn users 
  ([settings & args]
     (soundcloud (str "/users/" (join "/" args)) {"client_id" (:client-id settings)} )))

(defn playlists 
  ([settings & args]
     (soundcloud (str "/playlists/" (join "/" args)) {"client_id" (:client-id settings)} )))

(defn me 
  ([settings]
    (soundcloud "/me" {"oauth_token" (:token settings)} ))
  ([settings & args]
    (soundcloud (str "/me/" (join "/" args)) {"oauth_token" (:token settings)} )))

(defn search-users [settings query]
   (soundcloud "/users" (merge {"client_id" (:client-id settings)} query) ))

(defn get-user-id 
  "Get user id of current logged in user or search for user id based on username"
  ([settings]
    (:id (me settings)))
  ([settings username]
    (:id (first (search-users settings {:q username})) )))
