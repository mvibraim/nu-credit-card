(ns nu-credit-card.db
  (:require [datomic.api :as d]
            [nu-credit-card.schemas :as schemas]
            [nu-credit-card.model :as model]))

(def ^:private database-uri "datomic:dev://localhost:4334/nu-credit-card")

(defn- open-connection []
  (d/create-database database-uri)
  (d/connect database-uri))

(def conn (open-connection))

(defn- create-schemas []
  (d/transact conn schemas/client-schema)
  (d/transact conn schemas/creditcard-schema)
  (d/transact conn schemas/purchase-schema))

(create-schemas)

;; (d/delete-database database-uri)

(defn- add-client
  [name cpf email]
  (->> email
       (model/new-client (d/squuid) name cpf)
       (d/transact conn)))

(defn- add-creditcard
  [card-number security-code expiration limit cpf]
  (let [creditcard-id (d/squuid)]
    (d/transact conn (model/new-creditcard creditcard-id card-number security-code expiration limit cpf))
    (d/transact conn [[:db/add
                       [:client/cpf cpf]
                       :client/creditcards
                       [:creditcard/id creditcard-id]]])))

(defn- add-purchase
  [date value institution category card-number cpf]
  (let [purchase-id (d/squuid)]
    (d/transact conn (model/new-purchase purchase-id date value institution category card-number))
    (d/transact conn [[:db/add
                       [:client/cpf cpf]
                       :client/purchases
                       [:purchase/id purchase-id]]])))

(add-client "marcus" "12345" "marcus@nubank.com")
(add-client "leo" "123456" "leo@nubank.com")

(println (add-creditcard "55-00" "753" "03/29" 15000N "12345"))
(add-creditcard "66-00" "753" "03/29" 15000N "123456")

(add-purchase (java.util.Date.) 2000N "casa do ze" "grocery" "55-00" "12345")
(add-purchase (java.util.Date.) 1000N "casa do ze" "grocery" "55-00" "12345")
(add-purchase (java.util.Date.) 200N "casa maneira" "leisure" "55-00" "12345")
(add-purchase (java.util.Date.) 500N "casa maneira" "leisure" "55-00" "12345")

(add-purchase (java.util.Date.) 900N "casa do ze" "grocery" "66-00" "123456")
(add-purchase (java.util.Date.) 10N "casa do ze" "grocery" "66-00" "123456")
(add-purchase (java.util.Date.) 333N "casa maneira" "leisure" "66-00" "123456")
(add-purchase (java.util.Date.) 456N "casa maneira" "leisure" "66-00" "123456")
