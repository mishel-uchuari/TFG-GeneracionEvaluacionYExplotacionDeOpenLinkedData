(ns transformaciones.TransformacionesCalidadDelAire
  (:require 
    [clojure.string :as str]
    [grafter.rdf.protocols :as pr]
   ;[grafter.rdf :refer [prefixer s]] 
    [grafter.rdf.io :as io ]
    [grafter.rdf :refer [prefixer]]
    ))

(def base-domain (prefixer "http://opendata.euskadi.eus"))

(def base-graph (prefixer (base-domain "/graph/")))

(def base-estacion (prefixer (base-domain "/estacion/")))

(def base-medicion (prefixer (base-domain "/medicion/")))


(def CO8hAQ "CO-8h-AirQuality")
(def NO2AQ "NO2-AirQuality")
(def PM10AQ "PM10-AirQuality")
(def PM25AQ "PM25-AirQuality")
(def ICAAQ "Ica-Estacion-AirQuality")
(def CO "CO")
(def CO8h "CO8h")
(def Etilbenceno "Etilbenceno")
(def NO "NO")
(def NO2 "NO2")
(def NOX "NOX")
(def Ortoxileno "Ortoxileno")
(def PM10 "PM10")
(def PM25 "PM25")
(def Tolueno "Tolueno")
(def Benceno "Benceno")


(defn pasoEstacion [estacion]
  (def Estacion estacion)
  ) 

(defn base-NO2AQ [a]
  (base-medicion
    (str Estacion "/" a "/" (removeSymbols NO2AQ))))
(defn base-PM10AQ [a] 
  (base-medicion 
    (str Estacion "/" a "/" (removeSymbols PM10AQ))))
(defn base-CO8hAQ [a]
  (base-medicion
    (str Estacion "/" a "/"(removeSymbols CO8hAQ) )))
(defn base-PM25AQ [a]
  (base-medicion
    (str Estacion "/" a "/" (removeSymbols PM25AQ) )))
(defn base-ICAAQ [a]
  (base-medicion
    (str Estacion "/" a "/" (removeSymbols ICAAQ) )))
(defn base-Benceno[a] 
  (base-medicion
    (str Estacion "/" a "/" Benceno)))
(defn base-CO[a]
  (base-medicion
    (str Estacion "/" a "/" CO )))
(defn base-CO8h [a] 
  (base-medicion
    (str Estacion "/" a "/" CO8h )))
(defn base-Etilbenceno[a] 
  (base-medicion
    (str Estacion"/" a "/" Etilbenceno)))
(defn base-NO[a] 
  (base-medicion
    (str Estacion "/" a "/" NO )))
(defn base-NO2[a] 
  (base-medicion
    (str Estacion "/" a "/" NO2 )))
(defn base-NOX[a] 
  (base-medicion
    (str Estacion "/" a "/" NOX )))
(defn base-Ortoxileno[a] 
  (base-medicion
    (str Estacion "/" a "/" Ortoxileno )))
(defn base-PM10[a] 
  (base-medicion
    (str Estacion "/" a "/" PM10)))
(defn base-PM25[a] 
  (base-medicion
    (str Estacion "/" a  "/" PM25)))
(defn base-Tolueno[a] 
  (base-medicion
    (str Estacion "/" a "/" Tolueno)))
(defn base-Location[a] 
  (base-estacion
    (str (removeSymbols a))))
