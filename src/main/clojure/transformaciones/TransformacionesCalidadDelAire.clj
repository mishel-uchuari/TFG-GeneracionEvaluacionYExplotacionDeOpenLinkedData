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
(def uriCOgen  (base-medicion-pred CO)) 
(def uriCO8hAirQualitygen (base-medicion-pred CO8hAQ))
(def uriCO8hmaxgen  (base-medicion-pred CO8hmax))
(def uriNOgen  (base-medicion-pred NO))
(def uriNO2gen  (base-medicion-pred NO2))
(def uriNO2maxgen  (base-medicion-pred NO2max))
(def uriNO2AirQualitygen  (base-medicion-pred NO2AQ))
(def uriNOXgen (base-medicion-pred NOX))
(def uriPM10gen  (base-medicion-pred PM10))
(def uriPM10AirQualitygen (base-medicion-pred PM10AQ))
(def uriPM25gen  (base-medicion-pred PM25))
(def uriPM25AirQualitygen (base-medicion-pred PM25AQ))
(def uriICAEstaciongen (base-medicion-pred ICAAQ))
(def uriOrtoxilenogen  (base-medicion-pred Ortoxileno))
(def uriToluenogen (base-medicion-pred Tolueno))
(def uriBencenogen (base-medicion-pred Benceno))
(def uriEtilbencenogen  (base-medicion-pred Etilbenceno))


;Uri estacion
(defn prefixEstacion[pFecha]
  (base-estacion
    (str "-vitoria-gazteiz-" (formatoFecha pFecha))))
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
  (base-elemento-calidad-aire
    (str pFecha "/" Tolueno)))
(defn base-Etilbenceno [pFecha]
  (base-elemento-calidad-aire
    (str pFecha "/" Etilbenceno)))

;Comentarios Elementos

(def CO-coment (idiomaEs (str "El valor de " CO " en una fecha determinada")))
(def CO8hAQ-coment (idiomaEs (str CO8hmax " La calidad del aire en una fecha determinada")))
(def CO8hmax-coment (idiomaEs (str "El valor de " CO8hmax " en una fecha determinada")))
(def NO-coment (idiomaEs (str "El valor de " NO " en una fecha determinada")))
(def NO2-coment (idiomaEs (str "El valor de " NO2 " en una fecha determinada")))
(def NO2max-coment (idiomaEs (str "El valor de " NO2max " en una fecha determinada")))
(def NO2AQ-coment (idiomaEs (str NO2AQ " La calidad del aire en una fecha determinada")))
(def NOX-coment (idiomaEs (str "El valor de " NOX " en una fecha determinada")))
(def PM10-coment (idiomaEs (str "El valor de " PM10 " en una fecha determinada")))
(def PM10AQ-coment (idiomaEs (str PM10AQ " La calidad del aire en una fecha determinada")))
(def PM25-coment (idiomaEs (str "El valor de " PM25 " en una fecha determinada")))
(def PM25AQ-coment (idiomaEs (str PM25AQ " La calidad del aire en una fecha determinada")))
(def ICAAQ-coment (idiomaEs (str ICAAQ " La calidad del aire en una fecha determinada")))
(def Benceno-coment (idiomaEs (str "El valor de " Benceno " en una fecha determinada")))
(def Tolueno-coment (idiomaEs (str "El valor de " Tolueno " en una fecha determinada")))
(def Ortoxileno-coment (idiomaEs (str "El valor de " Ortoxileno " en una fecha determinada")))
(def Etilbenceno-coment (idiomaEs (str "El valor de " Etilbenceno " en una fecha determinada")))