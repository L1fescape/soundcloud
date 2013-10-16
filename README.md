# SoundCloud

A Clojure library for interacting with the [SoundCloud API](http://developers.soundcloud.com/docs/api/reference).


## Usage

```clojure
[soundcloud "0.1.1"]

;; In your ns statement:
(ns my.ns
  (:require [soundcloud.core :refer :all]))


;; define settings without oauth token
(def settings {:client-id "[soundcloud api id]" :client-secret "[soundcloud api secret]"})
;; define settings and generate an oauth token
(def settings (get-auth-token {:client-id "[soundcloud api id]" :client-secret "[soundcloud api secret]" :username "[soundcloud username]" :password "[soundcloud password]"}))

;; get most recent tracks posted to soundcloud
(tracks settings)

;; get info about the logged-in user (note: requires oauth token)
(me settings)
;;=> {:country "United States", :plan "Free", :kind "user", ... }

;; get user id of logged-in user
(get-user-id settings)
;; get user id from username
(get-user-id settings "L1fescape")

;; get tacks of logged-in user
(tracks settings (get-user-id settings))
;; get tracks of any user
(tracks settings (get-user-id settings "L1fescape"))
```

## API

TODO fix this

## License

Copyright © 2013 Andrew Kennedy

Distributed under the Eclipse Public License, the same as Clojure.
