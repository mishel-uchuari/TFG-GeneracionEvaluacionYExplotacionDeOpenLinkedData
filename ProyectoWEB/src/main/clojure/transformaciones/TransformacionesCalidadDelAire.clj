(ns transformaciones.TransformacionesCalidadDelAire
  (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
   ;[grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [grafter.rdf :refer [prefixer]]
    ))

(def base-graph (prefixer (http://opendata.euskadi.eus/catalogo/id/calidad-aire-en-euskadi-2017)))
(def base-medicion (prefixer "http://opendata.euskadi.eus/def/medio-ambiente/medicion/"))
(def base-elemento (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/elemento/"))
(def base-estacion (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/calidad-del-aire/observation/AV-GASTEIZ-"))
;Prefijos
(def prefix-fecha (prefixer "http://purl.org/dc/terms/date"))
(def prefix-localizacion (prefixer "http://www.w3.org/2003/01/geo/wgs84_pos#location"))
(def prefix-unidad-medida (prefixer "http://purl.org/linked-data/sdmx/2009/attribute#unitMeasure"))
(def prefix-valor-observacion (prefixer "http://purl.org/linked-data/sdmx/2009/measure#obsValue"))
(def prefix-miligramo-mcubico (prefixer "http://dd.eionet.europa.eu/vocabulary/uom/concentration/mg.m-3"))
(def prefix-micragramo-mcubico (prefixer "http://dd.eionet.europa.eu/vocabulary/uom/concentration/ug.m-3"))


;Elementos
(def CO "CO")
(def CO8hAQ "CO8hAirQuality")
(def CO8hmax "CO8h")
(def NO "NO")
(def NO2 "NO2")
(def NO2max "NO2max")
(def NO2AQ "NO2AirQuality")
(def NOX "NOX")
(def PM10 "PM10")
(def PM10AQ "PM10AirQuality")
(def PM25 "PM25")
(def PM25AQ "PM25AirQuality")
(def ICAAQ "ICAEstacion")
(def Benceno "Benceno")
(def Ortoxileno "Ortoxileno")
(def Tolueno "Tolueno")
(def Etilbenceno "Etilbenceno")



;Uris Elementos Generales
(def uriCOgen (prefixer (base-medicion CO)))
(def uriCO8hAirQualitygen (prefixer (base-medicion CO8hAQ)))
(def uriCO8hgen (prefixer (base-medicion CO8hmax)))
(def uriNOgen (prefixer (base-medicion NO)))
(def uriNO2gen (prefixer (base-medicion NO2)))
(def uriNO2maxgen (prefixer (base-medicion NO2max)))
(def uriNO2AirQualitygen (prefixer (base-medicion NO2AQ)))
(def uriNOXgen (prefixer (base-medicion NOX)))
(def uriPM10gen (prefixer (base-medicion PM10)))
(def uriPM10AirQualitygen (prefixer (base-medicion PM10AQ)))
(def uriPM25gen (prefixer (base-medicion PM25)))
(def uriPM25AirQualitygen (prefixer (base-medicion PM25AQ)))
(def uriICAEstaciongen (prefixer (base-medicion ICAAQ)))
(def uriOrtoxilenogen (prefixer (base-medicion Ortoxileno)))
(def uriToluenogen (prefixer (base-medicion Tolueno)))
(def uriBencenogen (prefixer (base-medicion Benceno)))
(def uriEtilbencenogen (prefixer (base-medicion Benceno)))


;Uri estacion
(defn prefixEstacion[pFecha]
  (base-elemento
    (str pFecha)))
(def estacionVitoria (prefixer "http://opendata.euskadi.eus/recurso/medio-ambiente/estacion/C040"))
;Uris Especificas a la medicion 
(defn base-CO[pFecha]
  (base-elemento
    (str pFecha "/" CO )))
(defn base-CO8hmax[pFecha]
  (base-elemento
    (str pFecha "/" CO8hmax )))
(defn base-CO8hAQ [pFecha]
  (base-elemento
    (str pFecha "/" CO8hAQ) ))
(defn base-NO[pFecha] 
  (base-elemento
    (str pFecha "/" NO )))
(defn base-NO2[pFecha] 
  (base-elemento
    (str pFecha "/"NO2 )))
(defn base-NO2max[pFecha] 
  (base-elemento
    (str pFecha "/" NO2max )))
(defn base-NO2AQ [pFecha]
  (base-elemento
    (str pFecha "/" NO2AQ)))
(defn base-NOX[pFecha] 
  (base-elemento
    (str pFecha "/" NOX )))
(defn base-PM10 [pFecha] 
  (base-elemento
    (str pFecha "/" PM10)))
(defn base-PM10AQ [pFecha] 
  (base-elemento
    (str pFecha "/" PM10AQ)))
(defn base-PM25 [pFecha]
  (base-elemento
    (str pFecha "/" PM25)))
(defn base-PM25AQ [pFecha]
  (base-elemento
    (str pFecha "/" PM25AQ) ))
(defn base-ICAAQ [pFecha]
  (base-elemento
    (str pFecha "/" ICAAQ)))
(defn base-Ortoxileno [pFecha]
  (base-elemento
    (str pFecha "/" Ortoxileno)))
(defn base-Benceno [pFecha]
  (base-elemento
    (str pFecha "/" Benceno)))
(defn base-Tolueno [pFecha]
  (base-elemento
    (str pFecha "/" Tolueno)))
(defn base-Etilbenceno [pFecha]
  (base-elemento
    (str pFecha "/" Etilbenceno)))

;Comentarios Elementos

(def CO-coment (idiomaEs (str "The value of " CO " in a determinate date")))
(def CO8hAQ-coment (idiomaEs (str CO8h " Air Quality in a determinate date")))
(def CO8hmax-coment (idiomaEs (str "The value of " CO8hmax " in a determinate date")))
(def NO-coment (idiomaEs (str "The value of " NO " in a determinate date")))
(def NO2-coment (idiomaEs (str "The value of " NO2 " in a determinate date")))
(def NO2max-coment (idiomaEs (str "The value of " NO2max " in a determinate date")))
(def NO2AQ-coment (idiomaEs (str NO2AQ " Air Quality in a determinate date")))
(def NOX-coment (idiomaEs (str "The value of " NOX " in a determinate date")))
(def PM10-coment (idiomaEs (str "The value of " PM10 " in a determinate date")))
(def PM10AQ-coment (idiomaEs (str PM10AQ " Air Quality in a determinate date")))
(def PM25-coment (idiomaEs (str "The value of " PM25 " in a determinate date")))
(def PM25AQ-coment (idiomaEs (str PM25AQ " Air Quality in a determinate date")))
(def ICAAQ-coment (idiomaEs (str ICAAQ " Air Quality in a determinate date")))
(def Benceno-coment (idiomaEs (str "The value of " Benceno " in a determinate date")))
(def Tolueno-coment (idiomaEs (str "The value of " Tolueno " in a determinate date")))
(def Ortoxileno-coment (idiomaEs (str "The value of " Ortoxileno " in a determinate date")))
(def Etilbenceno-coment (idiomaEs (str "The value of " Ortoxileno " in a determinate date")))
