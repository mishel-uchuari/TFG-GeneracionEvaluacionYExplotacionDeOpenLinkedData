(ns transformaciones.TransformacionGeneral
  (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
    [grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [AvenidaGasteiz.prefix :refer [base-graph base-domain base-medicion base-estacion]]
    )
)

(use 'clojure.java.io)

;Elimina los espacios en blanco en un string
(defn removeBlanks [string]
  (when (seq string)
    (str/replace string " " "")
  ))

;Metodo hace split a un string determinado
;Version castellano
(defn makeSplitCast[string]
(def var (str/split string #"/"))
(nth var 0)
)

;Metodo hace split a un string determinado
;Version euskera
(defn makeSplitEusk[string]
(def var (str/split string #"/"))
(get var 1)
)

(defn ->s [st] (if st (s st) ""))

;Cambia el formato de la fecha [dd/mm/yyyy ~> dd-mm-yyyy]
(defn organizeDate [date]
  (when (seq date)
    (let [[d m y] (str/split date #"/")]
      (apply str (interpose "-" [y m d] )))))

;Crea xsd:DateTime a partir de una fecha determinada
(defn dateTime
  [date]
  (when  (seq date)
    (let [d date
          ;dt (str d "T" time)
          ]
      (read-string (str "#inst " (pr-str d))))))

;Elimina simbolos "innecesarios"
(defn removeSymbols
  [st]
  (let [replace clojure.string/replace]
    
      (-> (str st)
          clojure.string/trim
          (replace "(" "-")
          (replace ")" "")
          (replace "  " "")
          (replace "," "-")
          (replace "." "")
          (replace " " "-")
          (replace "/" "-")
          (replace "'" "")
          (replace "---" "-")
          (replace "--" "-")
          )
     )
   )

;Convertidor de valor numerico a su correspondiente valor numerico en rdf
(defmulti parseValue class)
(defmethod parseValue :default            [x] x)
(defmethod parseValue nil                 [x] nil)
(defmethod parseValue java.lang.Character [x] (Character/getNumericValue x))
(defmethod parseValue java.lang.String    [x] (if (= "" x)
                                                nil
                                                (if (.contains x ".")
                                                  (Double/parseDouble x)
                                                  (Integer/parseInt x))))

;Pone etiqueta de idioma a un determinado literal (espanol)
(defn langSp
  [st]
    (io/s st :es))

;Pone etiqueta de idioma a un determinado literal (ingles)
(defn langEn
  [st]
    (io/s st :en))

;Pone etiqueta de idioma a un determinado literal (euskera)
(defn langVq
  [st]
    (io/s st :eu))

;Elimina los nodos que estan vacios
(defn missing-data-filter [triples]
                               (remove #(nil? (pr/object %)) triples))

(defn base-date[a] 
    (str "/" a ))

;(defn ->s [st] (if st (s st) ""))

;Cambia comas para formato adecuado del CSV
(defn removeComma[data]
(def s (slurp data))
(def sr (clojure.string/replace s "," "."))
(spit data sr)
(def s (slurp data))
(def sj (clojure.string/replace s ";" ","))
(spit data sj)
)