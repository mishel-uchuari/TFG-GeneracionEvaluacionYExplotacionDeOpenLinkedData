(ns pipelines.coma)

(use 'clojure.java.io)

(defn quitarComas[datos]
(def s (slurp datos))
(def sr (clojure.string/replace s "." ""))
(spit datos sr)
(def s (slurp datos))
(def sq (clojure.string/replace s "," ""))
(spit datos sq)
(def s (slurp datos))
(def sj (clojure.string/replace s ";" ","))
(spit datos sj)
)