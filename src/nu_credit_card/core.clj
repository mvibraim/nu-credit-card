(ns nu-credit-card.core
  (:require [clojure.pprint :refer [pprint]])
  (:import java.time.LocalDate)
  (:gen-class))

(defn- sum-purchases-value
  [purchases]
  (reduce #(+ %1 (:value %2)) 0 purchases))

(defn- get-credit-card-by-cpf
  [cpf]
  (->> creditcards
       (filter #(= cpf (:cpf %)))
       (first)))

(defn- get-purchases-by-card-number
  [card-number]
  (filter #(= card-number (:card-number %)) purchases))

(defn- get-purchases-by-cpf
  [cpf]
  (->> cpf
       (get-credit-card-by-cpf)
       (:card-number)
       (get-purchases-by-card-number)))

(defn- is-equals-month?
  [purchase, numeric-month]
  (->> (:date purchase)
       (LocalDate/parse)
       (.getMonthValue)
       (= numeric-month)))

(defn- expense-by-category
  "Agrupa gastos por categoria"
  [purchases]
  (reduce #(assoc %1 (first %2) (sum-purchases-value (last %2)))
          {}
          (group-by :category purchases)))

(defn- client-invoice
  "Fatura de um cliente"
  [cpf numeric-month]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(is-equals-month? % numeric-month))
       (sum-purchases-value)))

(defn- filter-purchase-by-value
  "Busca compras de um cliente pelo valor"
  [cpf value]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(= value (:value %)))))

(defn- filter-purchase-by-institution
  "Busca compras de um cliente pelo estabelecimento"
  [cpf institution]
  (->> cpf
       (get-purchases-by-cpf)
       (filter #(= institution (:institution %)))))
