(ns transformaciones.TransformacionesRetribucionesNominativas   (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
    [grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [transformaciones.TransformacionGeneral :refer :all]
    [transformaciones.Predicados :refer :all]   
    )
)

(use 'clojure.java.io)
;Prefijos
(def cargoPublico "cargo-publico")
(def idOrgano "id-organo")
(def organo "organo")
(def idDpto "departamento")
(def idCentroOrganico "id-centro-organico")
(def centroOrganico "centro-organico")

(def public-occupation-predicate (contract-predicate-base cargoPublico))
(def organ-id-predicate (contract-predicate-base idOrgano)) 
(def dpto-id-predicate (contract-predicate-base idDpto)) 
(def organ-predicate (contract-predicate-base organo)) 
(def organic-center-id-predicate (contract-predicate-base idCentroOrganico)) 
(def organic-center-predicate (contract-predicate-base centroOrganico)) 

(defn uriGralRNominativas[publicPosition employee department organ currentDate ] 
  (contract-resource-base
    (str (removeSymbols (str/lower-case publicPosition)) "-" (removeSymbols (str/lower-case employee))  "-" (removeSymbols (str/lower-case department)) "-" (removeSymbols (str/lower-case organ)) "-" currentDate)))

(defn uriGralDpto[dpto] 
  (contract-resource-base
    (str (str/lower-case dpto))))

(defn uriGralEmployee[employee] 
  (contract-resource-base
    (str (str/lower-case employee))))

;Comentario
(def retribucionesNom-coment (languageSpanish (str "Retribucion nominativa durante el 2017")))