(ns transformaciones.TransformacionGeneral
  (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
    [grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [transformaciones.TransformacionGeneral :refer :all]
    [transformaciones.Predicados :refer :all]   
    )
)
(use 'clojure.java.io)

;Elimina los espacios en blanco en un string
(defn removeBlanks [string]
  (when (seq string)
    (str/replace string "  " " ")
    (str/replace string " " "")
  ))

;Metodo hace split a un string determinado
;Version castellano
(defn makeSplitCast[string]
(def var (str/split (str/replace string " / " "/") #"/"))
(nth var 0)
)

;Metodo hace split a un string determinado
;Version euskera
(defn makeSplitEusk[string]
(def var (str/split (str/replace string " / " "/") #"/"))
(nth var 1)
)

(defn ->s [st] (if st (s st) ""))

;Cambia el formato de la fecha [dd/mm/yyyy ~> dd-mm-yyyy]
(defn organizeDate [date]
  (when (seq date)
    (let [[d m y] (str/split date #"/")]
      (apply str (interpose "-" [y m d] )))))


;Cambia el formato de la fecha [dd/mm/yyyy ~> dd-mm-yyyy]
(defn organizeDateUSA [date]
  (when (seq date)
    (let [[y m d] (str/split date #"/")]
      (apply str (interpose "-" [y m d] )))))

;Crea xsd:DateTime a partir de una fecha determinada
(defn dateLabel
  [date]
  (when  (seq date)
    (let [d date
          ;dt (str d "T" time)
          ]
      (read-string (str "#inst " (pr-str d))))))

(defn dateHourLabel
  "Given a date dd/mm/yyyy and a time hh:mm
  returns a XSDDatetime"
  [date time]
  (when (and (seq date) (seq time))
    (let [d  date
          dt (str d "T" time)]
      (read-string (str "#inst " (pr-str dt))))))

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
          (replace ":" "-")
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
(defn languageSpanish
  [st]
    (io/s st :es))

;Pone etiqueta de idioma a un determinado literal (ingles)
(defn languageEnglish
  [st]
    (io/s st :en))

;Pone etiqueta de idioma a un determinado literal (euskera)
(defn languageVasque
  [st]
    (io/s st :eu))

;Elimina los nodos que estan vacios
(defn missing-data-filter [triples]
                               (remove #(nil? (pr/object %)) triples))