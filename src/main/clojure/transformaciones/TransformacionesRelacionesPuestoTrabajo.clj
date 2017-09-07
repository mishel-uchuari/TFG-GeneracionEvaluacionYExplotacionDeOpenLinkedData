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

(def uriFechaDesc  (base-contrato-pred fechaDesc)) 
(def uriCodPuesto  (base-contrato-pred codPuesto)) 
(def uriDotacion  (base-contrato-pred dotacion)) 
(def uriCodDepto  (base-contrato-pred codDepto)) 
(def uriCodCentroOrg  (base-contrato-pred codCentroOrg)) 
(def uriCentroOrg  (base-contrato-pred centroOrg)) 
(def uriCodCentroDest  (base-contrato-pred codCentroDest)) 
(def uriCentroDestino  (base-contrato-pred centroDestino)) 
(def uriCategoriaRetrib  (base-contrato-pred categoriaRetrib)) 
(def uriPerfilLing  (base-contrato-pred perfilLing)) 

(defn uriGeneralRPuestoTrabajo[dotacion centroDestino codPuesto fechaModificacion] 
  (base-contrato-recurso
    (str dotacion "-" centroDestino "-" codPuesto "-" fechaModificacion)))