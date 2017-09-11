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

(def uriFechaDesc  (contract-predicate-base fechaDesc)) 
(def uriCodPuesto  (contract-predicate-base codPuesto)) 
(def uriDotacion  (contract-predicate-base dotacion)) 
(def uriCodDepto  (contract-predicate-base codDepto)) 
(def uriCodCentroOrg  (contract-predicate-base codCentroOrg)) 
(def uriCentroOrg  (contract-predicate-base centroOrg)) 
(def uriCodCentroDest  (contract-predicate-base codCentroDest)) 
(def uriCentroDestino  (contract-predicate-base centroDestino)) 
(def uriCategoriaRetrib  (contract-predicate-base categoriaRetrib)) 
(def uriPerfilLing  (contract-predicate-base perfilLing)) 

(defn uriGeneralRPuestoTrabajo[dotation destinationCenter occupationCod modifiedDate] 
  (contract-resource-base
    (str dotation "-" (str/lower-case destinationCenter) "-" occupationCod "-" modifiedDate)))