(ns transformaciones.TransformacionesRelacionesPuestoTrabajo (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
   ;[grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [transformaciones.TransformacionGeneral :refer :all]
    [transformaciones.Predicados :refer :all]      
    [grafter.rdf :refer [prefixer]]
    ))

(use 'clojure.java.io)
;Prefijos
(def fechaDesc "fecha-descarga")
(def codPuesto "cod-puesto")
(def dotacion "dotacion")
(def codDepto "cod-departamento")
(def codCentroOrg "cod-centro-organico")
(def centroOrg "centro-organico")
(def codCentroDest "cod-centro-destino")
(def centroDestino "centro-destino")
(def categoriaRetrib "categoria-retributiva")
(def perfilLing "perfil-linguistico")

(def discharge-date-predicate  (contract-predicate-base fechaDesc)) 
(def occupation-cod-predicate  (contract-predicate-base codPuesto)) 
(def endowment-predicate  (contract-predicate-base dotacion)) 
(def dpto-cod-predicate  (contract-predicate-base codDepto)) 
(def organic-cod-predicate  (contract-predicate-base codCentroOrg)) 
(def organic-center-predicate  (contract-predicate-base centroOrg)) 
(def destination-center-cod-predicate  (contract-predicate-base codCentroDest)) 
(def destination-center-predicate  (contract-predicate-base centroDestino)) 
(def retributive-category-predicate  (contract-predicate-base categoriaRetrib)) 
(def linguistic-profile-predicate  (contract-predicate-base perfilLing)) 
;Prefijo
(defn uriGeneralRPuestoTrabajo[dotation destinationCenter occupationCod modifiedDate] 
  (contract-resource-base
    (str dotation "-" (str/lower-case destinationCenter) "-" occupationCod "-" modifiedDate)))
;Comentario
(def relPuestosTrab-coment (languageSpanish (str "Descripcion contrato trabajo durante el 2017")))