(ns taia-server.utils)

(declare accumulate-value accumulate-list accumulate-map row-from-list row-from-map)

(defn accumulate-value [acc path value]
  (conj acc (conj path value)))



(defn accumulate-map [acc-fn acc path value]
  (loop [kv value, a acc, path path]
    (if (empty? kv)
      a
      ;;else
      (let [[k v] (first kv)
            tail (rest kv)]
      (recur tail (accumulate-list acc-fn a (conj path k) v) path)
      ))))



(defn accumulate-list [acc-fn acc path value]
  (loop [coll value, a acc, p path]
    (if (empty? coll)
      a
      ;;else
      (let [head (first coll)
            tail (rest coll)]
        (recur tail
               (if (map? head)
                 (accumulate-map acc-fn a p head)
                 (acc-fn a p head))
               p))
      )))

;; (accumulate-map accumulate-value [] [] {:a [1 2]})
;; (accumulate-map accumulate-value [] [:user] {:items [:id :name]})
;; (accumulate-value [] [:user] :id)
;; (accumulate-value [] [:user :items :a] :id )
;; (accumulate-list accumulate-value [] [:user] [:id :name :desc {:a [1 {:aa [2]}]}])



(defn row-from-map [acc-fn acc path value what]
  (loop [kv value, a acc, path path, w what]
    (if (empty? kv)
      a
      ;;else
      (let [[k v] (first kv)
            tail (rest kv)

            index (count (:paths acc))
            x (get what index)
            new-path (conj path k x)]
      (recur tail (row-from-list acc-fn a new-path v w) path w)
      ))))



(defn row-from-list [acc-fn acc path value what]
  (loop [coll value, a acc, p path , w what]
    (if (empty? coll)
      a
      ;;else
      (let [head (first coll)
            tail (rest coll)]
        (recur tail
               (if (map? head)
                 (row-from-map acc-fn a p head what)
                 (acc-fn a p head what))
               p
               w))
      )))




(defn row-from-value [acc path value what]
  (let [entity (last path)
        property value
        index (count (:paths acc))
        ]
    ;;only if it contains no nils in path
    (if (nil? (some nil? (conj path value)))
      (-> acc
          (assoc-in [:paths (conj path value)] (get what index))
          (assoc-in (conj path value) (get what index)))
      ;;else
      acc
      )))



(defn transform [coll json]
  (loop [a {}, c coll, j json]
    (if (empty? c)
      a
      ;;else
      (let [head (first c)
            tail (rest c)]
        (recur
          (-> (row-from-map row-from-value a [] j head)
              (dissoc :paths))
          tail
          j)
        ))))



;; (row-from-map row-from-value
;;                 {}
;;                 []
;;                 {:users [
;;                           :id
;;                           :name
;;                           {:items [
;;                                     :item_id
;;                                     :item_name
;;                                     {:images [:url]}]}]}
;;                 [1 "dan" 11 "flipboard" "a.jpg"])


;; {:users
;;  {1
;;   {:items
;;    {
;;      11 {
;;           :images {
;;                     "a.jpg" {:url "a.jpg"}
;;                     },
;;           :item_name "flipboard",
;;           :item_id 11}},
;;    :name "dan",
;;    :id 1}},

;;  :paths {[:users 1 :items 11 :images "a.jpg" :url] "a.jpg", [:users 1 :items 11 :item_name] "flipboard", [:users 1 :items 11 :item_id] 11, [:users 1 :name] "dan", [:users 1 :id] 1}}


