(ns nu-credit-card.schemas)

(def client-schema [#:db{:ident :client/id
                         :valueType :db.type/uuid
                         :cardinality :db.cardinality/one
                         :unique :db.unique/identity
                         :doc "Client ID"}
                    #:db{:ident :client/name
                         :valueType :db.type/string
                         :cardinality :db.cardinality/one
                         :doc "Client name"}
                    #:db{:ident :client/cpf
                         :valueType :db.type/string
                         :cardinality :db.cardinality/one
                         :unique :db.unique/identity
                         :doc "Client CPF number"}
                    #:db{:ident :client/email
                         :valueType :db.type/string
                         :cardinality :db.cardinality/one
                         :doc "Client email"}])

(def creditcard-schema [#:db{:ident :creditcard/id
                             :valueType :db.type/uuid
                             :cardinality :db.cardinality/one
                             :unique :db.unique/identity
                             :doc "Creditcard ID"}
                        #:db{:ident :creditcard/card-number
                             :valueType :db.type/string
                             :cardinality :db.cardinality/one
                             :unique :db.unique/identity
                             :doc "Creditcard number"}
                        #:db{:ident :creditcard/security-code
                             :valueType :db.type/string
                             :cardinality :db.cardinality/one
                             :doc "Creditcard security code"}
                        #:db{:ident :creditcard/expiration
                             :valueType :db.type/string
                             :cardinality :db.cardinality/one
                             :doc "Creditcard expiry date"}
                        #:db{:ident :creditcard/limit
                             :valueType :db.type/bigint
                             :cardinality :db.cardinality/one
                             :doc "Creditcard limit"}
                        #:db{:ident :creditcard/cpf
                             :valueType :db.type/string
                             :cardinality :db.cardinality/one
                             :doc "Creditcard CPF"}])

(def purchase-schema [#:db{:ident :purchase/id
                           :valueType :db.type/uuid
                           :cardinality :db.cardinality/one
                           :unique :db.unique/identity
                           :doc "Purchase ID"}
                      #:db{:ident :purchase/date
                           :valueType :db.type/instant
                           :cardinality :db.cardinality/one
                           :doc "Purchase date"}
                      #:db{:ident :purchase/value
                           :valueType :db.type/bigint
                           :cardinality :db.cardinality/one
                           :doc "Purchase value"}
                      #:db{:ident :purchase/institution
                           :valueType :db.type/string
                           :cardinality :db.cardinality/one
                           :doc "Purchase institution"}
                      #:db{:ident :purchase/category
                           :valueType :db.type/string
                           :cardinality :db.cardinality/one
                           :doc "Purchase category"}
                      #:db{:ident :purchase/card-number
                           :valueType :db.type/string
                           :cardinality :db.cardinality/one
                           :doc "Purchase card number"}])
