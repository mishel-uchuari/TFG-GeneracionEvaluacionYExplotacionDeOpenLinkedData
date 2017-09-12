(ns transformaciones.TransformacionesCalidadDelAire
  (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
   ;[grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [transformaciones.TransformacionGeneral :refer :all]
    [transformaciones.Predicados :refer :all]      
    [grafter.rdf :refer [prefixer]]
    ))

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
(def uriCOgen  (predicate-measurement-base CO)) 
(def uriCO8hAirQualitygen (predicate-measurement-base CO8hAQ))
(def uriCO8hmaxgen  (predicate-measurement-base CO8hmax))
(def uriNOgen  (predicate-measurement-base NO))
(def uriNO2gen  (predicate-measurement-base NO2))
(def uriNO2maxgen  (predicate-measurement-base NO2max))
(def uriNO2AirQualitygen  (predicate-measurement-base NO2AQ))
(def uriNOXgen (predicate-measurement-base NOX))
(def uriPM10gen  (predicate-measurement-base PM10))
(def uriPM10AirQualitygen (predicate-measurement-base PM10AQ))
(def uriPM25gen  (predicate-measurement-base PM25))
(def uriPM25AirQualitygen (predicate-measurement-base PM25AQ))
(def uriICAEstaciongen (predicate-measurement-base ICAAQ))
(def uriOrtoxilenogen  (predicate-measurement-base Ortoxileno))
(def uriToluenogen (predicate-measurement-base Tolueno))
(def uriBencenogen (predicate-measurement-base Benceno))
(def uriEtilbencenogen  (predicate-measurement-base Etilbenceno))


;Uri estacion
(defn station-uri[date]
  (station-base
    (str "av-gazteiz-" date)))
;Uris Especificas a la medicion 
(defn base-CO[date]
  (element-base
    (str date "-" CO )))
(defn base-CO8hmax[date]
  (element-base
    (str date "-" CO8hmax )))
(defn base-CO8hAQ [date] 
  (element-base
    (str date "-" CO8hAQ) ))
(defn base-NO[date] 
  (element-base
    (str date "-" NO )))
(defn base-NO2[date] 
  (element-base
    (str date "-" NO2 )))
(defn base-NO2max[date] 
  (element-base
    (str date "-" NO2max )))
(defn base-NO2AQ [date]
  (element-base
    (str date "-" NO2AQ)))
(defn base-NOX[date] 
  (element-base
    (str date "-" NOX )))
(defn base-PM10 [date] 
  (element-base
    (str date "-" PM10)))
(defn base-PM10AQ [date] 
  (element-base
    (str date "-" PM10AQ)))
(defn base-PM25 [date]
  (element-base
    (str date "-" PM25)))
(defn base-PM25AQ [date]
  (element-base
    (str date "-" PM25AQ) ))
(defn base-ICAAQ [date]
  (element-base
    (str date "-" ICAAQ)))
(defn base-Ortoxileno [date]
  (element-base
    (str date "-" Ortoxileno)))
(defn base-Benceno [date]
  (element-base
    (str date "-" Benceno)))
(defn base-Tolueno [date]
  (element-base
    (str date "-" Tolueno)))
(defn base-Etilbenceno [date]
  (element-base
    (str date "-" Etilbenceno)))

;Comentarios Elementos

(def CO-coment (languageSpanish (str "El valor de " CO " en una fecha determinada")))
(def CO8hAQ-coment (languageSpanish (str " La calidad del aire " CO8hmax " en una fecha determinada")))
(def CO8hmax-coment (languageSpanish (str "El valor de " CO8hmax " en una fecha determinada")))
(def NO-coment (languageSpanish (str "El valor de " NO " en una fecha determinada")))
(def NO2-coment (languageSpanish (str "El valor de " NO2 " en una fecha determinada")))
(def NO2max-coment (languageSpanish (str "El valor de " NO2max " en una fecha determinada")))
(def NO2AQ-coment (languageSpanish (str " La calidad del aire " NO2AQ " en una fecha determinada")))
(def NOX-coment (languageSpanish (str "El valor de " NOX " en una fecha determinada")))
(def PM10-coment (languageSpanish (str "El valor de " PM10 " en una fecha determinada")))
(def PM10AQ-coment (languageSpanish (str " La calidad del aire " PM10AQ " en una fecha determinada")))
(def PM25-coment (languageSpanish (str "El valor de " PM25 " en una fecha determinada")))
(def PM25AQ-coment (languageSpanish (str  " La calidad del aire " PM25AQ " en una fecha determinada")))
(def ICAAQ-coment (languageSpanish (str  " La calidad del aire " ICAAQ " en una fecha determinada")))
(def Benceno-coment (languageSpanish (str "El valor de " Benceno " en una fecha determinada")))
(def Tolueno-coment (languageSpanish (str "El valor de " Tolueno " en una fecha determinada")))
(def Ortoxileno-coment (languageSpanish (str "El valor de " Ortoxileno " en una fecha determinada")))
(def Etilbenceno-coment (languageSpanish (str "El valor de " Etilbenceno " en una fecha determinada")))