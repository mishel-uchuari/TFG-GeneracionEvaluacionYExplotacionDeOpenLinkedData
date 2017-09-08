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

(def uriCargoPublico (base-contrato-pred cargoPublico))
(def uriIdOrgano (base-contrato-pred idOrgano)) 
(def uriIdDpto (base-contrato-pred idDpto)) 
(def uriOrgano (base-contrato-pred organo)) 
(def uriIdCentroOrganico (base-contrato-pred idCentroOrganico)) 
(def uriCentroOrganico (base-contrato-pred centroOrganico)) 

(defn uriGralRNominativas[cargoPublico empleado departamento organo fechaActual ] 
  (base-contrato-recurso
    (str (removeSymbols (str/lower-case cargoPublico)) "-" (removeSymbols (str/lower-case empleado))  "-" (removeSymbols (str/lower-case departamento)) "-" (removeSymbols (str/lower-case organo)) "-" fechaActual)))

(defn uriGralDpto[dpto] 
  (base-contrato-recurso
    (str dpto)))

(defn uriGralEmpleado[dpto] 
  (base-contrato-recurso
    (str dpto)))