(ns nu-credit-card.database
  (:require [datomic.api :refer [create-database connect]]))

(def ^:private database-uri "datomic:dev://localhost:4334/nu-credit-card")

(defn open-connection []
  (create-database database-uri)
  (connect database-uri))

(open-connection)