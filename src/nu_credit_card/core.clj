(ns nu-credit-card.core
  (:require [clojure.pprint :refer [pprint]])
  (:import (java.time LocalDate))
  (:gen-class))

(defrecord Client [name cpf email])
(defrecord CreditCard [card-number security-code expiry-date limit cpf])
(defrecord Purchase [date value institution category card-number])

(def clients [])
(def credit-cards [])
(def purchases [])

(defn add-client
  [name cpf email]
  (->> email
       (->Client name cpf)
       (conj clients)
       (def clients)))

(defn add-credit-card
  [card-number security-code expiry-date limit cpf]
  (->> cpf
       (->CreditCard card-number security-code expiry-date limit)
       (conj credit-cards)
       (def credit-cards)))

(defn add-purchase
  [date value institution category card-number]
  (->> card-number
       (->Purchase date value institution category)
       (conj purchases)
       (def purchases)))

(defn sum-purchases-value
  [purchases]
  (reduce #(+ %1 (:value %2)) 0 purchases))

(defn get-credit-card-by-cpf
  [cpf]
  (->> credit-cards
       (filter #(= cpf (:cpf %)))
       (first)))

(defn get-purchases-by-card-number
  [card-number]
  (filter #(= card-number (:card-number %)) purchases))

(defn get-purchases-by-cpf
  [cpf]
  (->> cpf
       (get-credit-card-by-cpf)
       (:card-number)
       (get-purchases-by-card-number)))

(defn is-equals-month?
  [purchase, numeric-month]
  (->>
   (:date purchase)
   (LocalDate/parse)
   (.getMonthValue)
   (= numeric-month)))

(defn expense-by-category
  "Agrupa gastos por categoria"
  [purchases]
  (reduce (fn
            [result [category purchases-from-category]]
            (assoc result category (sum-purchases-value purchases-from-category)))
          {}
          (group-by :category purchases)))

(defn client-invoice
  "Fatura de um cliente"
  [cpf numeric-month]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(is-equals-month? %1 numeric-month))
       (sum-purchases-value)))

(defn filter-purchase-by-value
  "Busca compras de um cliente pelo valor"
  [cpf value]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(= value (:value %)))))

(defn filter-purchase-by-institution
  "Busca compras de um cliente pelo estabelecimento"
  [cpf institution]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(= institution (:institution %)))))

(add-client "marcus" "12345" "marcus@nubank.com")
(add-client "leo" "123456" "leo@nubank.com")

(add-credit-card "55-00" "753" "03/29" 15000 "12345")
(add-credit-card "66-00" "753" "03/29" 15000 "123456")

(add-purchase "2020-12-17" 2000 "casa do ze" "grocery" "55-00")
(add-purchase "2020-12-17" 1000 "casa do ze" "grocery" "55-00")
(add-purchase "2020-11-17" 200 "casa maneira" "leisure" "55-00")
(add-purchase "2020-11-17" 500 "casa maneira" "leisure" "55-00")

(add-purchase "2020-12-17" 900 "casa do ze" "grocery" "66-00")
(add-purchase "2020-12-17" 10 "casa do ze" "grocery" "66-00")
(add-purchase "2020-12-17" 333 "casa maneira" "leisure" "66-00")
(add-purchase "2020-12-17" 456 "casa maneira" "leisure" "66-00")

(pprint clients)
(pprint credit-cards)
(pprint purchases)
