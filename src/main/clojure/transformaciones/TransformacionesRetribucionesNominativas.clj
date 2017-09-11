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

(def cargoPublico "cargo-publico")
(def idOrgano "id-organo")
(def organo "organo")
(def idDpto "departamento")
(def idCentroOrganico "id-centro-organico")
(def centroOrganico "centro-organico")

(def uriCargoPublico (contract-predicate-base cargoPublico))
(def uriIdOrgano (contract-predicate-base idOrgano)) 
(def uriIdDpto (contract-predicate-base idDpto)) 
(def uriOrgano (contract-predicate-base organo)) 
(def uriIdCentroOrganico (contract-predicate-base idCentroOrganico)) 
(def uriCentroOrganico (contract-predicate-base centroOrganico)) 

(defn uriGralRNominativas[publicPosition employee department organ currentDate ] 
  (contract-resource-base
    (str (removeSymbols (str/lower-case publicPosition)) "-" (removeSymbols (str/lower-case employee))  "-" (removeSymbols (str/lower-case departament)) "-" (removeSymbols (str/lower-case organ)) "-" currentDate)))

(defn uriGralDpto[dpto] 
  (contract-resource-base
    (str dpto)))

(defn uriGralEmployee[dpto] 
  (contract-resource-base
    (str dpto)))