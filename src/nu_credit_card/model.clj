(ns nu-credit-card.model)

(defn new-client
  [id name cpf email]
  [#:client{:id id
            :name name
            :cpf cpf
            :email email}])

(defn new-creditcard
  [id card-number security-code expiration limit cpf]
  [#:creditcard{:id id
                :card-number card-number
                :security-code security-code
                :expiration expiration
                :limit limit
                :cpf cpf}])

(defn new-purchase
  [id date value institution category card-number]
  [#:purchase{:id id
              :date date
              :value value
              :institution institution
              :category category
              :card-number card-number}])
