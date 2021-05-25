(ns nu-credit-card.core
  (:require [clojure.pprint :refer [pprint]]
            [nu-credit-card.db :refer [conn]]
            [datomic.api :as d])
  (:import java.time.LocalDate)
  (:gen-class))

;; (defn- sum-purchases-value
;;   [purchases]
;;   (reduce #(+ %1 (:value %2)) 0 purchases))

;; (defn- get-credit-card-by-cpf
;;   [cpf]
;;   (->> creditcards
;;        (filter #(= cpf (:cpf %)))
;;        (first)))

;; (defn- get-purchases-by-card-number
;;   [card-number]
;;   (filter #(= card-number (:card-number %)) purchases))

;; (defn- get-purchases-by-cpf
;;   [cpf]
;;   (->> cpf
;;        (get-credit-card-by-cpf)
;;        (:card-number)
;;        (get-purchases-by-card-number)))

;; (defn- expense-by-category
;;   "Agrupa gastos por categoria"
;;   [purchases]
;;   (reduce #(assoc %1 (first %2) (sum-purchases-value (last %2)))
;;           {}
;;           (group-by :category purchases)))

(defn- is-equals-month?
  [purchase numeric-month]
  (->> (:date purchase)
       (LocalDate/parse)
       (.getMonthValue)
       (= numeric-month)))

(defn- client-invoice
  "Fatura de um cliente"
  [cpf numeric-month]
  (d/q '[:find (sum ?purchase)
         :in $ ?cpf ?numeric-month
         :where
         [?client :client/cpf ?cpf]
         [?client :client/purchases ?purchase]
         [(is-equals-month? ?purchase ?numeric-month)]] (d/db conn) cpf numeric-month))

(defn- purchases-by-value
  "Busca compras pelo valor"
  [cpf value]
  (d/q '[:find (pull ?purchase [*])
         :in $ ?cpf ?value
         :where
         [?client :client/cpf ?cpf]
         [?purchase :purchase/value ?value]
         [?client :client/purchases ?purchase]] (d/db conn) cpf value))

(defn- purchases-by-institution
  "Busca compras pelo estabelecimento"
  [cpf institution]
  (d/q '[:find (pull ?purchase [*])
         :in $ ?cpf ?institution
         :where
         [?client :client/cpf ?cpf]
         [?purchase :purchase/institution ?institution]
         [?client :client/purchases ?purchase]] (d/db conn) cpf institution))
